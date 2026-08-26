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
package srojak.xml;

import java.util.Objects;

import javax.xml.namespace.QName;

import srojak.core.data.DataErrorSeverity;

/**
 * @author Stephen
 *
 */
public class XmlParseErrorEntry
		implements XmlParseErrorDescr {
	private final QName _element;
	private final DataErrorSeverity _severity;
	private final String _text;
	
	public XmlParseErrorEntry(QName nameElement, DataErrorSeverity severity, String strText) {
		Objects.requireNonNull(nameElement, "nameElement");
		Objects.requireNonNull(severity, "severity");
		_element = nameElement;
		_severity = severity;
		_text = strText;
	}
	
	public XmlParseErrorEntry(DataErrorSeverity severity, String strText) {
		Objects.requireNonNull(severity, "severity");
		_element = null;
		_severity = severity;
		_text = strText;
	}

	@Override
	public boolean hasElementName() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public QName getElementName() {
		return _element;
	}
	
	@Override
	public DataErrorSeverity getSeverity() {
		return _severity;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void formatInto(StringBuilder sb) {
		if (_element != null) {
			sb.append("in element ");
			sb.append(_element);
			sb.append(", ");
		}
		sb.append("severity=");
		sb.append(_severity);
		sb.append(": ");
		sb.append(_text);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		formatInto(sb);
		return sb.toString();
	}
}
