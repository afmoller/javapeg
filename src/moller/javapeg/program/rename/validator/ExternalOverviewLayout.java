package moller.javapeg.program.rename.validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.rename.ValidatorStatus;

import org.xml.sax.SAXException;

/**
 * This class validates the content of an external layout.xml file, if it
 * exists against a schema.
 *
 * @author Fredrik
 */

public class ExternalOverviewLayout {

	/**
	 * The static singleton instance of this class.
	 */
	private static ExternalOverviewLayout instance;

	/**
	 * The system dependent file separator char
	 */
	private final static String FS = File.separator;

	/**
	 * Private constructor.
	 */
	private ExternalOverviewLayout() {
	}

	/**
	 * Accessor method for this Singleton class.
	 *
	 * @return the singleton instance of this class.
	 */
	public static ExternalOverviewLayout getInstance() {
		if (instance != null)
			return instance;
		synchronized (ExternalOverviewLayout.class) {
			if (instance == null) {
				instance = new ExternalOverviewLayout();
			}
			return instance;
		}
	}

	/**
	 * This method will validate an external layout.xml files, which is used to
	 * define the layout of the thumb nail overview, if it exists.
	 *
	 * If there is no external layout.xml file this method returns with status
	 * equal to success, otherwise, the validation starts and the return value
	 * depends on whether the validation against the schema layout.xsd was valid
	 * or not
	 *
	 * @return a {@link ValidatorStatus} object which values depends on the
	 *         validation result. If the validation was successful the values
	 *         are true and an empty message string otherwise the values are
	 *         false and a message specifying the validation error.
	 */
	public ValidatorStatus test() {

		Logger log = Logger.getInstance();
		Language lang = Language.getInstance();

		ValidatorStatus vs = new ValidatorStatus(true, "");

		InputStream layoutFile = null;

		try {
			layoutFile = new FileInputStream(System.getProperty("user.dir") + FS + "resources" + FS + "thumb" + FS + "layout.xml");
		} catch (FileNotFoundException e) {
			log.logDEBUG("No external layout.xml file was found in folder: " + System.getProperty("user.dir") + FS + "resources" + FS + "thumb" + FS);
			return vs;
		}

		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new StreamSource(StartJavaPEG.class.getResourceAsStream("resources/thumbnailoverview/layout.xsd")));

			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(layoutFile));
		} catch (SAXException ex) {
			vs.setValid(false);
			vs.setStatusMessage(lang.get("validator.externalOverviewLayout.invalidXMLFile"));
			log.logERROR(ex.getMessage());
			return vs;
		} catch (Exception ex) {
			log.logERROR(ex);
		}
		return vs;
	}
}