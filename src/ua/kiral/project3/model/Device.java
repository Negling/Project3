package ua.kiral.project3.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "name", "origin", "price", "type", "isCritical" })
@XmlRootElement(name = "device")
public class Device {

	@XmlElement(required = true)
	protected String name;
	@XmlElement(required = true)
	protected Origin origin;
	@XmlElement(required = true)
	protected BigDecimal price;
	@XmlElement(required = true)
	protected Type type;
	protected boolean isCritical;
	@XmlAttribute(name = "id", namespace = "http://www.epam.com/devices", required = true)
	@XmlSchemaType(name = "positiveInteger")
	protected BigInteger id;

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the origin property.
	 * 
	 * @return possible object is {@link Origin }
	 * 
	 */
	public Origin getOrigin() {
		return origin;
	}

	/**
	 * Sets the value of the origin property.
	 * 
	 * @param value
	 *            allowed object is {@link Origin }
	 * 
	 */
	public void setOrigin(Origin value) {
		this.origin = value;
	}

	/**
	 * Gets the value of the price property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Sets the value of the price property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setPrice(BigDecimal value) {
		this.price = value;
	}

	/**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link Type }
	 * 
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link Type }
	 * 
	 */
	public void setType(Type value) {
		this.type = value;
	}

	/**
	 * Gets the value of the isCritical property.
	 * 
	 */
	public boolean isCritical() {
		return isCritical;
	}

	/**
	 * Sets the value of the isCritical property.
	 * 
	 */
	public void setIsCritical(boolean value) {
		this.isCritical = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link BigInteger }
	 * 
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link BigInteger }
	 * 
	 */
	public void setId(BigInteger value) {
		this.id = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "value" })
	@XmlRootElement(name = "origin")
	public static class Origin {

		@XmlValue
		protected String value;
		@XmlAttribute(name = "country", namespace = "http://www.epam.com/devices", required = true)
		@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
		@XmlSchemaType(name = "NMTOKEN")
		protected String country;

		/**
		 * Gets the value of the value property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Sets the value of the value property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * Gets the value of the country property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getCountry() {
			return country;
		}

		/**
		 * Sets the value of the country property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setCountry(String value) {
			this.country = value;
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "isPeripherals", "energyConsumption", "hasCooler", "devicesGroup", "port" })
	@XmlRootElement(name = "type")
	public static class Type {

		protected boolean isPeripherals;
		@XmlSchemaType(name = "positiveInteger")
		protected int energyConsumption = -1;
		protected boolean hasCooler;
		@XmlElement(required = true)
		protected String devicesGroup;
		@XmlElement(required = true)
		protected Port port;

		/**
		 * Gets the value of the isPeripherals property.
		 * 
		 */
		public boolean isPeripherals() {
			return isPeripherals;
		}

		/**
		 * Sets the value of the isPeripherals property.
		 * 
		 */
		public void setIsPeripherals(boolean value) {
			this.isPeripherals = value;
		}

		/**
		 * Gets the value of the energyConsumption property.
		 * 
		 * @return possible object is {@link BigInteger }
		 * 
		 */
		public int getEnergyConsumption() {
			return energyConsumption;
		}

		/**
		 * Sets the value of the energyConsumption property.
		 * 
		 * @param value
		 *            allowed object is {@link BigInteger }
		 * 
		 */
		public void setEnergyConsumption(int value) {
			this.energyConsumption = value;
		}

		/**
		 * Gets the value of the hasCooler property.
		 * 
		 */
		public boolean isHasCooler() {
			return hasCooler;
		}

		/**
		 * Sets the value of the hasCooler property.
		 * 
		 */
		public void setHasCooler(boolean value) {
			this.hasCooler = value;
		}

		/**
		 * Gets the value of the devicesGroup property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getDevicesGroup() {
			return devicesGroup;
		}

		/**
		 * Sets the value of the devicesGroup property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setDevicesGroup(String value) {
			this.devicesGroup = value;
		}

		/**
		 * Gets the value of the port property.
		 * 
		 * @return possible object is {@link Port }
		 * 
		 */
		public Port getPort() {
			return port;
		}

		/**
		 * Sets the value of the port property.
		 * 
		 * @param value
		 *            allowed object is {@link Port }
		 * 
		 */
		public void setPort(Port value) {
			this.port = value;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "value" })
		@XmlRootElement(name = "port")
		public static class Port {

			@XmlValue
			protected PortValues value;
			@XmlAttribute(name = "id", namespace = "http://www.epam.com/devices", required = true)
			@XmlSchemaType(name = "positiveInteger")
			protected BigInteger id;

			/**
			 * Gets the value of the value property.
			 * 
			 * @return possible object is {@link PortValues }
			 * 
			 */
			public PortValues getValue() {
				return value;
			}

			/**
			 * Sets the value of the value property.
			 * 
			 * @param value
			 *            allowed object is {@link PortValues }
			 * 
			 */
			public void setValue(PortValues value) {
				this.value = value;
			}

			/**
			 * Gets the value of the id property.
			 * 
			 * @return possible object is {@link BigInteger }
			 * 
			 */
			public BigInteger getId() {
				return id;
			}

			/**
			 * Sets the value of the id property.
			 * 
			 * @param value
			 *            allowed object is {@link BigInteger }
			 * 
			 */
			public void setId(BigInteger value) {
				this.id = value;
			}

			@XmlType(name = "portValues")
			@XmlEnum
			public enum PortValues {

				USB, LPT, COM;

				public String value() {
					return name();
				}
			}
		}

	}
}