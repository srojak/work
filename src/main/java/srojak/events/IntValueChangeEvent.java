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
public class IntValueChangeEvent
		extends CoreEvent {
	private final int _value;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1579335365485616865L;

	/**
	 * @param source
	 */
	public IntValueChangeEvent(Object source, int value) {
		super(source);
		_value = value;
	}

	public int getValue() {
		return _value;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		sb.append(", value=");
		sb.append(_value);
	}

}
