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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
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
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.GUI.GUI;
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
    }

    private void addListeners() {
        this.addWindowListener(new WindowDestroyer());
        removeSelectedImagesButton.addActionListener(new RemoveSelectedImagesListener());
        imagesToViewList.addListSelectionListener(new ImagesToViewListListener());
    }

    private void createMainFrame() {
        GUI gUI = configuration.getgUI();

//        TODO: Remove hard coded string
        this.setTitle("Image Resizer");

        Rectangle sizeAndLocation = gUI.getImageViewer().getSizeAndLocation();

        this.setSize(sizeAndLocation.getSize());

        Point xyFromConfig = new Point(sizeAndLocation.getLocation());

        if(Screen.isOnScreen(xyFromConfig)) {
            this.setLocation(xyFromConfig);
        } else {
            this.setLocation(0,0);
            JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            logger.logERROR("Could not set location of Image Resizer GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");
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

        this.getContentPane().add(this.createLeftPanel(), posBackgroundPanel.expandH());
        this.getContentPane().add(this.createRightPanel(), posBackgroundPanel.nextCol().expandH().expandW());
    }

    private JPanel createLeftPanel() {

        JPanel imageAndPreviewPanel = new JPanel(new GridBagLayout());

        GBHelper posImageAndPreviewPanel = new GBHelper();

        imageAndPreviewPanel.add(this.createImageListPanel(), posImageAndPreviewPanel.expandH());
        imageAndPreviewPanel.add(new Gap(2), posImageAndPreviewPanel.nextCol());
        imageAndPreviewPanel.add(this.createPreviewPanel(), posImageAndPreviewPanel.nextCol().expandH());

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(new EmptyBorder(2, 2, 2, 1));

        GBHelper posBackgroundPanel= new GBHelper();

        backgroundPanel.add(imageAndPreviewPanel, posBackgroundPanel.expandH());
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

//        TODO: remove hard coded string
        JLabel outputLabel = new JLabel("PROCESS LOG");
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
        backgroundPanel.add(spImageList, posBackground.nextRow().expandH());
        backgroundPanel.add(new Gap(5), posBackground.nextRow());
        backgroundPanel.add(buttonPanel, posBackground.nextRow());

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

//        TODO: Remove hard coded string
        JLabel widthLabel = new JLabel("Width");

        widthTextField = new JTextField();
        ((AbstractDocument)widthTextField.getDocument()).setDocumentFilter(new IntegerDocumentFilter());

//      TODO: Remove hard coded string
        JLabel heightLabel = new JLabel("Height");

        heightTextField = new JTextField();
        ((AbstractDocument)heightTextField.getDocument()).setDocumentFilter(new IntegerDocumentFilter());

//      TODO: Remove hard coded string
        resizeButton = new JButton("Resize");
        resizeButton.addActionListener(new ResizeButtonListener());

//      TODO: Remove hard coded string
        cancelResizeButton = new JButton("Cancel");
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
        // TODO Auto-generated method stub

    }

    private class ResizeDestinationPathButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle(lang.get("fileSelectionDialog.destinationPathFileChooser"));

            if(chooser.showOpenDialog(ImageResizer.this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();

                if (!ApplicationContext.getInstance().getSourcePath().equals(selectedFile)) {

//                    ApplicationContext.getInstance().setDestinationPath(String.valueOf(tempArray));
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
            irw.cancel(true);
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

    private class ImageResizeWorker extends SwingWorker<String, String>
    {
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

            File destinationDirectory = new File(resizeDestinationPathTextField.getText(), "resized");

            destinationDirectory = DirectoryUtil.getUniqueDirectory(destinationDirectory.getParentFile(), destinationDirectory);

            publish("Rename process started");

            if (!destinationDirectory.mkdirs()) {
//                TODO: Remove hard coded string
                publish("Could not create directory:" + destinationDirectory.getAbsolutePath());
                logger.logERROR("Could not create directory:" + destinationDirectory.getAbsolutePath());

                //              TODO: Remove hard coded string
                return "Rename process aborted";
            } else {
                publish("Destination directory created: " + destinationDirectory.getAbsolutePath());

                int size = imagesToViewListModel.getSize();

                for (int index = 0; index < size; index++) {
                    if (!irw.isCancelled()) {
                        long startTimeImageResize = System.currentTimeMillis();
                        ImageUtil.resizeAndStoreImage(imagesToViewListModel.get(index), width, height, destinationDirectory);
                        //                    TODO: Remove hard coded string
                        publish("Image: " + imagesToViewListModel.get(index).getAbsolutePath() + " resized (" + (System.currentTimeMillis() - startTimeImageResize) + " milliseconds)");
                        setProgress((index + 1) * 100 / size);
                    } else {
//                      TODO: Remove hard coded string
                        publish("Resize cancelled");
                        break;
                    }
                }

                if (!irw.isCancelled()) {
//              TODO: Remove hard coded string
                    publish("Resize process took: " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");

//                TODO: Remove hard coded string
                    return "Resize done";
                } else {
//                  TODO: Remove hard coded string
                    publish("Resize process cancelled after: " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");

                    resizeButton.setEnabled(true);
//                TODO: Remove hard coded string
                    return "Resize cancelled";
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

    public void setLogMessage(String logMessage) {
        String formattedTimeStamp = sdf.format(new Date(System.currentTimeMillis()));
        outputTextArea.appendAndScroll(formattedTimeStamp + ": " + logMessage + "\n");
    }

    private class IntegerDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(DocumentFilter.FilterBypass fp
                , int offset, String string, AttributeSet aset)
                                    throws BadLocationException {
            // remove non-digits
            fp.insertString(offset, string.replaceAll("\\D++", ""), aset);
        }


        @Override
        public void replace(DocumentFilter.FilterBypass fp, int offset
                        , int length, String string, AttributeSet aset)
                                            throws BadLocationException {
         // remove non-digits
            fp.insertString(offset, string.replaceAll("\\D++", ""), aset);
        }
    }
}
