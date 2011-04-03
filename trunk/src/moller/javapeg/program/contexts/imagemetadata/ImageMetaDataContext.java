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
import moller.javapeg.program.datatype.ImageSize;
import moller.javapeg.program.datatype.ExposureTime;
import moller.javapeg.program.enumerations.MetaDataValueFieldName;


public class ImageMetaDataContext {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static ImageMetaDataContext instance;
	
	private Map<String, ImageSize> imageSizeStringImageSizeMappings;
	private Map<String, ExposureTime> exposureTimeStringExposureTimeMappings;
	
	/**
	 * Image Exif Meta Data
	 */
	private Map<String, Set<Integer>> cameraModels;
	private Map<String, Set<Integer>> dateTimeValues;
	
	private Map<Integer, Set<Integer>> yearValues;
	private Map<Integer, Set<Integer>> monthValues;
	private Map<Integer, Set<Integer>> dateValues;
	private Map<Integer, Set<Integer>> hourValues;
	private Map<Integer, Set<Integer>> minuteValues;
	private Map<Integer, Set<Integer>> secondValues;
	
	private Map<Integer, Set<Integer>> isoValues;
	private Map<String, Set<Integer>> imageSizeValues;
	private Map<String, Set<Integer>> exposureTimeValues;
	private Map<Double, Set<Integer>> apertureValues;
	
	private List<Set<Integer>> ratings; 
	private Map<String, Set<Integer>> categories;
	private Map<String, Set<Integer>> comments;
	
	private SimpleDateFormat sdf;
	
