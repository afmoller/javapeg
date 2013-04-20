package moller.javapeg.program.categories;

import java.io.File;

public class ImageMetaDataDataBaseItem {

    private File image;
    private String md5;
    private CategoryImageExifMetaData imageExifMetaData;
    private String comment;
    private int rating;
    private Categories categories;

    public ImageMetaDataDataBaseItem(File image, String md5, CategoryImageExifMetaData imageExifMetaData, String comment,int rating, Categories categories) {
        this.image = image;
        this.md5 = md5;
        this.imageExifMetaData = imageExifMetaData;
        this.comment = comment;
        this.rating = rating;
        this.categories = categories;
    }

    public ImageMetaDataDataBaseItem() {
        this.image = null;
        this.md5 = null;
        this.imageExifMetaData = null;
        this.comment = null;
        this.rating = 0;
        this.categories = null;
    }

    public File getImage() {
        return image;
    }

    public String getMd5() {
        return md5;
    }

    public CategoryImageExifMetaData getImageExifMetaData() {
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

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setImageExifMetaData(CategoryImageExifMetaData imageExifMetaData) {
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


    public boolean hasChanged(Categories categories, String comment, int rating) {
        if (!this.getCategories().equals(categories)) {
            return true;
        }
        if (!this.getComment().equals(comment)) {
            return true;
        }
        if (this.getRating() != rating) {
            return true;
        }
        return false;
    }
}
