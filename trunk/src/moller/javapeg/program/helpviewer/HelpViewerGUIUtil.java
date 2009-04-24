package moller.javapeg.program.helpviewer;
/**
 * This class was created : 2009-04-20 by Fredrik Möller
 * Latest changed         : 2009-04-24 by Fredrik Möller
 */

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import moller.javapeg.program.language.Language;

public class HelpViewerGUIUtil {
	
	public static TreeNode createNodes() {
		
		Language lang = Language.getInstance();

		DefaultMutableTreeNode root               = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.content"));
		DefaultMutableTreeNode programHelp        = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.programHelp"));
		DefaultMutableTreeNode versionInformation = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.versionInformation"));
		DefaultMutableTreeNode overViewCreator    = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.overviewCreator"));
		
		root.add(programHelp);
		root.add(overViewCreator);
		root.add(versionInformation);
		
		return root;	
	}
	
	public static String getFile(int selectedRow) {
		String fileToLoad = "";
		
		switch (selectedRow) {
		case 1:
			fileToLoad = "/user_manual";
			break;
		case 2:
			fileToLoad = "/thumbnail_overview";
			break;
		case 3:
			fileToLoad = "/version_information";
			break;
		default:
			break;
		}
		return fileToLoad;
	}
}