package moller.javapeg.program.thumbnailoverview;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.enumerations.LayoutMetaDataVariable;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.rename.RenameProcessContext;
import moller.util.html.DocType;
import moller.util.html.Tag;
import moller.util.io.FileUtil;
import moller.util.io.StreamUtil;
import moller.util.string.StringUtil;
import moller.util.string.Tab;

public class ThumbNailOverViewCreator {

	/**
	 * The static singleton instance of this class.
	 */
	private static ThumbNailOverViewCreator instance;

	private static String subDirName;
	private static String thumbDirName;
	private static String destPath;

	private String jpgName;

	private static final String FS = File.separator;

	private static Language lang = Language.getInstance();
	private static Logger logger = Logger.getInstance();

	/**
	 * Private constructor.
	 */
	private ThumbNailOverViewCreator() {
	}

	/**
	 * Accessor method for this Singleton class.
	 *
	 * @return the singleton instance of this class.
	 */
	public static ThumbNailOverViewCreator getInstance() {
		init();
		if (instance != null)
			return instance;
		synchronized (ThumbNailOverViewCreator.class) {
			if (instance == null) {
				instance = new ThumbNailOverViewCreator();
			}
			return instance;
		}
	}

	public void create() {
		createCSSFile();
		createHTMLFile();
	}

	private static void init() {
		RenameProcessContext rpc = RenameProcessContext.getInstance();
		subDirName = rpc.getSubDirectoryName();
		thumbDirName = rpc.getTHUMBNAIL_DIRECTORY_NAME();

		destPath = ApplicationContext.getInstance().getDestinationPath();
	}

