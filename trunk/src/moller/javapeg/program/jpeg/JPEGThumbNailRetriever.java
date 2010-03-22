package moller.javapeg.program.jpeg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.metadata.MetaDataRetriever;
import moller.util.jpeg.JPEGScaleAlgorithm;
import moller.util.jpeg.JPEGUtil;

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
		
		JPEGThumbNail thumbNail = null;
		JPEGThumbNailCache jpgtnc = null;
		
		if(config.getBooleanProperty("thumbnails.cache.enabled")) {
			// Get the thumbnail cache
			jpgtnc = JPEGThumbNailCache.getInstance();
					
			// ..request the thumbnail from cache
			thumbNail = jpgtnc.get(jpegFile);
		}
						
		// ..and if the thumbnail was not existing in the cache or cache not enabled.
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
							try {
								logger.logDEBUG("Searching for thumbnail in image: "  + jpegFile.getAbsolutePath());
								thumbNailData = JPEGUtil.searchForThumbnail(jpegFile);
								logger.logDEBUG("Found thumbnail in image: "  + jpegFile.getAbsolutePath());
							} catch (IOException iox) {
								logger.logDEBUG("Creating thumbnail for image: "  + jpegFile.getAbsolutePath());
								
								String algorithm = config.getStringProperty("thumbnails.view.create.algorithm");
								logger.logDEBUG("Thumbnail creation algorithm: " + algorithm);
								
								thumbNailData = JPEGUtil.createThumbNail(jpegFile, config.getIntProperty("thumbnails.view.width"), config.getIntProperty("thumbnails.view.height"), algorithm.equals("FAST") ? JPEGScaleAlgorithm.FAST : JPEGScaleAlgorithm.SMOOTH);
							}
						} else {
							logger.logDEBUG("Loading missing thumbnail image");
//							TODO: Implement a "missing thumbnail image"
						}
					}
										
					thumbNail.setThumbNailData(thumbNailData);
					thumbNail.setThumbNailSize(thumbNailLength);
					
					if(jpgtnc != null && config.getBooleanProperty("thumbnails.cache.enabled")) {
						jpgtnc.add(jpegFile, thumbNail);
					}
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