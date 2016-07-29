package ua.kiral.project3.parser;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ua.kiral.project3.model.Device;
import ua.kiral.project3.model.Device.Origin;
import ua.kiral.project3.model.Device.Type;
import ua.kiral.project3.model.Device.Type.Port;
import ua.kiral.project3.model.Device.Type.Port.PortValues;

class DeviceSAXParser extends DefaultHandler implements DevicesParser {
	private List<Device> devices;
	private Device currDevice;

	// Util boolean values
	private boolean name;
	private boolean isCritical;
	private boolean isPeripherals;
	private boolean hasCooler;
	private boolean energyConsumption;
	private boolean origin;
	private boolean price;
	private boolean port;
	private boolean devicesGroup;

	public void parse(String pathToXML) throws IOException {
		SAXParser parser = initialize();
		try {
			parser.parse(new File(pathToXML), this);
		} catch (SAXException ex) {
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public void startDocument() throws SAXException {
		devices = new ArrayList<>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (localName.equalsIgnoreCase("name") && uri.equalsIgnoreCase(SIMPLE_TYPES_NS))
			name = true;
		else if (localName.equalsIgnoreCase("price") && uri.equalsIgnoreCase(DEVICES_NS))
			price = true;
		else if (localName.equalsIgnoreCase("type") && uri.equalsIgnoreCase(DEVICES_NS))
			currDevice.setType(new Type());
		else if (localName.equalsIgnoreCase("isCritical") && uri.equalsIgnoreCase(SIMPLE_TYPES_NS))
			isCritical = true;
		else if (localName.equalsIgnoreCase("isPeripherals") && uri.equalsIgnoreCase(SIMPLE_TYPES_NS))
			isPeripherals = true;
		else if (localName.equalsIgnoreCase("energyConsumption") && uri.equalsIgnoreCase(SIMPLE_TYPES_NS))
			energyConsumption = true;
		else if (localName.equalsIgnoreCase("hasCooler") && uri.equalsIgnoreCase(SIMPLE_TYPES_NS))
			hasCooler = true;
		else if (localName.equalsIgnoreCase("devicesGroup") && uri.equalsIgnoreCase(DEVICES_NS))
			devicesGroup = true;
		else if (localName.equalsIgnoreCase("port") && uri.equalsIgnoreCase(DEVICES_NS)) {
			port = true;
			Port pt = new Port();
			pt.setId(new BigInteger(attributes.getValue(0)));
			currDevice.getType().setPort(pt);
		} else if (localName.equalsIgnoreCase("origin") && uri.equalsIgnoreCase(DEVICES_NS)) {
			origin = true;
			Origin or = new Origin();
			or.setCountry(attributes.getValue(0));
			currDevice.setOrigin(or);
		} else if (localName.equalsIgnoreCase("device") && uri.equalsIgnoreCase(DEVICES_NS)) {
			currDevice = new Device();
			devices.add(currDevice);
			currDevice.setId(new BigInteger(attributes.getValue(0)));
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (name) {
			currDevice.setName(new String(ch, start, length));
			name = false;
		} else if (isCritical) {
			currDevice.setIsCritical(new Boolean(new String(ch, start, length)));
			isCritical = false;
		} else if (isPeripherals) {
			currDevice.getType().setIsPeripherals(new Boolean(new String(ch, start, length)));
			isPeripherals = false;
		} else if (hasCooler) {
			currDevice.getType().setHasCooler(new Boolean(new String(ch, start, length)));
			hasCooler = false;
		} else if (energyConsumption) {
			currDevice.getType().setEnergyConsumption(Integer.parseInt(new String(ch, start, length)));
			energyConsumption = false;
		} else if (origin) {
			currDevice.getOrigin().setValue(new String(ch, start, length));
			origin = false;
		} else if (price) {
			currDevice.setPrice(new BigDecimal(new String(ch, start, length)));
			price = false;
		} else if (port) {
			currDevice.getType().getPort().setValue(PortValues.valueOf(new String(ch, start, length)));
			port = false;
		} else if (devicesGroup) {
			currDevice.getType().setDevicesGroup(new String(ch, start, length));
			devicesGroup = false;
		}
	}

	public List<Device> getResult(Comparator<Device> comparator) {
		if (comparator != null)
			devices.sort(comparator);
		return devices;
	}

	private SAXParser initialize() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		SAXParser saxParser = null;
		try {
			saxParser = factory.newSAXParser();
		} catch (ParserConfigurationException ex) {
			throw new IllegalStateException("XML Parser initialization error!", ex);
		} catch (SAXException ex) {
			throw new IllegalStateException(ex);
		}
		return saxParser;
	}
}