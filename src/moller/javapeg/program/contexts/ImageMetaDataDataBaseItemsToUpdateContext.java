package moller.javapeg.program.contexts;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moller.javapeg.program.imagemetadata.ImageMetaDataItem;

public class ImageMetaDataDataBaseItemsToUpdateContext {

    /**
     * The static singleton instance of this class.
     */
    private static ImageMetaDataDataBaseItemsToUpdateContext instance;

    private Map<File, ImageMetaDataItem> imageMetaDataDataBaseItems;

    private File repositoryPath;
    private File currentlySelectedImage;

    private boolean flushNeeded;

    /**
     * Private constructor.
     */
    private ImageMetaDataDataBaseItemsToUpdateContext() {
        imageMetaDataDataBaseItems = new HashMap<File, ImageMetaDataItem>();
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
        flushNeeded = false;
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

    public void setImageMetaDataItems(List<ImageMetaDataItem> imageMetaDataItems) {
        if (imageMetaDataItems == null) {
            this.imageMetaDataDataBaseItems = null;
        } else {
            for (ImageMetaDataItem imageMetaDataItem : imageMetaDataItems) {
                this.imageMetaDataDataBaseItems.put(imageMetaDataItem.getImage(), imageMetaDataItem);
            }
        }
    }

    public Map<File, ImageMetaDataItem> getImageMetaDataBaseItems() {
        return imageMetaDataDataBaseItems;
    }

    /**
     * This method will return an {@link ImageMetaDataItem} or null if
     * the requested jpegImage does not exist in the collection of
     * {@link ImageMetaDataItem} objects.
     *
     * @param jpegImage is the image to retrieve the
     *        {@link ImageMetaDataItem} object for.
     *
     * @return an {@link ImageMetaDataItem} object or null.
     */
    public ImageMetaDataItem getImageMetaDataBaseItem(File jpegImage) {
        return imageMetaDataDataBaseItems.get(jpegImage);
    }

    /**
     * @param jpegFile
     * @param imageMetaDataDataBaseItem
     */
    public void setImageMetaDatadataBaseItem(File jpegImage, ImageMetaDataItem imageMetaDataDataBaseItem) {
        imageMetaDataDataBaseItems.put(jpegImage, imageMetaDataDataBaseItem);
    }

    public boolean isFlushNeeded() {
        return flushNeeded;
    }

    public void setFlushNeeded(boolean flushNeeded) {
        this.flushNeeded = flushNeeded;
    }
}
