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
package srojak.numerics.nullable;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public final class NullableBooleanValue 
		implements NullableBoolean {
	private final byte _flags;
	
	private static final byte FLAGS_NULL = 0x1;
	private static final byte FLAGS_TRUE = 0x2;
	
	NullableBooleanValue() {
		_flags = FLAGS_NULL;
	}
	
	NullableBooleanValue(boolean bInitialValue) {
		_flags = bInitialValue ? FLAGS_TRUE : 0;
	}

	@Override
	public boolean isNull() {
		// TODO Auto-generated method stub
		return (_flags & FLAGS_NULL) != 0;
	}

	@Override
	public boolean isEqual(NullableBoolean obj) {
		if (obj == null) {
			return false;
		} else if (isTrue() == obj.isTrue()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isTrue() {
		return (_flags & FLAGS_TRUE) != 0;
	}

	@Override
	public boolean isFalse() {
		return _flags == 0;
	}

	@Override
	public NullableBoolean not() {
		if (isNull()) {
			return new NullableBooleanValue();
		} else {
			return new NullableBooleanValue(!isTrue());
		}
	}

	@Override
	public NullableBoolean and(NullableBoolean first, NullableBoolean... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		if (isNull()) {
			return new NullableBooleanValue();			
		}
		boolean bAnswer = first.isTrue();
		if (first.isNull()) {
			return new NullableBooleanValue();
		} else if (!first.isTrue()) {
			bAnswer = false;
		}
		for (NullableBoolean next : rest) {
			if (next.isNull()) {
				return new NullableBooleanValue();
			} else if (!next.isTrue()) {
				bAnswer = false;
			}
		}
		return new NullableBooleanValue(bAnswer);
	}

	@Override
	public NullableBoolean or(NullableBoolean first, NullableBoolean... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		if (isNull()) {
			return new NullableBooleanValue();			
		}
		boolean bAnswer = first.isTrue();
		if (first.isNull()) {
			return new NullableBooleanValue();
		} else if (first.isTrue()) {
			bAnswer = true;
		}
		for (NullableBoolean next : rest) {
			if (next.isNull()) {
				return new NullableBooleanValue();
			} else if (next.isTrue()) {
				bAnswer = true;
			}
		}
		return new NullableBooleanValue(bAnswer);
	}

	@Override
	public NullableBoolean xor(NullableBoolean operand) {
		Objects.requireNonNull(operand, "operand");
		if (isNull() || operand.isNull()) {
			return new NullableBooleanValue();
		} else {
			return new NullableBooleanValue(isTrue() != operand.isTrue());
		}
	}

	@Override
	public int hashCode() {
		return isNull() ? 0 : Boolean.hashCode(isTrue());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NullableBoolean other) {
			return isEqual(other);
		} else if (obj instanceof Boolean other) {
			if (isNull()) {
				return false;
			} else {
				return isTrue() == other.booleanValue();
			}
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		if (isNull()) {
			return STR_NULL;
		} else {
			return Boolean.toString(isTrue());
		}
	}

	@Override
	protected Object clone() 
			throws CloneNotSupportedException {
		if (isNull()) {
			return new NullableBooleanValue();
		} else {
			return new NullableBooleanValue(isTrue());
		}
	}

	static NullableBoolean parse(String strInput) {
		Objects.requireNonNull(strInput, "strInput");
		if (strInput.isEmpty() || strInput.equalsIgnoreCase(STR_NULL)) {
			return new NullableBooleanValue();
		} else {
			return new NullableBooleanValue(Boolean.parseBoolean(strInput));
		}
	}
}
