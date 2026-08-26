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

import java.util.NoSuchElementException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

import srojak.core.CommonCollectionSize;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.core.result.XResultOf;
import srojak.xml.stream.impl.XmlStreamElementAttribute;

/**
 * @author Stephen
 *
 */
public class StreamElementAttributeSet
		implements CommonCollectionSize {
	private final StreamElementAttribute[] _attribs;

	private final static String STRING_TRUE = "true";
	
	StreamElementAttributeSet(XMLStreamReader reader) {
		int nCount = reader.getAttributeCount();
		_attribs = new StreamElementAttribute[nCount];
		for (int index = 0; index < nCount; index++) {
			_attribs[index] = new XmlStreamElementAttribute(reader, index);
		}
	}

	@Override
	public boolean isEmpty() {
		return _attribs.length == 0;
	}

	@Override
	public int size() {
		return _attribs.length;
	}

	public StreamElementAttribute findAttributeByName(QName nameAttribute) {
		for (int index = 0; index < _attribs.length; index++) {
			if (_attribs[index].getName().equals(nameAttribute)) {
				return _attribs[index];
			}
		}
		return null;
	}
	
	public XResultOf<String> readRequiredStringAttribValue(QName nameAttribute) {
		XResultCarrierOf<String> result = new XResultCarrierOf<String>();	
		StreamElementAttribute attrib = findAttributeByName(nameAttribute);
		if (attrib == null) {
			result.caughtException(new NoSuchElementException(nameAttribute.toString()));
		} else {
			String strValue = attrib.getValue();
			if (strValue.isBlank()) {
				result.caughtException(new IllegalArgumentException("string is blank"));
			} else {
				result.setResult(strValue);
			}
		}
		return result;
	}
		
	public boolean readBooleanAttribValue(QName nameAttribute) {
		StreamElementAttribute attrib = findAttributeByName(nameAttribute);
		// starting out permissive: anything that is not true is false
		if (attrib != null && attrib.hasValue()) {
			String strValue = attrib.getValue();
			if (STRING_TRUE.equalsIgnoreCase(strValue)) {
				return true;
			}
		}
		return false;
	}
	
	public XResultInt readIntAttribValue(QName nameAttribute) {
		XResultIntCarrier result = new XResultIntCarrier();
		StreamElementAttribute attrib = findAttributeByName(nameAttribute);
		if (attrib == null) {
			result.caughtException(new NoSuchElementException(nameAttribute.toString()));
		} else {
			String strValue = attrib.getValue();
			try {
				int nValue = Integer.parseInt(strValue);
				result.setResult(nValue);
			} catch (NumberFormatException e) {
				result.caughtException(e);
			}
		}
		return result;
	}
}
