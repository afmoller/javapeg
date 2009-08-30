package moller.imageviewer;
/**
 * This class was created : 2009-08-15 by Fredrik Möller
 * Latest changed         : 2009-08-16 by Fredrik Möller
 *                        : 2009-08-20 by Fredrik Möller
 *                        : 2009-08-21 by Fredrik Möller
 *                        : 2009-08-23 by Fredrik Möller
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import moller.imageviewer.program.language.ImageViewerLanguage;
import moller.javapeg.program.gui.ImageViewer;
import moller.util.logger.Logger;

public class StartImageViewer {

	/**
	 * @param args contains the arguments provided by the calling object.
	 *        
	 *        Content of the argument array "args":
     *
	 *	      index: 0     = Language code in ISO639 format.
	 *
	 *               1     = A boolean value indicating whether the ImageViewer
	 *                       log shall be automatically rotated or not.
	 *                       
	 *               2     = A boolean value indicating whether the ImageViewer
	 *                       log shall be written to unbuffered or not.
	 *                       
	 *               3     = The name of the file containing the ImageViewer 
	 *                       log.
	 *               
	 *               4     = A SimpleDateFormat String defining how the 
	 *                       timestamp for each log entry in the ImageViewer 
	 *                       log shall look like.
	 *                      
	 *               5     = Which log level that shall be used by the 
	 *                       ImageViewer log.
	 *               
	 *               6     = The size in bytes when the automatically log
	 *                       rotation takes place.
	 *                       
	 *               7...n = Absolute paths to images
	 */
	public static void main(String[] args) {
		
		boolean rotateLog      = args[1].equals("true") ? true : false;
    	boolean developerMode  = args[2].equals("true") ? true : false;
    	
    	String logName         = args[3];
        String timeStampFormat = args[4];
        String logLevel        = args[5];
		
        int	rotateSize         = Integer.parseInt(args[6]);
        		        
		Logger logger = Logger.getInstance();
		try {
			logger.initiateLogger(rotateLog, developerMode, rotateSize, logName, timeStampFormat, logLevel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		List<File> images = new ArrayList<File>(args.length);
						
		ImageViewerLanguage imageViewerLanguage = ImageViewerLanguage.getInstance();
		imageViewerLanguage.loadLanguageFile(args[0]);
				
		for (int i = 7; i < args.length; i++) {
			images.add(new File(args[i]));
		}
			
		ImageViewer imageViewer  = new ImageViewer(images);
		imageViewer.setVisible(true);
	}
}