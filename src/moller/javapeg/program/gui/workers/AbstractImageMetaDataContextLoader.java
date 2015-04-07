/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program.gui.workers;

import moller.javapeg.program.C;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.repository.RepositoryPaths;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.imagemetadata.ImageMetaDataDataBase;
import moller.javapeg.program.imagemetadata.handler.ImageMetaDataDataBaseHandler;
import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.javapeg.program.logger.Logger;
import moller.util.io.DirectoryUtil;
import moller.util.io.Status;
import moller.util.result.ResultObject;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class AbstractImageMetaDataContextLoader extends SwingWorker<ResultObject<String[]>, ImageRepositoryItem> {

    private static final Configuration configuration = Config.getInstance().get();
    private static final Logger logger = Logger.getInstance();

    @Override
    protected ResultObject<String[]> doInBackground() throws Exception {

        StringBuilder inconsistenceErrorMessage = new StringBuilder();
        StringBuilder corruptErrorMessage = new StringBuilder();

        RepositoryPaths repositoryPaths = configuration.getRepository().getPaths();

        if(repositoryPaths != null) {

            List<File> repositoryPathsList = repositoryPaths.getPaths();
            double numberOfPaths = repositoryPathsList.size();
            double progress = 1;

            for (int index = 0; index < numberOfPaths; index++) {
                ImageRepositoryItem iri = new ImageRepositoryItem();

                iri.setPathStatus(DirectoryUtil.getStatus(repositoryPathsList.get(index)));
                iri.setPath(repositoryPathsList.get(index));

                switch (iri.getPathStatus()) {
                case EXISTS:
                    File imageMetaDataDataBaseFile = new File(repositoryPathsList.get(index), C.JAVAPEG_IMAGE_META_NAME);
                    if (imageMetaDataDataBaseFile.exists()) {

                        try {
                            logger.logDEBUG("Image Meta Data File: " + imageMetaDataDataBaseFile.getAbsolutePath() + " START CHECK against schema");
                            ResultObject<Exception> metaDataBaseValidation = ImageMetaDataDataBaseHandler.isMetaDataBaseValid(imageMetaDataDataBaseFile);
                            logger.logDEBUG("Image Meta Data File: " + imageMetaDataDataBaseFile.getAbsolutePath() + " FINISHED CHECK against schema");

                            if (!metaDataBaseValidation.getResult()) {
                                inconsistenceErrorMessage.append(imageMetaDataDataBaseFile);
                                inconsistenceErrorMessage.append(C.LS);
                                logger.logERROR("The meta data base file: " + imageMetaDataDataBaseFile + " is corrupt");
                                logger.logERROR(metaDataBaseValidation.getObject());
                                iri.setPathStatus(Status.CORRUPT);
                            } else {
                                ImageMetaDataDataBase imageMetaDataDataBase = ImageMetaDataDataBaseHandler.deserializeImageMetaDataDataBaseFile(imageMetaDataDataBaseFile);
                                String javaPEGId = imageMetaDataDataBase.getJavaPEGId();

                                logger.logDEBUG("Image Meta Data File: " + imageMetaDataDataBaseFile.getAbsolutePath() + " START CHECK consistency");
                                if (ImageMetaDataDataBaseHandler.isConsistent(imageMetaDataDataBase, repositoryPathsList.get(index))) {
                                    logger.logDEBUG("Image Meta Data File: " + imageMetaDataDataBaseFile.getAbsolutePath() + " FINISHED CHECK consistency");
                                    ImageMetaDataDataBaseHandler.showCategoryImportDialogIfNeeded(imageMetaDataDataBaseFile, javaPEGId);
                                    ImageMetaDataDataBaseHandler.populateImageMetaDataContext(javaPEGId, imageMetaDataDataBase.getImageMetaDataItems());
                                    logger.logDEBUG("Image Meta Data File: " + imageMetaDataDataBaseFile.getAbsolutePath() + " deserialized");
                                } else {
                                    inconsistenceErrorMessage.append(imageMetaDataDataBaseFile);
                                    inconsistenceErrorMessage.append(C.LS);
                                    logger.logERROR("The meta data base file: " + imageMetaDataDataBaseFile + " is inconsistent with the content in directory: " + repositoryPathsList.get(index));
                                    iri.setPathStatus(Status.INCONSISTENT);
                                }
                            }
                        } catch (ParserConfigurationException pcex) {
                            logger.logERROR("Could not create a DocumentBuilder");
                            logger.logERROR(pcex);
                        } catch (SAXException sex) {
                            logger.logERROR("Could not parse file: " + imageMetaDataDataBaseFile.getAbsolutePath());
                            logger.logERROR(sex);
                        } catch (IOException iox) {
                            logger.logERROR("IO exception occurred when parsing file: " + imageMetaDataDataBaseFile.getAbsolutePath());
                            logger.logERROR(iox);
                        }
                        publish(iri);
                        setProgress((int)(progress++ / numberOfPaths * 100));
                    } else {
                        publish(iri);
                        setProgress((int)(progress++ / numberOfPaths * 100));
                    }
                    break;
                case NOT_AVAILABLE:
                    publish(iri);
                    setProgress((int)(progress++ / numberOfPaths * 100));
                    break;
                case DOES_NOT_EXIST:
                    publish(iri);
                    setProgress((int)(progress++ / numberOfPaths * 100));
                    break;
                case INCONSISTENT:
                    // Do nothing here, since this status can only be set after
                    // an validation process.
                    break;
                default:
                    break;
                }
            }
        }

        String[] errorMessages = new String[2];

        if (!inconsistenceErrorMessage.toString().isEmpty()) {
            errorMessages[0] = inconsistenceErrorMessage.toString();
        }

        if (!corruptErrorMessage.toString().isEmpty()) {
            errorMessages[1] = corruptErrorMessage.toString();
        }

        ResultObject<String[]> result = new ResultObject<String[]>(ApplicationContext.getInstance().isRestartNeeded(), errorMessages);
        return result;
    }

}
