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
package moller.javapeg.program.contexts.imagemetadata;

import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.datatype.ExposureTime;
import moller.javapeg.program.datatype.ImageSize;
import moller.javapeg.program.enumerations.MetaDataValueFieldName;
import moller.util.string.StringUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImageMetaDataContext {

    /**
     * The static singleton instance of this class.
     */
    private static ImageMetaDataContext instance;

    private final Map<String, ImageSize> imageSizeStringImageSizeMappings;
    private final Map<String, ExposureTime> exposureTimeStringExposureTimeMappings;

    /**
     * Contains a mapping between an absolute path as a {@link String} object
     * and the timestamp, as a {@link Long} object, when a photo was taken.
     */
    private final Map<String, Long> dateTimeValues;

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
        exposureTimeStringExposureTimeMappings = new HashMap<>();
        imageSizeStringImageSizeMappings = new HashMap<>();

        dateTimeValues = new HashMap<>();

        javaPegIdToCameraModels = new HashMap<>();
        javaPegIdToYearValues = new HashMap<>();
        javaPegIdToMonthValues = new HashMap<>();
        javaPegIdToDateValues = new HashMap<>();
        javaPegIdToHourValues = new HashMap<>();
        javaPegIdToMinuteValues = new HashMap<>();
        javaPegIdToSecondValues = new HashMap<>();
        javaPegIdToIsoValues = new HashMap<>();
        javaPegIdToImageSizeValues = new HashMap<>();
        javaPegIdToExposureTimeValues = new HashMap<>();
        javaPegIdToFNumberValues = new HashMap<>();
        javaPegIdToRatings = new HashMap<>();
        javaPegIdToCategories = new HashMap<>();
        javaPegIdToComments = new HashMap<>();
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
        if (!StringUtil.isNotBlank(cameraModel)) {
            cameraModel = "Unknown";
        }

        if (!javaPegIdToCameraModels.containsKey(javaPegIdValue)) {
            Map<String, Set<Integer>> cameraModelToIndex = new HashMap<>();

            cameraModelToIndex.put(cameraModel, new HashSet<>());
            javaPegIdToCameraModels.put(javaPegIdValue, cameraModelToIndex);
        }

        if (!javaPegIdToCameraModels.get(javaPegIdValue).containsKey(cameraModel)) {
            javaPegIdToCameraModels.get(javaPegIdValue).put(cameraModel, new HashSet<>());
        }

        javaPegIdToCameraModels.get(javaPegIdValue).get(cameraModel).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
    }

    public void addDateTime(String javaPegIdValue, Date dateTime, String imagePath) {
        if (dateTime != null) {
            dateTimeValues.put(imagePath, dateTime.getTime());
            addToMap(javaPegIdToYearValues, Integer.parseInt(new SimpleDateFormat("yyyy").format(dateTime)), javaPegIdValue, imagePath);
            addToMap(javaPegIdToMonthValues, Integer.parseInt(new SimpleDateFormat("MM").format(dateTime)), javaPegIdValue, imagePath);
            addToMap(javaPegIdToDateValues, Integer.parseInt(new SimpleDateFormat("dd").format(dateTime)), javaPegIdValue, imagePath);
            addToMap(javaPegIdToHourValues, Integer.parseInt(new SimpleDateFormat("HH").format(dateTime)), javaPegIdValue, imagePath);
            addToMap(javaPegIdToMinuteValues, Integer.parseInt(new SimpleDateFormat("mm").format(dateTime)), javaPegIdValue, imagePath);
            addToMap(javaPegIdToSecondValues, Integer.parseInt(new SimpleDateFormat("ss").format(dateTime)), javaPegIdValue, imagePath);
        }
    }

    private void addToMap(Map<String, Map<Integer, Set<Integer>>> mapToAddTo, Integer value, String javaPegIdValue, String imagePath) {
        if (!mapToAddTo.containsKey(javaPegIdValue)) {
            Map<Integer, Set<Integer>> valuesToIndex = new HashMap<>();

            valuesToIndex.put(value, new HashSet<>());
            mapToAddTo.put(javaPegIdValue, valuesToIndex);
        }

        if (!mapToAddTo.get(javaPegIdValue).containsKey(value)) {
            mapToAddTo.get(javaPegIdValue).put(value, new HashSet<>());
        }
        mapToAddTo.get(javaPegIdValue).get(value).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
    }

    public void addIso(String javaPegIdValue, Integer iso, String imagePath) {
        if (iso != -1) {
            addToMap(javaPegIdToIsoValues, iso, javaPegIdValue, imagePath);
        }
    }

    public void addExposureTime(String javaPegIdValue, ExposureTime exposureTime, String imagePath) {
        if (exposureTime != null) {
            if (!javaPegIdToExposureTimeValues.containsKey(javaPegIdValue)) {
                Map<String, Set<Integer>> exposureTimeValuesToIndex = new HashMap<>();

                exposureTimeValuesToIndex.put(exposureTime.toString(), new HashSet<>());
                javaPegIdToExposureTimeValues.put(javaPegIdValue, exposureTimeValuesToIndex);
            }

            if (!javaPegIdToExposureTimeValues.get(javaPegIdValue).containsKey(exposureTime.toString())) {
                javaPegIdToExposureTimeValues.get(javaPegIdValue).put(exposureTime.toString(), new HashSet<>());
            }

            exposureTimeStringExposureTimeMappings.put(exposureTime.toString(), exposureTime);
            javaPegIdToExposureTimeValues.get(javaPegIdValue).get(exposureTime.toString()).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
        }
    }

    public void addImageSize(String javaPegIdValue, ImageSize imageSize, String imagePath) {
        if (!javaPegIdToImageSizeValues.containsKey(javaPegIdValue)) {
            Map<String, Set<Integer>> imageSizeValuesToIndex = new HashMap<>();

            imageSizeValuesToIndex.put(imageSize.toString(), new HashSet<>());
            javaPegIdToImageSizeValues.put(javaPegIdValue, imageSizeValuesToIndex);
        }

        if (!javaPegIdToImageSizeValues.get(javaPegIdValue).containsKey(imageSize.toString())) {
            javaPegIdToImageSizeValues.get(javaPegIdValue).put(imageSize.toString(), new HashSet<>());
        }

        imageSizeStringImageSizeMappings.put(imageSize.toString(), imageSize);
        javaPegIdToImageSizeValues.get(javaPegIdValue).get(imageSize.toString()).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
    }

    public void addFNumber(String javaPegIdValue, double fNumber, String imagePath) {
        if (!javaPegIdToFNumberValues.containsKey(javaPegIdValue)) {
            Map<Double, Set<Integer>> fNumberValuesToIndex = new HashMap<>();

            fNumberValuesToIndex.put(fNumber, new HashSet<>());
            javaPegIdToFNumberValues.put(javaPegIdValue, fNumberValuesToIndex);
        }

        if (!javaPegIdToFNumberValues.get(javaPegIdValue).containsKey(fNumber)) {
            javaPegIdToFNumberValues.get(javaPegIdValue).put(fNumber, new HashSet<>());
        }

        javaPegIdToFNumberValues.get(javaPegIdValue).get(fNumber).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
    }

    public void addRating(String javaPegIdValue, int rating, String imagePath) {
        if (!javaPegIdToRatings.containsKey(javaPegIdValue)) {
            Map<Integer, Set<Integer>> ratingToIndex = new HashMap<>();
            ratingToIndex.put(rating, new HashSet<>());
            javaPegIdToRatings.put(javaPegIdValue, ratingToIndex);
        }

        if (!javaPegIdToRatings.get(javaPegIdValue).containsKey(rating)) {
            javaPegIdToRatings.get(javaPegIdValue).put(rating, new HashSet<>());
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
            Map<String, Set<Integer>> categoryIdToIndex = new HashMap<>();

            categoryIdToIndex.put(categoryId, new HashSet<>());
            javaPegIdToCategories.put(javaPegId, categoryIdToIndex);
        }

        if (!javaPegIdToCategories.get(javaPegId).containsKey(categoryId)) {
            javaPegIdToCategories.get(javaPegId).put(categoryId, new HashSet<>());
        }

        javaPegIdToCategories.get(javaPegId).get(categoryId).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
    }

    public void addComment(String javaPegIdValue, String comment, String imagePath) {
        if (!javaPegIdToComments.containsKey(javaPegIdValue)) {
            Map<String, Set<Integer>> commentToIndex = new HashMap<>();

            commentToIndex.put(comment, new HashSet<>());
            javaPegIdToComments.put(javaPegIdValue, commentToIndex);
        }

        if (!javaPegIdToComments.get(javaPegIdValue).containsKey(comment)) {
            javaPegIdToComments.get(javaPegIdValue).put(comment, new HashSet<>());
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
        Set<String> sortedCameraModels = new TreeSet<>();
        for (String javaPegId : javaPegIdToCameraModels.keySet()) {
            sortedCameraModels.addAll(javaPegIdToCameraModels.get(javaPegId).keySet());
        }
        return sortedCameraModels;
    }

    public Set<Integer> getYears() {
        return ImageMetaDataContextUtil.getAllIntegerValues(javaPegIdToYearValues);
    }

    public Long getDateTime(File image) {
        Long dateTime = dateTimeValues.get(image.getAbsolutePath());

        if (dateTime == null) {
            return Long.MAX_VALUE;
        }

        return dateTime;
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
        return new TreeSet<>(exposureTimeStringExposureTimeMappings.values());
    }

    public Set<ImageSize> getImageSizeValues() {
        return new TreeSet<>(imageSizeStringImageSizeMappings.values());
    }

    public Set<File> findImagesByComment(String javaPegId, String commentToSearchFor) {
        Set<File> imagePaths = new HashSet<>();

        for (String comment : javaPegIdToComments.get(javaPegId).keySet()) {
            if (comment.toLowerCase().contains(commentToSearchFor.toLowerCase())) {
                Set<Integer> indexForImagePaths = javaPegIdToComments.get(javaPegId).get(comment);
                populateImagePathsSet(indexForImagePaths, imagePaths);
            }
        }
        return imagePaths;    }

    public Set<File> findImagesByCategory(String javaPegId, Categories c, boolean isAndCategoriesSearch) {
        Set<File> imagePaths = new HashSet<>();

        if (c.getCategories().size() > 0) {
            if (isAndCategoriesSearch) {
                List<Set<File>> searchResults = new ArrayList<>();

                for (String categoryId : c.getCategories()) {
                    searchResults.add(findImagesByCategory(javaPegId, new Categories(new String[] {categoryId}), false));
                }

                imagePaths = ImageMetaDataContextUtil.compileSearchResult(searchResults);
            } else {
                Map<String, Set<Integer>> categoryIdToIndex = javaPegIdToCategories.get(javaPegId);
                for (String categoryId : c.getCategories()) {

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
        Set<Integer> valuesKeys = new TreeSet<>(values.keySet());
        Iterator<Integer> iterator = valuesKeys.iterator();

        List<Integer> valuesToGet = new ArrayList<>();

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

        Set<File> imagePaths = new HashSet<>();

        for (Integer value : valuesToGet) {
            Set<Integer> indexForImagePaths = values.get(value);
            populateImagePathsSet(indexForImagePaths, imagePaths);
        }
        return imagePaths;
    }

    public Set<File> findImagesByFNumberValue(String javaPegId, String fNumber) {
        Set<Double> fNumberValuesKeys = new TreeSet<>(javaPegIdToFNumberValues.get(javaPegId).keySet());
        Iterator<Double> iterator = fNumberValuesKeys.iterator();

        List<Double> fNumberValuesToGet = new ArrayList<>();

        FindBy.fNumber(fNumber, iterator, fNumberValuesToGet);

        Set<File> imagePaths = new HashSet<>();

        for (Double fNumberValue : fNumberValuesToGet) {
            Set<Integer> indexForImagePaths = javaPegIdToFNumberValues.get(javaPegId).get(fNumberValue);
            populateImagePathsSet(indexForImagePaths, imagePaths);
        }
        return imagePaths;
    }

    public Set<File> findImagesByCameraModel(String javaPegId, String cameraModel) {

        Set<String> cameraModelKeys = new TreeSet<>(javaPegIdToCameraModels.get(javaPegId).keySet());
        Iterator<String> iterator = cameraModelKeys.iterator();

        List<String> cameraModelsToGet = new ArrayList<>();

        FindBy.cameraModel(cameraModel, iterator, cameraModelsToGet);

        Set<File> imagePaths = new HashSet<>();

        for (String cameraModelString : cameraModelsToGet) {
            Set<Integer> indexForImagePaths = javaPegIdToCameraModels.get(javaPegId).get(cameraModelString);
            populateImagePathsSet(indexForImagePaths, imagePaths);
        }
        return imagePaths;
    }

    public Set<File> findImagesByImageSize(String javaPegId, String imageSize) {

        Set<String> imageSizeValuesKeys = new TreeSet<>(javaPegIdToImageSizeValues.get(javaPegId).keySet());
        Iterator<String> iterator = imageSizeValuesKeys.iterator();

        List<String> imageSizeValuesToGet = new ArrayList<>();

        FindBy.imageSize(imageSize, iterator, imageSizeValuesToGet);

        Set<File> imagePaths = new HashSet<>();

        for (String imageSizeValue : imageSizeValuesToGet) {
            Set<Integer> indexForImagePaths = javaPegIdToImageSizeValues.get(javaPegId).get(imageSizeValue);
            populateImagePathsSet(indexForImagePaths, imagePaths);
        }
        return imagePaths;
    }

    public Set<File> findImagesByIso(String javaPegId, String iso) {

        Set<Integer> isoValuesKeys = new TreeSet<>(javaPegIdToIsoValues.get(javaPegId).keySet());
        Iterator<Integer> iterator = isoValuesKeys.iterator();

        List<Integer> isoValuesToGet = new ArrayList<>();

        FindBy.iso(iso, iterator, isoValuesToGet);

        Set<File> imagePaths = new HashSet<>();

        for (Integer isoValue : isoValuesToGet) {
            Set<Integer> indexForImagePaths = javaPegIdToIsoValues.get(javaPegId).get(isoValue);
            populateImagePathsSet(indexForImagePaths, imagePaths);
        }
        return imagePaths;
    }

    public Set<File> findImagesByRating(String javaPegId, boolean[] ratingSelection) {
        Set<File> imagePaths = new HashSet<>();

        for (int i = 0; i < ratingSelection.length; i++) {
            if (ratingSelection[i]) {
                Set<Integer> indexForImagePaths = javaPegIdToRatings.get(javaPegId).get(i);
                populateImagePathsSet(indexForImagePaths, imagePaths);
            }
        }
        return imagePaths;
    }

    public Set<File> findImagesByExposureTime(String javaPegId, String exposureTime) {

        Set<String> exposureTimeValuesKeys = new TreeSet<>(javaPegIdToExposureTimeValues.get(javaPegId).keySet());
        Iterator<String> iterator = exposureTimeValuesKeys.iterator();

        List<String> exposureTimeValuesToGet = new ArrayList<>();

        FindBy.exposureTime(exposureTime, iterator, exposureTimeValuesToGet);

        Set<File> imagePaths = new HashSet<>();

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

    public int getNumberOfImagesForCameraModel(String cameraModel) {
        int amountOfImages = 0;

        for (String javaPegId : javaPegIdToCameraModels.keySet()) {
             Map<String, Set<Integer>> cameraModelsToImagesMapping = javaPegIdToCameraModels.get(javaPegId);

             amountOfImages += cameraModelsToImagesMapping.get(cameraModel).size();
        }
        return amountOfImages;
    }

    public int getNumberOfImagesForYear(Integer year) {
        return getNumberOfImagesForInteger(javaPegIdToYearValues, year);
    }

    public int getNumberOfImagesForDate(Integer date) {
        return getNumberOfImagesForInteger(javaPegIdToDateValues, date);
    }

    public int getNumberOfImagesForHour(Integer hour) {
        return getNumberOfImagesForInteger(javaPegIdToHourValues, hour);
    }

    public int getNumberOfImagesForMinute(Integer minute) {
        return getNumberOfImagesForInteger(javaPegIdToMinuteValues, minute);
    }

    public int getNumberOfImagesForSecond(Integer second) {
        return getNumberOfImagesForInteger(javaPegIdToSecondValues, second);
    }

    public int getNumberOfImagesForISO(Integer iso) {
        return getNumberOfImagesForInteger(javaPegIdToIsoValues, iso);
    }

    private int getNumberOfImagesForInteger(Map<String, Map<Integer, Set<Integer>>> map, Integer integer) {
        int amountOfImages = 0;

        for (String javaPegId : map.keySet()) {
             Map<Integer, Set<Integer>> integersToImagesMapping = map.get(javaPegId);
             amountOfImages += integersToImagesMapping.get(integer).size();
        }
        return amountOfImages;
    }

    public int getNumberOfImagesForImageSize(ImageSize imageSize) {
        int amountOfImages = 0;

        for (String javaPegId : javaPegIdToImageSizeValues.keySet()) {
             Map<String, Set<Integer>> imageSizesToImagesMapping = javaPegIdToImageSizeValues.get(javaPegId);
             amountOfImages += imageSizesToImagesMapping.get(imageSize.toString()).size();
        }
        return amountOfImages;
    }

    public Integer getNumberOfImagesForExposureTime(ExposureTime exposureTime) {
        int amountOfImages = 0;

        for (String javaPegId : javaPegIdToExposureTimeValues.keySet()) {
             Map<String, Set<Integer>> exposureTimesToImagesMapping = javaPegIdToExposureTimeValues.get(javaPegId);
             amountOfImages += exposureTimesToImagesMapping.get(exposureTime.toString()).size();
        }
        return amountOfImages;
    }

    public Integer getNumberOfImagesForFNumber(Double fNumber) {
        int amountOfImages = 0;

        for (String javaPegId : javaPegIdToFNumberValues.keySet()) {
             Map<Double, Set<Integer>> exposureTimesToImagesMapping = javaPegIdToFNumberValues.get(javaPegId);
             amountOfImages += exposureTimesToImagesMapping.get(fNumber).size();
        }
        return amountOfImages;
    }

    /**
     * Returns the time stamp as a {@link Long} for all images which are stored
     * in the {@link ImageMetaDataContext}
     *
     * @return a {@link Map} which contains all the, in the
     *         {@link ImageMetaDataContext}, stored images and their corresponding timestamp.
     */
    public Map<String, Long> getDateTimeValues() {
        return dateTimeValues;
    }
}
