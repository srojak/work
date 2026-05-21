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

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

import srojak.xml.stream.XmlElementAttribute;

/**
 * @author Stephen
 *
 */
public class StreamElementAttribute 
		implements XmlElementAttribute {
	private final QName _name;
	private final String _strNamespace;
	private final String _strPrefix;
	private final String _strType;

	private final String _strValue;
	private final boolean _bIsSpecified;
	
	public StreamElementAttribute(XMLStreamReader reader, int index) {
		if (!reader.isStartElement())
			throw new IllegalStateException("reader is not in a valid state");
		_name = reader.getAttributeName(index);
		_strNamespace = reader.getAttributeNamespace(index);
		_strPrefix = reader.getAttributePrefix(index);
		_strType = reader.getAttributeType(index);
		_strValue = reader.getAttributeValue(index);
		_bIsSpecified = reader.isAttributeSpecified(index);
	}

	@Override
	public QName getName() {
		return _name;
	}

	@Override
	public String getNamespace() {
		return _strNamespace;
	}

	@Override
	public String getPrefix() {
		return _strPrefix;
	}

	@Override
	public String getType() {
		return _strType;
	}
	
	@Override
	public boolean hasValue() {
		return _strValue != null;
	}

	@Override
	public String getValue() {
		return _strValue;
	}

	@Override
	public boolean isSpecified() {
		return _bIsSpecified;
	}

}
