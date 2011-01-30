package moller.javapeg.program.model;

import moller.javapeg.program.categories.CategoryUtil;

public class ModelInstanceLibrary {

	/**
	 * The static singleton instance of this class.
	 */
	private static ModelInstanceLibrary instance;

	private final MetaDataTableModel metaDataTableModel;
	private final PreviewTableModel  previewTableModel;
	private final SortedListModel    imageRepositoryListModel;
	private final ImagesToViewModel  imagesToViewModel;
	private CategoriesModel    categoriesModel = null;

	/**
	 * Private constructor.
	 */
	private ModelInstanceLibrary() {
		metaDataTableModel       = new MetaDataTableModel();
		previewTableModel        = new PreviewTableModel();
		imageRepositoryListModel = new SortedListModel();
		imagesToViewModel        = new ImagesToViewModel();
	}

	/**
	 * Accessor method for this Singleton class.
	 *
	 * @return the singleton instance of this class.
	 */
	public static ModelInstanceLibrary getInstance() {
		if (instance != null)
			return instance;
		synchronized (ModelInstanceLibrary.class) {
			if (instance == null) {
				instance = new ModelInstanceLibrary();
			}
			return instance;
		}
	}

	public MetaDataTableModel getMetaDataTableModel() {
		return metaDataTableModel;
	}

	public PreviewTableModel getPreviewTableModel() {
		return previewTableModel;
	}

	public SortedListModel getImageRepositoryListModel() {
		return imageRepositoryListModel;
	}

	public ImagesToViewModel getImagesToViewModel() {
		return imagesToViewModel;
	}

	public CategoriesModel getCategoriesModel() {
		if (null == categoriesModel) {
			categoriesModel = new CategoriesModel(CategoryUtil.createCategoriesModel());
		}
		return categoriesModel;
	}
}