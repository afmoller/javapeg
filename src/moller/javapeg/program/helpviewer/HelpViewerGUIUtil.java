package moller.javapeg.program.helpviewer;
/**
 * This class was created : 2009-04-20 by Fredrik Möller
 * Latest changed         : 2009-04-24 by Fredrik Möller
 *                        : 2009-04-27 by Fredrik Möller
 */

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import moller.javapeg.program.language.Language;

public class HelpViewerGUIUtil {
	
	public static TreeNode createNodes() {
		
		Language lang = Language.getInstance();

		DefaultMutableTreeNode root                = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.content"));
		DefaultMutableTreeNode programHelpOverView = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.programHelpOverView"));
		DefaultMutableTreeNode versionInformation  = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.versionInformation"));
		DefaultMutableTreeNode references          = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.references"));
				
		DefaultMutableTreeNode programHelpRename          = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.programHelpRename"));
		DefaultMutableTreeNode programHelpImageList       = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.programHelpImageList"));
		DefaultMutableTreeNode programHeplOverViewCreator = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.programHelpOverviewCreator"));
		
		programHelpOverView.add(programHelpRename);
		programHelpOverView.add(programHelpImageList);
		programHelpOverView.add(programHeplOverViewCreator);
		
		root.add(programHelpOverView);
		root.add(versionInformation);
		root.add(references);
		
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