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
package moller.javapeg.program.helpviewer;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.gui.CustomizedJScrollPane;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.gui.Screen;
import moller.util.io.StreamUtil;
import moller.util.string.StringUtil;

public class HelpViewerGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTree tree;

    private JPanel contentJPanel;
    private JPanel backgroundsPanel;

    private JSplitPane splitPane;

    private final Configuration configuration;
    private final Logger   logger;
    private final Language lang;

    private String content = "";

    public HelpViewerGUI() {
        configuration = Config.getInstance().get();
        logger = Logger.getInstance();
        lang   = Language.getInstance();

        this.initiateWindow();
        this.addListeners();
    }

    private void initiateWindow() {

        GUI gUI = configuration.getgUI();

        Rectangle sizeAndLocation = gUI.getHelpViewer().getSizeAndLocation();

        this.setSize(sizeAndLocation.getSize());

        Point xyFromConfig = new Point(sizeAndLocation.getLocation());

        if (Screen.isVisibleOnScreen(sizeAndLocation)) {
            this.setLocation(xyFromConfig);
            this.setSize(sizeAndLocation.getSize());
        } else {
            JOptionPane.showMessageDialog(null, lang.get("helpViewerGUI.window.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            logger.logERROR("Could not set location of Help Viewer GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");

            this.setLocation(0,0);
            this.setSize(GUIDefaults.HELP_VIEWER_WIDTH, GUIDefaults.HELP_VIEWER_HEIGHT);
        }

        this.setLayout(new BorderLayout());

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
            logger.logERROR("Could not set desired Look And Feel for Help Viewer GUI");
            logger.logERROR("Below is the generated StackTrace");

            for(StackTraceElement element : e.getStackTrace()) {
                logger.logERROR(element.toString());
            }
        }
        this.getContentPane().add(this.initiateSplitPane(), BorderLayout.CENTER);

        this.setIconImage(IconLoader.getIcon(Icons.HELP).getImage());
        this.setTitle(lang.get("helpViewerGUI.window.title"));
    }

    private JSplitPane initiateSplitPane() {
        backgroundsPanel = new JPanel(new BorderLayout());
        backgroundsPanel.add(this.initiateContentPanel(), BorderLayout.CENTER);

        splitPane = new JSplitPane();
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);
        splitPane.add(this.initiateJTree(), JSplitPane.LEFT);
        splitPane.add(backgroundsPanel, JSplitPane.RIGHT);

        return splitPane;
    }

    private void addListeners(){
        this.addWindowListener(new WindowEventHandler());
        backgroundsPanel.addComponentListener(new WindowComponentListener());
    }

    private class WindowEventHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            updateWindowLocationAndSize();
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }

    private void updateWindowLocationAndSize() {
        GUI gUI = configuration.getgUI();

        Rectangle sizeAndLocation = gUI.getHelpViewer().getSizeAndLocation();

        sizeAndLocation.setLocation(this.getLocation().x, this.getLocation().y);
        sizeAndLocation.setSize(this.getSize().width, this.getSize().height);
    }

    private JScrollPane initiateJTree() {
        tree = new JTree(HelpViewerGUIUtil.createNodes());
        tree.setShowsRootHandles(true);
        tree.addMouseListener(new Mouselistener());
        return new JScrollPane(tree);
    }

    private CustomizedJScrollPane initiateContentPanel() {
        contentJPanel = new JPanel(new BorderLayout());
        return new CustomizedJScrollPane(contentJPanel);
    }

    private JTextArea getContent(String identityString) {
        if (content.equals("") && identityString != null) {

            String confLang = configuration.getLanguage().getgUILanguageISO6391();

            InputStream helpFile = null;

            try {
                helpFile = StartJavaPEG.class.getResourceAsStream("resources/help/" + confLang + "/" + identityString);
                content = StreamUtil.getString(helpFile, C.UTF8);
            } catch (IOException iox) {
                logger.logFATAL("Could not read content from helpfile");
                logger.logFATAL(iox);
            }
        }

        JTextArea textarea = new JTextArea();
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setEditable(false);
        textarea.setText(StringUtil.formatString(content));
        textarea.setCaretPosition(0);

        return textarea;
    }

    /**
     * Mouse listener
     */
    private class Mouselistener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            int selRow = tree.getRowForLocation(e.getX(), e.getY());

            if(selRow > -1) {
                String identity = ((UserObject)((DefaultMutableTreeNode)tree.getLastSelectedPathComponent()).getUserObject()).getIdentityString();

                content = "";
                contentJPanel.removeAll();
                contentJPanel.updateUI();
                contentJPanel.add(getContent(identity), BorderLayout.CENTER);
            }
        }
    }

    private class WindowComponentListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            contentJPanel.removeAll();
            contentJPanel.updateUI();
            contentJPanel.add(getContent(null), BorderLayout.CENTER);
        }
    }
}
