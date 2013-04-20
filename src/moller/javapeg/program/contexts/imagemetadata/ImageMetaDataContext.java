package moller.javapeg.program.contexts.imagemetadata;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.datatype.ExposureTime;
import moller.javapeg.program.datatype.ImageSize;
import moller.javapeg.program.enumerations.MetaDataValueFieldName;


public class ImageMetaDataContext {

    /**
     * The static singleton instance of this class.
     */
    private static ImageMetaDataContext instance;

    private final Map<String, ImageSize> imageSizeStringImageSizeMappings;
    private final Map<String, ExposureTime> exposureTimeStringExposureTimeMappings;

    /**
     * Image Exif Meta Data
     */
    private final Map<String, Map<Double, Set<Integer>>> javaPegIdToFNumberValues;
    private final Map<String, Map<Integer, Set<Integer>>> javaPegIdToYearValues;
    private final Map<String, Map<Integer, Set<Integer>>> javaPegIdToMonthValues;
    private final Map<String, Map<Integer, Set<Integer>>> javaPegIdToDateValues;
    private final Map<String, Map<Integer, Set<Integer>>> javaPegIdToHourValues;
    private final Map<String, Map<Integer, Set<Integer>>> javaPegIdToMinuteValues;
    private final Map<String, Map<Integer, Set<Integer>>> javaPegIdToSecondValues;
    private final Map<String, Map<Integer, Set<Integer>>> javaPegIdToIsoValues;
    private final Map<String, Map<Integer, Set<Integer>>> javaPegIdToRatings;
    private final Map<String, Map<String, Set<Integer>>> javaPegIdToCameraModels;
    private final Map<String, Map<String, Set<Integer>>> javaPegIdToImageSizeValues;
    private final Map<String, Map<String, Set<Integer>>> javaPegIdToExposureTimeValues;
    private final Map<String, Map<String, Set<Integer>>> javaPegIdToCategories;
    private final Map<String, Map<String, Set<Integer>>> javaPegIdToComments;

