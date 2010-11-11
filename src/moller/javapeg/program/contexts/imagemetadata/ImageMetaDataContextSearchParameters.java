package moller.javapeg.program.contexts.imagemetadata;

import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.logger.Logger;
import moller.util.datatype.ShutterSpeed;

public class ImageMetaDataContextSearchParameters {
	
	private static Logger logger = Logger.getInstance();
	
	private String cameraModel;
	private String comment;
	private Categories categories;
	private int iso;
	private ShutterSpeed shutterSpeed;
	private boolean[] ratings;
	
	public ImageMetaDataContextSearchParameters() {
		super();
		this.cameraModel = null;
		this.comment = null;
		this.categories = null;
		this.iso = -1;
		this.shutterSpeed = null;
		this.ratings = null;
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
	
	public int getIso() {
		return iso;
	}
	
	public boolean[] getRatings() {
		return ratings;
	}
	
	public ShutterSpeed getShutterSpeed() {
		return shutterSpeed;
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
	
	public void setIso(String iso) {
		if (!iso.equals("")) {
			try {
				this.iso = Integer.parseInt(iso);	
			} catch (NumberFormatException nfex) {
				logger.logERROR("Could not parse String " + iso + " to an integer, see stacktrace below for details");
				logger.logERROR(nfex);
				this.iso  = -1;
			}
		}
	}
	
	public void setRating(boolean[] ratings) {
		this.ratings = ratings;
	}
	
	public void setShutterSpeed(ShutterSpeed shutterSpeed) {
		this.shutterSpeed = shutterSpeed;
	}	
}
