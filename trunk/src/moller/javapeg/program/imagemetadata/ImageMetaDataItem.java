package moller.javapeg.program.imagemetadata;

import java.io.File;

import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.categories.CategoryImageExifMetaData;

public class ImageMetaDataItem {

    private File image;
    private CategoryImageExifMetaData imageExifMetaData;
    private String comment;
    private int rating;
    private Categories categories;

    public ImageMetaDataItem(File image, CategoryImageExifMetaData imageExifMetaData, String comment,int rating, Categories categories) {
        this.image = image;
        this.imageExifMetaData = imageExifMetaData;
        this.comment = comment;
        this.rating = rating;
        this.categories = categories;
    }

    public ImageMetaDataItem() {
        this.image = null;
        this.imageExifMetaData = null;
        this.comment = null;
        this.rating = 0;
        this.categories = null;
    }

    public File getImage() {
        return image;
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

    /**
     * This method returns a boolean value indicating whether the user entered
     * part of this object has changed.
     *
     * @param categories
     *            is the {@link Categories} object to compare this objects
     *            categories object with.
     * @param comment
     *            is the comment to compare this objects comment with.
     * @param rating
     *            is the rating to compare this objects rating to.
     * @return true if any of the incoming parameters are unequal to the
     *         parameters of this object, otherwise false.
     */
    public boolean hasChanged(Categories categories, String comment, int rating) {
        if (this.categories != null) {
            if (this.categories.getCategories().isEmpty()) {
                if (categories != null) {
                    return true;
                }
            } else {
                if (!this.getCategories().equals(categories)) {
                    return true;
                }
            }
        } else {
            if (categories != null) {
                return true;
            }
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
