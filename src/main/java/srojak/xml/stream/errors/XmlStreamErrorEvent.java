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
package srojak.xml.stream.errors;

import java.util.Objects;

import javax.xml.stream.Location;

import srojak.core.data.DataErrorSeverity;
import srojak.core.events.CoreEvent;

/**
 * @author Stephen
 *
 */
public class XmlStreamErrorEvent 
		extends CoreEvent {
	private final Location _location;
	private final DataErrorSeverity _severity;
	private final String _text;
	
	/**
	 * @param source
	 */
	public XmlStreamErrorEvent(Object source, Location location, DataErrorSeverity severity, String strText) {
		super(source);
		Objects.requireNonNull(location, "location");
		Objects.requireNonNull(severity, "severity");
		_location = location;
		_severity = severity;
		_text = strText == null ? "" : strText;
	}
	
	public Location getLocation() {
		return _location;
	}
	
	public DataErrorSeverity getSeverity() {
		return _severity;
	}
	
	public String getText() {
		return _text;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		sb.append(", location=");
		sb.append("[line=");
		if (_location.getLineNumber() < 0) {
			sb.append('?');
		} else {
			sb.append(_location.getLineNumber());
		}
		sb.append(", col=");
		if (_location.getColumnNumber() < 0) {
			sb.append('?');
		} else {
			sb.append(_location.getColumnNumber());
		}
		sb.append("], severity=");
		sb.append(_severity);
		sb.append(", \"");
		sb.append(_text);
		sb.append('\"');
	}

}
