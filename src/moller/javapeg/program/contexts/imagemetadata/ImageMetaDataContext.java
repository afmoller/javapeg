package moller.javapeg.program.contexts.imagemetadata;

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
	
	SimpleDateFormat sdf;
	
	Map<String, Integer> imagePathAndIndex;
	int imagePathIndex;
	
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
		
		ratings = new ArrayList<Set<Integer>>(5);
		
		for (int i = 0; i < ratings.size(); i++) {
			ratings.add(new HashSet<Integer>());
		}
		
		categories = new HashMap<String, Set<Integer>>();
		comments = new HashMap<String, Set<Integer>>();
		
		sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		
		imagePathAndIndex = new HashMap<String, Integer>();
		imagePathIndex = 0;
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
	
	private Integer getIndexForImagePath(String imagePath) {
		Integer index = imagePathAndIndex.get(imagePath);
		
		if (null == index) {
			imagePathIndex++;
			index = new Integer(imagePathIndex);
			imagePathAndIndex.put(imagePath, index);
		} 
		return index;
	}
	
	public Set<String> getImagePaths() {
		return imagePathAndIndex.keySet();
	}
	
	public void addCameraModel(String cameraModel, String imagePath) {
		if (!cameraModels.containsKey(cameraModel)) {
			cameraModels.put(cameraModel, new HashSet<Integer>());
		}
		cameraModels.get(cameraModel).add(getIndexForImagePath(imagePath));
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
		dateTimeValues.get(dateTimeString).add(getIndexForImagePath(imagePath));
		
		Integer year = Integer.parseInt(getYear.format(dateTime));
		if (!yearValues.containsKey(year)) {
			yearValues.put(year, new HashSet<Integer>());
		}
		yearValues.get(year).add(getIndexForImagePath(imagePath));
		
		Integer month = Integer.parseInt(getMonth.format(dateTime));
		if (!monthValues.containsKey(month)) {
			monthValues.put(month, new HashSet<Integer>());
		}
		monthValues.get(month).add(getIndexForImagePath(imagePath));
		
		Integer date = Integer.parseInt(getDate.format(dateTime));
		if (!dateValues.containsKey(date)) {
			dateValues.put(date, new HashSet<Integer>());
		}
		dateValues.get(date).add(getIndexForImagePath(imagePath));
		
		Integer hour = Integer.parseInt(getHour.format(dateTime));
		if (!hourValues.containsKey(hour)) {
			hourValues.put(hour, new HashSet<Integer>());
		}
		hourValues.get(hour).add(getIndexForImagePath(imagePath));
		
		Integer minute = Integer.parseInt(getMinute.format(dateTime));
		if (!minuteValues.containsKey(minute)) {
			minuteValues.put(minute, new HashSet<Integer>());
		}
		minuteValues.get(minute).add(getIndexForImagePath(imagePath));
		
		Integer second = Integer.parseInt(getSecond.format(dateTime));
		if (!secondValues.containsKey(second)) {
			secondValues.put(second, new HashSet<Integer>());
		}
		secondValues.get(second).add(getIndexForImagePath(imagePath));
	}
	
	public void addIso(Integer iso, String imagePath) {
		if (!isoValues.containsKey(iso)) {
			isoValues.put(iso, new HashSet<Integer>());
		}
		isoValues.get(iso).add(getIndexForImagePath(imagePath));
	}
	
	public void addShutterSpeed(ShutterSpeed shutterSpeed, String imagePath) {
		if (!shutterSpeedValues.containsKey(shutterSpeed.toString())) {
			shutterSpeedValues.put(shutterSpeed.toString(), new HashSet<Integer>());
			shutterSpeedStringShutterSpeedMappings.put(shutterSpeed.toString(), shutterSpeed);
		}
		shutterSpeedValues.get(shutterSpeed.toString()).add(getIndexForImagePath(imagePath));
	}
	
	public void addAperture(String aperture, String imagePath) {
		if (!apertureValues.containsKey(aperture)) {
			apertureValues.put(aperture, new HashSet<Integer>());
		}
		apertureValues.get(aperture).add(getIndexForImagePath(imagePath));
	}
	
	public void addRating(int rating, String imagePath) {
		if (rating > 0 && rating <= ratings.size())
		ratings.get(rating - 1).add(getIndexForImagePath(imagePath));
	}
	
	public void addCategory(String categoryId, String imagePath) {
		if (!categories.containsKey(categoryId)) {
			categories.put(categoryId, new HashSet<Integer>());
		}
		categories.get(categoryId).add(getIndexForImagePath(imagePath));
	}
	
	public void addComment(String comment, String imagePath) {
		if (!comments.containsKey(comment)) {
			comments.put(comment, new HashSet<Integer>());
		}
		comments.get(comment).add(getIndexForImagePath(imagePath));
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
}