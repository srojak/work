/**
 * Copyright © 2026 Stephen Rojak.
 * 
 * This file is part of the srojak Java portfolio.
 * 
 * The srojak Java portfolio is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 3 of the License.
 * 
 * The srojak Java portfolio is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this portfolio.
 * If not, see <https://www.gnu.org/licenses/>.
 */
package srojak.xml.stream;

import java.io.*;
import javax.xml.stream.*;

/**
 * @author Stephen
 *
 */
public class XmlStreamOutputBuilder {
	XMLOutputFactory _factoryOutput;
	
	/**
	 * 
	 */
	public XmlStreamOutputBuilder() {
		_factoryOutput = XMLOutputFactory.newInstance();
	}
	
	public void setFactoryProperty(String name, Object value) {
		_factoryOutput.setProperty(name, value);
	}

	public XMLStreamWriter createWriter(OutputStream stream) 
			throws XMLStreamException {
		return _factoryOutput.createXMLStreamWriter(stream);
	}
	
	public XMLEventWriter createEventWriter(OutputStream stream)
			throws XMLStreamException {
		return _factoryOutput.createXMLEventWriter(stream);
	}
}
