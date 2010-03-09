package moller.javapeg.program.contexts;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import moller.javapeg.program.categories.ImageMetaDataDataBaseItem;

public class ImageRepositoryContext {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static ImageRepositoryContext instance;
	
	private Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems;
	
	/**
	 * Private constructor.
	 */
	private ImageRepositoryContext() {
		imageMetaDataDataBaseItems = new HashMap<File, ImageMetaDataDataBaseItem>();
	}

	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static ImageRepositoryContext getInstance() {
		if (instance != null)
			return instance;
		synchronized (ImageRepositoryContext.class) {
			if (instance == null) {
				instance = new ImageRepositoryContext();
			}
			return instance;
		}
	}

	public void setImageMetaDataBaseItems(Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems) {
		this.imageMetaDataDataBaseItems.clear();
		this.imageMetaDataDataBaseItems.putAll(imageMetaDataDataBaseItems);
	}

	/**
	 * This method will return an {@link ImageMetaDataDataBaseItem} or null if 
	 * the requested jpegImage does not exist in the collection of 
	 * {@link ImageMetaDataDataBaseItem} objects.
	 * 
	 * @param jpegImage is the image to retrieve the 
	 *        {@link ImageMetaDataDataBaseItem} object for.
	 *        
	 * @return an {@link ImageMetaDataDataBaseItem} object or null.
	 */
	public ImageMetaDataDataBaseItem getImageMetaDataBaseItem(File jpegImage) {
		return imageMetaDataDataBaseItems.get(jpegImage);
	}
}
