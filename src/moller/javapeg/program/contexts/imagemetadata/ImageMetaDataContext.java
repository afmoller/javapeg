package moller.javapeg.program.contexts.imagemetadata;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import moller.javapeg.program.categories.Categories;
import moller.util.datatype.ShutterSpeed;


public class ImageMetaDataContext {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static ImageMetaDataContext instance;
	
	private Map<String, ShutterSpeed> shutterSpeedStringShutterSpeedMappings;
	
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
	private Map<String, Set<Integer>> shutterSpeedValues;
	private Map<String, Set<Integer>> apertureValues;
	
	private List<Set<Integer>> ratings; 
	private Map<String, Set<Integer>> categories;
	private Map<String, Set<Integer>> comments;
	
	private SimpleDateFormat sdf;
	
	/**
	 * Private constructor.
	 */
	private ImageMetaDataContext() {
		
		shutterSpeedStringShutterSpeedMappings = new HashMap<String, ShutterSpeed>();
		
		cameraModels = new HashMap<String, Set<Integer>>();
		dateTimeValues = new HashMap<String, Set<Integer>>();
		yearValues = new HashMap<Integer, Set<Integer>>();
		monthValues = new HashMap<Integer, Set<Integer>>();
		dateValues = new HashMap<Integer, Set<Integer>>();
		hourValues = new HashMap<Integer, Set<Integer>>();
		minuteValues = new HashMap<Integer, Set<Integer>>();
		secondValues = new HashMap<Integer, Set<Integer>>();
		isoValues = new HashMap<Integer, Set<Integer>>();
		shutterSpeedValues = new HashMap<String, Set<Integer>>();
		apertureValues = new HashMap<String, Set<Integer>>();
		
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
	
	public void addShutterSpeed(ShutterSpeed shutterSpeed, String imagePath) {
		if (!shutterSpeedValues.containsKey(shutterSpeed.toString())) {
			shutterSpeedValues.put(shutterSpeed.toString(), new HashSet<Integer>());
			shutterSpeedStringShutterSpeedMappings.put(shutterSpeed.toString(), shutterSpeed);
		}
		shutterSpeedValues.get(shutterSpeed.toString()).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
	}
	
	public void addAperture(String aperture, String imagePath) {
		if (!apertureValues.containsKey(aperture)) {
			apertureValues.put(aperture, new HashSet<Integer>());
		}
		apertureValues.get(aperture).add(ImagePathAndIndex.getInstance().getIndexForImagePath(imagePath));
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
	
	public Set<String> getCameraModels() {
		return new TreeSet<String>(cameraModels.keySet());
	}
	
	public Set<String> getYears() {
		Set<Integer> orderedIntegerYears = new TreeSet<Integer>(yearValues.keySet());
		Set<String>  orderedIntegerStrings = new LinkedHashSet<String>();
		
		for (Integer year : orderedIntegerYears) {
			orderedIntegerStrings.add(Integer.toString(year));
		}
		return orderedIntegerStrings;
	}
	
	public Set<Integer> getIsoValues() {
		return new TreeSet<Integer>(isoValues.keySet());
	}
	
	public Set<ShutterSpeed> getShutterSpeedValues() {
		Set<ShutterSpeed> shutterSpeedObjects = new TreeSet<ShutterSpeed>();
		
		for (String shutterSpeedValue : shutterSpeedValues.keySet()) {
			shutterSpeedObjects.add(shutterSpeedStringShutterSpeedMappings.get(shutterSpeedValue));
		}
		return shutterSpeedObjects;
	}
	
	public Set<String> getApertureValues() {
		return new TreeSet<String>(apertureValues.keySet());
	}
	
	public Set<File> findImagesByCameraModel(String cameraModel) {
		Set<File> imagePaths = new HashSet<File>();
		Set<Integer> indexForImagePaths = cameraModels.get(cameraModel);
		
		populateImagePathsSet(indexForImagePaths, imagePaths);
		return imagePaths;
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
	
	public Set<File> findImagesByCategory(Categories c) {
		Set<File> imagePaths = new HashSet<File>();
		
		for (String categroyId : c.getCategories()) {
			Set<Integer> indexForImagePaths = categories.get(categroyId);
			populateImagePathsSet(indexForImagePaths, imagePaths);
		}
		return imagePaths;
	}

	public Set<File> findImagesByIso(int iso) {
		Set<File> imagePaths = new HashSet<File>();
		Set<Integer> indexForImagePaths = isoValues.get(iso);
		
		populateImagePathsSet(indexForImagePaths, imagePaths);
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
	
	public Set<File> findImagesByShutterSpeed(ShutterSpeed shutterSpeed) {
		Set<File> imagePaths = new HashSet<File>();
		Set<Integer> indexForImagePaths = shutterSpeedValues.get(shutterSpeed.toString());
		
		populateImagePathsSet(indexForImagePaths, imagePaths);
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
