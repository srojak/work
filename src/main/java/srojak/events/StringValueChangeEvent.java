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
package srojak.events;

import srojak.core.events.CoreEvent;

/**
 * @author Stephen
 *
 */
public class StringValueChangeEvent
		extends CoreEvent {
	private final String _value;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7351690286021075275L;

	/**
	 * @param source
	 */
	public StringValueChangeEvent(Object source, String strValue) {
		super(source);
		_value = strValue;
	}
	
	public boolean isValueNull() {
		return _value == null;
	}
	
	public String getValue() {
		return _value;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		sb.append("value=");
		if (_value == null) {
			sb.append("(null)");
		} else {
			sb.append('\"');
			sb.append(_value);
			sb.append('\"');
		}

	}

}
