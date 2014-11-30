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
package moller.javapeg.program.gui.frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPane;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPaneUtil;
import moller.javapeg.program.config.model.applicationmode.resize.ResizeImages;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.gui.components.DestinationDirectorySelector;
import moller.javapeg.program.gui.frames.base.JavaPEGBaseFrame;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.jpeg.JPEGThumbNail;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.progress.CustomizedJTextArea;
import moller.util.image.ImageUtil;
import moller.util.io.DirectoryUtil;

public class ImageResizer extends JavaPEGBaseFrame {

    private static final long serialVersionUID = 1L;

    private DefaultListModel<File> imagesToViewListModel;

    private JTextField widthTextField;
    private JTextField heightTextField;

    private JComboBox<Integer> qualityComboBox;

    private JList<File> imagesToViewList;

    private JButton resizeButton;
    private JButton cancelResizeButton;
    private JButton removeSelectedImagesButton;

    private JLabel imagePreviewLabel;

    private final List<File> imagesToResize;

    private final SimpleDateFormat sdf;

    private CustomizedJTextArea outputTextArea;

    private JProgressBar progressBar;

    private ImageResizeWorker irw;

    private JSplitPane leftAndRightSplitpane;

    private DestinationDirectorySelector destinationDirectorySelector;

    public ImageResizer(List<File> imagesToResize) {

        this.imagesToResize = imagesToResize;

        sdf = getConfiguration().getRenameImages().getProgressLogTimestampFormat();

        this.createMainFrame();
        this.addListeners();
        this.initiateGUI();
    }

    private void initiateGUI() {
        imagesToViewList.setSelectedIndex(0);

        ResizeImages resizeImages = getConfiguration().getResizeImages();

        widthTextField.setText(resizeImages.getWidth().equals(-1) ? "" : Integer.toString(resizeImages.getWidth()));
        heightTextField.setText(resizeImages.getHeight().equals(-1) ? "" : Integer.toString(resizeImages.getHeight()));

        File pathDestination = resizeImages.getPathDestination();

        while (pathDestination != null && !pathDestination.exists()) {
            pathDestination = pathDestination.getParentFile();
        }

        destinationDirectorySelector.setText(pathDestination == null ? "" : pathDestination.getAbsolutePath());

        Integer selectedQualityIndex = resizeImages.getSelectedQualityIndex();

        if (!(selectedQualityIndex > -1) || !(selectedQualityIndex < qualityComboBox.getItemCount())) {
            selectedQualityIndex = 0;
        }
        qualityComboBox.setSelectedIndex(selectedQualityIndex);
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        removeSelectedImagesButton.addActionListener(new RemoveSelectedImagesListener());
        imagesToViewList.addListSelectionListener(new ImagesToViewListListener());
    }

    private void createMainFrame() {
        loadAndApplyGUISettings();

        this.setTitle(getLang().get("imageresizer.gui.title"));

        InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/ImageResizer16.gif");

        try {
            this.setIconImage(ImageIO.read(imageStream));
        } catch (IOException e) {
            getLogger().logERROR("Could not load icon: Open16.gif");
            getLogger().logERROR(e);
        }

        GBHelper posBackgroundPanel = new GBHelper();

        this.getContentPane().setLayout(new GridBagLayout());

        leftAndRightSplitpane = new JSplitPane();
        leftAndRightSplitpane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        leftAndRightSplitpane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(getGUIWindowConfig().getGuiWindowSplitPane(), ConfigElement.VERTICAL));
        leftAndRightSplitpane.setLeftComponent(this.createLeftPanel());
        leftAndRightSplitpane.setRightComponent(this.createRightPanel());

