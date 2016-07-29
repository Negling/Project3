package ua.kiral.project3.parser;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ua.kiral.project3.model.Device;

public class DevicesUtills {
	private static final DevicesParser SAX_PARSER = new DeviceSAXParser();
	private static final DevicesParser StAX_PARSER = new DeviceStAXParser();
	private static final DevicesParser DOM_PARSER = new DeviceDOMParser();
	
	/**
	 * Sorts devices by id.
	 */
	public static final Comparator<Device> DEFAULT_COMPARATOR = setDefaultComparator();

	enum Parser {
		StAX, DOM, SAX
	}

	/**
	 * This method provides converting xml file to {@link List} of
	 * {@link Device}.
	 * 
	 * @param pathToXML
	 *            path to XML file
	 * @param comparator
	 *            - comparator to sort data in list. If comparator is null -
	 *            devices in collection will appear in natural order. U can also
	 *            use DevicesUtills.DEFAULT_COMPARATOR as well.
	 * @param parser
	 *            selected type of parser.
	 * @return ArrayList of Devices.
	 * @throws IOException
	 *             if XML file not found.
	 */
	public static List<Device> parse(String pathToXML, Comparator<Device> comparator, Parser parser)
			throws IOException {
		switch (parser) {
		case DOM:
			DOM_PARSER.parse(pathToXML);
			return DOM_PARSER.getResult(comparator);
		case SAX:
			SAX_PARSER.parse(pathToXML);
			return SAX_PARSER.getResult(comparator);
		case StAX:
			StAX_PARSER.parse(pathToXML);
			return StAX_PARSER.getResult(comparator);
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Simple method to validate devices xml file by xsd schema.
	 * 
	 * @param pathToXML
	 *            path to XML file
	 * @param pathToSchema
	 *            path to schema
	 * @return <code>null</code> if validation was complected successfuly,
	 *         otherwise returns String with information about failure cause.
	 * @throws IOException
	 *             if XML or XDS file not found.
	 */
	public static String validateByXSD(String pathToXML, String pathToSchema) throws IOException {
		String result = null;

		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		f.setNamespaceAware(true);
		Document doc = null;
		Schema schema = null;

		try {
			doc = f.newDocumentBuilder().parse(new File(pathToXML));
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Source schemaFile = new StreamSource(pathToSchema);
			schema = factory.newSchema(schemaFile);
		} catch (SAXException ex) {
			throw new IllegalStateException(ex);
		} catch (ParserConfigurationException ex1) {
			throw new IllegalStateException("XML Parser initialization error!", ex1);
		}

		Validator validator = schema.newValidator();

		try {
			validator.validate(new DOMSource(doc));
		} catch (SAXException ex) {
			result = ex.getMessage();
		}

		return result;
	}

	/**
	 * Thransforms xml file to html by xslt file.
	 * 
	 * @param pathToXML
	 *            path to XML file
	 * @param pathToXSLT
	 *            path to XSLT file
	 * @param outputPath
	 *            path to html file, which method produces
	 * @return true in case of success, false otherwise
	 */
	public static boolean TransformToHTML(String pathToXML, String pathToXSLT, String outputPath) {
		boolean success = true;
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(pathToXSLT));
			transformer.transform(new StreamSource(pathToXML), new StreamResult(outputPath));
		} catch (TransformerException exeption) {
			success = false;
		}
		return success;
	}

	private static Comparator<Device> setDefaultComparator() {
		return new Comparator<Device>() {
			@Override
			public int compare(Device o1, Device o2) {
				return o1.getId().intValue() - o2.getId().intValue();
			}
		};
	}
}