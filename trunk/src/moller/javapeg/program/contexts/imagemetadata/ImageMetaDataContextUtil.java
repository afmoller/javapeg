package moller.javapeg.program.contexts.imagemetadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.C;
import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.config.model.repository.RepositoryPaths;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.imagemetadata.ImageMetaDataDataBase;
import moller.javapeg.program.imagemetadata.ImageMetaDataDataBaseHandler;
import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.model.SortedListModel;
import moller.util.io.DirectoryUtil;

import org.xml.sax.SAXException;

public class ImageMetaDataContextUtil {

    public static Set<File> performImageSearch(ImageMetaDataContextSearchParameters imageMetaDataContextSearchParameters) {
        if (containSearchParameters(imageMetaDataContextSearchParameters)) {
            Set<File> searchResult = new HashSet<File>();

            for (String javaPegId : imageMetaDataContextSearchParameters.getJavaPegIdToCategoriesMap().keySet()) {
                searchResult.addAll(doSearch(imageMetaDataContextSearchParameters, javaPegId));
            }

            return searchResult;
        } else {
            Set<String> imagePathsAsStrings = ImagePathAndIndex.getInstance().getImagePaths();
            Set<File> imagePathsAsFiles = new HashSet<File>(imagePathsAsStrings.size());

            for (String imagePathAsString : imagePathsAsStrings) {
                imagePathsAsFiles.add(new File(imagePathAsString));
            }
            return imagePathsAsFiles;
        }
    }

    private static Set<File> doSearch(ImageMetaDataContextSearchParameters imageMetaDataContextSearchParameters, String javaPegId) {

        List<Set<File>> searchResults = new ArrayList<Set<File>>();

        String year = imageMetaDataContextSearchParameters.getYear();
        if(year != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByYear(javaPegId, year));
        }

