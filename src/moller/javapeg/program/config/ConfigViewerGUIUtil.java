package moller.javapeg.program.config;
/**
 * This class was created : 2009-08-06 by Fredrik Möller
 * Latest changed         : 
 */

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public class ConfigViewerGUIUtil {
	
public static TreeNode createNodes() {
		
//		Language lang = Language.getInstance();

//	TODO: Remove hard coded strings
		DefaultMutableTreeNode root    = new DefaultMutableTreeNode("Configuration");
		DefaultMutableTreeNode logging = new DefaultMutableTreeNode("Logging");
		DefaultMutableTreeNode updates = new DefaultMutableTreeNode("Updates");
		DefaultMutableTreeNode rename  = new DefaultMutableTreeNode("Rename");
		DefaultMutableTreeNode language  = new DefaultMutableTreeNode("Language");
				
		root.add(logging);
		root.add(updates);
		root.add(rename);
		root.add(language);
		
		
				
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
