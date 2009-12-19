package moller.javapeg.program.model;
/**
 * This class was created : 2009-12-17 by Fredrik Möller
 * Latest changed         : 
 */

public class ModelInstanceLibrary {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static ModelInstanceLibrary instance;
	
	private MetaDataTableModel metaDataTableModel;
	private PreviewTableModel  previewTableModel;
	private SortedListModel    sortedListModel;
	
	/**
	 * Private constructor.
	 */
	private ModelInstanceLibrary() {
		metaDataTableModel = new MetaDataTableModel();
		previewTableModel  = new PreviewTableModel();
		sortedListModel    = new SortedListModel();
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
	
	public SortedListModel getSortedListModel() {
		return sortedListModel;
	}
}