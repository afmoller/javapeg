package moller.javapeg.program.config;
/**
 * This class was created : 2009-08-06 by Fredrik Möller
 * Latest changed         : 2009-09-06 by Fredrik Möller
 *                        : 2010-01-05 by Fredrik Möller
 */

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import moller.javapeg.program.language.Language;

public class ConfigViewerGUIUtil {
	
public static TreeNode createNodes() {
		
		Language lang = Language.getInstance();

		DefaultMutableTreeNode root     = new DefaultMutableTreeNode(lang.get("configviewer.tree.root"));
		DefaultMutableTreeNode logging  = new DefaultMutableTreeNode(lang.get("configviewer.tree.node.logging"));
		DefaultMutableTreeNode updates  = new DefaultMutableTreeNode(lang.get("configviewer.tree.node.updates"));
		DefaultMutableTreeNode rename   = new DefaultMutableTreeNode(lang.get("configviewer.tree.node.rename"));
		DefaultMutableTreeNode language = new DefaultMutableTreeNode(lang.get("configviewer.tree.node.language"));
//		TODO: Hard coded string
		DefaultMutableTreeNode thumbnail = new DefaultMutableTreeNode("Thumbnail");
				
		root.add(logging);
		root.add(updates);
		root.add(rename);
		root.add(language);
		root.add(thumbnail);
					
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