package moller.javapeg.program.model;

import moller.javapeg.program.imagerepository.ImageRepositoryItem;

public class ModelInstanceLibrary {

    /**
     * The static singleton instance of this class.
     */
    private static ModelInstanceLibrary instance;

    private final MetaDataTableModel metaDataTableModel;
    private final PreviewTableModel  previewTableModel;
    private final SortedListModel<ImageRepositoryItem> imageRepositoryListModel;
    private final ImagesToViewModel  imagesToViewModel;
    private final CategoriesModel    categoriesModel;

    /**
     * Private constructor.
     */
    private ModelInstanceLibrary() {
        metaDataTableModel       = new MetaDataTableModel();
        previewTableModel        = new PreviewTableModel();
        imagesToViewModel        = new ImagesToViewModel();
        imageRepositoryListModel = new SortedListModel<ImageRepositoryItem>();
        categoriesModel          = new CategoriesModel(null);
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

    public SortedListModel<ImageRepositoryItem> getImageRepositoryListModel() {
        return imageRepositoryListModel;
    }

    public ImagesToViewModel getImagesToViewModel() {
        return imagesToViewModel;
    }

    public CategoriesModel getCategoriesModel() {
        return categoriesModel;
    }
}
