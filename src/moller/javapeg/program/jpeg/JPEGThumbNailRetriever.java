package moller.javapeg.program.jpeg;
/**
 * This class was created : 2007-01-17 by Fredrik Möller
 * Latest changed         : 2007-01-18 by Fredrik Möller
 *                        : 2009-02-05 by Fredrik möller
 *                        : 2009-02-19 by Fredrik Möller
 *                        : 2009-04-15 by Fredrik Möller
 *                        : 2009-04-16 by Fredrik Möller
 *                        : 2010-01-02 by Fredrik Möller
 *                        : 2010-01-03 by Fredrik Möller
 *                        : 2010-01-04 by Fredrik Möller
 *                        : 2010-01-07 by Fredrik Möller
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.metadata.MetaDataRetriever;
import moller.util.io.JPEGUtil;

public class JPEGThumbNailRetriever {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static JPEGThumbNailRetriever instance;
	
	private static Logger logger;
	private static Config config;
	
	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static JPEGThumbNailRetriever getInstance() {
		if (instance != null)
			return instance;
		synchronized (JPEGThumbNailRetriever.class) {
			if (instance == null) {
				instance = new JPEGThumbNailRetriever();
			}
			return instance;
		}
	}
	
	private JPEGThumbNailRetriever() {
		logger = Logger.getInstance();
		config = Config.getInstance();
	}
	
	public JPEGThumbNail retrieveThumbNailFrom(File jpegFile) {
					
		// Get the thumbnail cache
		JPEGThumbNailCache jpgtnc = JPEGThumbNailCache.getInstance();
				
		// ..request the thumbnail from cache
		JPEGThumbNail thumbNail = jpgtnc.get(jpegFile);
				
		// ..and if the thumbnail was not existing in the cache.
		if (thumbNail == null) {
			thumbNail = new JPEGThumbNail();
			
			// Hämta in metadata för filen som tumnageln skall hämtas ur
			MetaDataRetriever mdr = new MetaDataRetriever(jpegFile);
			MetaData md = mdr.getMetaData();

			int thumbNailLength = md.getThumbNailLength();
						
			// Skapa plats för bilden
			byte [] thumbNailData = new byte[thumbNailLength];

			FileInputStream image = null;
			
			try {
				image = new FileInputStream(jpegFile);

				try {
					// +12 = jpgfilens header i bytes
					long thumbNailOffsetLong = (long)md.getThumbNailOffset() + 12;
					long skippedBytes = image.skip(thumbNailOffsetLong);

					// eftersom skip()-metoden enligt api:t kan skippa färre bytes
					// än anigvina så kontrolleras detta här och korrigeras vid behov
					// detta.
					while(skippedBytes < thumbNailOffsetLong) {
						thumbNailOffsetLong = thumbNailOffsetLong - skippedBytes;
						skippedBytes = image.skip(thumbNailOffsetLong);
					}
					image.read(thumbNailData, 0, thumbNailLength);
					
					if (!JPEGUtil.isJPEG(thumbNailData)) {
						logger.logDEBUG("Embedded thumbnail missing or corrupt in file: " + jpegFile.getAbsolutePath());
						
						if(config.getBooleanProperty("thumbnails.view.create-if-missing-or-corrupt")) {
							logger.logDEBUG("Creating thumbnail for image: "  + jpegFile.getAbsolutePath());
							thumbNailData = JPEGUtil.createThumbNail(jpegFile, 160, 120).toByteArray();	
						} else {
							logger.logDEBUG("Loading missing thumbnail image");
//							TODO: Implement a "missing thumbnail image"
						}
					}
										
					thumbNail.setThumbNailData(thumbNailData);
					thumbNail.setThumbNailSize(thumbNailLength);
					
					jpgtnc.add(jpegFile, thumbNail);
				} catch(IOException iox) {
					logger.logERROR("Could not retrieve the thumbnail for image: " + jpegFile.getAbsolutePath());
					logger.logERROR(iox);
				} finally {
					if (image != null) {
						try {
							image.close();
						} catch (IOException e) {
							logger.logERROR(e);
						}
					}
				}
			} catch(FileNotFoundException fnfex) {
				logger.logERROR(fnfex);
			}
		}	
		return thumbNail;
	}
}