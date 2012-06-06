package moller.javapeg.program.model;

import java.io.File;

import moller.javapeg.program.C;
import moller.javapeg.program.categories.CategoryUtil;
import moller.javapeg.program.imagerepository.ImageRepository;
import moller.javapeg.program.imagerepository.ImageRepositoryUtil;

public class ModelInstanceLibrary {

	/**
	 * The static singleton instance of this class.
	 */
	private static ModelInstanceLibrary instance;

	private final MetaDataTableModel metaDataTableModel;
	private final PreviewTableModel  previewTableModel;
	private final SortedListModel    imageRepositoryListModel;
	private final SortedListModel    imageRepositoryPaths;
	private final SortedListModel    addDirectoriesAutomaticallyModel;
	private final SortedListModel    doNotAddDirectoriesAutomaticallyModel;
	private final ImagesToViewModel  imagesToViewModel;
	private CategoriesModel          categoriesModel = null;

	/**
	 * Private constructor.
	 */
	private ModelInstanceLibrary() {
		metaDataTableModel                    = new MetaDataTableModel();
		previewTableModel                     = new PreviewTableModel();
		imagesToViewModel                     = new ImagesToViewModel();
		imageRepositoryListModel              = new SortedListModel();

		ImageRepository ir = ImageRepositoryUtil.getInstance().createImageRepositoryModel();

		addDirectoriesAutomaticallyModel      = new SortedListModel(ir.getAllwaysAdd());
	    doNotAddDirectoriesAutomaticallyModel = new SortedListModel(ir.getNeverAdd());
	    imageRepositoryPaths                  = new SortedListModel(ir.getPaths());
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
			categoriesModel = new CategoriesModel(CategoryUtil.createCategoriesModel(new File(C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION + C.FS + "config" + C.FS +  "categories.xml")));
		}
		return categoriesModel;
	}

	public SortedListModel getAddDirectoriesAutomaticallyModel() {
	    return addDirectoriesAutomaticallyModel;
    }

	public SortedListModel getDoNotAddDirectoriesAutomaticallyModel() {
	    return doNotAddDirectoriesAutomaticallyModel;
    }

	public SortedListModel getImageRepositoryPaths() {
        return imageRepositoryPaths;
    }
}