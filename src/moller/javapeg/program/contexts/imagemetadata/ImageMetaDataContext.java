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
	private final Map<String, Map<String, Set<Integer>>> cameraModels;
	private final Map<String, Map<String, Set<Integer>>> dateTimeValues;

	private final Map<String, Map<Integer, Set<Integer>>> yearValues;
	private final Map<String, Map<Integer, Set<Integer>>> monthValues;
	private final Map<String, Map<Integer, Set<Integer>>> dateValues;
	private final Map<String, Map<Integer, Set<Integer>>> hourValues;
	private final Map<String, Map<Integer, Set<Integer>>> minuteValues;
	private final Map<String, Map<Integer, Set<Integer>>> secondValues;

	private final Map<String, Map<Integer, Set<Integer>>> isoValues;
	private final Map<String, Map<String, Set<Integer>>> imageSizeValues;
	private final Map<String, Map<String, Set<Integer>>> exposureTimeValues;
	private final Map<String, Map<Double, Set<Integer>>> fNumberValues;

	private final Map<String, Map<Integer, Set<Integer>>> ratings;
	private final Map<String, Map<String, Set<Integer>>> javaPegIdToCategories;
	private final Map<String, Map<String, Set<Integer>>> comments;

	private final SimpleDateFormat sdf;

	/**
	 * Private constructor.
	 */
	private ImageMetaDataContext() {

		exposureTimeStringExposureTimeMappings = new HashMap<String, ExposureTime>();
		imageSizeStringImageSizeMappings = new HashMap<String, ImageSize>();

		cameraModels = new HashMap<String, Map<String, Set<Integer>>>();
		dateTimeValues = new HashMap<String, Map<String, Set<Integer>>>();
		yearValues = new HashMap<String, Map<Integer, Set<Integer>>>();
		monthValues = new HashMap<String, Map<Integer, Set<Integer>>>();
		dateValues = new HashMap<String, Map<Integer, Set<Integer>>>();
		hourValues = new HashMap<String, Map<Integer, Set<Integer>>>();
		minuteValues = new HashMap<String, Map<Integer, Set<Integer>>>();
		secondValues = new HashMap<String, Map<Integer, Set<Integer>>>();
		isoValues = new HashMap<String, Map<Integer, Set<Integer>>>();
		imageSizeValues = new HashMap<String, Map<String, Set<Integer>>>();
		exposureTimeValues = new HashMap<String, Map<String, Set<Integer>>>();
		fNumberValues = new HashMap<String, Map<Double, Set<Integer>>>();
		ratings = new HashMap<String, Map<Integer, Set<Integer>>>();

		javaPegIdToCategories = new HashMap<String, Map<String, Set<Integer>>>();
		comments = new HashMap<String, Map<String, Set<Integer>>>();

		sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
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
		if (!cameraModels.containsKey(javaPegIdValue)) {
            Map<String, Set<Integer>> cameraModelToIndex = new HashMap<String, Set<Integer>>();

            cameraModelToIndex.put(cameraModel, new HashSet<Integer>());
            cameraModels.put(javaPegIdValue, cameraModelToIndex);
        }

        if (!cameraModels.get(javaPegIdValue).containsKey(cameraModel)) {
            cameraModels.get(javaPegIdValue).put(cameraModel, new HashSet<Integer>());
        }

        cameraModels.get(javaPegIdValue).get(cameraModel).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}

	public void addDateTime(String javaPegIdValue, Date dateTime, String imagePath) {
		SimpleDateFormat getYear = new SimpleDateFormat("yyyy");
		SimpleDateFormat getMonth = new SimpleDateFormat("MM");
		SimpleDateFormat getDate = new SimpleDateFormat("dd");
		SimpleDateFormat getHour = new SimpleDateFormat("HH");
		SimpleDateFormat getMinute = new SimpleDateFormat("mm");
		SimpleDateFormat getSecond = new SimpleDateFormat("ss");

		String dateTimeString = sdf.format(dateTime);
		if (!dateTimeValues.containsKey(javaPegIdValue)) {
            Map<String, Set<Integer>> dateTimeValuesToIndex = new HashMap<String, Set<Integer>>();

            dateTimeValuesToIndex.put(dateTimeString, new HashSet<Integer>());
            dateTimeValues.put(javaPegIdValue, dateTimeValuesToIndex);
        }

        if (!dateTimeValues.get(javaPegIdValue).containsKey(dateTimeString)) {
            dateTimeValues.get(javaPegIdValue).put(dateTimeString, new HashSet<Integer>());
        }
        dateTimeValues.get(javaPegIdValue).get(dateTimeString).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));

		Integer year = Integer.parseInt(getYear.format(dateTime));
		if (!yearValues.containsKey(javaPegIdValue)) {
            Map<Integer, Set<Integer>> yearValuesToIndex = new HashMap<Integer, Set<Integer>>();

            yearValuesToIndex.put(year, new HashSet<Integer>());
            yearValues.put(javaPegIdValue, yearValuesToIndex);
        }

        if (!yearValues.get(javaPegIdValue).containsKey(year)) {
            yearValues.get(javaPegIdValue).put(year, new HashSet<Integer>());
        }
        yearValues.get(javaPegIdValue).get(year).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));


		Integer month = Integer.parseInt(getMonth.format(dateTime));
		if (!monthValues.containsKey(javaPegIdValue)) {
            Map<Integer, Set<Integer>> monthValuesToIndex = new HashMap<Integer, Set<Integer>>();

            monthValuesToIndex.put(month, new HashSet<Integer>());
            monthValues.put(javaPegIdValue, monthValuesToIndex);
        }

        if (!monthValues.get(javaPegIdValue).containsKey(month)) {
            monthValues.get(javaPegIdValue).put(month, new HashSet<Integer>());
        }
        monthValues.get(javaPegIdValue).get(month).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));

		Integer date = Integer.parseInt(getDate.format(dateTime));
		if (!dateValues.containsKey(javaPegIdValue)) {
            Map<Integer, Set<Integer>> dateValuesToIndex = new HashMap<Integer, Set<Integer>>();

            dateValuesToIndex.put(date, new HashSet<Integer>());
            dateValues.put(javaPegIdValue, dateValuesToIndex);
        }

        if (!dateValues.get(javaPegIdValue).containsKey(date)) {
            dateValues.get(javaPegIdValue).put(date, new HashSet<Integer>());
        }
        dateValues.get(javaPegIdValue).get(date).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));


		Integer hour = Integer.parseInt(getHour.format(dateTime));
		if (!hourValues.containsKey(javaPegIdValue)) {
            Map<Integer, Set<Integer>> hourValuesToIndex = new HashMap<Integer, Set<Integer>>();

            hourValuesToIndex.put(hour, new HashSet<Integer>());
            hourValues.put(javaPegIdValue, hourValuesToIndex);
        }

        if (!hourValues.get(javaPegIdValue).containsKey(hour)) {
            hourValues.get(javaPegIdValue).put(hour, new HashSet<Integer>());
        }
        hourValues.get(javaPegIdValue).get(hour).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));

		Integer minute = Integer.parseInt(getMinute.format(dateTime));
		if (!minuteValues.containsKey(javaPegIdValue)) {
            Map<Integer, Set<Integer>> minuteValuesToIndex = new HashMap<Integer, Set<Integer>>();

            minuteValuesToIndex.put(minute, new HashSet<Integer>());
            minuteValues.put(javaPegIdValue, minuteValuesToIndex);
        }

        if (!minuteValues.get(javaPegIdValue).containsKey(minute)) {
            minuteValues.get(javaPegIdValue).put(minute, new HashSet<Integer>());
        }
        minuteValues.get(javaPegIdValue).get(minute).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));

		Integer second = Integer.parseInt(getSecond.format(dateTime));
		if (!secondValues.containsKey(javaPegIdValue)) {
		    Map<Integer, Set<Integer>> secondValuesToIndex = new HashMap<Integer, Set<Integer>>();

		    secondValuesToIndex.put(second, new HashSet<Integer>());
		    secondValues.put(javaPegIdValue, secondValuesToIndex);
		}

		if (!secondValues.get(javaPegIdValue).containsKey(second)) {
		    secondValues.get(javaPegIdValue).put(second, new HashSet<Integer>());
		}
		secondValues.get(javaPegIdValue).get(second).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}

	public void addIso(String javaPegIdValue, Integer iso, String imagePath) {
		if (iso != -1) {
		    if (!isoValues.containsKey(javaPegIdValue)) {
		        Map<Integer, Set<Integer>> isoValuesToIndex = new HashMap<Integer, Set<Integer>>();

		        isoValuesToIndex.put(iso, new HashSet<Integer>());
		        isoValues.put(javaPegIdValue, isoValuesToIndex);
		    }

		    if (!isoValues.get(javaPegIdValue).containsKey(iso)) {
		        isoValues.get(javaPegIdValue).put(iso, new HashSet<Integer>());
		    }

		    isoValues.get(javaPegIdValue).get(iso).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
		}
	}

	public void addExposureTime(String javaPegIdValue, ExposureTime exposureTime, String imagePath) {
		if (!exposureTimeValues.containsKey(javaPegIdValue)) {
            Map<String, Set<Integer>> exposureTimeValuesToIndex = new HashMap<String, Set<Integer>>();

            exposureTimeValuesToIndex.put(exposureTime.toString(), new HashSet<Integer>());
            exposureTimeValues.put(javaPegIdValue, exposureTimeValuesToIndex);
        }

        if (!exposureTimeValues.get(javaPegIdValue).containsKey(exposureTime.toString())) {
            exposureTimeValues.get(javaPegIdValue).put(exposureTime.toString(), new HashSet<Integer>());
        }

        exposureTimeStringExposureTimeMappings.put(exposureTime.toString(), exposureTime);
        exposureTimeValues.get(javaPegIdValue).get(exposureTime.toString()).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}

	public void addImageSize(String javaPegIdValue, ImageSize imageSize, String imagePath) {
	    if (!imageSizeValues.containsKey(javaPegIdValue)) {
            Map<String, Set<Integer>> imageSizeValuesToIndex = new HashMap<String, Set<Integer>>();

            imageSizeValuesToIndex.put(imageSize.toString(), new HashSet<Integer>());
            imageSizeValues.put(javaPegIdValue, imageSizeValuesToIndex);
        }

        if (!imageSizeValues.get(javaPegIdValue).containsKey(imageSize.toString())) {
            imageSizeValues.get(javaPegIdValue).put(imageSize.toString(), new HashSet<Integer>());
        }

        imageSizeStringImageSizeMappings.put(imageSize.toString(), imageSize);
        imageSizeValues.get(javaPegIdValue).get(imageSize.toString()).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}

	public void addFNumber(String javaPegIdValue, double fNumber, String imagePath) {
		if (!fNumberValues.containsKey(javaPegIdValue)) {
            Map<Double, Set<Integer>> fNumberValuesToIndex = new HashMap<Double, Set<Integer>>();

            fNumberValuesToIndex.put(fNumber, new HashSet<Integer>());
            fNumberValues.put(javaPegIdValue, fNumberValuesToIndex);
        }

        if (!fNumberValues.get(javaPegIdValue).containsKey(fNumber)) {
            fNumberValues.get(javaPegIdValue).put(fNumber, new HashSet<Integer>());
        }

        fNumberValues.get(javaPegIdValue).get(fNumber).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}

	public void addRating(String javaPegIdValue, int rating, String imagePath) {
		if (!ratings.containsKey(javaPegIdValue)) {
		    Map<Integer, Set<Integer>> ratingToIndex = new HashMap<Integer, Set<Integer>>();
		    ratingToIndex.put(rating, new HashSet<Integer>());
		    ratings.put(javaPegIdValue, ratingToIndex);
		}

		if (!ratings.get(javaPegIdValue).containsKey(rating)) {
		    ratings.get(javaPegIdValue).put(rating, new HashSet<Integer>());
		}

		// Ignore all other ratings than between 0 and 4.. Since what is used
		// by the JavaPEG application is 0 to 4. Any other value have been
		// entered manually in the data base files.
		if (rating < 5 && rating > -1) {
		    ratings.get(javaPegIdValue).get(rating).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
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
	    if (!comments.containsKey(javaPegIdValue)) {
            Map<String, Set<Integer>> commentToIndex = new HashMap<String, Set<Integer>>();

            commentToIndex.put(comment, new HashSet<Integer>());
            comments.put(javaPegIdValue, commentToIndex);
        }

        if (!comments.get(javaPegIdValue).containsKey(comment)) {
            comments.get(javaPegIdValue).put(comment, new HashSet<Integer>());
        }

        comments.get(javaPegIdValue).get(comment).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}


	public Map<String, Set<Integer>> getComments(String javaPegIdValue) {
		return comments.get(javaPegIdValue);
	}

	public Map<Integer, Set<Integer>> getRatings(String javaPegIdValue) {
		return ratings.get(javaPegIdValue);
	}

	public Map<String, Map<String, Set<Integer>>> getCategories() {
		return javaPegIdToCategories;
	}

	public Set<String> getCameraModels() {
	    Set<String> sortedCameraModels = new TreeSet<String>();
	    for (String javaPegId : cameraModels.keySet()) {
	        sortedCameraModels.addAll(cameraModels.get(javaPegId).keySet());
	    }
		return sortedCameraModels;
	}

	public Set<Integer> getYears() {
	    return ImageMetaDataContextUtil.getAllIntegerValues(yearValues);
	}

	public Set<Integer> getMonths() {
	    return ImageMetaDataContextUtil.getAllIntegerValues(monthValues);
	}

	public Set<Integer> getDates() {
	    return ImageMetaDataContextUtil.getAllIntegerValues(dateValues);
	}

	public Set<Integer> getHours() {
	    return ImageMetaDataContextUtil.getAllIntegerValues(hourValues);
	}

	public Set<Integer> getMinutes() {
	    return ImageMetaDataContextUtil.getAllIntegerValues(minuteValues);
	}

	public Set<Integer> getSeconds() {
	    return ImageMetaDataContextUtil.getAllIntegerValues(secondValues);
	}

	public Set<Integer> getIsoValues() {
	    return ImageMetaDataContextUtil.getAllIntegerValues(isoValues);
	}

	public Set<Double> getFNumberValues() {
	    return ImageMetaDataContextUtil.getAllDoubleValues(fNumberValues);
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

		for (String comment : comments.get(javaPegId).keySet()) {
			if (comment.toLowerCase().contains(commentToSearchFor.toLowerCase())) {
				Set<Integer> indexForImagePaths = comments.get(javaPegId).get(comment);
				populateImagePathsSet(indexForImagePaths, imagePaths);
			}
		}
		return imagePaths;	}

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
		return findImagesByInteger(year, yearValues.get(javaPegId), MetaDataValueFieldName.YEAR);
	}

	public Set<File> findImagesByMonth(String javaPegId, String month) {
		return findImagesByInteger(month, monthValues.get(javaPegId), MetaDataValueFieldName.MONTH);
	}

	public Set<File> findImagesByDay(String javaPegId, String day) {
		return findImagesByInteger(day, dateValues.get(javaPegId), MetaDataValueFieldName.DAY);
	}

	public Set<File> findImagesByHour(String javaPegId, String hour) {
		return findImagesByInteger(hour, hourValues.get(javaPegId), MetaDataValueFieldName.HOUR);
	}

	public Set<File> findImagesByMinute(String javaPegId, String minute) {
		return findImagesByInteger(minute, minuteValues.get(javaPegId), MetaDataValueFieldName.MINUTE);
	}

	public Set<File> findImagesBySecond(String javaPegId, String second) {
		return findImagesByInteger(second, secondValues.get(javaPegId), MetaDataValueFieldName.SECOND);
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
		Set<Double> fNumberValuesKeys = new TreeSet<Double>(fNumberValues.get(javaPegId).keySet());
		Iterator<Double> iterator = fNumberValuesKeys.iterator();

		List<Double> fNumberValuesToGet = new ArrayList<Double>();

		FindBy.fNumber(fNumber, iterator, fNumberValuesToGet);

		Set<File> imagePaths = new HashSet<File>();

		for (Double fNumberValue : fNumberValuesToGet) {
			Set<Integer> indexForImagePaths = fNumberValues.get(javaPegId).get(fNumberValue);
			populateImagePathsSet(indexForImagePaths, imagePaths);
		}
		return imagePaths;
	}

	public Set<File> findImagesByCameraModel(String javaPegId, String cameraModel) {

		Set<String> cameraModelKeys = new TreeSet<String>(cameraModels.get(javaPegId).keySet());
		Iterator<String> iterator = cameraModelKeys.iterator();

		List<String> cameraModelsToGet = new ArrayList<String>();

		FindBy.cameraModel(cameraModel, iterator, cameraModelsToGet);

		Set<File> imagePaths = new HashSet<File>();

		for (String cameraModelString : cameraModelsToGet) {
			Set<Integer> indexForImagePaths = cameraModels.get(javaPegId).get(cameraModelString);
			populateImagePathsSet(indexForImagePaths, imagePaths);
		}
		return imagePaths;
	}

	public Set<File> findImagesByImageSize(String javaPegId, String imageSize) {

		Set<String> imageSizeValuesKeys = new TreeSet<String>(imageSizeValues.get(javaPegId).keySet());
		Iterator<String> iterator = imageSizeValuesKeys.iterator();

		List<String> imageSizeValuesToGet = new ArrayList<String>();

		FindBy.imageSize(imageSize, iterator, imageSizeValuesToGet);

		Set<File> imagePaths = new HashSet<File>();

		for (String imageSizeValue : imageSizeValuesToGet) {
			Set<Integer> indexForImagePaths = imageSizeValues.get(javaPegId).get(imageSizeValue);
			populateImagePathsSet(indexForImagePaths, imagePaths);
		}
		return imagePaths;
	}

	public Set<File> findImagesByIso(String javaPegId, String iso) {

		Set<Integer> isoValuesKeys = new TreeSet<Integer>(isoValues.get(javaPegId).keySet());
		Iterator<Integer> iterator = isoValuesKeys.iterator();

		List<Integer> isoValuesToGet = new ArrayList<Integer>();

		FindBy.iso(iso, iterator, isoValuesToGet);

		Set<File> imagePaths = new HashSet<File>();

		for (Integer isoValue : isoValuesToGet) {
			Set<Integer> indexForImagePaths = isoValues.get(javaPegId).get(isoValue);
			populateImagePathsSet(indexForImagePaths, imagePaths);
		}
		return imagePaths;
	}

	public Set<File> findImagesByRating(String javaPegId, boolean[] ratingSelection) {
		Set<File> imagePaths = new HashSet<File>();

		for (int i = 0; i < ratingSelection.length; i++) {
			if (ratingSelection[i] == true) {
				Set<Integer> indexForImagePaths = ratings.get(javaPegId).get(i);
				populateImagePathsSet(indexForImagePaths, imagePaths);
			}
		}
		return imagePaths;
	}

	public Set<File> findImagesByExposureTime(String javaPegId, String exposureTime) {

		Set<String> exposureTimeValuesKeys = new TreeSet<String>(exposureTimeValues.get(javaPegId).keySet());
		Iterator<String> iterator = exposureTimeValuesKeys.iterator();

		List<String> exposureTimeValuesToGet = new ArrayList<String>();

		FindBy.exposureTime(exposureTime, iterator, exposureTimeValuesToGet);

		Set<File> imagePaths = new HashSet<File>();

		for (String exposureTimeValue : exposureTimeValuesToGet) {
			Set<Integer> indexForImagePaths = exposureTimeValues.get(javaPegId).get(exposureTimeValue);
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
