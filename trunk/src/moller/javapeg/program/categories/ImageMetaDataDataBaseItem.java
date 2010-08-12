package moller.javapeg.program.categories;

import java.io.File;
import java.util.List;

public class ImageMetaDataDataBaseItem {
    
    private File image;
    private ImageExifMetaData imageExifMetaData;
    private String comment;
    private int rating;
    private List<Tag> tags;
    
    public ImageMetaDataDataBaseItem(File image, ImageExifMetaData imageExifMetaData, String comment,int rating, List<Tag> tags) {
        this.image = image;
        this.imageExifMetaData = imageExifMetaData;
        this.comment = comment;
        this.rating = rating;
        this.tags = tags;
    }
    
    public ImageMetaDataDataBaseItem() {
        this.image = null;
        this.imageExifMetaData = null;
        this.comment = null;
        this.rating = 0;
        this.tags = null;
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

    public List<Tag> getTags() {
        return tags;
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

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
