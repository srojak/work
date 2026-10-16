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
package srojak.core.observe.code;

import java.util.EnumSet;
import java.util.Objects;

import srojak.core.observe.ObsFormatOption;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCommon;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.impl.Unknown;

/**
 * @author Stephen
 *
 */
public class CodeObjectValueObsInstance 
		extends CodeObsInstance 
		implements CodeObjectValueObservation {
	private final String _name;
	private final Object _value;

	/**
	 * @param level
	 * @param location
	 */
	public CodeObjectValueObsInstance(ObsLevel level, SourceLocation location, String strName, Object objValue) {
		super(level, location);
		Objects.requireNonNull(strName, "strName");
		_name = strName;
		if (objValue == null) {
			_value = new Unknown();
		} else {
			_value = objValue;
		}
	}

	@Override
	public String name() {
		return _name;
	}

	@Override
	public Object value() {
		return _value;
	}

	@Override
	public Class<?> classValue() {
		return _value.getClass();
	}

	@Override
	public void writeSelfTo(StringBuilder sb, EnumSet<ObsFormatOption> optionsFormat) {
		Objects.requireNonNull(sb, "sb");
		Objects.requireNonNull(optionsFormat, "optionsFormat");
		sb.append(_level.getName());
		innerWriteOrigin(sb, optionsFormat);
		innerWriteOriginClass(sb, optionsFormat);
		sb.append(": ");
		sb.append(_name);
		sb.append(" is ");
		sb.append("class ");
		sb.append(getClassNameByRule(_value.getClass()));
		if (_sbContent.length() > 0) {
			sb.append('\n');
			sb.append(_sbContent.toString());
		}
	}
}
