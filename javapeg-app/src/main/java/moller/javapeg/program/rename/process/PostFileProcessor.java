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
package moller.javapeg.program.rename.process;

import moller.javapeg.program.C;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.progress.RenameProcess;
import moller.javapeg.program.rename.FileAndType;
import moller.javapeg.program.rename.RenameProcessContext;
import moller.util.hash.MD5Calculator;
import moller.util.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PostFileProcessor {

    /**
     * The static singleton instance of this class.
     */
    private static PostFileProcessor instance;

    /**
     * Private constructor.
     */
    private PostFileProcessor() {
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static PostFileProcessor getInstance() {
        if (instance != null)
            return instance;
        synchronized (PostFileProcessor.class) {
            if (instance == null) {
                instance = new PostFileProcessor();
            }
            return instance;
        }
    }

    public boolean process(RenameProcess rp) {

        Language lang = Language.getInstance();

        rp.setRenameProgressMessages(lang.get("rename.PostFileProcessor.integrityCheck.checking"));

        boolean copySucess = true;

        RenameProcessContext rpc = RenameProcessContext.getInstance();

        Map<File, FileAndType> allNonJPEGFileNameMappings = rpc.getAllNonJPEGFileNameMappings();

        for (File sourceFile : allNonJPEGFileNameMappings.keySet()) {
            FileAndType fat = allNonJPEGFileNameMappings.get(sourceFile);
            File destFile = fat.getFile();

            if (sourceFile.getName().equals(C.JAVAPEG_IMAGE_META_NAME)) {
                if (notJavaPegImageMetaFileEqual(destFile)) {
                    copySucess = false;
                }
            } else {
                if (notEqual(sourceFile, destFile)) {
                    copySucess = false;
                }
            }

            if (copySucess) {
                rp.setLogMessage(lang.get("rename.PostFileProcessor.renameFromLabel") + " " + sourceFile.getName() + " " + lang.get("rename.PostFileProcessor.copiedOK") + " " + destFile.getAbsolutePath());
            } else {
                rp.setLogMessage(lang.get("rename.PostFileProcessor.renameFromLabel") + " " + sourceFile.getName() + " " + lang.get("rename.PostFileProcessor.copiedWithError") + " " + destFile.getAbsolutePath());
            }
        }

        Map<File, File> allJPEGFileNameMappings =  rpc.getAllJPEGFileNameMappings();

        for (File sourceFile : allJPEGFileNameMappings.keySet()) {
            File destFile = allJPEGFileNameMappings.get(sourceFile);

            if (notEqual(sourceFile, destFile)) {
                copySucess = false;
                rp.setLogMessage(lang.get("rename.PostFileProcessor.renameFromLabel") + " " + sourceFile.getName() + " " + lang.get("rename.PostFileProcessor.renamedWithError") + " " + destFile.getName());
            } else {
                rp.setLogMessage(lang.get("rename.PostFileProcessor.renameFromLabel") + " " + sourceFile.getName() + " " + lang.get("rename.PostFileProcessor.renamedOK") + " " + destFile.getName());
            }
        }
        return copySucess;
    }

    private boolean notEqual(File sourceFile, File destFile) {
        Logger logger = Logger.getInstance();

        String sourceHash = MD5Calculator.calculate(sourceFile);
        String destHash   = MD5Calculator.calculate(destFile);

        logger.logDEBUG(sourceHash + " = Hash value for file: " + sourceFile.getAbsolutePath());
        logger.logDEBUG(destHash + " = Hash value for file: " + destFile.getAbsolutePath());

        if(!destHash.equals(sourceHash)) {
            Logger.getInstance().logERROR("File: " + sourceFile.getAbsolutePath() + " was not correctly copied to: " + destFile.getAbsolutePath());
            return true;
        }
        return false;
    }

    private boolean notJavaPegImageMetaFileEqual(File destFile) {
        try {
            return !FileUtil.readFromFile(destFile).equals(RenameProcessContext.getInstance().getJavaPegImageMetaFileContent());
        } catch (IOException iox) {
            Logger logger = Logger.getInstance();
            logger.logERROR("Could not read from file: " + destFile.getAbsolutePath() + " see stacktrace below for details");
            logger.logERROR(iox);
            return false;
        }
    }
}
