package moller.javapeg.program.helpviewer;

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
		DefaultMutableTreeNode logging   = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.logging"), "configuration_logging"));
		DefaultMutableTreeNode updates   = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.updates"), "configuration_updates"));
		DefaultMutableTreeNode rename    = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.rename"), "configuration_rename"));
		DefaultMutableTreeNode language  = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.language"), "configuration_language"));
		DefaultMutableTreeNode thumbnail = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.thumbnail"), "configuration_thumbnail"));
		DefaultMutableTreeNode tag       = new DefaultMutableTreeNode(new UserObject(lang.get("helpViewerGUI.tree.tag"), "configuration_tag"));

		// Add configuration help items to the Configuration node
		configuration.add(logging);
		configuration.add(updates);
		configuration.add(rename);
		configuration.add(language);
		configuration.add(thumbnail);
		configuration.add(tag);

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