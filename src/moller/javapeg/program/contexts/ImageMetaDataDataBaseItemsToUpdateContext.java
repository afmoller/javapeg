package moller.javapeg.program.contexts;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import moller.javapeg.program.categories.ImageMetaDataDataBaseItem;

public class ImageMetaDataDataBaseItemsToUpdateContext {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static ImageMetaDataDataBaseItemsToUpdateContext instance;
	
	private Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems;
	
	private File repositoryPath;
	private File currentlySelectedImage;
	
	/**
	 * Private constructor.
	 */
	private ImageMetaDataDataBaseItemsToUpdateContext() {
		imageMetaDataDataBaseItems = new HashMap<File, ImageMetaDataDataBaseItem>();
		repositoryPath = null;
	}

	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static ImageMetaDataDataBaseItemsToUpdateContext getInstance() {
		if (instance != null)
			return instance;
		synchronized (ImageMetaDataDataBaseItemsToUpdateContext.class) {
			if (instance == null) {
				instance = new ImageMetaDataDataBaseItemsToUpdateContext();
			}
			return instance;
		}
	}
	
	public void reInit() {
		imageMetaDataDataBaseItems.clear();
		currentlySelectedImage = null;
		repositoryPath = null;
	}
	
	public void setCurrentlySelectedImage(File currentlySelectedImage) {
		this.currentlySelectedImage = currentlySelectedImage;
	}
	
	public File getCurrentlySelectedImage() {
		return currentlySelectedImage;
	}
	
	public void setRepositoryPath(File repositoryPath) {
		this.repositoryPath = repositoryPath;
	}
	
	public File getLoadedRepositoryPath() {
		return repositoryPath;
	}

	public void setImageMetaDataBaseItems(Map<File, ImageMetaDataDataBaseItem> imageMetaDataDataBaseItems) {
		this.imageMetaDataDataBaseItems = imageMetaDataDataBaseItems;
	}

	public Map<File, ImageMetaDataDataBaseItem> getImageMetaDataBaseItems() {
		return imageMetaDataDataBaseItems;
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
	
	/**
	 * @param jpegFile
	 * @param imageMetaDataDataBaseItem
	 */
	public void setImageMetaDatadataBaseItem(File jpegImage, ImageMetaDataDataBaseItem imageMetaDataDataBaseItem) {
		imageMetaDataDataBaseItems.put(jpegImage, imageMetaDataDataBaseItem);
	}
}
