package moller.javapeg.program.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
import javax.swing.UIManager;
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
import moller.javapeg.program.Gap;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPane;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPaneUtil;
import moller.javapeg.program.config.model.applicationmode.resize.ResizeImages;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.jpeg.JPEGThumbNail;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.progress.CustomizedJTextArea;
import moller.util.gui.Screen;
import moller.util.image.ImageUtil;
import moller.util.io.DirectoryUtil;

public class ImageResizer extends JFrame {

    private static final long serialVersionUID = 1L;

    private static Configuration configuration;
    private static Logger logger;
    private static Language lang;

    private DefaultListModel<File> imagesToViewListModel;

    private JTextField resizeDestinationPathTextField;
    private JTextField widthTextField;
    private JTextField heightTextField;

    private JComboBox<Integer> qualityComboBox;

    private JList<File> imagesToViewList;

    private JButton resizeDestinationPathButton;
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

    public ImageResizer(List<File> imagesToResize) {

        this.imagesToResize = imagesToResize;

        configuration = Config.getInstance().get();
        logger = Logger.getInstance();
        lang   = Language.getInstance();

        sdf = Config.getInstance().get().getRenameImages().getProgressLogTimestampFormat();

        this.createMainFrame();
        this.addListeners();
        this.initiateGUI();
    }

    private void initiateGUI() {
        imagesToViewList.setSelectedIndex(0);

        ResizeImages resizeImages = configuration.getResizeImages();

        widthTextField.setText(resizeImages.getWidth().equals(-1) ? "" : Integer.toString(resizeImages.getWidth()));
        heightTextField.setText(resizeImages.getHeight().equals(-1) ? "" : Integer.toString(resizeImages.getHeight()));

        File pathDestination = resizeImages.getPathDestination();

        while (pathDestination != null && !pathDestination.exists()) {
            pathDestination = pathDestination.getParentFile();
        }

        resizeDestinationPathTextField.setText(pathDestination == null ? "" : pathDestination.getAbsolutePath());

        Integer selectedQualityIndex = resizeImages.getSelectedQualityIndex();

        if (!(selectedQualityIndex > -1) || !(selectedQualityIndex < qualityComboBox.getItemCount())) {
            selectedQualityIndex = 0;
        }
        qualityComboBox.setSelectedIndex(selectedQualityIndex);
    }

    private void addListeners() {
        this.addWindowListener(new WindowDestroyer());
        removeSelectedImagesButton.addActionListener(new RemoveSelectedImagesListener());
        imagesToViewList.addListSelectionListener(new ImagesToViewListListener());
    }

    private void createMainFrame() {
        GUI gUI = configuration.getgUI();

        this.setTitle(lang.get("imageresizer.gui.title"));

        GUIWindow imageResizer = gUI.getImageResizer();

        Point xyFromConfig = imageResizer.getSizeAndLocation().getLocation();

        if (Screen.isVisibleOnScreen(imageResizer.getSizeAndLocation())) {
            this.setLocation(xyFromConfig);
            this.setSize(imageResizer.getSizeAndLocation().getSize());

        } else {
            JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            logger.logERROR("Could not set location of Image Resizer GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");

            this.setLocation(0,0);
            this.setSize(GUIDefaults.IMAGE_RESIZER_WIDTH, GUIDefaults.IMAGE_RESIZER_HEIGHT);
        }

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            logger.logERROR("Could not set desired Look And Feel for Main GUI");
            logger.logERROR(e);
        }

        InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/ImageResizer16.gif");

        try {
            this.setIconImage(ImageIO.read(imageStream));
        } catch (IOException e) {
            logger.logERROR("Could not load icon: Open16.gif");
            logger.logERROR(e);
        }

        GBHelper posBackgroundPanel = new GBHelper();

        this.getContentPane().setLayout(new GridBagLayout());

        leftAndRightSplitpane = new JSplitPane();
        leftAndRightSplitpane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        leftAndRightSplitpane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(imageResizer.getGuiWindowSplitPane(), ConfigElement.VERTICAL));
        leftAndRightSplitpane.setLeftComponent(this.createLeftPanel());
        leftAndRightSplitpane.setRightComponent(this.createRightPanel());

        this.getContentPane().add(leftAndRightSplitpane, posBackgroundPanel.expandH().expandW());