        String month = imageMetaDataContextSearchParameters.getMonth();
        if(month != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByMonth(javaPegId, month));
        }

        String day = imageMetaDataContextSearchParameters.getDay();
        if(day != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByDay(javaPegId, day));
        }

        String hour = imageMetaDataContextSearchParameters.getHour();
        if(hour != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByHour(javaPegId, hour));
        }

        String minute = imageMetaDataContextSearchParameters.getMinute();
        if(minute != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByMinute(javaPegId, minute));
        }

        String second = imageMetaDataContextSearchParameters.getSecond();
        if(second != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesBySecond(javaPegId, second));
        }

        String apertureValue = imageMetaDataContextSearchParameters.getFNumber();
        if(apertureValue != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByFNumberValue(javaPegId, apertureValue));
        }

        String cameraModel = imageMetaDataContextSearchParameters.getCameraModel();
        if(cameraModel != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByCameraModel(javaPegId, cameraModel));
        }

        String comment = imageMetaDataContextSearchParameters.getComment();
        if(comment != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByComment(javaPegId, comment));
        }

        Categories categories = imageMetaDataContextSearchParameters.getJavaPegIdToCategoriesMap().get(javaPegId);
        if(categories != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByCategory(javaPegId, categories, imageMetaDataContextSearchParameters.getJavaPegToAndCategoriesSearch().get(javaPegId)));
        }

        String imageSize = imageMetaDataContextSearchParameters.getImageSize();
        if(imageSize != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByImageSize(javaPegId, imageSize));
        }

        String iso = imageMetaDataContextSearchParameters.getIso();
        if(iso != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByIso(javaPegId, iso));
        }

        boolean[] ratings = imageMetaDataContextSearchParameters.getRatings();
        if(ratings != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByRating(javaPegId, ratings));
        }

        String exposureTime = imageMetaDataContextSearchParameters.getExposureTime();
        if(exposureTime != null) {
            searchResults.add(ImageMetaDataContext.getInstance().findImagesByExposureTime(javaPegId, exposureTime));
        }
        return compileSearchResult(searchResults);
    }

    public static Set<File> compileSearchResult(List<Set<File>> searchResults) {

        if (searchResults.size() > 1) {
            Set<File> result = new HashSet<File>();

            int indexWithLowestSize = getIndexWithLowestSize(searchResults);

            if (Integer.MAX_VALUE == indexWithLowestSize) {
                return result;
            } else {
                Set<File> limitingSet = searchResults.get(indexWithLowestSize);

                for (File itemInLimitingSet : limitingSet) {
                    if (existInAllSearchResults(itemInLimitingSet, searchResults, indexWithLowestSize)) {
                        result.add(itemInLimitingSet);
                    }
                }
                return result;
            }
        } else {
            return searchResults.get(0);
        }
    }

    private static boolean existInAllSearchResults(File itemToSearchFor, List<Set<File>> searchResults, int excludeSetAtIndex) {
        for (int index = 0; index < searchResults.size(); index++) {
            if (index != excludeSetAtIndex) {
                if (!searchResults.get(index).contains(itemToSearchFor)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method returns the index where the smallest (size) set is stored.
     * If there is no stored in the list then Integer.MAX_VALUE will be
     * returned.
     *
     * @param searchResults is the list that contains sets.
     *
     * @return the index for where the set with the smallest size is stored or
     *         Integer.MAX_VALUE if no set is stored in the list.
     */
    private static int getIndexWithLowestSize(List<Set<File>> searchResults) {
        int lowestSize          = Integer.MAX_VALUE;
        int indexWithLowestSize = Integer.MAX_VALUE;

        for (int index = 0; index < searchResults.size(); index++) {
            if (searchResults.get(index).size() < lowestSize) {
                indexWithLowestSize = index;
            }
        }
        return indexWithLowestSize;
    }

    private static boolean containSearchParameters(ImageMetaDataContextSearchParameters imageMetaDataContextSearchParameters) {

        if (imageMetaDataContextSearchParameters.getYear() != null) {
            return true;
        }
        if (imageMetaDataContextSearchParameters.getMonth() != null) {
            return true;
        }
        if (imageMetaDataContextSearchParameters.getDay() != null) {
            return true;
        }
        if (imageMetaDataContextSearchParameters.getHour() != null) {
            return true;
        }
        if (imageMetaDataContextSearchParameters.getMinute() != null) {
            return true;
        }
        if (imageMetaDataContextSearchParameters.getSecond() != null) {
            return true;
        }
        if (imageMetaDataContextSearchParameters.getFNumber() != null) {
            return true;
        }
        if (imageMetaDataContextSearchParameters.getCameraModel() != null) {
            return true;
        }
        if (imageMetaDataContextSearchParameters.getComment() != null) {
            return true;
        }

        Map<String, Categories> javaPegIdToCategoriesMap = imageMetaDataContextSearchParameters.getJavaPegIdToCategoriesMap();

        if (javaPegIdToCategoriesMap != null) {
            for (Categories categories : javaPegIdToCategoriesMap.values()) {
                if (categories != null) {
                    return true;
                }
            }
        }

        if (imageMetaDataContextSearchParameters.getImageSize() != null) {
            return true;
        }
        if (imageMetaDataContextSearchParameters.getIso() != null) {
            return true;
        }
        if (imageMetaDataContextSearchParameters.getRatings() != null) {
            return true;
        }
        if (imageMetaDataContextSearchParameters.getExposureTime() != null) {
            return true;
        }
        return false;
    }

    /**
     * This method looks up all the values (for instance years) to display in
     * the, selection box for search parameters when the year box is selected.
     *
     * @param integerCollectionWithValuesToFetch is the collection to search
     *        for values to display.
     * @return a sorted set with all found values for a specific
     *         {@link Integer} based search box; years, months, dates, ...
     */
    public static Set<Integer> getAllIntegerValues(Map<String, Map<Integer, Set<Integer>>> integerCollectionWithValuesToFetch) {
        Set<Integer> returnValues = new TreeSet<Integer>();

        for (String javaPegId : integerCollectionWithValuesToFetch.keySet()) {
            returnValues.addAll(integerCollectionWithValuesToFetch.get(javaPegId).keySet());
        }
        return returnValues;
    }

    /**
     * This method looks up all the values (for instance years) to display in
     * the, selection box for search parameters when the FNumber box is selected.
     *
     * @param doubleCollectionWithValuesToFetch is the collection to search
     *        for values to display.
     * @return a sorted set with all found values for a specific
     *         {@link Double} based search box; FNumber
     */
    public static Set<Double> getAllDoubleValues(Map<String, Map<Double, Set<Integer>>> doubleCollectionWithValuesToFetch) {
        Set<Double> returnValues = new TreeSet<Double>();

        for (String javaPegId : doubleCollectionWithValuesToFetch.keySet()) {
            returnValues.addAll(doubleCollectionWithValuesToFetch.get(javaPegId).keySet());
        }
        return returnValues;
    }

    /**
     * This method loads (de-serializes) any available and configured image meta
     * data file into the {@link ImageMetaDataContext}.
     *
     * @param repositoryPaths
     *            contains the paths to all image meta data files that must be
     *            de-serialized.
     * @param automaticallyRemoveNonExistingImagePath
     *            if the path does not longer exist, then it will be
     *            automatically removed from the list of configured repository
     *            paths.
     * @param imageRepositoryListModel
     *            to which model the the {@link ImageRepositoryItem} objects
     *            shall be added.
     * @param logger
     *            is the logger to log to.
     * @return a boolean value indicating whether or not the application needs
     *         to be restarted after the de-serialization of all image meta data
     *         files.
     */
    public static boolean initiateImageMetaDataContext(RepositoryPaths repositoryPaths, boolean automaticallyRemoveNonExistingImagePath, SortedListModel<ImageRepositoryItem> imageRepositoryListModel, Logger logger) {

        if(repositoryPaths != null) {
            for (File repositoryPath : repositoryPaths.getPaths()) {
                ImageRepositoryItem iri = new ImageRepositoryItem();

                iri.setPathStatus(DirectoryUtil.getStatus(repositoryPath));
                iri.setPath(repositoryPath);

                switch (iri.getPathStatus()) {
                case EXISTS:
                    File imageMetaDataDataBaseFile = new File(repositoryPath, C.JAVAPEG_IMAGE_META_NAME);
                    if (imageMetaDataDataBaseFile.exists()) {

                        try {
                            // TODO: check imageMetaDataDataBaseFile against schema
                            ImageMetaDataDataBase imageMetaDataDataBase = ImageMetaDataDataBaseHandler.deserializeImageMetaDataDataBaseFile(imageMetaDataDataBaseFile);
                            String javaPEGId = imageMetaDataDataBase.getJavaPEGId();
                            // TODO: check file consistency:
                            // 1: amount of referenced files
                            // 2: The names of referenced files.

                            ImageMetaDataDataBaseHandler.showCategoryImportDialogIfNeeded(imageMetaDataDataBaseFile, javaPEGId);
                            ImageMetaDataDataBaseHandler.populateImageMetaDataContext(javaPEGId, imageMetaDataDataBase.getImageMetaDataItems());
                            logger.logDEBUG("Image Meta Data File: " + imageMetaDataDataBaseFile.getAbsolutePath() + " deserialized");
                        } catch (ParserConfigurationException pcex) {
                            logger.logERROR("Could not create a DocumentBuilder");
                            logger.logERROR(pcex);
                        } catch (SAXException sex) {
                            logger.logERROR("Could not parse file: " + imageMetaDataDataBaseFile.getAbsolutePath());
                            logger.logERROR(sex);
                        } catch (IOException iox) {
                            logger.logERROR("IO exception occurred when parsing file: " + imageMetaDataDataBaseFile.getAbsolutePath());
                            logger.logERROR(iox);
                        } catch (XPathExpressionException xpee) {
                            logger.logERROR("XPathExpression exception occurred when parsing file: " + imageMetaDataDataBaseFile.getAbsolutePath());
                            logger.logERROR(xpee);
                        }
                        imageRepositoryListModel.add(iri);
                    } else {
                        if (!automaticallyRemoveNonExistingImagePath) {
                            imageRepositoryListModel.add(iri);
                        }
                    }
                    break;
                case NOT_AVAILABLE:
                    imageRepositoryListModel.add(iri);
                    break;
                case DOES_NOT_EXIST:
                    if (!automaticallyRemoveNonExistingImagePath) {
                        imageRepositoryListModel.add(iri);
                    }
                    break;
                }
            }
        }
        return ApplicationContext.getInstance().isRestartNeeded();
    }
}
