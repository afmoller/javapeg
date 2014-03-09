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

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import moller.javapeg.program.language.Language;

public class HelpViewerGUIUtil {

    public static TreeNode createNodes() {

        Language lang = Language.getInstance();

        // Create children for the functionality node
        DefaultMutableTreeNode programHelpMerge           = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpMerge"), "user_manual_merge"));
        DefaultMutableTreeNode programHelpRename          = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpRename"), "user_manual_rename"));
        DefaultMutableTreeNode programHelpViewImages      = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpViewImages"), "user_manual_viewimages"));
        DefaultMutableTreeNode programHelpTagImages       = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpImagesTag"), "user_manual_image_tag"));
        DefaultMutableTreeNode programHelpSearchImages    = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpImagesSearch"), "user_manual_image_search"));
        DefaultMutableTreeNode programHelpImageViewer     = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpImageViewer"), "user_manual_imageviewer"));
        DefaultMutableTreeNode programHelpImageResizer    = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpImageResizer"), "user_manual_imageresizer"));
        DefaultMutableTreeNode programHelpOverViewCreator = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpOverviewCreator"), "thumbnail_overview"));

        // Create the Functionality node and add functionality help items to
        // the node.
        DefaultMutableTreeNode functionality = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.functionality"), null));
        functionality.add(programHelpMerge);
        functionality.add(programHelpRename);
        functionality.add(programHelpViewImages);
        functionality.add(programHelpTagImages);
        functionality.add(programHelpSearchImages);
        functionality.add(programHelpImageViewer);
        functionality.add(programHelpImageResizer);
        functionality.add(programHelpOverViewCreator);


        // Create child nodes to the Configuration node.
        DefaultMutableTreeNode logging   = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.logging"), "configuration_logging"));
        DefaultMutableTreeNode updates   = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.updates"), "configuration_updates"));
        DefaultMutableTreeNode rename    = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.rename"), "configuration_rename"));
        DefaultMutableTreeNode language  = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.language"), "configuration_language"));
        DefaultMutableTreeNode thumbnail = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.thumbnail"), "configuration_thumbnail"));
        DefaultMutableTreeNode tag       = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.tag"), "configuration_tag"));

        // Create and add configuration help items to the Configuration node
        DefaultMutableTreeNode configuration = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.configuration"), null));
        configuration.add(logging);
        configuration.add(updates);
        configuration.add(rename);
        configuration.add(language);
        configuration.add(thumbnail);
        configuration.add(tag);

        // Create child nodes to the root node.
        DefaultMutableTreeNode programHelpOverView = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpOverView"), "user_manual_overview"));
        DefaultMutableTreeNode versionInformation  = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.versionInformation"), "version_information"));
        DefaultMutableTreeNode references          = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.references"), "references"));


        DefaultMutableTreeNode metaDataBaseCorrupt      = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.corrupt"), "help_metadatabase_corrupt"));
        DefaultMutableTreeNode metaDataBaseInconsistent = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.inconsistent"), "help_metadatabase_inconsistent"));

        DefaultMutableTreeNode problems = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.problems"), null));
        problems.add(metaDataBaseCorrupt);
        problems.add(metaDataBaseInconsistent);

        //Create root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.content"), null));

        // Add nodes to the root node.
        root.add(programHelpOverView);
        root.add(versionInformation);
        root.add(references);
        root.add(functionality);
        root.add(configuration);
        root.add(problems);

        return root;
    }
}
