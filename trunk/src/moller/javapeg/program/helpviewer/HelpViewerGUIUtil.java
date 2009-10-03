package moller.javapeg.program.helpviewer;
/**
 * This class was created : 2009-04-20 by Fredrik Möller
 * Latest changed         : 2009-04-24 by Fredrik Möller
 *                        : 2009-04-27 by Fredrik Möller
 *                        : 2009-07-21 by Fredrik Möller
 *                        : 2009-07-22 by Fredrik Möller
 *                        : 2009-09-19 by Fredrik Möller
 *                        : 2009-09-20 by Fredrik Möller
 *                        : 2009-10-03 by Fredrik Möller
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
		DefaultMutableTreeNode programHelpRename          = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpRename"), "user_manual_rename"));
		DefaultMutableTreeNode programHelpViewImages      = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpViewImages"), "user_manual_viewimages"));
		DefaultMutableTreeNode programHelpImageViewer     = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpImageViewer"), "user_manual_imageviewer"));
		DefaultMutableTreeNode programHelpOverViewCreator = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.programHelpOverviewCreator"), "thumbnail_overview"));
		DefaultMutableTreeNode versionInformation         = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.versionInformation"), "version_information"));
		DefaultMutableTreeNode references                 = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.references"), "references"));
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
		root.add(programHelpViewImages);
		root.add(programHelpImageViewer);
		root.add(programHelpOverViewCreator);		
		root.add(versionInformation);
		root.add(references);
		root.add(configuration);
			
		return root;	
	}
}