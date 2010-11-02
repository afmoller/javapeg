package moller.javapeg.program.contexts.imagemetadata;

import moller.javapeg.program.categories.Categories;

public class ImageMetaDataContextSearchParameters {
	
	private Categories categories;
	
	public ImageMetaDataContextSearchParameters() {
		super();
		this.categories = null;
	}

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		if (categories.size() > 0) {
			this.categories = categories;
		}
	}
}