//        this.getContentPane().add(this.createLeftPanel(), posBackgroundPanel.expandH());
//        this.getContentPane().add(this.createRightPanel(), posBackgroundPanel.nextCol().expandH().expandW());
    }

    private JPanel createLeftPanel() {

        JPanel imageAndPreviewPanel = new JPanel(new GridBagLayout());

        GBHelper posImageAndPreviewPanel = new GBHelper();

        imageAndPreviewPanel.add(this.createImageListPanel(), posImageAndPreviewPanel.expandH().expandW());
        imageAndPreviewPanel.add(new Gap(2), posImageAndPreviewPanel.nextCol());
        imageAndPreviewPanel.add(this.createPreviewPanel(), posImageAndPreviewPanel.nextCol().expandH());

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(new EmptyBorder(2, 2, 2, 1));

        GBHelper posBackgroundPanel= new GBHelper();

        backgroundPanel.add(imageAndPreviewPanel, posBackgroundPanel.expandH().expandW());
        backgroundPanel.add(new Gap(2), posBackgroundPanel.nextRow());
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

        JLabel outputLabel = new JLabel(lang.get("imageresizer.processlog.title"));
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

        InputStream imageStream = null;

        ImageIcon removePictureImageIcon = new ImageIcon();

        imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/remove.gif");
        try {
            removePictureImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        removeSelectedImagesButton = new JButton();
        removeSelectedImagesButton.setIcon(removePictureImageIcon);
        removeSelectedImagesButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.removeSelectedImages"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        buttonPanel.add(removeSelectedImagesButton);

        JLabel imageListLabel = new JLabel(lang.get("maingui.tabbedpane.imagelist.label.list"));
        imageListLabel.setForeground(Color.GRAY);

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        GBHelper posBackground = new GBHelper();

        backgroundPanel.add(imageListLabel, posBackground);
        backgroundPanel.add(spImageList, posBackground.nextRow().expandH().expandW());
        backgroundPanel.add(new Gap(5), posBackground.nextRow());
        backgroundPanel.add(buttonPanel, posBackground.nextRow().expandW());

        return backgroundPanel;
    }

    private JPanel createPreviewPanel() {

        JLabel previewLabel = new JLabel(lang.get("maingui.tabbedpane.imagelist.label.preview"));
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

        JLabel resizeDestinationPathLabel = new JLabel(lang.get("labels.destinationPath"));
        resizeDestinationPathLabel.setForeground(Color.GRAY);

        ImageIcon openPictureImageIcon = new ImageIcon();
        try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/open.gif")){
            openPictureImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException iox) {
            logger.logERROR("Could not open the image open.gif");
            logger.logERROR(iox);
        }

        resizeDestinationPathTextField = new JTextField();
        resizeDestinationPathTextField.setEditable(false);
        resizeDestinationPathTextField.setBackground(Color.WHITE);

        resizeDestinationPathButton = new JButton(openPictureImageIcon);
        resizeDestinationPathButton.setActionCommand("destinationPathButton");
        resizeDestinationPathButton.addActionListener(new ResizeDestinationPathButtonListener());

        resizeDestinationPathButton.setToolTipText(lang.get("tooltip.destinationPathButton"));
        resizeDestinationPathButton.setPreferredSize(new Dimension(30, 20));
        resizeDestinationPathButton.setMinimumSize(new Dimension(30, 20));

        JLabel widthLabel = new JLabel(lang.get("imageresizer.resize.input.width"));

        widthTextField = new JTextField();
        ((AbstractDocument)widthTextField.getDocument()).setDocumentFilter(new IntegerDocumentFilter());

        JLabel heightLabel = new JLabel(lang.get("imageresizer.resize.input.height"));

        heightTextField = new JTextField();
        ((AbstractDocument)heightTextField.getDocument()).setDocumentFilter(new IntegerDocumentFilter());

        JLabel qualityLabel = new JLabel(lang.get("imageresizer.resize.input.quality"));

        qualityComboBox = new JComboBox<Integer>();
        qualityComboBox.addItem(Integer.valueOf(100));
        qualityComboBox.addItem(Integer.valueOf(75));
        qualityComboBox.addItem(Integer.valueOf(50));
        qualityComboBox.addItem(Integer.valueOf(25));

        resizeButton = new JButton(lang.get("imageresizer.resize.input.button.resize"));
        resizeButton.addActionListener(new ResizeButtonListener());

        cancelResizeButton = new JButton(lang.get("imageresizer.resize.input.button.cancel"));
        cancelResizeButton.addActionListener(new CancelResizeButtonListener());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        buttonPanel.add(resizeButton);
        buttonPanel.add(cancelResizeButton);

        GBHelper posBackground = new GBHelper();

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        backgroundPanel.add(resizeDestinationPathLabel, posBackground);
        backgroundPanel.add(resizeDestinationPathTextField, posBackground.nextRow().expandW());
        backgroundPanel.add(resizeDestinationPathButton, posBackground.nextCol());
        backgroundPanel.add(new Gap(3), posBackground.nextRow());
        backgroundPanel.add(widthLabel, posBackground.nextRow());
        backgroundPanel.add(widthTextField, posBackground.nextRow().expandW());
        backgroundPanel.add(new Gap(3), posBackground.nextRow());
        backgroundPanel.add(heightLabel, posBackground.nextRow());
        backgroundPanel.add(heightTextField, posBackground.nextRow().expandW());
        backgroundPanel.add(new Gap(3), posBackground.nextRow());
        backgroundPanel.add(qualityLabel, posBackground.nextRow());
        backgroundPanel.add(qualityComboBox, posBackground.nextRow().expandW());
        backgroundPanel.add(new Gap(3), posBackground.nextRow());
        backgroundPanel.add(buttonPanel, posBackground.nextRow());

        return backgroundPanel;
    }

    private class WindowDestroyer extends WindowAdapter {
        @Override
        public void windowClosing (WindowEvent e) {
            disposeFrame();
        }
    }

    private void disposeFrame() {
        this.saveSettings();
        this.setVisible(false);
        this.dispose();
    }

    private void saveSettings() {
        GUI gUI = configuration.getgUI();

        if (this.isVisible()) {

            Rectangle sizeAndLocation = gUI.getImageResizer().getSizeAndLocation();

            sizeAndLocation.setSize(this.getSize().width, this.getSize().height);
            sizeAndLocation.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);
        }

        List<GUIWindowSplitPane> guiWindowSplitPanes = gUI.getImageResizer().getGuiWindowSplitPane();

        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.VERTICAL, leftAndRightSplitpane.getDividerLocation());

        ResizeImages resizeImages = configuration.getResizeImages();

        resizeImages.setHeight(heightTextField.getText().equals("") ? -1 : Integer.parseInt(heightTextField.getText()));
        resizeImages.setWidth(widthTextField.getText().equals("") ? -1 : Integer.parseInt(widthTextField.getText()));
        resizeImages.setPathDestination(resizeDestinationPathTextField.getText().equals("") ? null : new File(resizeDestinationPathTextField.getText()));
        resizeImages.setSelectedQualityIndex(qualityComboBox.getSelectedIndex());

    }

    private class ResizeDestinationPathButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle(lang.get("fileSelectionDialog.destinationPathFileChooser"));

            if (!resizeDestinationPathTextField.getText().equals("")) {
                File destinationPath = new File(resizeDestinationPathTextField.getText());
                if (destinationPath.isDirectory() && destinationPath.canRead()) {
                    chooser.setCurrentDirectory(destinationPath);
                }
            }

            if(chooser.showOpenDialog(ImageResizer.this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();

                if (!ApplicationContext.getInstance().getSourcePath().equals(selectedFile)) {
                    resizeDestinationPathTextField.setText(selectedFile.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.sameSourceAndDestination"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class RemoveSelectedImagesListener implements ActionListener {
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
        public void actionPerformed(ActionEvent e) {

            if (!imagesToViewListModel.isEmpty() && !resizeDestinationPathTextField.getText().isEmpty() && (!widthTextField.getText().isEmpty() || !heightTextField.getText().isEmpty()) ) {
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
        public void actionPerformed(ActionEvent e) {
            if (irw != null && !irw.isCancelled()) {
                irw.cancel(true);
            }
        }
    }

    private class ImagesToViewListListener implements ListSelectionListener {

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

            float quality = 10;

            if (qualityComboBox.getItemAt(qualityComboBox.getSelectedIndex()) != null) {
                quality = qualityComboBox.getItemAt(qualityComboBox.getSelectedIndex());
            }

            float floatQuality = quality / 100;

            File destinationDirectory = new File(resizeDestinationPathTextField.getText(), "resized");

            destinationDirectory = DirectoryUtil.getUniqueDirectory(destinationDirectory.getParentFile(), destinationDirectory);

            publish(lang.get("imageresizer.resize.process.started"));

            if (!destinationDirectory.mkdirs()) {
                publish(lang.get("imageresizer.resize.process.couldNotCreateDirectory") + ": " + destinationDirectory.getAbsolutePath());
                logger.logERROR("Could not create directory:" + destinationDirectory.getAbsolutePath());

                return lang.get("imageresizer.resize.process.aborted");
            } else {
                publish(lang.get("imageresizer.resize.process.destination.directory.created") + " " + destinationDirectory.getAbsolutePath());

                int size = imagesToViewListModel.getSize();

                for (int index = 0; index < size; index++) {
                    if (!irw.isCancelled()) {
                        long startTimeImageResize = System.currentTimeMillis();
                        ImageUtil.resizeAndStoreImage(imagesToViewListModel.get(index), width, height, destinationDirectory, floatQuality);

                        publish(String.format(lang.get("imageresizer.processlog.image.processed"), imagesToViewListModel.get(index).getAbsolutePath(), (System.currentTimeMillis() - startTimeImageResize)));
                        setProgress((index + 1) * 100 / size);
                    } else {
                        publish(lang.get("imageresizer.resize.process.cancelled"));
                        break;
                    }
                }

                if (!irw.isCancelled()) {
                    publish(String.format(lang.get("imageresizer.processlog.image.resize.done"), (System.currentTimeMillis() - startTime) / 1000));
                    return lang.get("imageresizer.resize.process.done");
                } else {
                    publish(String.format(lang.get("imageresizer.processlog.image.resize.done.cancelled"), (System.currentTimeMillis() - startTime) / 1000));

                    resizeButton.setEnabled(true);
                    return lang.get("imageresizer.resize.process.cancelled");
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
        @Override
        public void insertString(DocumentFilter.FilterBypass fp, int offset, String string, AttributeSet aset)
                                    throws BadLocationException {
            // remove non-digits
            fp.insertString(offset, string.replaceAll("\\D++", ""), aset);
        }


        @Override
        public void replace(DocumentFilter.FilterBypass fp, int offset, int length, String string, AttributeSet aset)
                                            throws BadLocationException {
         // remove non-digits
            fp.insertString(offset, string.replaceAll("\\D++", ""), aset);
        }
    }
}
