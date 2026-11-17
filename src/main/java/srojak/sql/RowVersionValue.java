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
package srojak.sql;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class RowVersionValue 
		implements RowVersion {
	private final long _value;
	
	RowVersionValue(byte[] bytes) {
		Objects.requireNonNull(bytes, "bytes");
		if (bytes.length < 8) {
			throw new IllegalArgumentException("misformed byte array");
		}
		long lnAccum = bytes[0];
		for (int idx = 1; idx < 8; idx++) {
			lnAccum <<= 8;
			lnAccum |= bytes[idx];
		}
		_value = lnAccum;
	}
	
	RowVersionValue() {
		_value = 0;
	}

	@Override
	public boolean isNull() {
		return _value == 0;
	}

	@Override
	public long value() {
		return _value;
	}

	@Override
	public boolean isEqual(RowVersion other) {
		Objects.requireNonNull(other, "other");
		return _value == other.value();
	}

}
