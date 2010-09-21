package moller.javapeg.program.categories;

import java.io.File;

public class ImageMetaDataDataBaseItem {
    
    private File image;
    private ImageExifMetaData imageExifMetaData;
    private String comment;
    private int rating;
    private Categories categories;
    
    public ImageMetaDataDataBaseItem(File image, ImageExifMetaData imageExifMetaData, String comment,int rating, Categories categories) {
        this.image = image;
        this.imageExifMetaData = imageExifMetaData;
        this.comment = comment;
        this.rating = rating;
        this.categories = categories;
    }
    
    public ImageMetaDataDataBaseItem() {
        this.image = null;
        this.imageExifMetaData = null;
        this.comment = null;
        this.rating = 0;
        this.categories = null;
    }

    public File getImage() {
        return image;
    }

    public ImageExifMetaData getImageExifMetaData() {
        return imageExifMetaData;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    /**
     * @return
     */
    public Categories getCategories() {
    	return categories;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public void setImageExifMetaData(ImageExifMetaData imageExifMetaData) {
        this.imageExifMetaData = imageExifMetaData;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }
}
