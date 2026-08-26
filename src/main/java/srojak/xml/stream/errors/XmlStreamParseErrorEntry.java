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

import javax.xml.namespace.QName;
import javax.xml.stream.Location;

import srojak.core.InputLocation;
import srojak.core.data.DataErrorSeverity;
import srojak.xml.XmlParseErrorEntry;

/**
 * @author Stephen
 *
 */
public class XmlStreamParseErrorEntry 
		extends XmlParseErrorEntry
		implements XmlStreamParseErrorDescr {
	private Location _location;

	public XmlStreamParseErrorEntry(Location location, QName nameElement, DataErrorSeverity severity, String strText) {
		super(nameElement, severity, strText);
		Objects.requireNonNull(location, "location");
		_location = location;
	}
	
	public XmlStreamParseErrorEntry(Location location, DataErrorSeverity severity, String strText) {
		super(severity, strText);
		Objects.requireNonNull(location, "location");
		_location = location;
	}

	@Override
	public Location getLocation() {
		return _location;
	}

	@Override
	protected void formatInto(StringBuilder sb) {
		sb.append("at ");
		sb.append(_location);
		sb.append(' ');
		super.formatInto(sb);
	}
}
