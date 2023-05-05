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

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.gui.CustomizedJScrollPane;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.gui.frames.base.JavaPEGBaseFrame;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.util.io.StreamUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * This class is responsible for displaying the localized help file contents
 * which are available in JavaPEG.
 * </p>
 * <p>
 * The expected input is html.
 * </p>
 *
 * @author Fredrik
 *
 */
public class HelpViewerGUI extends JavaPEGBaseFrame {

    private static final long serialVersionUID = 1L;

    private JTree tree;

    private JEditorPane contentJEditorPane;
    private JPanel backgroundsPanel;

    private JSplitPane splitPane;

    public HelpViewerGUI() {
        this.initiateWindow();
        this.addListeners();
    }

    private void initiateWindow() {
        loadAndApplyGUISettings();

        this.getContentPane().add(this.initiateSplitPane(), BorderLayout.CENTER);

        this.setIconImage(IconLoader.getIcon(Icons.HELP).getImage());
        this.setTitle(getLang().get("helpViewerGUI.window.title"));
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

    private JScrollPane initiateJTree() {
        tree = new JTree(HelpViewerGUIUtil.createNodes());
        tree.setShowsRootHandles(true);
        tree.addMouseListener(new MouseListener());
        return new JScrollPane(tree);
    }

    private CustomizedJScrollPane initiateContentPanel() {
        contentJEditorPane = new JEditorPane();
        contentJEditorPane.setEditable(false);
        contentJEditorPane.setContentType("text/html");

        return new CustomizedJScrollPane(contentJEditorPane);
    }

    private String getHelpFileContent(String identityString) {
        String helpFileContent = "";

        if (identityString != null) {
            String confLang = getConfiguration().getLanguage().getgUILanguageISO6391();

            // The version information file is not localized, and exists only
            // one time in a separate directory called "common".
            if (identityString.equals("version_information")) {
                confLang = "common";
            }

            try {
                InputStream helpFile = StartJavaPEG.class.getResourceAsStream("/help/" + confLang + "/" + identityString + ".html");
                helpFileContent = StreamUtil.getString(helpFile, C.UTF8);
            } catch (IOException iox) {
                getLogger().logFATAL("Could not read content from helpfile: " + "/help/" + confLang + "/" + identityString + ".html");
                getLogger().logFATAL(iox);
            }
        }
        return helpFileContent;
    }

    /**
     * Mouse listener
     */
    private class MouseListener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            int selRow = tree.getRowForLocation(e.getX(), e.getY());

            if(selRow > -1) {
                String identity = ((UserObject)((DefaultMutableTreeNode)tree.getLastSelectedPathComponent()).getUserObject()).getIdentityString();

                contentJEditorPane.removeAll();
                contentJEditorPane.updateUI();
                contentJEditorPane.setText(getHelpFileContent(identity));
                contentJEditorPane.setCaretPosition(0);
            }
        }
    }

    @Override
    public GUIWindow getGUIWindowConfig() {
        return getConfiguration().getgUI().getHelpViewer();
    }

    @Override
    public Dimension getDefaultSize() {
        return new Dimension(GUIDefaults.HELP_VIEWER_WIDTH, GUIDefaults.HELP_VIEWER_HEIGHT);
    }
}