        this.getContentPane().add(leftAndRightSplitpane, posBackgroundPanel.expandH().expandW());
    }

    private JPanel createLeftPanel() {

        JPanel imageAndPreviewPanel = new JPanel(new GridBagLayout());

        GBHelper posImageAndPreviewPanel = new GBHelper();

        imageAndPreviewPanel.add(this.createImageListPanel(), posImageAndPreviewPanel.expandH().expandW());
        imageAndPreviewPanel.add(Box.createHorizontalStrut(2), posImageAndPreviewPanel.nextCol());
        imageAndPreviewPanel.add(this.createPreviewPanel(), posImageAndPreviewPanel.nextCol().expandH());

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(new EmptyBorder(2, 2, 2, 1));

        GBHelper posBackgroundPanel= new GBHelper();

        backgroundPanel.add(imageAndPreviewPanel, posBackgroundPanel.expandH().expandW());
        backgroundPanel.add(Box.createVerticalStrut(2), posBackgroundPanel.nextRow());
        backgroundPanel.add(this.createInputPanel(), posBackgroundPanel.nextRow().expandW());

        return backgroundPanel;
    }

    private JPanel createRightPanel() {
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(new EmptyBorder(2, 1, 2, 2));

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(this.createOutputPanel(), posBackgroundPanel.expandH().expandW());
        backgroundPanel.add(this.createStatusBarPanel(), posBackgroundPanel.nextRow().expandW());

        return backgroundPanel;
    }

    private JPanel createOutputPanel() {

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        GBHelper posBackgroundPanel = new GBHelper();

        JLabel outputLabel = new JLabel(getLang().get("imageresizer.processlog.title"));
        outputLabel.setForeground(Color.GRAY);

        outputTextArea = new CustomizedJTextArea();
        outputTextArea.setEditable(false);

        JScrollPane sp = new JScrollPane(outputTextArea);

        backgroundPanel.add(outputLabel,posBackgroundPanel);
        backgroundPanel.add(sp, posBackgroundPanel.nextRow().expandH().expandW());

        return backgroundPanel;
    }

    private Component createStatusBarPanel() {
        JPanel backgroundPanel = new JPanel(new GridBagLayout());

        GBHelper posBackgroundPanel = new GBHelper();

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        backgroundPanel.add(progressBar, posBackgroundPanel.expandW());

        return backgroundPanel;
    }

    private JPanel createImageListPanel() {

        imagesToViewListModel = new DefaultListModel<File>();

        for (File imageToResize : imagesToResize) {
            imagesToViewListModel.addElement(imageToResize);
        }

        imagesToViewList = new JList<File>(imagesToViewListModel);
        imagesToViewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane spImageList = new JScrollPane(imagesToViewList);

        removeSelectedImagesButton = new JButton();
        removeSelectedImagesButton.setIcon(IconLoader.getIcon(Icons.REMOVE));
        removeSelectedImagesButton.setToolTipText(getLang().get("maingui.tabbedpane.imagelist.button.removeSelectedImages"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        buttonPanel.add(removeSelectedImagesButton);

        JLabel imageListLabel = new JLabel(getLang().get("maingui.tabbedpane.imagelist.label.list"));
        imageListLabel.setForeground(Color.GRAY);

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        GBHelper posBackground = new GBHelper();

        backgroundPanel.add(imageListLabel, posBackground);
        backgroundPanel.add(spImageList, posBackground.nextRow().expandH().expandW());
        backgroundPanel.add(Box.createVerticalStrut(5), posBackground.nextRow());
        backgroundPanel.add(buttonPanel, posBackground.nextRow().expandW());

        return backgroundPanel;
    }

    private JPanel createPreviewPanel() {

        JLabel previewLabel = new JLabel(getLang().get("maingui.tabbedpane.imagelist.label.preview"));
        previewLabel.setForeground(Color.GRAY);

        JPanel previewBackgroundPanel = new JPanel(new GridBagLayout());
        previewBackgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        GBHelper posPreviewPanel = new GBHelper();

        imagePreviewLabel = new JLabel();

        JPanel previewPanel = new JPanel();
        previewPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        previewPanel.add(imagePreviewLabel);

        previewBackgroundPanel.add(previewLabel, posPreviewPanel);
        previewBackgroundPanel.add(previewPanel, posPreviewPanel.nextRow());
        previewBackgroundPanel.add(new JPanel(), posPreviewPanel.nextRow().expandH());

        return previewBackgroundPanel;
    }

    private JPanel createInputPanel() {

        destinationDirectorySelector = new DestinationDirectorySelector(false);

        JLabel widthLabel = new JLabel(getLang().get("imageresizer.resize.input.width"));

        widthTextField = new JTextField();
        ((AbstractDocument)widthTextField.getDocument()).setDocumentFilter(new IntegerDocumentFilter());

        JLabel heightLabel = new JLabel(getLang().get("imageresizer.resize.input.height"));

        heightTextField = new JTextField();
        ((AbstractDocument)heightTextField.getDocument()).setDocumentFilter(new IntegerDocumentFilter());

        JLabel qualityLabel = new JLabel(getLang().get("imageresizer.resize.input.quality"));

        qualityComboBox = new JComboBox<Integer>();
        qualityComboBox.addItem(Integer.valueOf(100));
        qualityComboBox.addItem(Integer.valueOf(75));
        qualityComboBox.addItem(Integer.valueOf(50));
        qualityComboBox.addItem(Integer.valueOf(25));

        resizeButton = new JButton(getLang().get("imageresizer.resize.input.button.resize"));
        resizeButton.addActionListener(new ResizeButtonListener());

        cancelResizeButton = new JButton(getLang().get("imageresizer.resize.input.button.cancel"));
        cancelResizeButton.addActionListener(new CancelResizeButtonListener());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        buttonPanel.add(resizeButton);
        buttonPanel.add(cancelResizeButton);

        GBHelper posBackground = new GBHelper();

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        backgroundPanel.add(destinationDirectorySelector, posBackground);
        backgroundPanel.add(Box.createVerticalStrut(3), posBackground.nextRow());
        backgroundPanel.add(widthLabel, posBackground.nextRow());
        backgroundPanel.add(widthTextField, posBackground.nextRow().expandW());
        backgroundPanel.add(Box.createVerticalStrut(3), posBackground.nextRow());
        backgroundPanel.add(heightLabel, posBackground.nextRow());
        backgroundPanel.add(heightTextField, posBackground.nextRow().expandW());
        backgroundPanel.add(Box.createVerticalStrut(3), posBackground.nextRow());
        backgroundPanel.add(qualityLabel, posBackground.nextRow());
        backgroundPanel.add(qualityComboBox, posBackground.nextRow().expandW());
        backgroundPanel.add(Box.createVerticalStrut(3), posBackground.nextRow());
        backgroundPanel.add(buttonPanel, posBackground.nextRow());

        return backgroundPanel;
    }

    @Override
    protected void saveSettings() {
        super.saveSettings();

        List<GUIWindowSplitPane> guiWindowSplitPanes = getGUIWindowConfig().getGuiWindowSplitPane();

        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.VERTICAL, leftAndRightSplitpane.getDividerLocation());

        ResizeImages resizeImages = getConfiguration().getResizeImages();

        resizeImages.setHeight(heightTextField.getText().equals("") ? -1 : Integer.parseInt(heightTextField.getText()));
        resizeImages.setWidth(widthTextField.getText().equals("") ? -1 : Integer.parseInt(widthTextField.getText()));
        resizeImages.setPathDestination(destinationDirectorySelector.getText().equals("") ? null : new File(destinationDirectorySelector.getText()));
        resizeImages.setSelectedQualityIndex(qualityComboBox.getSelectedIndex());
    }

    private class RemoveSelectedImagesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!imagesToViewList.isSelectionEmpty()) {

                int [] selectedIndices = imagesToViewList.getSelectedIndices();

                // Remove all the selected indices
                while(selectedIndices.length > 0) {
                    imagesToViewListModel.remove(selectedIndices[0]);
                    selectedIndices = imagesToViewList.getSelectedIndices();
                }
                imagePreviewLabel.setIcon(null);
            }
        }
    }

    private class ResizeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!imagesToViewListModel.isEmpty() && !destinationDirectorySelector.getText().isEmpty() && (!widthTextField.getText().isEmpty() || !heightTextField.getText().isEmpty()) ) {
                resizeButton.setEnabled(false);
                irw = new ImageResizeWorker();
                irw.addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(final PropertyChangeEvent event) {
                      switch (event.getPropertyName()) {
                      case "progress":
                          progressBar.setIndeterminate(false);
                          progressBar.setValue((Integer) event.getNewValue());
                        break;
                      }
                    }
                  });
                irw.execute();
            }
        }
    }

    private class CancelResizeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (irw != null && !irw.isCancelled()) {
                irw.cancel(true);
            }
        }
    }

    private class ImagesToViewListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int selectedIndex = imagesToViewList.getSelectedIndex();

            if (selectedIndex > -1) {
                JPEGThumbNail thumbNail = JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(imagesToViewListModel.get(selectedIndex));
                imagePreviewLabel.setIcon(new ImageIcon(thumbNail.getThumbNailData()));
            }
        }
    }

    private class ImageResizeWorker extends SwingWorker<String, String> {
        @Override
        protected String doInBackground() throws Exception {
            long startTime = System.currentTimeMillis();

            int width = 0;
            int height = 0;

            try {
                width = Integer.parseInt(widthTextField.getText());
            } catch (Exception e) {
                // Do nothing, go on with default value;
            }

            try {
                height = Integer.parseInt(heightTextField.getText());
            } catch (Exception e) {
                // Do nothing, go on with default value;
            }

            float quality = 100;

            if (qualityComboBox.getItemAt(qualityComboBox.getSelectedIndex()) != null) {
                quality = qualityComboBox.getItemAt(qualityComboBox.getSelectedIndex());
            }

            File destinationDirectory = new File(destinationDirectorySelector.getText(), "resized");

            destinationDirectory = DirectoryUtil.getUniqueDirectory(destinationDirectory.getParentFile(), destinationDirectory);

            publish(getLang().get("imageresizer.resize.process.started"));

            if (!destinationDirectory.mkdirs()) {
                publish(getLang().get("imageresizer.resize.process.couldNotCreateDirectory") + ": " + destinationDirectory.getAbsolutePath());
                getLogger().logERROR("Could not create directory:" + destinationDirectory.getAbsolutePath());

                return getLang().get("imageresizer.resize.process.aborted");
            } else {
                publish(getLang().get("imageresizer.resize.process.destination.directory.created") + " " + destinationDirectory.getAbsolutePath());

                int size = imagesToViewListModel.getSize();

                for (int index = 0; index < size; index++) {
                    if (!irw.isCancelled()) {
                        long startTimeImageResize = System.currentTimeMillis();

                        float floatQuality = quality / 100;
                        ImageUtil.resizeAndStoreImage(imagesToViewListModel.get(index), width, height, destinationDirectory, floatQuality);
                        publish(String.format(getLang().get("imageresizer.processlog.image.processed"), imagesToViewListModel.get(index).getAbsolutePath(), (System.currentTimeMillis() - startTimeImageResize)));
                        setProgress((index + 1) * 100 / size);
                    } else {
                        publish(getLang().get("imageresizer.resize.process.cancelled"));
                        break;
                    }
                }

                if (!irw.isCancelled()) {
                    publish(String.format(getLang().get("imageresizer.processlog.image.resize.done"), (System.currentTimeMillis() - startTime) / 1000));
                    return getLang().get("imageresizer.resize.process.done");
                } else {
                    publish(String.format(getLang().get("imageresizer.processlog.image.resize.done.cancelled"), (System.currentTimeMillis() - startTime) / 1000));

                    resizeButton.setEnabled(true);
                    return getLang().get("imageresizer.resize.process.cancelled");
                }
            }
        }

        @Override
        protected void process(List<String> chunks) {
            for (String chunk : chunks) {
                setLogMessage(chunk);
            }
        }

        @Override
        protected void done() {
            try {
                JOptionPane.showMessageDialog(ImageResizer.this, get());
                resizeButton.setEnabled(true);
            } catch (ExecutionException e) {
                throw new RuntimeException(e.getCause());
            } catch (InterruptedException e) {
                resizeButton.setEnabled(true);
            } catch (Exception e) {
            }
        }
    }

    /**
     * Convenience method for adding a log statement to the process log. After
     * each log statement there is automatically a newline added, and the log
     * is automatically scrolled to bottom.
     *
     * @param logMessage is the string that shall be added to the log.
     */
    public void setLogMessage(String logMessage) {
        String formattedTimeStamp = sdf.format(new Date(System.currentTimeMillis()));
        outputTextArea.appendAndScroll(formattedTimeStamp + ": " + logMessage + "\n");
    }

    /**
     * This class makes it possible to restrict the input to integers only when
     * applied to for instance a {@link JTextField}.
     *
     * @author Fredrik
     *
     */
    private class IntegerDocumentFilter extends DocumentFilter {
        // Regular expression which finds all non digits
        private final String NON_INTEGER_REGEXP = "\\D++";

        @Override
        public void insertString(DocumentFilter.FilterBypass fp, int offset, String string, AttributeSet aset) throws BadLocationException {
            fp.insertString(offset, string.replaceAll(NON_INTEGER_REGEXP, ""), aset);
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fp, int offset, int length, String string, AttributeSet aset) throws BadLocationException {
            fp.insertString(offset, string.replaceAll(NON_INTEGER_REGEXP, ""), aset);
        }
    }

    @Override
    public GUIWindow getGUIWindowConfig() {
        return getConfiguration().getgUI().getImageResizer();
    }

    @Override
    public Dimension getDefaultSize() {
        return new Dimension(GUIDefaults.IMAGE_RESIZER_WIDTH, GUIDefaults.IMAGE_RESIZER_HEIGHT);
    }
}