    /**
     * Private constructor.
     */
    private ImageMetaDataContext() {
        exposureTimeStringExposureTimeMappings = new HashMap<String, ExposureTime>();
        imageSizeStringImageSizeMappings = new HashMap<String, ImageSize>();

        javaPegIdToCameraModels = new HashMap<String, Map<String, Set<Integer>>>();
        javaPegIdToYearValues = new HashMap<String, Map<Integer, Set<Integer>>>();
        javaPegIdToMonthValues = new HashMap<String, Map<Integer, Set<Integer>>>();
        javaPegIdToDateValues = new HashMap<String, Map<Integer, Set<Integer>>>();
        javaPegIdToHourValues = new HashMap<String, Map<Integer, Set<Integer>>>();
        javaPegIdToMinuteValues = new HashMap<String, Map<Integer, Set<Integer>>>();
        javaPegIdToSecondValues = new HashMap<String, Map<Integer, Set<Integer>>>();
        javaPegIdToIsoValues = new HashMap<String, Map<Integer, Set<Integer>>>();
        javaPegIdToImageSizeValues = new HashMap<String, Map<String, Set<Integer>>>();
        javaPegIdToExposureTimeValues = new HashMap<String, Map<String, Set<Integer>>>();
        javaPegIdToFNumberValues = new HashMap<String, Map<Double, Set<Integer>>>();
        javaPegIdToRatings = new HashMap<String, Map<Integer, Set<Integer>>>();
        javaPegIdToCategories = new HashMap<String, Map<String, Set<Integer>>>();
        javaPegIdToComments = new HashMap<String, Map<String, Set<Integer>>>();
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static ImageMetaDataContext getInstance() {
        if (instance != null)
            return instance;
        synchronized (ImageMetaDataContext.class) {
            if (instance == null) {
                instance = new ImageMetaDataContext();
            }
            return instance;
        }
    }

    public void addCameraModel(String javaPegIdValue, String cameraModel, String imagePath) {
        if (!javaPegIdToCameraModels.containsKey(javaPegIdValue)) {
            Map<String, Set<Integer>> cameraModelToIndex = new HashMap<String, Set<Integer>>();

            cameraModelToIndex.put(cameraModel, new HashSet<Integer>());
            javaPegIdToCameraModels.put(javaPegIdValue, cameraModelToIndex);
        }

        if (!javaPegIdToCameraModels.get(javaPegIdValue).containsKey(cameraModel)) {
            javaPegIdToCameraModels.get(javaPegIdValue).put(cameraModel, new HashSet<Integer>());
        }

        javaPegIdToCameraModels.get(javaPegIdValue).get(cameraModel).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
    }

    public void addDateTime(String javaPegIdValue, Date dateTime, String imagePath) {
        addToMap(javaPegIdToYearValues, Integer.parseInt(new SimpleDateFormat("yyyy").format(dateTime)), javaPegIdValue, imagePath);
        addToMap(javaPegIdToMonthValues, Integer.parseInt(new SimpleDateFormat("MM").format(dateTime)), javaPegIdValue, imagePath);
        addToMap(javaPegIdToDateValues, Integer.parseInt(new SimpleDateFormat("dd").format(dateTime)), javaPegIdValue, imagePath);
        addToMap(javaPegIdToHourValues, Integer.parseInt(new SimpleDateFormat("HH").format(dateTime)), javaPegIdValue, imagePath);
        addToMap(javaPegIdToMinuteValues, Integer.parseInt(new SimpleDateFormat("mm").format(dateTime)), javaPegIdValue, imagePath);
        addToMap(javaPegIdToSecondValues, Integer.parseInt(new SimpleDateFormat("ss").format(dateTime)), javaPegIdValue, imagePath);
    }

    private void addToMap(Map<String, Map<Integer, Set<Integer>>> mapToAddTo, Integer value, String javaPegIdValue, String imagePath) {
        if (!mapToAddTo.containsKey(javaPegIdValue)) {
            Map<Integer, Set<Integer>> valuesToIndex = new HashMap<Integer, Set<Integer>>();

            valuesToIndex.put(value, new HashSet<Integer>());
            mapToAddTo.put(javaPegIdValue, valuesToIndex);
        }

        if (!mapToAddTo.get(javaPegIdValue).containsKey(value)) {
            mapToAddTo.get(javaPegIdValue).put(value, new HashSet<Integer>());
        }
        mapToAddTo.get(javaPegIdValue).get(value).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
    }

    public void addIso(String javaPegIdValue, Integer iso, String imagePath) {
        if (iso != -1) {
            addToMap(javaPegIdToIsoValues, iso, javaPegIdValue, imagePath);
        }
    }

    public void addExposureTime(String javaPegIdValue, ExposureTime exposureTime, String imagePath) {
        if (!javaPegIdToExposureTimeValues.containsKey(javaPegIdValue)) {
            Map<String, Set<Integer>> exposureTimeValuesToIndex = new HashMap<String, Set<Integer>>();

            exposureTimeValuesToIndex.put(exposureTime.toString(), new HashSet<Integer>());
            javaPegIdToExposureTimeValues.put(javaPegIdValue, exposureTimeValuesToIndex);
        }

        if (!javaPegIdToExposureTimeValues.get(javaPegIdValue).containsKey(exposureTime.toString())) {
            javaPegIdToExposureTimeValues.get(javaPegIdValue).put(exposureTime.toString(), new HashSet<Integer>());
        }

        exposureTimeStringExposureTimeMappings.put(exposureTime.toString(), exposureTime);
        javaPegIdToExposureTimeValues.get(javaPegIdValue).get(exposureTime.toString()).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
    }

    public void addImageSize(String javaPegIdValue, ImageSize imageSize, String imagePath) {
        if (!javaPegIdToImageSizeValues.containsKey(javaPegIdValue)) {
            Map<String, Set<Integer>> imageSizeValuesToIndex = new HashMap<String, Set<Integer>>();

            imageSizeValuesToIndex.put(imageSize.toString(), new HashSet<Integer>());
            javaPegIdToImageSizeValues.put(javaPegIdValue, imageSizeValuesToIndex);
        }

        if (!javaPegIdToImageSizeValues.get(javaPegIdValue).containsKey(imageSize.toString())) {
            javaPegIdToImageSizeValues.get(javaPegIdValue).put(imageSize.toString(), new HashSet<Integer>());
        }

        imageSizeStringImageSizeMappings.put(imageSize.toString(), imageSize);
        javaPegIdToImageSizeValues.get(javaPegIdValue).get(imageSize.toString()).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
    }

    public void addFNumber(String javaPegIdValue, double fNumber, String imagePath) {
        if (!javaPegIdToFNumberValues.containsKey(javaPegIdValue)) {
            Map<Double, Set<Integer>> fNumberValuesToIndex = new HashMap<Double, Set<Integer>>();

            fNumberValuesToIndex.put(fNumber, new HashSet<Integer>());
            javaPegIdToFNumberValues.put(javaPegIdValue, fNumberValuesToIndex);
        }

        if (!javaPegIdToFNumberValues.get(javaPegIdValue).containsKey(fNumber)) {
            javaPegIdToFNumberValues.get(javaPegIdValue).put(fNumber, new HashSet<Integer>());
        }

        javaPegIdToFNumberValues.get(javaPegIdValue).get(fNumber).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
    }

    public void addRating(String javaPegIdValue, int rating, String imagePath) {
        if (!javaPegIdToRatings.containsKey(javaPegIdValue)) {
            Map<Integer, Set<Integer>> ratingToIndex = new HashMap<Integer, Set<Integer>>();
            ratingToIndex.put(rating, new HashSet<Integer>());
            javaPegIdToRatings.put(javaPegIdValue, ratingToIndex);
        }

        if (!javaPegIdToRatings.get(javaPegIdValue).containsKey(rating)) {
            javaPegIdToRatings.get(javaPegIdValue).put(rating, new HashSet<Integer>());
        }

        // Ignore all other ratings than between 0 and 4.. Since what is used
        // by the JavaPEG application is 0 to 4. Any other value have been
        // entered manually in the data base files.
        if (rating < 5 && rating > -1) {
            javaPegIdToRatings.get(javaPegIdValue).get(rating).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
        }
    }

    public void addCategory(String javaPegId, String categoryId, String imagePath) {
         if (!javaPegIdToCategories.containsKey(javaPegId)) {
            Map<String, Set<Integer>> categoryIdToIndex = new HashMap<String, Set<Integer>>();

            categoryIdToIndex.put(categoryId, new HashSet<Integer>());
            javaPegIdToCategories.put(javaPegId, categoryIdToIndex);
        }

        if (!javaPegIdToCategories.get(javaPegId).containsKey(categoryId)) {
            javaPegIdToCategories.get(javaPegId).put(categoryId, new HashSet<Integer>());
        }

        javaPegIdToCategories.get(javaPegId).get(categoryId).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
    }

    public void addComment(String javaPegIdValue, String comment, String imagePath) {
        if (!javaPegIdToComments.containsKey(javaPegIdValue)) {
            Map<String, Set<Integer>> commentToIndex = new HashMap<String, Set<Integer>>();

            commentToIndex.put(comment, new HashSet<Integer>());
            javaPegIdToComments.put(javaPegIdValue, commentToIndex);
        }

        if (!javaPegIdToComments.get(javaPegIdValue).containsKey(comment)) {
            javaPegIdToComments.get(javaPegIdValue).put(comment, new HashSet<Integer>());
        }

        javaPegIdToComments.get(javaPegIdValue).get(comment).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
    }


    public Map<String, Set<Integer>> getComments(String javaPegIdValue) {
        return javaPegIdToComments.get(javaPegIdValue);
    }

    public Map<Integer, Set<Integer>> getRatings(String javaPegIdValue) {
        return javaPegIdToRatings.get(javaPegIdValue);
    }

    public Map<String, Map<String, Set<Integer>>> getCategories() {
        return javaPegIdToCategories;
    }

    public Set<String> getCameraModels() {
        Set<String> sortedCameraModels = new TreeSet<String>();
        for (String javaPegId : javaPegIdToCameraModels.keySet()) {
            sortedCameraModels.addAll(javaPegIdToCameraModels.get(javaPegId).keySet());
        }
        return sortedCameraModels;
    }

    public Set<Integer> getYears() {
        return ImageMetaDataContextUtil.getAllIntegerValues(javaPegIdToYearValues);
    }

    public Set<Integer> getMonths() {
        return ImageMetaDataContextUtil.getAllIntegerValues(javaPegIdToMonthValues);
    }

    public Set<Integer> getDates() {
        return ImageMetaDataContextUtil.getAllIntegerValues(javaPegIdToDateValues);
    }

    public Set<Integer> getHours() {
        return ImageMetaDataContextUtil.getAllIntegerValues(javaPegIdToHourValues);
    }

    public Set<Integer> getMinutes() {
        return ImageMetaDataContextUtil.getAllIntegerValues(javaPegIdToMinuteValues);
    }

    public Set<Integer> getSeconds() {
        return ImageMetaDataContextUtil.getAllIntegerValues(javaPegIdToSecondValues);
    }

    public Set<Integer> getIsoValues() {
        return ImageMetaDataContextUtil.getAllIntegerValues(javaPegIdToIsoValues);
    }

    public Set<Double> getFNumberValues() {
        return ImageMetaDataContextUtil.getAllDoubleValues(javaPegIdToFNumberValues);
    }

    public Set<ExposureTime> getExposureTimeValues() {
        Set<ExposureTime> exposureTimeObjects = new TreeSet<ExposureTime>();

        for (ExposureTime exposureTime : exposureTimeStringExposureTimeMappings.values()) {
            exposureTimeObjects.add(exposureTime);
        }
        return exposureTimeObjects;
    }

    public Set<ImageSize> getImageSizeValues() {
        Set<ImageSize> imageSizeObjects = new TreeSet<ImageSize>();

        for (ImageSize imageSize : imageSizeStringImageSizeMappings.values()) {
            imageSizeObjects.add(imageSize);
        }
        return imageSizeObjects;
    }

    public Set<File> findImagesByComment(String javaPegId, String commentToSearchFor) {
        Set<File> imagePaths = new HashSet<File>();

        for (String comment : javaPegIdToComments.get(javaPegId).keySet()) {
            if (comment.toLowerCase().contains(commentToSearchFor.toLowerCase())) {
                Set<Integer> indexForImagePaths = javaPegIdToComments.get(javaPegId).get(comment);
                populateImagePathsSet(indexForImagePaths, imagePaths);
            }
        }
        return imagePaths;    }

    public Set<File> findImagesByCategory(String javaPegId, Categories c, boolean isAndCategoriesSearch) {
        Set<File> imagePaths = new HashSet<File>();

        if (c.getCategories().size() > 0) {
            if (isAndCategoriesSearch) {
                List<Set<File>> searchResults = new ArrayList<Set<File>>();

                for (String categoryId : c.getCategories()) {
                    searchResults.add(findImagesByCategory(javaPegId, new Categories(new String[] {categoryId}), false));
                }

                imagePaths = ImageMetaDataContextUtil.compileSearchResult(searchResults);
            } else {
                for (String categoryId : c.getCategories()) {
                    Map<String, Set<Integer>> categoryIdToIndex = javaPegIdToCategories.get(javaPegId);

                    // If there are any images associated with the selected
                    // categoryId.
                    if (categoryIdToIndex != null) {
                        Set<Integer> indexForImagePaths = categoryIdToIndex.get(categoryId);
                        populateImagePathsSet(indexForImagePaths, imagePaths);
                    }
                }
            }
        }
        return imagePaths;
    }

    public Set<File> findImagesByYear(String javaPegId, String year) {
        return findImagesByInteger(year, javaPegIdToYearValues.get(javaPegId), MetaDataValueFieldName.YEAR);
    }

    public Set<File> findImagesByMonth(String javaPegId, String month) {
        return findImagesByInteger(month, javaPegIdToMonthValues.get(javaPegId), MetaDataValueFieldName.MONTH);
    }

    public Set<File> findImagesByDay(String javaPegId, String day) {
        return findImagesByInteger(day, javaPegIdToDateValues.get(javaPegId), MetaDataValueFieldName.DAY);
    }

    public Set<File> findImagesByHour(String javaPegId, String hour) {
        return findImagesByInteger(hour, javaPegIdToHourValues.get(javaPegId), MetaDataValueFieldName.HOUR);
    }

    public Set<File> findImagesByMinute(String javaPegId, String minute) {
        return findImagesByInteger(minute, javaPegIdToMinuteValues.get(javaPegId), MetaDataValueFieldName.MINUTE);
    }

    public Set<File> findImagesBySecond(String javaPegId, String second) {
        return findImagesByInteger(second, javaPegIdToSecondValues.get(javaPegId), MetaDataValueFieldName.SECOND);
    }

    private Set<File> findImagesByInteger(String valueString, Map<Integer, Set<Integer>> values, MetaDataValueFieldName mdvfn) {
        Set<Integer> valuesKeys = new TreeSet<Integer>(values.keySet());
        Iterator<Integer> iterator = valuesKeys.iterator();

        List<Integer> valuesToGet = new ArrayList<Integer>();

        switch (mdvfn) {
        case YEAR:
            FindBy.year(valueString, iterator, valuesToGet);
            break;
        case MONTH:
            FindBy.month(valueString, iterator, valuesToGet);
            break;
        case DAY:
            FindBy.day(valueString, iterator, valuesToGet);
            break;
        case HOUR:
            FindBy.hour(valueString, iterator, valuesToGet);
            break;
        case MINUTE:
            FindBy.minute(valueString, iterator, valuesToGet);
            break;
        case SECOND:
            FindBy.second(valueString, iterator, valuesToGet);
            break;
        default:
            break;
        }

        Set<File> imagePaths = new HashSet<File>();

        for (Integer value : valuesToGet) {
            Set<Integer> indexForImagePaths = values.get(value);
            populateImagePathsSet(indexForImagePaths, imagePaths);
        }
        return imagePaths;
    }

    public Set<File> findImagesByFNumberValue(String javaPegId, String fNumber) {
        Set<Double> fNumberValuesKeys = new TreeSet<Double>(javaPegIdToFNumberValues.get(javaPegId).keySet());
        Iterator<Double> iterator = fNumberValuesKeys.iterator();

        List<Double> fNumberValuesToGet = new ArrayList<Double>();

        FindBy.fNumber(fNumber, iterator, fNumberValuesToGet);

        Set<File> imagePaths = new HashSet<File>();

        for (Double fNumberValue : fNumberValuesToGet) {
            Set<Integer> indexForImagePaths = javaPegIdToFNumberValues.get(javaPegId).get(fNumberValue);
            populateImagePathsSet(indexForImagePaths, imagePaths);
        }
        return imagePaths;
    }

    public Set<File> findImagesByCameraModel(String javaPegId, String cameraModel) {

        Set<String> cameraModelKeys = new TreeSet<String>(javaPegIdToCameraModels.get(javaPegId).keySet());
        Iterator<String> iterator = cameraModelKeys.iterator();

        List<String> cameraModelsToGet = new ArrayList<String>();

        FindBy.cameraModel(cameraModel, iterator, cameraModelsToGet);

        Set<File> imagePaths = new HashSet<File>();

        for (String cameraModelString : cameraModelsToGet) {
            Set<Integer> indexForImagePaths = javaPegIdToCameraModels.get(javaPegId).get(cameraModelString);
            populateImagePathsSet(indexForImagePaths, imagePaths);
        }
        return imagePaths;
    }

    public Set<File> findImagesByImageSize(String javaPegId, String imageSize) {

        Set<String> imageSizeValuesKeys = new TreeSet<String>(javaPegIdToImageSizeValues.get(javaPegId).keySet());
        Iterator<String> iterator = imageSizeValuesKeys.iterator();

        List<String> imageSizeValuesToGet = new ArrayList<String>();

        FindBy.imageSize(imageSize, iterator, imageSizeValuesToGet);

        Set<File> imagePaths = new HashSet<File>();

        for (String imageSizeValue : imageSizeValuesToGet) {
            Set<Integer> indexForImagePaths = javaPegIdToImageSizeValues.get(javaPegId).get(imageSizeValue);
            populateImagePathsSet(indexForImagePaths, imagePaths);
        }
        return imagePaths;
    }

    public Set<File> findImagesByIso(String javaPegId, String iso) {

        Set<Integer> isoValuesKeys = new TreeSet<Integer>(javaPegIdToIsoValues.get(javaPegId).keySet());
        Iterator<Integer> iterator = isoValuesKeys.iterator();

        List<Integer> isoValuesToGet = new ArrayList<Integer>();

        FindBy.iso(iso, iterator, isoValuesToGet);

        Set<File> imagePaths = new HashSet<File>();

        for (Integer isoValue : isoValuesToGet) {
            Set<Integer> indexForImagePaths = javaPegIdToIsoValues.get(javaPegId).get(isoValue);
            populateImagePathsSet(indexForImagePaths, imagePaths);
        }
        return imagePaths;
    }

    public Set<File> findImagesByRating(String javaPegId, boolean[] ratingSelection) {
        Set<File> imagePaths = new HashSet<File>();

        for (int i = 0; i < ratingSelection.length; i++) {
            if (ratingSelection[i] == true) {
                Set<Integer> indexForImagePaths = javaPegIdToRatings.get(javaPegId).get(i);
                populateImagePathsSet(indexForImagePaths, imagePaths);
            }
        }
        return imagePaths;
    }

    public Set<File> findImagesByExposureTime(String javaPegId, String exposureTime) {

        Set<String> exposureTimeValuesKeys = new TreeSet<String>(javaPegIdToExposureTimeValues.get(javaPegId).keySet());
        Iterator<String> iterator = exposureTimeValuesKeys.iterator();

        List<String> exposureTimeValuesToGet = new ArrayList<String>();

        FindBy.exposureTime(exposureTime, iterator, exposureTimeValuesToGet);

        Set<File> imagePaths = new HashSet<File>();

        for (String exposureTimeValue : exposureTimeValuesToGet) {
            Set<Integer> indexForImagePaths = javaPegIdToExposureTimeValues.get(javaPegId).get(exposureTimeValue);
            populateImagePathsSet(indexForImagePaths, imagePaths);
        }
        return imagePaths;
    }

    private void  populateImagePathsSet(Set<Integer> indexForImagePaths, Set<File> imagePaths) {
        if (indexForImagePaths != null) {
            for(Integer indexForImagePath : indexForImagePaths) {
                imagePaths.add(new File(ImagePathAndIndex.getInstance().getImagePathForIndex(indexForImagePath)));
            }
        }
    }
}
