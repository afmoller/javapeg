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
package moller.javapeg.program.config.model;

import java.util.Map;

import javax.swing.tree.TreeNode;

import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.javapeg.program.config.model.applicationmode.resize.ResizeImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.javapeg.program.config.model.metadata.MetaData;
import moller.javapeg.program.config.model.repository.Repository;
import moller.javapeg.program.config.model.thumbnail.ThumbNail;

public class Configuration {

    private Logging logging;
    private String javapegClientId;
    private GUI gUI;
    private UpdatesChecker updatesChecker;
    private Language language;
    private RenameImages renameImages;
    private ResizeImages resizeImages;
    private ImageViewerState imageViewerState;
    private TagImages tagImages;
    private ThumbNail thumbNail;
    private ToolTips toolTips;
    private TreeNode categories;
    private Repository repository;
    private Map<String, ImportedCategories> importedCategoriesConfig;
    private ImageSearchResultViewerState imageSearchResultViewerState;
    private MetaData metadata;

    public MetaData getMetadata() {
        return metadata;
    }
    public Logging getLogging() {
        return logging;
    }
    public String getJavapegClientId() {
        return javapegClientId;
    }
    public GUI getgUI() {
        return gUI;
    }
    public UpdatesChecker getUpdatesChecker() {
        return updatesChecker;
    }
    public Language getLanguage() {
        return language;
    }
    public RenameImages getRenameImages() {
        return renameImages;
    }
    public ResizeImages getResizeImages() {
        return resizeImages;
    }
    public ImageViewerState getImageViewerState() {
        return imageViewerState;
    }
    public TagImages getTagImages() {
        return tagImages;
    }
    public ThumbNail getThumbNail() {
        return thumbNail;
    }
    public ToolTips getToolTips() {
        return toolTips;
    }
    public TreeNode getCategories() {
        return categories;
    }
    public Repository getRepository() {
        return repository;
    }
    public Map<String, ImportedCategories> getImportedCategoriesConfig() {
        return importedCategoriesConfig;
    }
    public ImageSearchResultViewerState getImageSearchResultViewerState() {
        return imageSearchResultViewerState;
    }
    public void setLogging(Logging logging) {
        this.logging = logging;
    }
    public void setJavapegClientId(String javapegClientId) {
        this.javapegClientId = javapegClientId;
    }
    public void setgUI(GUI gUI) {
        this.gUI = gUI;
    }
    public void setUpdatesChecker(UpdatesChecker updatesChecker) {
        this.updatesChecker = updatesChecker;
    }
    public void setLanguage(Language language) {
        this.language = language;
    }
    public void setRenameImages(RenameImages renameImages) {
        this.renameImages = renameImages;
    }
    public void setResizeImages(ResizeImages resizeImages) {
        this.resizeImages = resizeImages;
    }
    public void setImageViewerState(ImageViewerState imageViewerState) {
        this.imageViewerState = imageViewerState;
    }
    public void setTagImages(TagImages tagImages) {
        this.tagImages = tagImages;
    }
    public void setThumbNail(ThumbNail thumbNail) {
        this.thumbNail = thumbNail;
    }
    public void setToolTips(ToolTips toolTips) {
        this.toolTips = toolTips;
    }
    public void setCategories(TreeNode categories) {
        this.categories = categories;
    }
    public void setRepository(Repository repository) {
        this.repository = repository;
    }
    public void setImportedCategories(Map<String, ImportedCategories> importedCategoriesConfig) {
        this.importedCategoriesConfig = importedCategoriesConfig;
    }
    public void setImageSearchResultViewerState(ImageSearchResultViewerState imageSearchResultViewerState) {
        this.imageSearchResultViewerState = imageSearchResultViewerState;
    }
    public void setMetadata(MetaData metaData) {
        this.metadata = metaData;
    }

}
