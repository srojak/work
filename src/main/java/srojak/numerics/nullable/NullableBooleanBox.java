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

import srojak.core.excepts.NullValueException;

/**
 * @author Stephen
 *
 */
public final class NullableBooleanBox 
		implements NullableBoolean {
	private final byte _flags;
	
	private static final byte FLAGS_NULL = 0x1;
	private static final byte FLAGS_TRUE = 0x2;
	
	NullableBooleanBox() {
		_flags = FLAGS_NULL;
	}
	
	NullableBooleanBox(boolean bInitialValue) {
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
	public int compareTo(NullableBoolean o) {
		if (o == null || isNull() || o.isNull()) {
			throw new NullValueException("at least one operand in comparison is null");
		} else {
			return Boolean.compare(isTrue(), o.isTrue());
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
			return new NullableBooleanBox();
		} else {
			return new NullableBooleanBox(!isTrue());
		}
	}

	@Override
	public NullableBoolean and(NullableBoolean first, NullableBoolean... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		if (isNull()) {
			return new NullableBooleanBox();			
		}
		boolean bAnswer = first.isTrue();
		if (first.isNull()) {
			return new NullableBooleanBox();
		} else if (!first.isTrue()) {
			bAnswer = false;
		}
		for (NullableBoolean next : rest) {
			if (next.isNull()) {
				return new NullableBooleanBox();
			} else if (!next.isTrue()) {
				bAnswer = false;
			}
		}
		return new NullableBooleanBox(bAnswer);
	}

	@Override
	public NullableBoolean or(NullableBoolean first, NullableBoolean... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		if (isNull()) {
			return new NullableBooleanBox();			
		}
		boolean bAnswer = first.isTrue();
		if (first.isNull()) {
			return new NullableBooleanBox();
		} else if (first.isTrue()) {
			bAnswer = true;
		}
		for (NullableBoolean next : rest) {
			if (next.isNull()) {
				return new NullableBooleanBox();
			} else if (next.isTrue()) {
				bAnswer = true;
			}
		}
		return new NullableBooleanBox(bAnswer);
	}

	@Override
	public NullableBoolean xor(NullableBoolean operand) {
		Objects.requireNonNull(operand, "operand");
		if (isNull() || operand.isNull()) {
			return new NullableBooleanBox();
		} else {
			return new NullableBooleanBox(isTrue() != operand.isTrue());
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
			return new NullableBooleanBox();
		} else {
			return new NullableBooleanBox(isTrue());
		}
	}

	static NullableBoolean parse(String strInput) {
		Objects.requireNonNull(strInput, "strInput");
		if (strInput.isEmpty() || strInput.equalsIgnoreCase(STR_NULL)) {
			return new NullableBooleanBox();
		} else {
			return new NullableBooleanBox(Boolean.parseBoolean(strInput));
		}
	}
}
