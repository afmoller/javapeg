package moller.javapeg.program.jpeg;
/**
 * This class was created : 2007-01-17 by Fredrik Möller
 * Latest changed         : 2007-01-18 by Fredrik Möller
 *                        : 2009-02-05 by Fredrik möller
 *                        : 2009-02-19 by Fredrik Möller
 *                        : 2009-04-15 by Fredrik Möller
 *                        : 2009-04-16 by Fredrik Möller
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.metadata.MetaDataRetriever;

public class JPEGThumbNailRetriever {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static JPEGThumbNailRetriever instance;
	
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
	}
	
	public JPEGThumbNail retrieveThumbNailFrom(File jpegFile) {
					
		// Get the thumbnail cache
		JPEGThumbNailCache jpgtnc = JPEGThumbNailCache.getInstance();
				
		// ..request the thumbnail from cache
		JPEGThumbNail thumbNail = jpgtnc.get(jpegFile);
				
		// ..and if the thumbnail was not exisitng in the cache.
		if (thumbNail == null) {
			
			thumbNail = new JPEGThumbNail();
			
			// Hämta in metadata för filen som tumnageln skall hämtas ur
			MetaDataRetriever mdr = new MetaDataRetriever(jpegFile);
			MetaData md = mdr.getMetaData();

			int thumbNailLength = md.getThumbNailLength();
						
			// Skapa plats för bilden
			byte [] thumbNailData = new byte[thumbNailLength];

			try {
				FileInputStream image = new FileInputStream(jpegFile);

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
					
					thumbNail.setThumbNailData(thumbNailData);
					thumbNail.setThumbNailSize(thumbNailLength);
					
					jpgtnc.add(jpegFile, thumbNail);
				} catch(IOException iox) {
					System.out.println(iox);
				}
			} catch(FileNotFoundException fnfex) {
			}
		}	
		return thumbNail;
	}
}