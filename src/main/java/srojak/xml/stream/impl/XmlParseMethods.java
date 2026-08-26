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
package srojak.xml.stream.impl;

import javax.xml.stream.XMLStreamReader;

import org.xml.sax.SAXParseException;

import srojak.core.InputLocation;
import srojak.xml.stream.StreamElementAttribute;
import srojak.xml.stream.XmlInputLocationContainer;

/**
 * @author Stephen
 *
 */
public class XmlParseMethods {
	
	public static StringBuilder getTextChars(XMLStreamReader reader) {
		StringBuilder sb = new StringBuilder();
		sb.append(reader.getTextCharacters(), reader.getTextStart(), reader.getTextLength());
		return sb;
	}
	
	@Deprecated
	public static StreamElementAttribute[] getAttributes(XMLStreamReader reader) {
		int nCount = reader.getAttributeCount();
		StreamElementAttribute[] array = new StreamElementAttribute[nCount];
		for (int index = 0; index < nCount; index++) {
			array[index] = new XmlStreamElementAttribute(reader, index);
		}
		return array;
	}
}