	private void createCSSFile() {

		InputStream cssSource = null;
		OutputStream cssDest  = null;

		try {
			cssSource = new FileInputStream(System.getProperty("user.dir") + FS + "resources" + FS + "thumb" + FS + "style.css");
		} catch (FileNotFoundException e1) {
			cssSource = StartJavaPEG.class.getResourceAsStream("resources/thumbnailoverview/style.css");
		}

		File cssFile = new File(destPath + FS + subDirName + FS + thumbDirName + FS + "style.css");
		if(!cssFile.exists()) {
			FileUtil.createFile(cssFile);
		}

		try {
			cssDest = new FileOutputStream(cssFile);
			byte[] buf = new byte[1024];
			int len;
			while ((len = cssSource.read(buf)) > 0) {
				cssDest.write(buf, 0, len);
			}
		} catch (FileNotFoundException fnx) {
			JOptionPane.showMessageDialog(null, lang.get("thumbnailoverview.ThumbNailOverViewCreator.error.createCSSFile"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			logger.logERROR("Could not create CSS file in destination directory. See stack trace below for details.");
			for(StackTraceElement element : fnx.getStackTrace()) {
				logger.logERROR(element.toString());
			}
		} catch (IOException iox) {
			JOptionPane.showMessageDialog(null, lang.get("thumbnailoverview.ThumbNailOverViewCreator.error.accessCSSFile"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			logger.logERROR("Could not access CSS file. See stack trace below for details.");
			for(StackTraceElement element : iox.getStackTrace()) {
				logger.logERROR(element.toString());
			}
		} finally {
			try {
				StreamUtil.closeStream(cssSource);
			} catch (IOException iox) {
				logger.logERROR("Could not close InputStream:");
				logger.logERROR(iox);
			}
			try {
				StreamUtil.closeStream(cssDest);
			} catch (IOException iox) {
				logger.logERROR("Could not close OutputStream for: " + cssFile.getAbsolutePath());
				logger.logERROR(iox);
			}
		}
	}

	private void createHTMLFile(){

		LayoutParser lp =  LayoutParser.getInstance();
		lp.parse();

		StringBuilder xHTML = new StringBuilder(16384);

		xHTML.append(DocType.XHTML_1_0_Strict);
		xHTML.append(Tag.HTMLOpen.attributes("xmlns=\"http://www.w3.org/1999/xhtml\""));
		xHTML.append(Tab.ONE.value()   + Tag.HEADOpen);
		xHTML.append(Tab.TWO.value()   + Tag.META.attributes("http-equiv=\"Content-Type\" content=\"text/html;charset=iso-8859-1\""));
		xHTML.append(Tab.TWO.value()   + Tag.TITLEOpenNoLF + lp.getDocumentTitle() + " " + subDirName + Tag.TITLEClose);
		xHTML.append(Tab.TWO.value()   + Tag.LINK.attributes("href=\"style.css\" rel=\"stylesheet\" type=\"text/css\""));
		xHTML.append(Tab.ONE.value()   + Tag.HEADClose);
		xHTML.append(Tab.ONE.value()   + Tag.BODYOpen);
		xHTML.append(Tab.TWO.value()   + Tag.DIVOpen.classAttribute(lp.getRowClass()));
		xHTML.append(Tab.THREE.value() + Tag.SPANOpenNoLF.classAttribute("text") + subDirName + Tag.SPANClose);
		xHTML.append(Tab.TWO.value()   + Tag.DIVClose);

		RenameProcessContext rpc = RenameProcessContext.getInstance();

		Map<File, File> allThumbNailFileNAmeMAppings = rpc.getAllThumbNailFileNameMappings();
		Map<File, File> allJPEGFileNameMappings      = rpc.getAllJPEGFileNameMappings();

		List<MetaData> metaDataObjects = ApplicationContext.getInstance().getMetaDataObjects();

		Map<File, MetaData> jpegFileMetaDataObjectMapping = new HashMap<File, MetaData>();

		for(MetaData metaData : metaDataObjects) {
			jpegFileMetaDataObjectMapping.put(metaData.getFileObject(), metaData);
		}

		int index = 0;

		String thmName = "";
		MetaData md;
		int columns = lp.getColumnAmount();

		Set<File> sortedSet = new TreeSet<File>(allThumbNailFileNAmeMAppings.keySet());

		for(File jPEGFile : sortedSet) {

			thmName = allThumbNailFileNAmeMAppings.get(jPEGFile).getName();
			jpgName = allJPEGFileNameMappings.get(jPEGFile).getName();
			md = jpegFileMetaDataObjectMapping.get(jPEGFile);

			if(index % columns == 0){
				xHTML.append(Tab.TWO.value() + Tag.DIVOpen.classAttribute(lp.getRowClass()));
			}
			xHTML.append(Tab.THREE.value() + Tag.DIVOpen.classAttribute(lp.getColumnClass()));
			xHTML.append(Tab.FOUR.value() + Tag.AOpen.attributes("href=\"../" + jpgName + "\""));
			xHTML.append(Tab.FIVE.value() + Tag.IMG.attributes("class=\"" + lp.getImageClass()  + "\"" + " src=\"" + thmName + "\" alt=\"" + thmName + "\" title=\"" + jpgName + "\""));
			xHTML.append(Tab.FOUR.value() + Tag.AClose);
			xHTML.append(Tab.FOUR.value() + Tag.DIVOpen.classAttribute(lp.getMetaClass()));

			for(LayoutMetaItem mi : lp.getMetaItems()) {
				xHTML.append(Tab.FIVE.value() + Tag.SPANOpenNoLF.classAttribute(mi.getClassString()) + mi.getLabelString() + getMetaDataValue(md, mi.getMetaString()) + Tag.SPANCloseNoLF + Tag.BR);
			}

			xHTML.append(Tab.FOUR.value()  + Tag.DIVClose);
			xHTML.append(Tab.THREE.value() + Tag.DIVClose);

			if((index % columns == columns - 1 && index != 0) || (index == (allThumbNailFileNAmeMAppings.size() - 1))) {
				xHTML.append(Tab.TWO.value() + Tag.DIVClose);
			}
			index++;
		}

		xHTML.append(Tab.ONE.value() + Tag.BODYClose);
		xHTML.append(Tag.HTMLClose);

		File html = new File(destPath + File.separator + subDirName + File.separator + thumbDirName + File.separator +  "index.html");

		if(FileUtil.createFile(html)) {
			Writer writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(html));
				writer.write(xHTML.toString());
			} catch (IOException iox) {
				JOptionPane.showMessageDialog(null, lang.get("thumbnailoverview.ThumbNailOverViewCreator.error.accessHTMLFile"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
				logger.logERROR("Could not access HTML file. See stack trace below for details.");
				for(StackTraceElement element : iox.getStackTrace()) {
					logger.logERROR(element.toString());
				}
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, lang.get("thumbnailoverview.ThumbNailOverViewCreator.error.createHTMLFile"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			logger.logERROR("Could not create HTML file in destination directory.");
		}
	}

	private String getMetaDataValue(MetaData md, String metaVariable) {

		if (metaVariable.equals(LayoutMetaDataVariable.APERTUREVALUE.toString())) {
			return Double.toString(md.getExifFNumber());
		}
		if (metaVariable.equals(LayoutMetaDataVariable.CAMERAMODEL.toString())) {
			return md.getExifCameraModel();
		}
		if (metaVariable.equals(LayoutMetaDataVariable.DATE.toString())) {
			return md.getExifDateAsString();
		}
		if (metaVariable.equals(LayoutMetaDataVariable.FILENAME.toString())) {
			return jpgName;
		}
		if (metaVariable.equals(LayoutMetaDataVariable.FILESIZE.toString())) {
			return StringUtil.formatBytes(md.getFileObject().length(), "0.00");
		}
		if (metaVariable.equals(LayoutMetaDataVariable.ISOVALUE.toString())) {
			return Integer.toString(md.getExifISOValue());
		}
		if (metaVariable.equals(LayoutMetaDataVariable.PICTUREHEIGHT.toString())) {
			return Integer.toString(md.getExifPictureHeight());
		}
		if (metaVariable.equals(LayoutMetaDataVariable.PICTUREWIDTH.toString())) {
			return Integer.toString(md.getExifPictureWidth());
		}
		if (metaVariable.equals(LayoutMetaDataVariable.EXPOSURETIME.toString())) {
			return md.getExifExposureTime().toString();
		}
		if (metaVariable.equals(LayoutMetaDataVariable.TIME.toString())) {
			return md.getExifTimeAsString();
		}
		return "n/a";
	}
}
