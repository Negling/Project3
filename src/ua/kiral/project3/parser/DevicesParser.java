package ua.kiral.project3.parser;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import ua.kiral.project3.model.Device;

interface DevicesParser {
	String SIMPLE_TYPES_NS = "http://www.epam.com/simpleTypes";
	String DEVICES_NS = "http://www.epam.com/devices";

	/**
	 * Parses XML file to java structure.
	 * 
	 * @param pathToXML
	 *            - path to xml file.
	 * @throws IOException
	 *             if file by inputed path not found.
	 */
	public void parse(String pathToXML) throws IOException;

	/**
	 * Returns sorted by comparator list {@link ArrayList} of Devices from
	 * parsed XML file. If comparator is null - elements will appear in
	 * natural order.
	 * 
	 * @param comparator
	 * @return {@link ArrayList} devices
	 */
	public List<Device> getResult(Comparator<Device> comparator);
}
