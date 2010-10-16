package moller.javapeg.program.contexts;

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
	private Map<String, Set<String>> cameraModels;
	private Map<String, Set<String>> dateTimeValues;
	
	private Map<Integer, Set<String>> yearValues;
	private Map<Integer, Set<String>> monthValues;
	private Map<Integer, Set<String>> dateValues;
	private Map<Integer, Set<String>> hourValues;
	private Map<Integer, Set<String>> minuteValues;
	private Map<Integer, Set<String>> secondValues;
	
	private Map<Integer, Set<String>> isoValues;
	private Map<String, Set<String>> shutterSpeedValues;
	private Map<String, Set<String>> apertureValues;
	
	private List<Set<String>> ratings; 
	private Map<String, Set<String>> categories;
	private Map<String, Set<String>> comments;
	
	SimpleDateFormat sdf;

	
	
	
	/**
	 * Private constructor.
	 */
	private ImageMetaDataContext() {
		
		shutterSpeedStringShutterSpeedMappings = new HashMap<String, ShutterSpeed>();
		
		cameraModels = new HashMap<String, Set<String>>();
		dateTimeValues = new HashMap<String, Set<String>>();
		yearValues = new HashMap<Integer, Set<String>>();
		monthValues = new HashMap<Integer, Set<String>>();
		dateValues = new HashMap<Integer, Set<String>>();
		hourValues = new HashMap<Integer, Set<String>>();
		minuteValues = new HashMap<Integer, Set<String>>();
		secondValues = new HashMap<Integer, Set<String>>();
		isoValues = new HashMap<Integer, Set<String>>();
		shutterSpeedValues = new HashMap<String, Set<String>>();
		apertureValues = new HashMap<String, Set<String>>();
		
		ratings = new ArrayList<Set<String>>(5);
		
		for (int i = 0; i < ratings.size(); i++) {
			ratings.add(new HashSet<String>());
		}
		
		categories = new HashMap<String, Set<String>>();
		comments = new HashMap<String, Set<String>>();
		
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
			cameraModels.put(cameraModel, new HashSet<String>());
		}
		cameraModels.get(cameraModel).add(imagePath);
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
			dateTimeValues.put(dateTimeString, new HashSet<String>());
		}
		dateTimeValues.get(dateTimeString).add(imagePath);
		
		Integer year = Integer.parseInt(getYear.format(dateTime));
		if (!yearValues.containsKey(year)) {
			yearValues.put(year, new HashSet<String>());
		}
		yearValues.get(year).add(imagePath);
		
		Integer month = Integer.parseInt(getMonth.format(dateTime));
		if (!monthValues.containsKey(month)) {
			monthValues.put(month, new HashSet<String>());
		}
		monthValues.get(month).add(imagePath);
		
		Integer date = Integer.parseInt(getDate.format(dateTime));
		if (!dateValues.containsKey(date)) {
			dateValues.put(date, new HashSet<String>());
		}
		dateValues.get(date).add(imagePath);
		
		Integer hour = Integer.parseInt(getHour.format(dateTime));
		if (!hourValues.containsKey(hour)) {
			hourValues.put(hour, new HashSet<String>());
		}
		hourValues.get(hour).add(imagePath);
		
		Integer minute = Integer.parseInt(getMinute.format(dateTime));
		if (!minuteValues.containsKey(minute)) {
			minuteValues.put(minute, new HashSet<String>());
		}
		minuteValues.get(minute).add(imagePath);
		
		Integer second = Integer.parseInt(getSecond.format(dateTime));
		if (!secondValues.containsKey(second)) {
			secondValues.put(second, new HashSet<String>());
		}
		secondValues.get(second).add(imagePath);
		
		
	}
	
	public void addIso(Integer iso, String imagePath) {
		if (!isoValues.containsKey(iso)) {
			isoValues.put(iso, new HashSet<String>());
		}
		isoValues.get(iso).add(imagePath);
	}
	
	public void addShutterSpeed(ShutterSpeed shutterSpeed, String imagePath) {
		if (!shutterSpeedValues.containsKey(shutterSpeed.toString())) {
			shutterSpeedValues.put(shutterSpeed.toString(), new HashSet<String>());
			shutterSpeedStringShutterSpeedMappings.put(shutterSpeed.toString(), shutterSpeed);
		}
		shutterSpeedValues.get(shutterSpeed.toString()).add(imagePath);
	}
	
	public void addAperture(String aperture, String imagePath) {
		if (!apertureValues.containsKey(aperture)) {
			apertureValues.put(aperture, new HashSet<String>());
		}
		apertureValues.get(aperture).add(imagePath);
	}
	
	public void addRating(int rating, String imagePath) {
		if (rating > 0 && rating <= ratings.size())
		ratings.get(rating - 1).add(imagePath);
	}
	
	public void addCategory(String categoryId, String imagePath) {
		if (!categories.containsKey(categoryId)) {
			categories.put(categoryId, new HashSet<String>());
		}
		categories.get(categoryId).add(imagePath);
	}
	
	public void addComment(String comment, String imagePath) {
		if (!comments.containsKey(comment)) {
			comments.put(comment, new HashSet<String>());
		}
		comments.get(comment).add(imagePath);
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
