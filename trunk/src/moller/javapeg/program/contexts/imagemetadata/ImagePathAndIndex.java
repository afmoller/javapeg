package moller.javapeg.program.contexts.imagemetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ImagePathAndIndex {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static ImagePathAndIndex instance;
	
	private Map<Integer, String> indexAndImagePath;
	private Map<String, Integer> imagePathAndIndex;
	private int imagePathIndex;
	
	/**
	 * Private constructor.
	 */
	private ImagePathAndIndex() {
		indexAndImagePath = new HashMap<Integer, String>();
		imagePathAndIndex = new HashMap<String, Integer>();
		imagePathIndex = 0;
	}
	
	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static ImagePathAndIndex getInstance() {
		if (instance != null)
			return instance;
		synchronized (ImagePathAndIndex.class) {
			if (instance == null) {
				instance = new ImagePathAndIndex();
			}
			return instance;
		}
	}
	
	public Integer getIndexForImagePath(String imagePath) {
		Integer index = imagePathAndIndex.get(imagePath);
		
		if (null == index) {
			imagePathIndex++;
			index = new Integer(imagePathIndex);
			imagePathAndIndex.put(imagePath, index);
			indexAndImagePath.put(index, imagePath);
		} 
		return index;
	}
	
	public Set<String> getImagePaths() {
		return imagePathAndIndex.keySet();
	}
	
	public String getImagePathForIndex(Integer index) {
		return indexAndImagePath.get(index);
	}
}
