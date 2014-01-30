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
package moller.javapeg.program.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;

/**
 * This class constructs a reusable GUI component that consists of a
 * {@link JLabel}, {@link JTextField} and a {@link JButton}. These elements are
 * placed in the following layout:
 *
 * <pre>
 * |----------------------|
 * | JLABEL     |         |
 * |----------------------|
 * | JTEXTFIELD | JBUTTON |
 * |----------------------|
 * </pre>
 *
 * When the JButton is clicked, then a {@link JFileChooser} is opened and in
 * this chooser is it possible to select a directory which will be used as a
 * destination directory.
 *
 * @author Fredrik
 *
 */
public class DestinationDirectorySelector extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField destinationPathTextField;
    private JButton destinationPathButton;

    private static Language lang;
    private static Logger logger;

    /**
     * Constructor
     *
     * @param ignoreSourcePath
     *            defines whether or not the selected path shall be validated
     *            against a selected source path which is stored in the
     *            {@link ApplicationContext} or not.
     */
    public DestinationDirectorySelector(boolean ignoreSourcePath) {

        lang = Language.getInstance();
        logger = Logger.getInstance();

        this.construct(ignoreSourcePath);
    }

    /**
     * Build the GUI components and places them into the layout.
     *
     * @param ignoreSourcePath
     *            defines whether or not the selected path shall be validated
     *            against a selected source path which is stored in the
     *            {@link ApplicationContext} or not.
     */
    private void construct(boolean ignoreSourcePath) {
        JLabel destinationPathLabel = new JLabel(lang.get("labels.destinationPath"));
        destinationPathLabel.setForeground(Color.GRAY);

        ImageIcon openPictureImageIcon = new ImageIcon();
        try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/open.gif")) {
            openPictureImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException iox) {
            logger.logERROR("Could not open the image open.gif");
            logger.logERROR(iox);
        }

        destinationPathTextField = new JTextField();
        destinationPathTextField.setEditable(false);
        destinationPathTextField.setBackground(Color.WHITE);

        destinationPathButton = new JButton(openPictureImageIcon);
        destinationPathButton.setActionCommand("destinationPathButton");
        destinationPathButton.addActionListener(new DestinationPathButtonListener(ignoreSourcePath));

        destinationPathButton.setToolTipText(lang.get("tooltip.destinationPathButton"));
        destinationPathButton.setPreferredSize(new Dimension(30, 20));
        destinationPathButton.setMinimumSize(new Dimension(30, 20));

        this.setLayout(new GridBagLayout());

        GBHelper posBackground = new GBHelper();

        this.add(destinationPathLabel, posBackground);
        this.add(destinationPathTextField, posBackground.nextRow().expandW());
        this.add(destinationPathButton, posBackground.nextCol());

    }

    /**
     * The listener that is connected to the {@link JButton} element of this
     * component. It opens a {@link JFileChooser} and handles the needed
     * actions, depending on what the user do.
     *
     * @author Fredrik
     *
     */
    private class DestinationPathButtonListener implements ActionListener {

        boolean ignoreSourcePath;

        /**
         * Constructor
         *
         * @param ignoreSourcePath
         *            defines whether or not the selected path shall be validated
         *            against a selected source path which is stored in the
         *            {@link ApplicationContext} or not.
         */
        public DestinationPathButtonListener(boolean ignoreSourcePath) {
            this.ignoreSourcePath = ignoreSourcePath;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle(lang.get("fileSelectionDialog.destinationPathFileChooser"));

            if (!destinationPathTextField.getText().equals("")) {
                File destinationPath = new File(destinationPathTextField.getText());
                if (destinationPath.isDirectory() && destinationPath.canRead()) {
                    chooser.setCurrentDirectory(destinationPath);
                }
            }

            if (chooser.showOpenDialog(DestinationDirectorySelector.this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();

                if (ignoreSourcePath || !ApplicationContext.getInstance().getSourcePath().equals(selectedFile)) {
                    destinationPathTextField.setText(selectedFile.getAbsolutePath());
                } else {
                    JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.sameSourceAndDestination"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Gives access to the embedded {@link JTextField} element of this component
     * and makes it possible to set a text to be displayed by the
     * {@link JTextField}.
     *
     * @param text
     *            is the text that shall be displayed by the {@link JTextField}
     *            element
     */
    public void setText(String text) {
        destinationPathTextField.setText(text);
    }

    /**
     * Gives access to the embedded {@link JTextField} element of this component
     * and makes it possible to get the text that is displayed to be displayed
     * by the {@link JTextField}.
     *
     * @return the text that is displayed by the {@link JTextField} in this
     *         component.
     */
    public String getText() {
        return destinationPathTextField.getText();
    }
}
