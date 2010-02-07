package moller.javapeg.program.categories;
/**
 * This class was created : 2010-02-06 by Fredrik Möller
 * Latest changed         : 2010-02-07 by Fredrik Möller
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.C;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.metadata.MetaDataRetriever;
import moller.util.io.XMLUtil;
import moller.util.jpeg.JPEGUtil;

public class ImageMetaDataDataBase {
	
	public static boolean create(File directory) {
		
		File imageMetaDataDataBase = new File(directory, C.JAVAPEG_IMAGE_META_NAME);
		
		if(imageMetaDataDataBase.exists()) {
			
		} else {
			try {
				List<File> jpegFiles = JPEGUtil.getJPEGFiles(directory);
				
				OutputStream os = null;
				try {
					os = new FileOutputStream(imageMetaDataDataBase);
					XMLOutputFactory factory = XMLOutputFactory.newInstance();
					XMLStreamWriter w = factory.createXMLStreamWriter(os);
					
					XMLUtil.writeStartDocument("1.0", w);
					XMLUtil.writeComment("Test", w);
					XMLUtil.writeElementStart("javapeg-image-meta-data-data-base", "version", C.IMAGE_META_DATA_DATA_BASE_VERSION, w);
										
					for(File jpegFile : jpegFiles) {
						MetaDataRetriever mdr = new MetaDataRetriever(jpegFile);
						MetaData md = mdr.getMetaData();
						
						XMLUtil.writeElementStart("image", "file", jpegFile.getAbsolutePath(), w);
							XMLUtil.writeElementStart("exif-meta-data", w);
								XMLUtil.writeElement("aperture-value", md.getExifApertureValue(), w);
								XMLUtil.writeElement("camera-model"  , md.getExifCameraModel()  , w);
								XMLUtil.writeElement("date"          , md.getExifDate()         , w);
								XMLUtil.writeElement("iso-value"     , md.getExifISOValue()     , w);
								XMLUtil.writeElement("picture-height", md.getExifPictureHeight(), w);
								XMLUtil.writeElement("picture-width" , md.getExifPictureWidth() , w);
								XMLUtil.writeElement("shutter-speed" , md.getExifShutterSpeed() , w);
								XMLUtil.writeElement("time"          , md.getExifTime()         , w);
							XMLUtil.writeElementEnd(w);
							XMLUtil.writeElement("comment", "", w);
							XMLUtil.writeElement("rating", "", w);
							XMLUtil.writeElementStart("tags", w);
							XMLUtil.writeElementEnd(w);
							XMLUtil.writeElementEnd(w);
					}
					XMLUtil.writeElementEnd(w);
					w.flush();
				} catch (XMLStreamException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if(os != null) {
						os.close();
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
