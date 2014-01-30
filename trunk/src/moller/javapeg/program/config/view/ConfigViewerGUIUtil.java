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
package moller.javapeg.program.config.view;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import moller.javapeg.program.language.Language;

public class ConfigViewerGUIUtil {

public static TreeNode createNodes() {

        Language lang = Language.getInstance();

        DefaultMutableTreeNode root      = new DefaultMutableTreeNode(lang.get("configviewer.tree.root"));
        DefaultMutableTreeNode logging   = new DefaultMutableTreeNode(lang.get("configviewer.tree.node.logging"));
        DefaultMutableTreeNode updates   = new DefaultMutableTreeNode(lang.get("configviewer.tree.node.updates"));
        DefaultMutableTreeNode rename    = new DefaultMutableTreeNode(lang.get("configviewer.tree.node.rename"));
        DefaultMutableTreeNode language  = new DefaultMutableTreeNode(lang.get("configviewer.tree.node.language"));
        DefaultMutableTreeNode thumbnail = new DefaultMutableTreeNode(lang.get("configviewer.tree.node.thumbnail"));
        DefaultMutableTreeNode tag       = new DefaultMutableTreeNode(lang.get("configviewer.tree.node.tag"));

        root.add(logging);
        root.add(updates);
        root.add(rename);
        root.add(language);
        root.add(thumbnail);
        root.add(tag);

        return root;
    }

    public static String getFile(int selectedRow) {
        String fileToLoad = "";

        switch (selectedRow) {
        case 1:
            fileToLoad = "/user_manual_overview";
            break;
        case 2:
            fileToLoad = "/user_manual_rename";
            break;
        case 3:
            fileToLoad = "/user_manual_imagelist";
            break;
        case 4:
            fileToLoad = "/thumbnail_overview";
            break;
        case 5:
            fileToLoad = "/version_information";
            break;
        case 6:
            fileToLoad = "/references";
            break;
        default:
            break;
        }
        return fileToLoad;
    }
}
