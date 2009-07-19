package moller.javapeg.program.imagelistformat;
/**
* This class was created : 2009-07-13 by Fredrik Möller
* Latest changed         : 2009-07-14 by Fredrik Möller
*                        : 2009-07-16 by Fredrik Möller
*                        : 2009-07-18 by Fredrik Möller
*                        : 2009-07-19 by Fredrik Möller
*/

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import moller.javapeg.program.language.Language;
import moller.util.io.FileUtil;

public class ImageList {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static ImageList instance;
	
	private static Language lang;
			
	/**
	 * Private constructor.
	 */
	private ImageList() {
		lang = Language.getInstance();
	}

	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static ImageList getInstance() {
		if (instance != null)
			return instance;
		synchronized (ImageList.class) {
			if (instance == null) {
				instance = new ImageList();
			}
			return instance;
		}
	}
		
	public String getName() {
		return "ImageList";
	}
		
	public void createList(DefaultListModel images, File destination, String fileExtension) {
							
		/**
		 * This check is done to see if an file extension has been entered in
		 * the JFileChooser. If it is missing, the extension will be added here
		 */
		if(!FileUtil.isOfType("." + fileExtension, destination)) {
			destination = new File(destination.getAbsolutePath() + "." + fileExtension);
		}

		if(destination.exists()) {
			int returnValue = JOptionPane.showConfirmDialog(null, destination.getAbsolutePath() + " " + lang.get("maingui.tabbedpane.imagelist.imagelistformat.imageList.listAlreadyExists"));
				
			/**
			 * 0 indicates a yes answer, and then the file shall be overwritten
			 * and that is accomplished by first delete the old file and the 
			 * create a new.
			 */ 
			if(returnValue == 0) {
				destination.delete();
			}
		}
		
		if (fileExtension.equals("pvs")) {
			PolyView.createAndWriteToFile(images, destination);
		} else if (fileExtension.equals("jil")) {
			JavaPEG.createAndWriteToFile(images, destination);	
		}
	}
}