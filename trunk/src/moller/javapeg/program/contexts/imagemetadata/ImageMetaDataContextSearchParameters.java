package moller.javapeg.program.contexts.imagemetadata;

import moller.javapeg.program.categories.Categories;

public class ImageMetaDataContextSearchParameters {
	
	private String cameraModel;
	private String comment;
	private Categories categories;
	private String iso;
	private String shutterSpeed;
	private String imageSize;
	private boolean[] ratings;
	
	/**
	 * Indicates whether the selected categories (if any) should be evaluated
	 * as AND or OR. Meaning that all selected categories must be assigned to
	 * an image if the the image shall be added to the search result, if this 
	 * parameter is set to true.
	 */
	private boolean andCategoriesSearch;
	
	public ImageMetaDataContextSearchParameters() {
		super();
		this.cameraModel = null;
		this.comment = null;
		this.categories = null;
		this.iso = null;
		this.shutterSpeed = null;
		this.imageSize = null;
		this.ratings = null;
		this.andCategoriesSearch = false;
	}
	
	public boolean isAndCategoriesSearch() {
		return andCategoriesSearch;
	}

	public String getCameraModel() {
		return cameraModel;
	}
	
	public String getComment() {
		return comment;
	}

	public Categories getCategories() {
		return categories;
	}
	
	public String getImageSize() {
		return imageSize;
	}
	
	public String getIso() {
		return iso;
	}
	
	public boolean[] getRatings() {
		return ratings;
	}
	
	public String getShutterSpeed() {
		return shutterSpeed;
	}
	
	public void setAndCategoriesSearch(boolean andCategoriesSearch) {
		this.andCategoriesSearch = andCategoriesSearch;
	}

	public void setCameraModel(String cameraModel) {
		if (!cameraModel.equals("")) {
			this.cameraModel = cameraModel;	
		}
	}
	
	public void setComment(String comment) {
		if (!comment.equals("")) {
			this.comment = comment;	
		}
	}

	public void setCategories(Categories categories) {
		if (categories.size() > 0) {
			this.categories = categories;
		}
	}
	
	public void setImageSize(String imageSize) {
		if (!imageSize.equals("")) {
			this.imageSize  = imageSize;
		}
	}
	
	public void setIso(String iso) {
		if (!iso.equals("")) {
			this.iso  = iso;
		}
	}
	
	public void setRating(boolean[] ratings) {
		this.ratings = ratings;
	}
	
	public void setShutterSpeed(String shutterSpeed) {
		if (!shutterSpeed.equals("")) {
			this.shutterSpeed  = shutterSpeed;
		}
	}	
}
