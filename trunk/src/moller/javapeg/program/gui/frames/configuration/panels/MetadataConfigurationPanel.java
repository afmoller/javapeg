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
import moller.javapeg.program.config.model.metadata.ISOFilter;
import moller.javapeg.program.config.model.metadata.MetaData;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContext;
import moller.javapeg.program.enumerations.ISOFilterMask;
import moller.javapeg.program.gui.ComboboxToolTipRenderer;
import moller.javapeg.program.gui.CustomizedJTable;
import moller.javapeg.program.gui.frames.configuration.panels.base.BaseConfigurationPanel;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.model.iso.CameraAndISOFilterPair;
import moller.javapeg.program.model.iso.ISOFilteringTableModel;

public class MetadataConfigurationPanel extends BaseConfigurationPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JTabbedPane tabbedPane;

    private JButton addNewRuleButton;
    private JButton removeRuleButton;
    private JComboBox<String> cameraModelsJComboBox;
    private JComboBox<ISOFilterMask> isoPatterns;

    private ISOFilteringTableModel isoFilteringTableModel;
    private CustomizedJTable isoRuleToCameraModelTable;

    @Override
    public boolean isValidConfiguration() {
        return true;
    }

    @Override
    protected void addListeners() {
        addNewRuleButton.addActionListener(new AddNewRuleListener());
        removeRuleButton.addActionListener(new RemoveRuleListener());
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
//      TODO: fix hard coded string
        backgroundPanel.setBorder(BorderFactory.createTitledBorder("Configurations"));

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
//        TODO: fix hard coded string
        backgroundPanel.setBorder(BorderFactory.createTitledBorder("Camera model"));
        backgroundPanel.add(cameraModelsJComboBox, posPanel.nextRow().expandW());

        return backgroundPanel;
    }

    private JPanel createExposureTimeConfigurationPanel() {
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setName(getLang().get("variable.shutterSpeed"));

        return backgroundPanel;
    }

    private JPanel createISOConfigurationPanel() {
        isoFilteringTableModel = new ISOFilteringTableModel();

        MetaData metadata = getConfiguration().getMetadata();
        List<ISOFilter> isoFilters = metadata.getIsoFilters();

        for (ISOFilter isoFilter : isoFilters) {
            CameraAndISOFilterPair cameraAndISOFilterPair = new CameraAndISOFilterPair();
            cameraAndISOFilterPair.setCameraModel(isoFilter.getCameraModel());
            cameraAndISOFilterPair.setiSOFilter(isoFilter.getIsoFilterMask());
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
        isoRuleToCameraModelScrollPanePanel.setBorder(BorderFactory.createTitledBorder("Configured filterpatterns"));
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
//        TODO: fix hard coded string
        tooltips.add("tooltip 1");
        tooltips.add("tooltip 2");
        tooltips.add("tooltip 3");
        tooltips.add("tooltip 4");
        comboboxToolTipRenderer.setTooltips(tooltips);

        isoPatterns = new JComboBox<ISOFilterMask>(isoPatternModel);
        isoPatterns.setRenderer(comboboxToolTipRenderer);

        addNewRuleButton = new JButton(IconLoader.getIcon(Icons.ADD));
//      TODO: Fix hard coded string
        addNewRuleButton.setToolTipText("Add rule for selected selected camera model");

        removeRuleButton = new JButton(IconLoader.getIcon(Icons.REMOVE));

        GBHelper posButtonPanel = new GBHelper();
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Manage filterpatterns"));
        buttonPanel.add(isoPatterns, posButtonPanel.nextRow());
        buttonPanel.add(Box.createHorizontalStrut(3), posButtonPanel.nextCol());
        buttonPanel.add(addNewRuleButton, posButtonPanel.nextCol());
        buttonPanel.add(removeRuleButton, posButtonPanel.nextCol());
        buttonPanel.add(Box.createHorizontalGlue(), posButtonPanel.nextCol().expandW());
        return buttonPanel;
    }

    @Override
    public String getChangedConfigurationMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateConfiguration() {
        MetaData metadata = getConfiguration().getMetadata();

        List<ISOFilter> isoFilters = metadata.getIsoFilters();

        isoFilters.clear();

        int rowCount = isoFilteringTableModel.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            CameraAndISOFilterPair row = isoFilteringTableModel.getRow(i);

            String cameraModel = row.getCameraModel();
            ISOFilterMask isoFilterMask = row.getiSOFilter();

            ISOFilter isoFilter = new ISOFilter();
            isoFilter.setCameraModel(cameraModel);
            isoFilter.setIsoFilter(isoFilterMask);
            isoFilters.add(isoFilter);
        }
    }

    private class AddNewRuleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CameraAndISOFilterPair cameraAndISOFilterPair = new CameraAndISOFilterPair();
            cameraAndISOFilterPair.setCameraModel((String)cameraModelsJComboBox.getSelectedItem());
            cameraAndISOFilterPair.setiSOFilter((ISOFilterMask) isoPatterns.getSelectedItem());

            isoFilteringTableModel.addRow(cameraAndISOFilterPair);
        }
    }

    private class RemoveRuleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int[] selectedRowIndices = isoRuleToCameraModelTable.getSelectedRows();

            if (selectedRowIndices.length > 0) {
                List<CameraAndISOFilterPair> rowsToRemove = new ArrayList<CameraAndISOFilterPair>();
                for (int selectedRowIndex : selectedRowIndices) {
                    CameraAndISOFilterPair row = isoFilteringTableModel.getRow(isoRuleToCameraModelTable.convertRowIndexToModel(selectedRowIndex));
                    rowsToRemove.add(row);
                }

                StringBuilder paths = new StringBuilder();

                for (CameraAndISOFilterPair rowToRemove : rowsToRemove) {
                    paths.append(rowToRemove.getCameraModel() + " " + rowToRemove.getiSOFilter());
                    paths.append(C.LS);
                }

                int result = displayConfirmDialog(getLang().get("configviewer.metadata.isofiltertable.delete.confirmmessage") + C.LS + C.LS + paths.toString(), getLang().get("common.confirmation"), JOptionPane.OK_CANCEL_OPTION);

                if (result == 0) {
                    for (CameraAndISOFilterPair rowToRemove : rowsToRemove) {
                        isoFilteringTableModel.removeRow(rowToRemove);
                    }
                }
            }
        }
    }
}
