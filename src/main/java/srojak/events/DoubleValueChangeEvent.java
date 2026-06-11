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
public class DoubleValueChangeEvent
		extends CoreEvent {
	private final double _value;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3173640976862942338L;

	/**
	 * @param source
	 */
	public DoubleValueChangeEvent(Object source, double value) {
		super(source);
		_value = value;
	}

	public double getValue() {
		return _value;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		sb.append(String.format("value=%.3f", _value));
	}

}
