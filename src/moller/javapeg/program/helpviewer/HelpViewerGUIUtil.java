package moller.javapeg.program.helpviewer;
/**
 * This class was created : 2009-04-20 by Fredrik M�ller
 * Latest changed         : 2009-04-24 by Fredrik M�ller
 *                        : 2009-04-27 by Fredrik M�ller
 *                        : 2009-07-21 by Fredrik M�ller
 *                        : 2009-07-22 by Fredrik M�ller
 *                        : 2009-09-19 by Fredrik M�ller
 *                        : 2009-09-20 by Fredrik M�ller
 *                        : 2009-10-03 by Fredrik M�ller
 */

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import moller.javapeg.program.language.Language;

public class HelpViewerGUIUtil {
	
	public static TreeNode createNodes() {
		
		Language lang = Language.getInstance();
				
		//Create root node
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.content"), null));
				
		// Create child nodes to the root node.
		DefaultMutableTreeNode programHelpOverView        = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpOverView"), "user_manual_overview"));
		DefaultMutableTreeNode versionInformation         = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.versionInformation"), "version_information"));
		DefaultMutableTreeNode references                 = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.references"), "references"));
		DefaultMutableTreeNode programHelpRename          = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpRename"), "user_manual_rename"));
		DefaultMutableTreeNode programHelpImageViewer     = new DefaultMutableTreeNode(new UserObject("BILDVISARE", "user_manual_imageviewer"));
		DefaultMutableTreeNode programHelpImageList       = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpImageList"), "user_manual_imagelist"));
		DefaultMutableTreeNode programHeplOverViewCreator = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpOverviewCreator"), "thumbnail_overview"));
		DefaultMutableTreeNode configuration              = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.configuration"), null));
				
		// Create child nodes to the Configuration node.
		DefaultMutableTreeNode logging       = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.logging"), "configuration_logging"));
		DefaultMutableTreeNode updates       = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.updates"), "configuration_updates"));
		DefaultMutableTreeNode rename        = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.rename"), "configuration_rename"));
		DefaultMutableTreeNode language      = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.language"), "configuration_language"));
				
		// Add configuration help items to the Configuration node
		configuration.add(logging);
		configuration.add(updates);
		configuration.add(rename);
		configuration.add(language);
				
		// Add nodes to the root node.
		root.add(programHelpOverView);
		root.add(programHelpRename);
		root.add(programHelpImageViewer);
		root.add(programHelpImageList);
		root.add(programHeplOverViewCreator);		
		root.add(versionInformation);
		root.add(references);
		root.add(configuration);
			
		return root;	
	}
}