package moller.javapeg.program.imagemetadata;

import java.io.File;

import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.categories.CategoryImageExifMetaData;

public class ImageMetaDataDataBaseItem {

    private File image;
    private CategoryImageExifMetaData imageExifMetaData;
    private String comment;
    private int rating;
    private Categories categories;

    public ImageMetaDataDataBaseItem(File image, CategoryImageExifMetaData imageExifMetaData, String comment,int rating, Categories categories) {
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
        if (categories != null) {
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
