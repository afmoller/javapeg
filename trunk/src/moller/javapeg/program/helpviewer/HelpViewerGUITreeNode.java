package moller.javapeg.program.helpviewer;
/**
 * This class was created : 2009-04-20 by Fredrik Möller
 * Latest changed         : 
 */

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import moller.javapeg.program.language.Language;

public class HelpViewerGUITreeNode {
	
	public static TreeNode createNodes() {
		
		Language lang = Language.getInstance();

		DefaultMutableTreeNode root               = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.content"));
		DefaultMutableTreeNode programHelp        = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.programHelp"));
		DefaultMutableTreeNode versionInformation = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.versionInformation"));
		DefaultMutableTreeNode overViewCreator    = new DefaultMutableTreeNode(lang.get("helpViewerGUI.tree.overviewCreator"));
		
		root.add(programHelp);
		root.add(versionInformation);
		root.add(overViewCreator);

		return root;	
	}
}