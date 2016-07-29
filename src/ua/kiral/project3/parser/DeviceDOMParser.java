package ua.kiral.project3.parser;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ua.kiral.project3.model.Device;
import ua.kiral.project3.model.Device.Origin;
import ua.kiral.project3.model.Device.Type;
import ua.kiral.project3.model.Device.Type.Port;
import ua.kiral.project3.model.Device.Type.Port.PortValues;

class DeviceDOMParser implements DevicesParser {
	private List<Device> devices;

	public void parse(String xmlPath) throws IOException {
		devices = new ArrayList<>();
		NodeList devcs = getDeviceNodes(xmlPath);
		for (int i = 0; i < devcs.getLength(); i++) {
			Element currElem = (Element) devcs.item(i);
			devices.add(getDevice(currElem));
		}
	}

	/**
	 * Returns list of devices from current xml file as Nodes.
	 * 
	 * @param xmlPath
	 *            - path to xml file.
	 * @return
	 * @throws IOException
	 */
	private NodeList getDeviceNodes(String xmlPath) throws IOException {
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		f.setNamespaceAware(true);
		Document doc = null;
		try {
			doc = f.newDocumentBuilder().parse(new File(xmlPath));
		} catch (ParserConfigurationException ex) {
			throw new IllegalStateException("XML Document initialization error!", ex);
		} catch (SAXException ex) {
			throw new IllegalStateException(ex);
		}
		return doc.getDocumentElement().getElementsByTagNameNS(DEVICES_NS, "device");
	}

	/**
	 * Transforms Device Node to a exemplar of Device.class.
	 * 
	 * @param elem
	 * @return
	 */
	private Device getDevice(Element elem) {
		Device dev = new Device();

		dev.setId(new BigInteger(elem.getAttributeNS(DEVICES_NS, "id")));
		dev.setName(getNodeValue(elem, SIMPLE_TYPES_NS, "name"));
		dev.setOrigin(getOrigin(elem));
		dev.setPrice(new BigDecimal(getNodeValue(elem, DEVICES_NS, "price")));
		dev.setType(getType(elem));
		dev.setIsCritical(new Boolean(getNodeValue(elem, SIMPLE_TYPES_NS, "isCritical")));

		return dev;
	}

	/**
	 * Transforms Origin Node to a exemplar of Origin.class.
	 * 
	 * @param elem
	 * @return
	 */
	private Origin getOrigin(Element elem) {
		NodeList list = elem.getElementsByTagNameNS(DEVICES_NS, "origin");
		Element currElem = (Element) list.item(0);
		Origin orig = new Origin();

		orig.setCountry(currElem.getAttributeNS(DEVICES_NS, "country"));
		orig.setValue(currElem.getTextContent());

		return orig;
	}

	/**
	 * Transforms Type Node to a exemplar of Type.class.
	 * 
	 * @param elem
	 * @return
	 */
	private Type getType(Element elem) {
		NodeList list = elem.getElementsByTagNameNS(DEVICES_NS, "type");
		Element currElem = (Element) list.item(0);
		Type type = new Type();

		type.setIsPeripherals(new Boolean(getNodeValue(currElem, SIMPLE_TYPES_NS, "isPeripherals")));
		type.setDevicesGroup(getNodeValue(currElem, DEVICES_NS, "devicesGroup"));
		type.setHasCooler(new Boolean(getNodeValue(currElem, SIMPLE_TYPES_NS, "hasCooler")));
		type.setPort(getPort(currElem));

		String energyConsumption = getNodeValue(currElem, SIMPLE_TYPES_NS, "energyConsumption");
		if (energyConsumption != null)
			type.setEnergyConsumption(Integer.parseInt(energyConsumption));
		return type;
	}

	/**
	 * Transforms port Node to a exemplar of Port.class.
	 * 
	 * @param elem
	 * @return
	 */
	private Port getPort(Element elem) {
		NodeList list = elem.getElementsByTagNameNS(DEVICES_NS, "port");
		Element currElem = (Element) list.item(0);
		Port port = new Port();

		port.setId(new BigInteger(currElem.getAttributeNS(DEVICES_NS, "id")));
		port.setValue(PortValues.valueOf(currElem.getTextContent()));

		return port;
	}

	/**
	 * Returns text value of node with current tag.
	 * 
	 * @param elem
	 * @param tag
	 * @return
	 */
	private String getNodeValue(Element elem, String namespace, String tag) {
		NodeList list = elem.getElementsByTagNameNS(namespace, tag);
		if (list.getLength() < 1)
			return null;
		else
			return list.item(0).getTextContent();
	}

	public List<Device> getResult(Comparator<Device> comparator) {
		if (comparator != null)
			devices.sort(comparator);
		return devices;
	}
}