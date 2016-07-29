<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dev="http://www.epam.com/devices"
	xmlns:st="http://www.epam.com/simpleTypes">

	<xsl:template match="/">
		<html>
			<body>
				<h2>List of aviliable devices:</h2>
				<xsl:for-each select="dev:devices/dev:device">
					<details>
						<summary>
							<xsl:value-of select="st:name" />
						</summary>
						<pre>
							<table border="1">
								<tr bgcolor="#9acd32">
									<th>ID</th>
									<th>Manufacturer</th>
									<th>Manufacturer ID</th>
									<th>Price</th>
									<th>Is Peripherals</th>
									<th>Energy Consumption</th>
									<th>Has Cooler</th>
									<th>Devices Group</th>
									<th>Port Type</th>
									<th>Port ID</th>
									<th>Critical to Work</th>
								</tr>
								<tr>
									<td style="text-align:center">
										<xsl:value-of select="@dev:id" />
									</td>
									<td style="text-align:center">
										<xsl:value-of select="dev:origin" />
									</td>
									<td style="text-align:center">
										<xsl:value-of select="dev:origin/@dev:country" />
									</td>
									<td style="text-align:center">
										<xsl:value-of select="dev:price" />
									</td>
									<td style="text-align:center">
										<xsl:value-of select="dev:type/st:isPeripherals" />
									</td>
									<td style="text-align:center">
										<xsl:value-of select="dev:type/st:energyConsumption" />
									</td>
									<td style="text-align:center">
										<xsl:value-of select="dev:type/st:hasCooler" />
									</td>
									<td style="text-align:center">
										<xsl:value-of select="dev:type/dev:devicesGroup" />
									</td>
									<td style="text-align:center">
										<xsl:value-of select="dev:type/dev:port" />
									</td>
									<td style="text-align:center">
										<xsl:value-of select="dev:type/dev:port/@dev:id" />
									</td>
									<td style="text-align:center">
										<xsl:value-of select="st:isCritical" />
									</td>
								</tr>
							</table>
						</pre>
					</details>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>