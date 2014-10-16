/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program.model;


public class ModelInstanceLibrary {

    /**
     * The static singleton instance of this class.
     */
    private static ModelInstanceLibrary instance;

    private final MetaDataTableModel metaDataTableModel;
    private final PreviewTableModel  previewTableModel;
    private final ImageRepositoriesTableModel imageRepositoriesTableModel;
    private final ImagesToViewModel  imagesToViewModel;
    private final CategoriesModel    categoriesModel;

    /**
     * Private constructor.
     */
    private ModelInstanceLibrary() {
        imageRepositoriesTableModel = new ImageRepositoriesTableModel();
        metaDataTableModel          = new MetaDataTableModel();
        previewTableModel           = new PreviewTableModel();
        imagesToViewModel           = new ImagesToViewModel();
        categoriesModel             = new CategoriesModel(null);
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

    public ImagesToViewModel getImagesToViewModel() {
        return imagesToViewModel;
    }

    public CategoriesModel getCategoriesModel() {
        return categoriesModel;
    }

    public ImageRepositoriesTableModel getImageRepositoriesTableModel() {
        return imageRepositoriesTableModel;
    }
}
