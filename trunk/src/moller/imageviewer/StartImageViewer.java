package moller.imageviewer;
/**
 * This class was created : 2009-08-15 by Fredrik Möller
 * Latest changed         : 2009-08-16 by Fredrik Möller
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import moller.imageviewer.program.gui.ImageViewer;

public class StartImageViewer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		List<File> images = new ArrayList<File>(args.length);
		
		for (int i = 0; i < args.length; i++) {
			images.add(new File(args[i]));
		}
				
		ImageViewer imageViewer  = new ImageViewer(images);
		imageViewer.setVisible(true);
	}
}