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

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import moller.javapeg.program.C;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.model.metadata.MetaData;
import moller.javapeg.program.config.model.metadata.MetaDataFilter;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContext;
import moller.javapeg.program.enumerations.ExposureTimeFilterMask;
import moller.javapeg.program.enumerations.IFilterMask;
import moller.javapeg.program.enumerations.ISOFilterMask;
import moller.javapeg.program.gui.ComboboxToolTipRenderer;
import moller.javapeg.program.gui.CustomizedJTable;
import moller.javapeg.program.gui.frames.configuration.panels.base.BaseConfigurationPanel;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.model.iso.CameraAndFilterPair;
import moller.javapeg.program.model.iso.FilteringTableModel;

public class MetadataConfigurationPanel extends BaseConfigurationPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JTabbedPane tabbedPane;

    private JButton addNewISORuleButton;
    private JButton removeISORuleButton;

    private JButton addNewExposureTimeRuleButton;
    private JButton removeExposureTimeRuleButton;

    private JComboBox<String> cameraModelsJComboBox;

    private JComboBox<ISOFilterMask> isoPatterns;
    private JComboBox<ExposureTimeFilterMask> exposureTimePatterns;

    private FilteringTableModel<ISOFilterMask> isoFilteringTableModel;
    private CustomizedJTable isoRuleToCameraModelTable;

    private FilteringTableModel<ExposureTimeFilterMask> exposureTimeFilteringTableModel;
    private CustomizedJTable exposureTimeRuleToCameraModelTable;

    @Override
    public boolean isValidConfiguration() {
        // No specific validation is needed to be performed, always return
        // true.
        return true;
    }

    @Override
    protected void addListeners() {
        addNewISORuleButton.addActionListener(new AddNewISORuleListener());
        removeISORuleButton.addActionListener(new RemoveISORuleListener());
        addNewExposureTimeRuleButton.addActionListener(new AddNewExposureTimeRuleListener());
        removeExposureTimeRuleButton.addActionListener(new RemoveExposureTimeRuleListener());
    }

    @Override
    protected void createPanel() {
        setLayout(new GridBagLayout());

        GBHelper posPanel = new GBHelper();

        add(createCameraModelComboBox(), posPanel);
        add(createTabbePaneBoxPanel(), posPanel.nextRow().expandH().expandW());
    }

    private JPanel createTabbePaneBoxPanel() {
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.metadata.label.configurations")));

        GBHelper posPanel = new GBHelper();

        tabbedPane = new JTabbedPane();
        tabbedPane.add(createISOConfigurationPanel());
        tabbedPane.add(createExposureTimeConfigurationPanel());

        backgroundPanel.add(tabbedPane, posPanel.expandH().expandW());

        return backgroundPanel;
    }

    private JPanel createCameraModelComboBox() {
        ImageMetaDataContext instance = ImageMetaDataContext.getInstance();
        Set<String> cameraModels = instance.getCameraModels();

        DefaultComboBoxModel<String> cameraModelsModel = new DefaultComboBoxModel<String>();
        for (String cameraModel : cameraModels) {
            cameraModelsModel.addElement(cameraModel);
        }

        cameraModelsJComboBox = new JComboBox<String>(cameraModelsModel );

        GBHelper posPanel = new GBHelper();

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.metadata.label.cameramodel")));
        backgroundPanel.add(cameraModelsJComboBox, posPanel.nextRow().expandW());

        return backgroundPanel;
    }

    private JPanel createExposureTimeConfigurationPanel() {

        exposureTimeFilteringTableModel = new FilteringTableModel<ExposureTimeFilterMask>();

        MetaData metadata = getConfiguration().getMetadata();
        List<MetaDataFilter<ExposureTimeFilterMask>> exposureTimeFilters = metadata.getExposureTimeFilters();

        for (MetaDataFilter<ExposureTimeFilterMask> exposureTimeFilter : exposureTimeFilters) {
            CameraAndFilterPair<ExposureTimeFilterMask> cameraAndExposureTimeFilterPair = new CameraAndFilterPair<ExposureTimeFilterMask>();
            cameraAndExposureTimeFilterPair.setCameraModel(exposureTimeFilter.getCameraModel());
            cameraAndExposureTimeFilterPair.setFilterMask(exposureTimeFilter.getFilterMask());
            exposureTimeFilteringTableModel.addRow(cameraAndExposureTimeFilterPair);
        }

        TableRowSorter<TableModel> exposureTimeFilteringTableModelSorter = new TableRowSorter<TableModel>(exposureTimeFilteringTableModel);
        exposureTimeRuleToCameraModelTable = new CustomizedJTable(exposureTimeFilteringTableModel);
        exposureTimeRuleToCameraModelTable.setRowSorter(exposureTimeFilteringTableModelSorter);
        exposureTimeRuleToCameraModelTable.getRowSorter().toggleSortOrder(0);

        JScrollPane exposureTimeRuleToCameraModelScrollPane = new JScrollPane(exposureTimeRuleToCameraModelTable);
        exposureTimeRuleToCameraModelScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel exposureTimeRuleToCameraModelScrollPanePanel = new JPanel(new GridBagLayout());
        GBHelper posExposureTimeRuleToCameraModelScrollPanePanel = new GBHelper();
        exposureTimeRuleToCameraModelScrollPanePanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.metadata.scrollpane.configured.fillters")));
        exposureTimeRuleToCameraModelScrollPanePanel.add(exposureTimeRuleToCameraModelScrollPane, posExposureTimeRuleToCameraModelScrollPanePanel.expandH().expandW());

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setName(getLang().get("variable.shutterSpeed"));

        GBHelper posPanel = new GBHelper();
        backgroundPanel.add(createExposureTimeButtonPanel(),posPanel);
        backgroundPanel.add(exposureTimeRuleToCameraModelScrollPanePanel, posPanel.nextRow().expandH().expandW());

        return backgroundPanel;
    }

    private JPanel createISOConfigurationPanel() {
        isoFilteringTableModel = new FilteringTableModel<ISOFilterMask>();

        MetaData metadata = getConfiguration().getMetadata();
        List<MetaDataFilter<ISOFilterMask>> isoFilters = metadata.getIsoFilters();

        for (MetaDataFilter<ISOFilterMask> isoFilter : isoFilters) {
            CameraAndFilterPair<ISOFilterMask> cameraAndISOFilterPair = new CameraAndFilterPair<ISOFilterMask>();
            cameraAndISOFilterPair.setCameraModel(isoFilter.getCameraModel());
            cameraAndISOFilterPair.setFilterMask(isoFilter.getFilterMask());
            isoFilteringTableModel.addRow(cameraAndISOFilterPair);
        }

        TableRowSorter<TableModel> isoFilteringTableModelSorter = new TableRowSorter<TableModel>(isoFilteringTableModel);
        isoRuleToCameraModelTable = new CustomizedJTable(isoFilteringTableModel);
        isoRuleToCameraModelTable.setRowSorter(isoFilteringTableModelSorter);
        isoRuleToCameraModelTable.getRowSorter().toggleSortOrder(0);

        JScrollPane isoRuleToCameraModelScrollPane = new JScrollPane(isoRuleToCameraModelTable);
        isoRuleToCameraModelScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel isoRuleToCameraModelScrollPanePanel = new JPanel(new GridBagLayout());
        GBHelper posIsoRuleToCameraModelScrollPanePanel = new GBHelper();
        isoRuleToCameraModelScrollPanePanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.metadata.scrollpane.configured.fillters")));
        isoRuleToCameraModelScrollPanePanel.add(isoRuleToCameraModelScrollPane, posIsoRuleToCameraModelScrollPanePanel.expandH().expandW());

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setName(getLang().get("variable.isoValue"));

        GBHelper posPanel = new GBHelper();
        backgroundPanel.add(createISOButtonPanel(),posPanel);
        backgroundPanel.add(isoRuleToCameraModelScrollPanePanel, posPanel.nextRow().expandH().expandW());

        return backgroundPanel;
    }

    private JPanel createISOButtonPanel() {
        DefaultComboBoxModel<ISOFilterMask> isoPatternModel = new DefaultComboBoxModel<ISOFilterMask>();
        isoPatternModel.addElement(ISOFilterMask.NO_MASK);
        isoPatternModel.addElement(ISOFilterMask.MASK_UP_TO_POSITON_FIRST);
        isoPatternModel.addElement(ISOFilterMask.MASK_UP_TO_POSITON_SECOND);
        isoPatternModel.addElement(ISOFilterMask.MASK_UP_TO_POSITON_THIRD);
        isoPatternModel.addElement(ISOFilterMask.MASK_UP_TO_POSITON_FOURTH);
        isoPatternModel.addElement(ISOFilterMask.MASK_UP_TO_POSITON_FIFTH);
        isoPatternModel.addElement(ISOFilterMask.MASK_UP_TO_POSITON_SIXTH);

        ComboboxToolTipRenderer comboboxToolTipRenderer = new ComboboxToolTipRenderer();
        ArrayList<String> tooltips = new ArrayList<String>();

        String willBe = getLang().get("configviewer.metadata.filterlists.tooltip.willbe");

        tooltips.add("218 " + willBe + " 218");
        tooltips.add("218 " + willBe + " 220");
        tooltips.add("218 " + willBe + " 200");
        tooltips.add("25718 " + willBe + " 26000");
        tooltips.add("25718 " + willBe + " 30000");
        tooltips.add("25718 " + willBe + " 30000");
        tooltips.add("25718 " + willBe + " 30000");
        comboboxToolTipRenderer.setTooltips(tooltips);

        isoPatterns = new JComboBox<ISOFilterMask>(isoPatternModel);
        isoPatterns.setRenderer(comboboxToolTipRenderer);

        addNewISORuleButton = new JButton(IconLoader.getIcon(Icons.ADD));
        addNewISORuleButton.setToolTipText(getLang().get("configviewer.metadata.filterbuttonpanel.addnewrulebutton"));

        removeISORuleButton = new JButton(IconLoader.getIcon(Icons.REMOVE));

        GBHelper posButtonPanel = new GBHelper();
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.metadata.filterbuttonpanel.title")));
        buttonPanel.add(isoPatterns, posButtonPanel.nextRow());
        buttonPanel.add(Box.createHorizontalStrut(3), posButtonPanel.nextCol());
        buttonPanel.add(addNewISORuleButton, posButtonPanel.nextCol());
        buttonPanel.add(removeISORuleButton, posButtonPanel.nextCol());
        buttonPanel.add(Box.createHorizontalGlue(), posButtonPanel.nextCol().expandW());
        return buttonPanel;
    }

    private Component createExposureTimeButtonPanel() {
        DefaultComboBoxModel<ExposureTimeFilterMask> exposureTimePatternModel = new DefaultComboBoxModel<ExposureTimeFilterMask>();
        exposureTimePatternModel.addElement(ExposureTimeFilterMask.NO_MASK);
        exposureTimePatternModel.addElement(ExposureTimeFilterMask.MASK_UP_TO_POSITON_FIRST);
        exposureTimePatternModel.addElement(ExposureTimeFilterMask.MASK_UP_TO_POSITON_SECOND);
        exposureTimePatternModel.addElement(ExposureTimeFilterMask.MASK_UP_TO_POSITON_THIRD);
        exposureTimePatternModel.addElement(ExposureTimeFilterMask.MASK_UP_TO_POSITON_FOURTH);
        exposureTimePatternModel.addElement(ExposureTimeFilterMask.MASK_UP_TO_POSITON_FIFTH);
        exposureTimePatternModel.addElement(ExposureTimeFilterMask.MASK_UP_TO_POSITON_SIXTH);

        ComboboxToolTipRenderer comboboxToolTipRenderer = new ComboboxToolTipRenderer();
        ArrayList<String> tooltips = new ArrayList<String>();

        String willBe = getLang().get("configviewer.metadata.filterlists.tooltip.willbe");

        tooltips.add("1/218 " + willBe + " 1/218");
        tooltips.add("1/218 " + willBe + " 1/220");
        tooltips.add("1/218 " + willBe + " 1/200");
        tooltips.add("1/25718 " + willBe + " 1/26000");
        tooltips.add("1/25718 " + willBe + " 1/30000");
        tooltips.add("1/25718 " + willBe + " 1/30000");
        tooltips.add("1/25718 " + willBe + " 1/30000");
        comboboxToolTipRenderer.setTooltips(tooltips);

        exposureTimePatterns = new JComboBox<ExposureTimeFilterMask>(exposureTimePatternModel);
        exposureTimePatterns.setRenderer(comboboxToolTipRenderer);

        addNewExposureTimeRuleButton = new JButton(IconLoader.getIcon(Icons.ADD));
        addNewExposureTimeRuleButton.setToolTipText(getLang().get("configviewer.metadata.filterbuttonpanel.addnewrulebutton"));

        removeExposureTimeRuleButton = new JButton(IconLoader.getIcon(Icons.REMOVE));

        GBHelper posButtonPanel = new GBHelper();
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.metadata.filterbuttonpanel.title")));
        buttonPanel.add(exposureTimePatterns, posButtonPanel.nextRow());
        buttonPanel.add(Box.createHorizontalStrut(3), posButtonPanel.nextCol());
        buttonPanel.add(addNewExposureTimeRuleButton, posButtonPanel.nextCol());
        buttonPanel.add(removeExposureTimeRuleButton, posButtonPanel.nextCol());
        buttonPanel.add(Box.createHorizontalGlue(), posButtonPanel.nextCol().expandW());
        return buttonPanel;
    }

    @Override
    public String getChangedConfigurationMessage() {

        // Exposure Time
        List<CameraAndFilterPair<ExposureTimeFilterMask>> exposureTimeCameraAndFilterPairsFromTableModel = createCameraAndFilterPairsFromTableModel(exposureTimeFilteringTableModel);
        List<CameraAndFilterPair<ExposureTimeFilterMask>> exposureTimeCameraAndFilterPairsFromConfig     = createCameraAndFilterPairsFromConfig(getConfiguration().getMetadata().getExposureTimeFilters());

        List<CameraAndFilterPair<ExposureTimeFilterMask>> addedExposureTimeCameraAndFilterPairs   = getAddedFilterPairs(exposureTimeCameraAndFilterPairsFromTableModel, exposureTimeCameraAndFilterPairsFromConfig);
        List<CameraAndFilterPair<ExposureTimeFilterMask>> removedExposureTimeCameraAndFilterPairs = getRemovedFilterPairs(exposureTimeCameraAndFilterPairsFromTableModel, exposureTimeCameraAndFilterPairsFromConfig);

        // ISO
        List<CameraAndFilterPair<ISOFilterMask>> isoCameraAndFilterPairsFromTableModel = createCameraAndFilterPairsFromTableModel(isoFilteringTableModel);
        List<CameraAndFilterPair<ISOFilterMask>> isoCameraAndFilterPairsFromConfig     = createCameraAndFilterPairsFromConfig(getConfiguration().getMetadata().getIsoFilters());

        List<CameraAndFilterPair<ISOFilterMask>> addedIsoCameraAndFilterPairs   = getAddedFilterPairs(isoCameraAndFilterPairsFromTableModel, isoCameraAndFilterPairsFromConfig);
        List<CameraAndFilterPair<ISOFilterMask>> removedIsoCameraAndFilterPairs = getRemovedFilterPairs(isoCameraAndFilterPairsFromTableModel, isoCameraAndFilterPairsFromConfig);

        // Build display message
        StringBuilder displayMessage = new StringBuilder();

        appendConfigurationDisplayMessage(displayMessage, getLang().get("configviewer.metadata.exposuretimefilter.added"), addedExposureTimeCameraAndFilterPairs);
        appendConfigurationDisplayMessage(displayMessage, getLang().get("configviewer.metadata.exposuretimefilter.removed"), removedExposureTimeCameraAndFilterPairs);

        appendConfigurationDisplayMessage(displayMessage, getLang().get("configviewer.metadata.isofilter.added"), addedIsoCameraAndFilterPairs);
        appendConfigurationDisplayMessage(displayMessage, getLang().get("configviewer.metadata.isofilter.removed"), removedIsoCameraAndFilterPairs);

        return displayMessage.toString();
    }

    private <F extends IFilterMask> void appendConfigurationDisplayMessage(StringBuilder displayMessage, String prefix, List<CameraAndFilterPair<F>> modifiedCameraAndFilterPairs) {
        if (!modifiedCameraAndFilterPairs.isEmpty()) {
            for (CameraAndFilterPair<F> modifiedCameraAndFilterPair : modifiedCameraAndFilterPairs) {
                displayMessage.append(prefix + " " + modifiedCameraAndFilterPair.toString());
                displayMessage.append("\n");
            }
        }
    }

    private <F extends IFilterMask> List<CameraAndFilterPair<F>> createCameraAndFilterPairsFromTableModel(FilteringTableModel<F> filteringTableModel) {
        List<CameraAndFilterPair<F>> cameraAndFilterPairsFromTableModel = new ArrayList<CameraAndFilterPair<F>>();

        int rowCount = filteringTableModel.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            cameraAndFilterPairsFromTableModel.add(filteringTableModel.getRow(i));
        }
        return cameraAndFilterPairsFromTableModel;
    }

    private <F extends IFilterMask> List<CameraAndFilterPair<F>> createCameraAndFilterPairsFromConfig(List<MetaDataFilter<F>> exposureTimeFilters) {
        List<CameraAndFilterPair<F>> cameraAndFilterPairsFromConfig = new ArrayList<CameraAndFilterPair<F>>();

        for (MetaDataFilter<F> exposureTimeFilter : exposureTimeFilters) {
            cameraAndFilterPairsFromConfig.add(createCameraAndFilterPair(exposureTimeFilter.getCameraModel(), exposureTimeFilter.getFilterMask()));
        }

        return cameraAndFilterPairsFromConfig;
    }


    private <F extends IFilterMask> List<CameraAndFilterPair<F>> getRemovedFilterPairs(List<CameraAndFilterPair<F>> cameraAndFilterPairsFromTableModel, List<CameraAndFilterPair<F>> cameraAndFilterPairsFromConfig) {
        List<CameraAndFilterPair<F>> removedCameraAndFilterPairs = new ArrayList<CameraAndFilterPair<F>>();

        for (CameraAndFilterPair<F> cameraAndFilterPairFromConfig : cameraAndFilterPairsFromConfig) {
            if (!cameraAndFilterPairsFromTableModel.contains(cameraAndFilterPairFromConfig)) {
                removedCameraAndFilterPairs.add(cameraAndFilterPairFromConfig);
            }
        }
        return removedCameraAndFilterPairs;
    }


    private <F extends IFilterMask> List<CameraAndFilterPair<F>> getAddedFilterPairs(List<CameraAndFilterPair<F>> cameraAndFilterPairsFromTableModel, List<CameraAndFilterPair<F>> cameraAndFilterPairsFromConfig) {
        List<CameraAndFilterPair<F>> addedCameraAndFilterPairs = new ArrayList<CameraAndFilterPair<F>>();

        for (CameraAndFilterPair<F> cameraAndFilterPairFromTableModel : cameraAndFilterPairsFromTableModel ) {
            if (!cameraAndFilterPairsFromConfig.contains(cameraAndFilterPairFromTableModel)) {
                addedCameraAndFilterPairs.add(cameraAndFilterPairFromTableModel);
            }
        }
        return addedCameraAndFilterPairs;
    }

    @Override
    public void updateConfiguration() {
        MetaData metadata = getConfiguration().getMetadata();

        List<MetaDataFilter<ISOFilterMask>> isoFilters = metadata.getIsoFilters();

        isoFilters.clear();

        int rowCount = isoFilteringTableModel.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            CameraAndFilterPair<ISOFilterMask> row = isoFilteringTableModel.getRow(i);

            String cameraModel = row.getCameraModel();
            ISOFilterMask isoFilterMask = row.getFilterMask();

            MetaDataFilter<ISOFilterMask> isoFilter = new MetaDataFilter<ISOFilterMask>();
            isoFilter.setCameraModel(cameraModel);
            isoFilter.setFilterMask(isoFilterMask);
            isoFilters.add(isoFilter);
        }

        List<MetaDataFilter<ExposureTimeFilterMask>> exposureTimeFilters = metadata.getExposureTimeFilters();

        exposureTimeFilters.clear();

        rowCount = exposureTimeFilteringTableModel.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            CameraAndFilterPair<ExposureTimeFilterMask> row = exposureTimeFilteringTableModel.getRow(i);

            String cameraModel = row.getCameraModel();
            ExposureTimeFilterMask exposureTimeFilterMask = row.getFilterMask();

            MetaDataFilter<ExposureTimeFilterMask> exposureTimeFilter = new MetaDataFilter<ExposureTimeFilterMask>();
            exposureTimeFilter.setCameraModel(cameraModel);
            exposureTimeFilter.setFilterMask(exposureTimeFilterMask);
            exposureTimeFilters.add(exposureTimeFilter);
        }
    }

    private class AddNewISORuleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CameraAndFilterPair<ISOFilterMask> createdCameraAndFilterPair = createCameraAndFilterPair((String)cameraModelsJComboBox.getSelectedItem(), (ISOFilterMask)isoPatterns.getSelectedItem());
            isoFilteringTableModel.addRow(createdCameraAndFilterPair);
        }
    }

    private class AddNewExposureTimeRuleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CameraAndFilterPair<ExposureTimeFilterMask> createdCameraAndFilterPair = createCameraAndFilterPair((String)cameraModelsJComboBox.getSelectedItem(), (ExposureTimeFilterMask)exposureTimePatterns.getSelectedItem());
            exposureTimeFilteringTableModel.addRow(createdCameraAndFilterPair);
        }
    }

    private <T extends IFilterMask> CameraAndFilterPair<T> createCameraAndFilterPair(String cameraModel, T filterMask) {
        CameraAndFilterPair<T> cameraAndExposureTimeFilterPair = new CameraAndFilterPair<T>();
        cameraAndExposureTimeFilterPair.setCameraModel(cameraModel);
        cameraAndExposureTimeFilterPair.setFilterMask(filterMask);

        return cameraAndExposureTimeFilterPair;
    }

    private class RemoveISORuleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int[] selectedRowIndices = isoRuleToCameraModelTable.getSelectedRows();

            if (selectedRowIndices.length > 0) {
                List<CameraAndFilterPair<ISOFilterMask>> rowsToRemove = new ArrayList<CameraAndFilterPair<ISOFilterMask>>();
                for (int selectedRowIndex : selectedRowIndices) {
                    CameraAndFilterPair<ISOFilterMask> row = isoFilteringTableModel.getRow(isoRuleToCameraModelTable.convertRowIndexToModel(selectedRowIndex));
                    rowsToRemove.add(row);
                }

                StringBuilder paths = new StringBuilder();

                for (CameraAndFilterPair<ISOFilterMask> rowToRemove : rowsToRemove) {
                    paths.append(rowToRemove.getCameraModel() + " " + rowToRemove.getFilterMask());
                    paths.append(C.LS);
                }

                int result = displayConfirmDialog(getLang().get("configviewer.metadata.isofiltertable.delete.confirmmessage") + C.LS + C.LS + paths.toString(), getLang().get("common.confirmation"), JOptionPane.OK_CANCEL_OPTION);

                if (result == 0) {
                    for (CameraAndFilterPair<ISOFilterMask> rowToRemove : rowsToRemove) {
                        isoFilteringTableModel.removeRow(rowToRemove);
                    }
                }
            }
        }
    }

    private class RemoveExposureTimeRuleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int[] selectedRowIndices = exposureTimeRuleToCameraModelTable.getSelectedRows();

            if (selectedRowIndices.length > 0) {
                List<CameraAndFilterPair<ExposureTimeFilterMask>> rowsToRemove = new ArrayList<CameraAndFilterPair<ExposureTimeFilterMask>>();
                for (int selectedRowIndex : selectedRowIndices) {
                    CameraAndFilterPair<ExposureTimeFilterMask> row = exposureTimeFilteringTableModel.getRow(exposureTimeRuleToCameraModelTable.convertRowIndexToModel(selectedRowIndex));
                    rowsToRemove.add(row);
                }

                StringBuilder paths = new StringBuilder();

                for (CameraAndFilterPair<ExposureTimeFilterMask> rowToRemove : rowsToRemove) {
                    paths.append(rowToRemove.getCameraModel() + " " + rowToRemove.getFilterMask());
                    paths.append(C.LS);
                }

                int result = displayConfirmDialog(getLang().get("configviewer.metadata.exposuretimefiltertable.delete.confirmmessage") + C.LS + C.LS + paths.toString(), getLang().get("common.confirmation"), JOptionPane.OK_CANCEL_OPTION);

                if (result == 0) {
                    for (CameraAndFilterPair<ExposureTimeFilterMask> rowToRemove : rowsToRemove) {
                        exposureTimeFilteringTableModel.removeRow(rowToRemove);
                    }
                }
            }
        }
    }
}
