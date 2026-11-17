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
package srojak.mantle.text;

/**
 * @author Stephen
 *
 */
public class TextChannelNumberBox 
		implements TextChannelNumber {
	private final byte _number;
	
	/**
	 * 
	 */
	TextChannelNumberBox(byte nChannel) {
		if (nChannel < 0) {
			throw new IllegalArgumentException("nChannel cannot be negative");
		}
		_number = nChannel;
	}

	@Override
	public boolean isEqual(TextChannelNumber obj) {
		if (obj == this) {
			return true;
		} else if (obj == null) {
			return false;
		} else {
			return _number == obj.number();
		}
	}

	@Override
	public byte number() {
		return _number;
	}

	@Override
	public boolean isNumericallyEqual(int nChannel) {
		return _number == nChannel;
	}

	@Override
	public int hashCode() {
		return (int) _number;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TextChannelNumber other) {
			return isEqual(other);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "ch(" + String.valueOf(_number) + ")";
	}

}
