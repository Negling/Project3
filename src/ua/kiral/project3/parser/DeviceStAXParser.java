package ua.kiral.project3.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import ua.kiral.project3.model.Device;
import ua.kiral.project3.model.Device.Origin;
import ua.kiral.project3.model.Device.Type;
import ua.kiral.project3.model.Device.Type.Port;
import ua.kiral.project3.model.Device.Type.Port.PortValues;

class DeviceStAXParser implements DevicesParser {
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

	public void parse(String pathToXML) throws FileNotFoundException {
		XMLEventReader eventReader = initialize(pathToXML);
		while (eventReader.hasNext()) {
			XMLEvent event = null;
			try {
				event = eventReader.nextEvent();
			} catch (XMLStreamException ex) {
				throw new IllegalStateException(ex);
			}
			switch (event.getEventType()) {
			case XMLStreamConstants.START_DOCUMENT:
				startDocument();
				break;
			case XMLStreamConstants.START_ELEMENT:
				startElement(event.asStartElement());
				break;
			case XMLStreamConstants.CHARACTERS:
				characters(event.asCharacters());
			}
		}
	}

	private void startElement(StartElement element) {
		String localName = element.getName().getLocalPart();
		String uri = element.getName().getNamespaceURI();
		Iterator<Attribute> itrtr = element.getAttributes();
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
			pt.setId(new BigInteger(itrtr.next().getValue()));
			currDevice.getType().setPort(pt);
		} else if (localName.equalsIgnoreCase("origin") && uri.equalsIgnoreCase(DEVICES_NS)) {
			origin = true;
			Origin or = new Origin();
			or.setCountry(itrtr.next().getValue());
			currDevice.setOrigin(or);
		} else if (localName.equalsIgnoreCase("device") && uri.equalsIgnoreCase(DEVICES_NS)) {
			currDevice = new Device();
			devices.add(currDevice);
			currDevice.setId(new BigInteger(itrtr.next().getValue()));
		}
	}

	private void characters(Characters characters) {
		if (name) {
			currDevice.setName(characters.getData());
			name = false;
		} else if (isCritical) {
			currDevice.setIsCritical(new Boolean(characters.getData()));
			isCritical = false;
		} else if (isPeripherals) {
			currDevice.getType().setIsPeripherals(new Boolean(characters.getData()));
			isPeripherals = false;
		} else if (hasCooler) {
			currDevice.getType().setHasCooler(new Boolean(characters.getData()));
			hasCooler = false;
		} else if (energyConsumption) {
			currDevice.getType().setEnergyConsumption(Integer.parseInt(characters.getData()));
			energyConsumption = false;
		} else if (origin) {
			currDevice.getOrigin().setValue(characters.getData());
			origin = false;
		} else if (price) {
			currDevice.setPrice(new BigDecimal(characters.getData()));
			price = false;
		} else if (port) {
			currDevice.getType().getPort().setValue(PortValues.valueOf(characters.getData()));
			port = false;
		} else if (devicesGroup) {
			currDevice.getType().setDevicesGroup(characters.getData());
			devicesGroup = false;
		}
	}

	private void startDocument() {
		devices = new ArrayList<>();
	}

	private XMLEventReader initialize(String pathToXML) throws FileNotFoundException {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader reader = null;
		try {
			reader = factory.createXMLEventReader(new FileReader(pathToXML));
		} catch (XMLStreamException ex) {
			throw new IllegalStateException("XML Event Reader initialization error!", ex);
		}
		return reader;
	}

	public List<Device> getResult(Comparator<Device> comparator) {
		if (comparator != null)
			devices.sort(comparator);
		return devices;
	}
}