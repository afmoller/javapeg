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
package moller.javapeg.program.gui.frames.configuration.panels;

import moller.javapeg.program.C;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.categories.CategoryUtil;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesCategories;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPaths;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPreview;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.javapeg.program.config.model.repository.RepositoryExceptions;
import moller.javapeg.program.gui.CustomizedJTable;
import moller.javapeg.program.gui.frames.configuration.ImageRepositoriesTableCellRenderer;
import moller.javapeg.program.gui.frames.configuration.panels.base.BaseConfigurationPanel;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.javapeg.program.model.ImageRepositoriesTableModel;
import moller.javapeg.program.model.ModelInstanceLibrary;
import moller.util.io.Status;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TagConfigurationPanel extends BaseConfigurationPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JRadioButton useEmbeddedThumbnail;
    private JRadioButton useScaledThumbnail;
    private JCheckBox warnWhenRemoveCategory;
    private JCheckBox warnWhenRemoveCategoryWithSubCategories;
    private JList<ImportedCategories> importedCategoriesList;
    private JPopupMenu importedCategoriesPopupMenu;
    private JRadioButton addAutomaticallyRadioButton;
    private JRadioButton askToAddRadioButton;
    private JRadioButton doNotAddRadioButton;
    private JList<Object> imageRepositoriesAllwaysAddList;
    private JList<Object> imageRepositoriesNeverAddList;
    private CustomizedJTable imageRepositoriesTable;
    private JButton removeSelectedImagePathsButton;
    private JLabel imageRepositoriesMetaDataLabel;

    private ImportedCategories theImportedCategoriesToRenameOrDelete;

    @Override
    public boolean isValidConfiguration() {
        return true;
    }

    @Override
    protected void addListeners() {
        removeSelectedImagePathsButton.addActionListener(new RemoveSelectedImagePathsButtonListener());

        ModelInstanceLibrary.getInstance().getImageRepositoriesTableModel().addTableModelListener(new ImageRepositoriesTableModelListener());

        // Force an invocation of the registered listener if all the image
        // repositories already have been added to the model (which occurs when
        // the application is started).
        ModelInstanceLibrary.getInstance().getImageRepositoriesTableModel().fireTableDataChanged();
    }

    @Override
    protected void createPanel() {
        TagImagesPreview tagImagesPreview = getConfiguration().getTagImages().getPreview();

        /**
         * Start of Preview Image Area
         */
        useEmbeddedThumbnail = new JRadioButton(getLang().get("configviewer.tag.previewimage.label.embeddedthumbnail"));
        useEmbeddedThumbnail.setSelected(tagImagesPreview.getUseEmbeddedThumbnail());

        useScaledThumbnail = new JRadioButton(getLang().get("configviewer.tag.previewimage.label.scaledthumbnail"));
        useScaledThumbnail.setSelected(!tagImagesPreview.getUseEmbeddedThumbnail());

        ButtonGroup group = new ButtonGroup();
        group.add(useEmbeddedThumbnail);
        group.add(useScaledThumbnail);

        JPanel previewImagePanel = new JPanel(new GridBagLayout());
        previewImagePanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.tag.previewimage.label")));

        GBHelper posPreviewImagePanel = new GBHelper();

        previewImagePanel.add(useEmbeddedThumbnail, posPreviewImagePanel.expandW());
        previewImagePanel.add(useScaledThumbnail, posPreviewImagePanel.nextRow().expandW());
        previewImagePanel.add(Box.createVerticalGlue(), posPreviewImagePanel.nextRow().expandH());

        TagImagesCategories tagImagesCategories = getConfiguration().getTagImages().getCategories();

        /**
         * Start of Categories Area
         */
        warnWhenRemoveCategory = new JCheckBox(getLang().get("configviewer.tag.categories.warnWhenRemove"));
        warnWhenRemoveCategory.setSelected(tagImagesCategories.getWarnWhenRemove());
        warnWhenRemoveCategoryWithSubCategories = new JCheckBox(getLang().get("configviewer.tag.categories.warnWhenRemoveCategoryWithSubCategories"));
        warnWhenRemoveCategoryWithSubCategories.setSelected(tagImagesCategories.getWarnWhenRemoveWithSubCategories());

        JPanel categoriesPanel = new JPanel(new GridBagLayout());
        categoriesPanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.tag.categories.label")));

        GBHelper posCategoriesPanel = new GBHelper();

        categoriesPanel.add(warnWhenRemoveCategory, posCategoriesPanel.expandW());
        categoriesPanel.add(warnWhenRemoveCategoryWithSubCategories, posCategoriesPanel.nextRow().expandW());
        categoriesPanel.add(Box.createVerticalGlue(), posCategoriesPanel.nextRow().expandH());

        /**
         * Start of Imported Categories Area
         */
        Map<String, ImportedCategories> importedCategories = getConfiguration().getImportedCategoriesConfig();

        DefaultListModel<ImportedCategories> importedCategoriesListModel = new DefaultListModel<ImportedCategories>();
        for (ImportedCategories importedCategory : importedCategories.values()) {
            importedCategoriesListModel.addElement(importedCategory);
        }

        importedCategoriesList = new JList<ImportedCategories>(importedCategoriesListModel);
        importedCategoriesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        importedCategoriesList.addMouseListener(new RightClickMouseListener());

        JMenuItem renameImportedCategoriesJMenuItem = new JMenuItem(getLang().get("configviewer.tag.importedCategories.menuitem.rename"));
        renameImportedCategoriesJMenuItem.setActionCommand("Rename");
        renameImportedCategoriesJMenuItem.addActionListener(new ImportedCategoiresPopupListener());

        JMenuItem deleteImportedCategoriesJMenuItem = new JMenuItem(getLang().get("configviewer.tag.importedCategories.menuitem.delete"));
        deleteImportedCategoriesJMenuItem.setActionCommand("Delete");
        deleteImportedCategoriesJMenuItem.addActionListener(new ImportedCategoiresPopupListener());

        importedCategoriesPopupMenu = new JPopupMenu();
        importedCategoriesPopupMenu.add(renameImportedCategoriesJMenuItem);
        importedCategoriesPopupMenu.add(deleteImportedCategoriesJMenuItem);

        JScrollPane importedCategoriesScrollPane = new JScrollPane(importedCategoriesList);
        importedCategoriesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JButton removeSelectedImportedCategoriesButton = new JButton();
        removeSelectedImportedCategoriesButton.setToolTipText(getLang().get("configviewer.tag.importedCategories.removeSelectedImportedCategoriesButton.tooltip"));
        removeSelectedImportedCategoriesButton.addActionListener(new RemoveImportedCategoriesListener());

        JPanel importedCategoriesPanel = new JPanel(new GridBagLayout());
        importedCategoriesPanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.tag.importedCategories.panel.border.label")));

        GBHelper posImportedCategoriesPanel = new GBHelper();

        importedCategoriesPanel.add(importedCategoriesScrollPane , posImportedCategoriesPanel.expandW().expandH());
        importedCategoriesPanel.add(removeSelectedImportedCategoriesButton, posImportedCategoriesPanel.nextRow().align(GridBagConstraints.WEST));

        /**
         * Start of Image Repositories Area
         */
        addAutomaticallyRadioButton = new JRadioButton(getLang().get("configviewer.tag.imageRepositories.label.addAutomatically"));
        addAutomaticallyRadioButton.setName("0");
        askToAddRadioButton = new JRadioButton(getLang().get("configviewer.tag.imageRepositories.label.askToAdd"));
        askToAddRadioButton.setName("1");
        doNotAddRadioButton = new JRadioButton(getLang().get("configviewer.tag.imageRepositories.label.doNotAskToAdd"));
        doNotAddRadioButton.setName("2");

        ButtonGroup buttonGroup = new ButtonGroup();

        buttonGroup.add(addAutomaticallyRadioButton);
        buttonGroup.add(askToAddRadioButton);
        buttonGroup.add(doNotAddRadioButton);

        TagImagesPaths tagImagesPaths = getConfiguration().getTagImages().getImagesPaths();

        String addToImageRepositoryPolicy = Integer.toString(tagImagesPaths.getAddToRepositoryPolicy());

        if (addToImageRepositoryPolicy.equalsIgnoreCase(addAutomaticallyRadioButton.getName())) {
            addAutomaticallyRadioButton.setSelected(true);
        } else if (addToImageRepositoryPolicy.equalsIgnoreCase(askToAddRadioButton.getName())) {
            askToAddRadioButton.setSelected(true);
        } else {
            doNotAddRadioButton.setSelected(true);
        }

        RepositoryExceptions repositoryExceptions = getConfiguration().getRepository().getExceptions();

        imageRepositoriesAllwaysAddList = new JList<Object>(repositoryExceptions.getAllwaysAdd().toArray());
        imageRepositoriesAllwaysAddList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane imageRepositoriesAllwaysAddScrollPane = new JScrollPane(imageRepositoriesAllwaysAddList);
        imageRepositoriesAllwaysAddScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        imageRepositoriesNeverAddList = new JList<Object>(repositoryExceptions.getNeverAdd().toArray());
        imageRepositoriesNeverAddList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane imageRepositoriesNeverAddScrollPane = new JScrollPane(imageRepositoriesNeverAddList);
        imageRepositoriesNeverAddScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JButton removeSelectedAllwaysAddImagePaths = new JButton();
        removeSelectedAllwaysAddImagePaths.setToolTipText(getLang().get("configviewer.tag.imageRepositories.label.removeSelectedPaths"));
        removeSelectedAllwaysAddImagePaths.setName("AllwaysAddImagePaths");
        removeSelectedAllwaysAddImagePaths.addActionListener(new RemoveExceptionPathsListener());

        JButton removeSelectedDoNotAllwaysAddImagePaths = new JButton();
        removeSelectedDoNotAllwaysAddImagePaths.setToolTipText(getLang().get("configviewer.tag.imageRepositories.label.removeSelectedPaths"));
        removeSelectedDoNotAllwaysAddImagePaths.setName("DoNotAllwaysAddImagePaths");
        removeSelectedDoNotAllwaysAddImagePaths.addActionListener(new RemoveExceptionPathsListener());

        removeSelectedAllwaysAddImagePaths.setIcon(IconLoader.getIcon(Icons.REMOVE));
        removeSelectedDoNotAllwaysAddImagePaths.setIcon(IconLoader.getIcon(Icons.REMOVE));
        removeSelectedImportedCategoriesButton.setIcon(IconLoader.getIcon(Icons.REMOVE));

        ImageRepositoriesTableModel imageRepositoriesTableModel = ModelInstanceLibrary.getInstance().getImageRepositoriesTableModel();

        TableRowSorter<TableModel> imageRepositoriesTableModelSorter = new TableRowSorter<TableModel>(imageRepositoriesTableModel);
        imageRepositoriesTable = new CustomizedJTable(imageRepositoriesTableModel);
        imageRepositoriesTable.setRowSorter(imageRepositoriesTableModelSorter);
        imageRepositoriesTable.getRowSorter().toggleSortOrder(0);
        imageRepositoriesTable.setDefaultRenderer(Object.class, new ImageRepositoriesTableCellRenderer());

        JScrollPane imageRepositoriesScrollPane = new JScrollPane(imageRepositoriesTable);
        imageRepositoriesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel imageRepositoriesAdditionModePanel = new JPanel(new GridBagLayout());
        imageRepositoriesAdditionModePanel.setBorder(BorderFactory.createTitledBorder(""));
        imageRepositoriesAdditionModePanel.setName(getLang().get("configviewer.tag.imageRepositoriesAdditionMode.label"));

        GBHelper posImageRepositories = new GBHelper();

        imageRepositoriesAdditionModePanel.add(addAutomaticallyRadioButton, posImageRepositories.expandW());
        imageRepositoriesAdditionModePanel.add(askToAddRadioButton, posImageRepositories.nextRow().expandW());
        imageRepositoriesAdditionModePanel.add(doNotAddRadioButton, posImageRepositories.nextRow().expandW());

        imageRepositoriesAdditionModePanel.add(Box.createVerticalStrut(10), posImageRepositories.nextRow());
        imageRepositoriesAdditionModePanel.add(new JLabel(getLang().get("configviewer.tag.imageRepositoriesAdditionMode.allwaysAdd.label")), posImageRepositories.nextRow());
        imageRepositoriesAdditionModePanel.add(imageRepositoriesAllwaysAddScrollPane, posImageRepositories.nextRow().expandW().expandH());
        imageRepositoriesAdditionModePanel.add(removeSelectedAllwaysAddImagePaths, posImageRepositories.nextRow().align(GridBagConstraints.WEST));
        imageRepositoriesAdditionModePanel.add(Box.createVerticalStrut(15), posImageRepositories.nextRow());
        imageRepositoriesAdditionModePanel.add(new JLabel(getLang().get("configviewer.tag.imageRepositoriesAdditionMode.neverAdd.label")), posImageRepositories.nextRow().nextRow());
        imageRepositoriesAdditionModePanel.add(imageRepositoriesNeverAddScrollPane, posImageRepositories.nextRow().expandW().expandH());
        imageRepositoriesAdditionModePanel.add(removeSelectedDoNotAllwaysAddImagePaths, posImageRepositories.nextRow().align(GridBagConstraints.WEST));

        JPanel imageRepositoriesContentPanel = new JPanel(new GridBagLayout());
        imageRepositoriesContentPanel.setBorder(BorderFactory.createTitledBorder(""));
        imageRepositoriesContentPanel.setName(getLang().get("configviewer.tag.imageRepositoriesContent.label"));

        JPanel buttonPanel = new JPanel(new GridBagLayout());

        removeSelectedImagePathsButton = new JButton();
        removeSelectedImagePathsButton.setIcon(IconLoader.getIcon(Icons.REMOVE));
        removeSelectedImagePathsButton.setToolTipText("Remove selected path(s) from the image repository");

        GBHelper posButtonPanel = new GBHelper();

        buttonPanel.add(removeSelectedImagePathsButton, posButtonPanel.align(GridBagConstraints.WEST));

        GBHelper posImageRepositoriesContent = new GBHelper();

        imageRepositoriesMetaDataLabel = new JLabel();
        imageRepositoriesContentPanel.add(imageRepositoriesMetaDataLabel, posImageRepositoriesContent);
        imageRepositoriesContentPanel.add(imageRepositoriesScrollPane, posImageRepositoriesContent.nextRow().expandW().expandH());
        imageRepositoriesContentPanel.add(Box.createVerticalStrut(2), posImageRepositoriesContent.nextRow());
        imageRepositoriesContentPanel.add(buttonPanel, posImageRepositoriesContent.nextRow().align(GridBagConstraints.WEST));

        JTabbedPane imageRepositoryTabbedPane = new JTabbedPane();
        imageRepositoryTabbedPane.add(imageRepositoriesAdditionModePanel);
        imageRepositoryTabbedPane.add(imageRepositoriesContentPanel);

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        GBHelper posPanel = new GBHelper();

        add(previewImagePanel, posPanel.expandW());
        add(categoriesPanel, posPanel.nextRow().expandW());
        add(importedCategoriesPanel, posPanel.nextRow().expandW().expandH());
        add(imageRepositoryTabbedPane, posPanel.nextRow().expandW().expandH());
    }

    @Override
    public String getChangedConfigurationMessage() {
        StringBuilder displayMessage = new StringBuilder();

        TagImages tagImages = getConfiguration().getTagImages();
        TagImagesPreview preview = tagImages.getPreview();

        if(preview.getUseEmbeddedThumbnail() != useEmbeddedThumbnail.isSelected()) {
            if (preview.getUseEmbeddedThumbnail()) {
                displayMessage .append(getLang().get("configviewer.tag.previewimage.label.scaledthumbnail")+ " (" + getLang().get("configviewer.tag.previewimage.label.embeddedthumbnail") + ")\n");
            } else {
                displayMessage.append(getLang().get("configviewer.tag.previewimage.label.embeddedthumbnail")+ " (" + getLang().get("configviewer.tag.previewimage.label.scaledthumbnail") + ")\n");
            }
        }

        TagImagesPaths imagesPaths = tagImages.getImagesPaths();

        if(!imagesPaths.getAddToRepositoryPolicy().equals(Integer.parseInt(getAddToRepositoryPolicy()))) {
            String previous = "";

            switch (imagesPaths.getAddToRepositoryPolicy()) {
            case 0:
                previous = getLang().get("configviewer.tag.imageRepositories.label.addAutomatically");
                break;
            case 1:
                previous = getLang().get("configviewer.tag.imageRepositories.label.askToAdd");
                break;
            case 2:
                previous = getLang().get("configviewer.tag.imageRepositories.label.doNotAskToAdd");
                break;
            }

            int currentAddToImageRepositoryPolicy = Integer.parseInt(getAddToRepositoryPolicy());
            String current = "";

            switch (currentAddToImageRepositoryPolicy) {
            case 0:
                current = getLang().get("configviewer.tag.imageRepositories.label.addAutomatically");
                break;
            case 1:
                current = getLang().get("configviewer.tag.imageRepositories.label.askToAdd");
                break;
            case 2:
                current = getLang().get("configviewer.tag.imageRepositories.label.doNotAskToAdd");
                break;
            }

            displayMessage.append(getLang().get("configviewer.tag.imageRepositoriesAdditionMode.label") + ": " + current + " (" + previous + ")\n");
        }

        TagImagesCategories categories = tagImages.getCategories();

        if(categories.getWarnWhenRemove() != warnWhenRemoveCategory.isSelected()) {
            displayMessage.append(getLang().get("configviewer.tag.categories.warnWhenRemove") + ": " + warnWhenRemoveCategory.isSelected() + " (" + categories.getWarnWhenRemove() + ")\n");
        }

        if(categories.getWarnWhenRemoveWithSubCategories() != warnWhenRemoveCategoryWithSubCategories.isSelected()) {
            displayMessage.append(getLang().get("configviewer.tag.categories.warnWhenRemoveCategoryWithSubCategories") + ": " + warnWhenRemoveCategoryWithSubCategories.isSelected() + " (" + categories.getWarnWhenRemoveWithSubCategories() + ")\n");
        }

        return displayMessage.toString();
    }

    @Override
    public void updateConfiguration() {
        TagImages tagImages = getConfiguration().getTagImages();

        TagImagesCategories tagImagesCategories = tagImages.getCategories();

        tagImagesCategories.setWarnWhenRemove(warnWhenRemoveCategory.isSelected());
        tagImagesCategories.setWarnWhenRemoveWithSubCategories(warnWhenRemoveCategoryWithSubCategories.isSelected());

        TagImagesPaths tagImagesPaths = tagImages.getImagesPaths();

        tagImagesPaths.setAddToRepositoryPolicy(Integer.parseInt(getAddToRepositoryPolicy()));

        TagImagesPreview tagImagesPreview = tagImages.getPreview();

        tagImagesPreview.setUseEmbeddedThumbnail(useEmbeddedThumbnail.isSelected());

    }

    private String getAddToRepositoryPolicy() {
        if (addAutomaticallyRadioButton.isSelected()) {
            return addAutomaticallyRadioButton.getName();
        } else if (askToAddRadioButton.isSelected()) {
            return askToAddRadioButton.getName();
        } else {
            return doNotAddRadioButton.getName();
        }
    }

    private class RemoveExceptionPathsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RepositoryExceptions repositoryExceptions = Config.getInstance().get().getRepository().getExceptions();

            if (((JButton)e.getSource()).getName().equals("AllwaysAddImagePaths")) {
                if (!imageRepositoriesAllwaysAddList.isSelectionEmpty()) {
                    for (Object selectedValue : imageRepositoriesAllwaysAddList.getSelectedValuesList()) {
                        repositoryExceptions.getAllwaysAdd().remove(selectedValue);
                    }
                    imageRepositoriesAllwaysAddList.clearSelection();
                }
            } else {
                if (!imageRepositoriesNeverAddList.isSelectionEmpty()) {
                    for (Object selectedValue : imageRepositoriesNeverAddList.getSelectedValuesList()) {
                        repositoryExceptions.getNeverAdd().remove(selectedValue);
                    }
                    imageRepositoriesNeverAddList.clearSelection();
                }
            }
        }
    }

    private class RemoveImportedCategoriesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!importedCategoriesList.isSelectionEmpty()) {
                removeImportedCategories();
            }
        }
    }

    private class RightClickMouseListener extends MouseAdapter  {

        @SuppressWarnings("unchecked")
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                ((JList<Object>)e.getSource()).setSelectedIndex(((JList<Object>)e.getSource()).locationToIndex(e.getPoint()) );

                int selecteIndex = ((JList<Object>)e.getSource()).locationToIndex(e.getPoint());

                if (selecteIndex > -1) {
                    theImportedCategoriesToRenameOrDelete = (ImportedCategories)((JList<Object>)e.getSource()).getModel().getElementAt(selecteIndex);
                }
                importedCategoriesPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private class ImportedCategoiresPopupListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Rename")) {
                if (theImportedCategoriesToRenameOrDelete != null) {
                    String newName = CategoryUtil.displayInputDialog(TagConfigurationPanel.this, getLang().get("configviewer.tag.importedCategories.rename.dialog.title"), getLang().get("configviewer.tag.importedCategories.rename.dialog.text") + " " + theImportedCategoriesToRenameOrDelete.getDisplayName(), "");

                    // If newName is null then the cancel button has been
                    // clicked, then do nothing.
                    if (newName != null) {
                        if (CategoryUtil.displayNameAlreadyInUse(newName, getConfiguration().getImportedCategoriesConfig().values()) || newName.trim().length() == 0) {
                            theImportedCategoriesToRenameOrDelete.setDisplayName(CategoryUtil.askForANewDisplayName(TagConfigurationPanel.this, newName, getConfiguration().getImportedCategoriesConfig()));
                        } else {
                            theImportedCategoriesToRenameOrDelete.setDisplayName(newName);
                        }
                    }
                }
            } else if (e.getActionCommand().equals("Delete")) {
                if (theImportedCategoriesToRenameOrDelete != null) {
                    removeImportedCategories();
                    theImportedCategoriesToRenameOrDelete = null;
                }
            }
        }
    }

    private void removeImportedCategories() {
        Map<String, ImportedCategories> importedCategoriesMap = Config.getInstance().get().getImportedCategoriesConfig();

        Set<String> keysToRemove = new HashSet<String>();

        // Search for imported categories to remove...
        for (Object selectedValue : importedCategoriesList.getSelectedValuesList()) {

            for (String key : importedCategoriesMap.keySet()) {
                if (importedCategoriesMap.get(key).equals(selectedValue)) {
                    keysToRemove.add(key);
                    ((DefaultListModel<ImportedCategories>)importedCategoriesList.getModel()).removeElement(selectedValue);
                }
            }
        }

        // ... and remove the found matches.
        for (String key : keysToRemove) {
            importedCategoriesMap.remove(key);
        }

        importedCategoriesList.clearSelection();
    }

    private class RemoveSelectedImagePathsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ImageRepositoriesTableModel imageRepositoriesTableModel = ModelInstanceLibrary.getInstance().getImageRepositoriesTableModel();

            List<ImageRepositoryItem> selectedValues = getSelectedValues(imageRepositoriesTable);

            StringBuilder paths = new StringBuilder();

            for (Object selectedValue : selectedValues) {
                ImageRepositoryItem iri = (ImageRepositoryItem)selectedValue;

                String status = getLang().get(iri.getPathStatus().getTextKey());

                paths.append(iri.getPath() + " (" + status + ")");
                paths.append(C.LS);
            }

            int result = displayConfirmDialog(getLang().get("configviewer.tag.imageRepositories.label.pathsWillBeRemoved") + C.LS + C.LS + paths.toString(), getLang().get("common.confirmation"), JOptionPane.OK_CANCEL_OPTION);

            if (result == 0) {
                for (ImageRepositoryItem selectedValue : selectedValues) {
                    imageRepositoriesTableModel.removeRow(selectedValue);
                }
            }
        }

        private List<ImageRepositoryItem> getSelectedValues(CustomizedJTable imageRepositoriesTable) {

            int[] selectedRows = imageRepositoriesTable.getSelectedRows();

            List<ImageRepositoryItem> selectedValues = new ArrayList<ImageRepositoryItem>();

            for (int selectedRow : selectedRows) {
                File path = (File) imageRepositoriesTable.getValueAt(selectedRow, 0);
                Status status = (Status) imageRepositoriesTable.getValueAt(selectedRow, 1);

                ImageRepositoryItem iri = new ImageRepositoryItem(path, status);

                selectedValues.add(iri);
            }
            return selectedValues;
        }
    }

    /**
     * This class listens for changes to the image repositories TableModel and
     * prints information about how many entries and of which type there are in
     * the model onto a {@link JLabel}.
     *
     * @author Fredrik
     *
     */
    private class ImageRepositoriesTableModelListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {
            ImageRepositoriesTableModel imageRepositoriesTableModel = (ImageRepositoriesTableModel)e.getSource();

            int total = imageRepositoriesTableModel.getRowCount();
            Map<Status, AtomicInteger> numberOfRowsPerStatus = imageRepositoriesTableModel.getNumberOfRowsPerStatus();

            String labelText = createLabelText(total, numberOfRowsPerStatus);
            String labelTooltipText = createLabelTooltipText(total, numberOfRowsPerStatus);

            imageRepositoriesMetaDataLabel.setText(labelText);
            imageRepositoriesMetaDataLabel.setToolTipText(labelTooltipText);
        }

        private String createLabelTooltipText(int total, Map<Status, AtomicInteger> numberOfRowsPerStatus) {
            String stringToFormat = createText(total, numberOfRowsPerStatus);

            String totalAmount = getLang().get("configviewer.tag.imageRepositories.label.totalAmount");
            String exists = getLang().get("configviewer.tag.imageRepositories.label.exists");
            String notAvailable = getLang().get("configviewer.tag.imageRepositories.label.notAvailable");
            String doesNotExist = getLang().get("configviewer.tag.imageRepositories.label.doesNotExist");
            String inconsistent = getLang().get("configviewer.tag.imageRepositories.label.inconsistent");
            String corrupt = getLang().get("configviewer.tag.imageRepositories.label.corrupt");

            stringToFormat = "<html>" + stringToFormat + "</html>";

            return String.format(stringToFormat,totalAmount, "<br/>" + exists, "<br/>" + doesNotExist, "<br/>" + notAvailable, "<br/>" + inconsistent, "<br/>" + corrupt);
        }

        private String createLabelText(int total, Map<Status, AtomicInteger> numberOfRowsPerStatus) {
            String stringToFormat = createText(total, numberOfRowsPerStatus);

            String totalAmountMnemonic = getLang().get("configviewer.tag.imageRepositories.label.totalAmountMnemonic");
            String existsMnemonic = getLang().get("configviewer.tag.imageRepositories.label.existsMnemonic");
            String notAvailableMnemonic = getLang().get("configviewer.tag.imageRepositories.label.notAvailableMnemonic");
            String doesNotExistMnemonic = getLang().get("configviewer.tag.imageRepositories.label.doesNotExistMnemonic");
            String inconsistentMnemonic = getLang().get("configviewer.tag.imageRepositories.label.inconsistentMnemonic");
            String corruptMnemonic = getLang().get("configviewer.tag.imageRepositories.label.corruptMnemonic");

            return String.format(stringToFormat, totalAmountMnemonic, existsMnemonic, doesNotExistMnemonic, notAvailableMnemonic, inconsistentMnemonic, corruptMnemonic);
        }

        private String createText(int total, Map<Status, AtomicInteger> numberOfRowsPerStatus) {
            StringBuilder builder = new StringBuilder();
            builder.append("%s: ");
            builder.append(total);
            builder.append(" %s: ");
            builder.append(getNumberOfForStatus(numberOfRowsPerStatus, Status.EXISTS));
            builder.append(" %s: ");
            builder.append(getNumberOfForStatus(numberOfRowsPerStatus, Status.DOES_NOT_EXIST));
            builder.append(" %s: ");
            builder.append(getNumberOfForStatus(numberOfRowsPerStatus, Status.NOT_AVAILABLE));
            builder.append(" %s: ");
            builder.append(getNumberOfForStatus(numberOfRowsPerStatus, Status.INCONSISTENT));
            builder.append(" %s: ");
            builder.append(getNumberOfForStatus(numberOfRowsPerStatus, Status.CORRUPT));

            return builder.toString();
        }

        private Number getNumberOfForStatus(Map<Status, AtomicInteger> numberOfRowsPerStatus, Status status) {
            return numberOfRowsPerStatus.get(status) == null ? 0 : numberOfRowsPerStatus.get(status);
        }
    }

}
