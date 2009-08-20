package moller.imageviewer;
/**
 * This class was created : 2009-08-15 by Fredrik Möller
 * Latest changed         : 2009-08-16 by Fredrik Möller
 *                        : 2009-08-20 by Fredrik Möller
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import moller.imageviewer.program.gui.ImageViewer;
import moller.imageviewer.program.language.ImageViewerLanguage;

public class StartImageViewer {

	/**
	 * @param args contains the arguments provided by the calling object.
	 *        
	 *        Content of the argument array "args":
     *
	 *	      index: 0     = Language code in ISO639 format
	 *               1...n = Absolute paths to images
	 */
	public static void main(String[] args) {
		
		List<File> images = new ArrayList<File>(args.length);
				
		ImageViewerLanguage imageViewerLanguage = ImageViewerLanguage.getInstance();
		imageViewerLanguage.loadLanguageFile(args[0]);
		
		for (int i = 1; i < args.length; i++) {
			images.add(new File(args[i]));
		}
			
		ImageViewer imageViewer  = new ImageViewer(images);
		imageViewer.setVisible(true);
	}
}