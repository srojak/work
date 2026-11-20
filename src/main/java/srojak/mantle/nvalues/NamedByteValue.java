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
package srojak.mantle.nvalues;

/**
 * @author Stephen
 *
 */
public class NamedByteValue 
		extends NamedValueBase 
		implements NameAndByte {
	private byte _value;

	/**
	 * @param strName
	 */
	public NamedByteValue(String strName, byte valueInitial) {
		super(strName);
		_value = valueInitial;
	}
	
	protected void validate(byte value) {
		// base class method does nothing
	}

	@Override
	public byte value() {
		return _value;
	}

	@Override
	public boolean isValueEqual(byte other) {
		return _value == other;
	}

	public void setValue(byte value) {
		validate(value);
		_value = value;
	}

	@Override
	protected void formatValue(StringBuilder sb) {
		sb.append(_value);
	}

}