	/**
	 * Private constructor.
	 */
	private ImageMetaDataContext() {
		
		exposureTimeStringExposureTimeMappings = new HashMap<String, ExposureTime>();
		imageSizeStringImageSizeMappings = new HashMap<String, ImageSize>();
		
		cameraModels = new HashMap<String, Set<Integer>>();
		dateTimeValues = new HashMap<String, Set<Integer>>();
		yearValues = new HashMap<Integer, Set<Integer>>();
		monthValues = new HashMap<Integer, Set<Integer>>();
		dateValues = new HashMap<Integer, Set<Integer>>();
		hourValues = new HashMap<Integer, Set<Integer>>();
		minuteValues = new HashMap<Integer, Set<Integer>>();
		secondValues = new HashMap<Integer, Set<Integer>>();
		isoValues = new HashMap<Integer, Set<Integer>>();
		imageSizeValues = new HashMap<String, Set<Integer>>();
		exposureTimeValues = new HashMap<String, Set<Integer>>();
		apertureValues = new HashMap<Double, Set<Integer>>();
		
		ratings = new ArrayList<Set<Integer>>();
		
		for (int i = 0; i <= 5; i++) {
			ratings.add(new HashSet<Integer>());
		}
		
		categories = new HashMap<String, Set<Integer>>();
		comments = new HashMap<String, Set<Integer>>();
		
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
	
	public void addCameraModel(String cameraModel, String imagePath) {
		if (!cameraModels.containsKey(cameraModel)) {
			cameraModels.put(cameraModel, new HashSet<Integer>());
		}
		cameraModels.get(cameraModel).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}
	
	public void addDateTime(Date dateTime, String imagePath) {
		SimpleDateFormat getYear = new SimpleDateFormat("yyyy");
		SimpleDateFormat getMonth = new SimpleDateFormat("MM");
		SimpleDateFormat getDate = new SimpleDateFormat("dd");
		SimpleDateFormat getHour = new SimpleDateFormat("HH");
		SimpleDateFormat getMinute = new SimpleDateFormat("mm");
		SimpleDateFormat getSecond = new SimpleDateFormat("ss");
		
		String dateTimeString = sdf.format(dateTime); 
		if (!dateTimeValues.containsKey(dateTimeString)) {
			dateTimeValues.put(dateTimeString, new HashSet<Integer>());
		}
		dateTimeValues.get(dateTimeString).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
		
		Integer year = Integer.parseInt(getYear.format(dateTime));
		if (!yearValues.containsKey(year)) {
			yearValues.put(year, new HashSet<Integer>());
		}
		yearValues.get(year).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
		
		Integer month = Integer.parseInt(getMonth.format(dateTime));
		if (!monthValues.containsKey(month)) {
			monthValues.put(month, new HashSet<Integer>());
		}
		monthValues.get(month).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
		
		Integer date = Integer.parseInt(getDate.format(dateTime));
		if (!dateValues.containsKey(date)) {
			dateValues.put(date, new HashSet<Integer>());
		}
		dateValues.get(date).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
		
		Integer hour = Integer.parseInt(getHour.format(dateTime));
		if (!hourValues.containsKey(hour)) {
			hourValues.put(hour, new HashSet<Integer>());
		}
		hourValues.get(hour).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
		
		Integer minute = Integer.parseInt(getMinute.format(dateTime));
		if (!minuteValues.containsKey(minute)) {
			minuteValues.put(minute, new HashSet<Integer>());
		}
		minuteValues.get(minute).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
		
		Integer second = Integer.parseInt(getSecond.format(dateTime));
		if (!secondValues.containsKey(second)) {
			secondValues.put(second, new HashSet<Integer>());
		}
		secondValues.get(second).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}
	
	public void addIso(Integer iso, String imagePath) {
		if (iso != -1) {
			if (!isoValues.containsKey(iso)) {
				isoValues.put(iso, new HashSet<Integer>());
			}
			isoValues.get(iso).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));	
		}
	}
	
	public void addExposureTime(ExposureTime exposureTime, String imagePath) {
		if (!exposureTimeValues.containsKey(exposureTime.toString())) {
			exposureTimeValues.put(exposureTime.toString(), new HashSet<Integer>());
			exposureTimeStringExposureTimeMappings.put(exposureTime.toString(), exposureTime);
		}
		exposureTimeValues.get(exposureTime.toString()).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}
	
	public void addImageSize(ImageSize imageSize, String imagePath) {
		if (!imageSizeValues.containsKey(imageSize.toString())) {
			imageSizeValues.put(imageSize.toString(), new HashSet<Integer>());
			imageSizeStringImageSizeMappings.put(imageSize.toString(), imageSize);
		}
		imageSizeValues.get(imageSize.toString()).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}
	
	public void addAperture(double apertureValue, String imagePath) {
		if (!apertureValues.containsKey(apertureValue)) {
			apertureValues.put(apertureValue, new HashSet<Integer>());
		}
		apertureValues.get(apertureValue).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}
	
	public void addRating(int rating, String imagePath) {
		if (rating < ratings.size()) {
			ratings.get(rating).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
		}
	}
	
	public void addCategory(String categoryId, String imagePath) {
		if (!categories.containsKey(categoryId)) {
			categories.put(categoryId, new HashSet<Integer>());
		}
		categories.get(categoryId).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}
	
	public void addComment(String comment, String imagePath) {
		if (!comments.containsKey(comment)) {
			comments.put(comment, new HashSet<Integer>());
		}
		comments.get(comment).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}
	
	
	public Map<String, Set<Integer>> getComments() {
		return comments;
	}
	
	public List<Set<Integer>> getRatings() {
		return ratings;
	}
	
	public Map<String, Set<Integer>> getCategories() {
		return categories;
	}
	
	public Set<String> getCameraModels() {
		return new TreeSet<String>(cameraModels.keySet());
	}
	
	public Set<Integer> getYears() {
		return new TreeSet<Integer>(yearValues.keySet());
	}
	
	public Set<Integer> getMonths() {
		return new TreeSet<Integer>(monthValues.keySet());
	}
	
	public Set<Integer> getDates() {
		return new TreeSet<Integer>(dateValues.keySet());
	}
	
	public Set<Integer> getHours() {
		return new TreeSet<Integer>(hourValues.keySet());
	}
	
	public Set<Integer> getMinutes() {
		return new TreeSet<Integer>(minuteValues.keySet());
	}
	
	public Set<Integer> getSeconds() {
		return new TreeSet<Integer>(secondValues.keySet());
	}
	
	public Set<Integer> getIsoValues() {
		return new TreeSet<Integer>(isoValues.keySet());
	}
	
	public Set<Double> getApertureValues() {
		return new TreeSet<Double>(apertureValues.keySet());
	}
	
	public Set<ExposureTime> getExposureTimeValues() {
		Set<ExposureTime> exposureTimeObjects = new TreeSet<ExposureTime>();
		
		for (String exposureTimeValue : exposureTimeValues.keySet()) {
			exposureTimeObjects.add(exposureTimeStringExposureTimeMappings.get(exposureTimeValue));
		}
		return exposureTimeObjects;
	}
	
	public Set<ImageSize> getImageSizeValues() {
		Set<ImageSize> imageSizeObjects = new TreeSet<ImageSize>();
		
		for (String imageSizeValue : imageSizeValues.keySet()) {
			imageSizeObjects.add(imageSizeStringImageSizeMappings.get(imageSizeValue));
		}
		return imageSizeObjects;
	}
	
	public Set<File> findImagesByComment(String commentToSearchFor) {
		Set<File> imagePaths = new HashSet<File>();
		
		for (String comment : comments.keySet()) {
			if (comment.toLowerCase().contains(commentToSearchFor.toLowerCase())) {
				Set<Integer> indexForImagePaths = comments.get(comment);
				populateImagePathsSet(indexForImagePaths, imagePaths);		
			}
		}
		return imagePaths;	}
	
	public Set<File> findImagesByCategory(Categories c, boolean isAndCategoriesSearch) {
		Set<File> imagePaths = new HashSet<File>();
		
		if (isAndCategoriesSearch) {
			List<Set<File>> searchResults = new ArrayList<Set<File>>();
			
			for (String categoryId : c.getCategories()) {
				searchResults.add(findImagesByCategory(new Categories(new String[] {categoryId}), false));
			}
			
			imagePaths = ImageMetaDataContextUtil.compileSearchResult(searchResults);
		} else {
			for (String categroyId : c.getCategories()) {
				Set<Integer> indexForImagePaths = categories.get(categroyId);
				populateImagePathsSet(indexForImagePaths, imagePaths);
			}	
		}
		return imagePaths;
	}
	
	public Set<File> findImagesByYear(String year) {
		return findImagesByInteger(year, yearValues, MetaDataValueFieldName.YEAR);
	}

	public Set<File> findImagesByMonth(String month) {
		return findImagesByInteger(month, monthValues, MetaDataValueFieldName.MONTH);
	}

	public Set<File> findImagesByDay(String day) {
		return findImagesByInteger(day, dateValues, MetaDataValueFieldName.DAY);
	}
	
	public Set<File> findImagesByHour(String hour) {
		return findImagesByInteger(hour, hourValues, MetaDataValueFieldName.HOUR);
	}

	public Set<File> findImagesByMinute(String minute) {
		return findImagesByInteger(minute, minuteValues, MetaDataValueFieldName.MINUTE);
	}

	public Set<File> findImagesBySecond(String second) {
		return findImagesByInteger(second, secondValues, MetaDataValueFieldName.SECOND);
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
		}
								
		Set<File> imagePaths = new HashSet<File>();
		
		for (Integer value : valuesToGet) {
			Set<Integer> indexForImagePaths = values.get(value);
			populateImagePathsSet(indexForImagePaths, imagePaths);
		}	
		return imagePaths;
	}	
	
	public Set<File> findImagesByApertureValue(String apertureValue) {
		Set<Double> apertureValueValuesKeys = new TreeSet<Double>(apertureValues.keySet());
		Iterator<Double> iterator = apertureValueValuesKeys.iterator();
		
		List<Double> apertureValueValuesToGet = new ArrayList<Double>(); 
			
		FindBy.apertureValue(apertureValue, iterator, apertureValueValuesToGet);
				
		Set<File> imagePaths = new HashSet<File>();
		
		for (Double apertureValueValue : apertureValueValuesToGet) {
			Set<Integer> indexForImagePaths = apertureValues.get(apertureValueValue);
			populateImagePathsSet(indexForImagePaths, imagePaths);
		}	
		return imagePaths;
	}
	
	public Set<File> findImagesByCameraModel(String cameraModel) {
		
		Set<String> cameraModelKeys = new TreeSet<String>(cameraModels.keySet());
		Iterator<String> iterator = cameraModelKeys.iterator();
		
		List<String> cameraModelsToGet = new ArrayList<String>(); 
			
		FindBy.cameraModel(cameraModel, iterator, cameraModelsToGet);
				
		Set<File> imagePaths = new HashSet<File>();
		
		for (String cameraModelString : cameraModelsToGet) {
			Set<Integer> indexForImagePaths = cameraModels.get(cameraModelString);
			populateImagePathsSet(indexForImagePaths, imagePaths);
		}		
		return imagePaths;
	}
	
	public Set<File> findImagesByImageSize(String imageSize) {
		
		Set<String> imageSizeValuesKeys = new TreeSet<String>(imageSizeValues.keySet());
		Iterator<String> iterator = imageSizeValuesKeys.iterator();
		
		List<String> imageSizeValuesToGet = new ArrayList<String>(); 
			
		FindBy.imageSize(imageSize, iterator, imageSizeValuesToGet);
				
		Set<File> imagePaths = new HashSet<File>();
		
		for (String imageSizeValue : imageSizeValuesToGet) {
			Set<Integer> indexForImagePaths = imageSizeValues.get(imageSizeValue);
			populateImagePathsSet(indexForImagePaths, imagePaths);
		}	
		return imagePaths;
	}

	public Set<File> findImagesByIso(String iso) {
		
		Set<Integer> isoValuesKeys = new TreeSet<Integer>(isoValues.keySet());
		Iterator<Integer> iterator = isoValuesKeys.iterator();
		
		List<Integer> isoValuesToGet = new ArrayList<Integer>(); 
			
		FindBy.iso(iso, iterator, isoValuesToGet);
				
		Set<File> imagePaths = new HashSet<File>();
		
		for (Integer isoValue : isoValuesToGet) {
			Set<Integer> indexForImagePaths = isoValues.get(isoValue);
			populateImagePathsSet(indexForImagePaths, imagePaths);
		}	
		return imagePaths;
	}
			
	public Set<File> findImagesByRating(boolean[] ratingSelection) {
		Set<File> imagePaths = new HashSet<File>();
		
		for (int i = 0; i < ratingSelection.length; i++) {
			if (ratingSelection[i] == true) {
				Set<Integer> indexForImagePaths = ratings.get(i);
				populateImagePathsSet(indexForImagePaths, imagePaths);		
			}
		}
		return imagePaths;
	}
	
	public Set<File> findImagesByExposureTime(String exposureTime) {
		
		Set<String> exposureTimeValuesKeys = new TreeSet<String>(exposureTimeValues.keySet());
		Iterator<String> iterator = exposureTimeValuesKeys.iterator();
		
		List<String> exposureTimeValuesToGet = new ArrayList<String>(); 
			
		FindBy.exposureTime(exposureTime, iterator, exposureTimeValuesToGet);
				
		Set<File> imagePaths = new HashSet<File>();
		
		for (String exposureTimeValue : exposureTimeValuesToGet) {
			Set<Integer> indexForImagePaths = exposureTimeValues.get(exposureTimeValue);
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
