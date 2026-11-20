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

import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import srojak.collate.Collation;
import srojak.collate.CollationRegexOptions;
import srojak.core.excepts.NullValueException;
import srojak.numerics.OrderedComparison;
import srojak.numerics.nullable.NullableBoolean;
import srojak.numerics.nullable.NullableValueBase;

/**
 * @author Stephen
 *
 */
public final class NullableCollatedStringValue
		extends NullableValueBase
		implements NullableCollatedString {
	private final Collation _collation;
	private final String _str;

	NullableCollatedStringValue(Collation collation) {
		super(true);
		Objects.requireNonNull(collation, "collation");
		_collation = collation;
		_str = "";
	}

	NullableCollatedStringValue(Collation collation, String strContent) {
		super(false);
		Objects.requireNonNull(collation, "collation");
		Objects.requireNonNull(strContent, "strContent");
		_collation = collation;
		_str = strContent;
	}

	NullableCollatedStringValue(Collation collation, StringBuilder sbContent) {
		super(false);
		Objects.requireNonNull(collation, "collation");
		Objects.requireNonNull(sbContent, "sbContent");
		_collation = collation;
		_str = new String(sbContent);
	}
	
	NullableCollatedStringValue(Collation collation, byte[] bytes, int offset, int length) {
		super(false);
		Objects.requireNonNull(collation, "collation");
		Objects.requireNonNull(bytes, "bytes");
		_collation = collation;
		_str = new String(bytes, offset, length, _collation.getCharset());
	}

	@Override
	public Collation getCollation() {
		return _collation;
	}
	
	private boolean isContentEqual(String other) {
		return _collation.compare(_str, other) == 0;
	}

	@Override
	public boolean isEqual(NullableCollatedString obj) {
		Objects.requireNonNull(obj, "obj");
		if (_isNull || obj.isNull()) {
			throw new NullValueException("at least one operand in comparison is null");
		} else {
			return isContentEqual(obj.value());
		}
	}

	@Override
	public int compareTo(NullableCollatedString o) {
		Objects.requireNonNull(o, "o");
		if (_isNull || o.isNull()) {
			throw new NullValueException("at least one operand in comparison is null");
		} else {
			return _collation.compare(_str, o.value());
		}
	}

	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param strValue The value to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and the comparison is true.
	 */
	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, String strValue) {
		Objects.requireNonNull(comparison, "comparison");
		Objects.requireNonNull(strValue, "strValue");
		if (_isNull) {
			return NullableBoolean.fromNull();
		} else {
			boolean bCompar = comparison.evaluate(_collation.compare(_str, strValue));
			return NullableBoolean.from(bCompar);
		}
	}

	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param other The other operand to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} if the value of the object is not {@code null},
	 * 	@{code other} is not {@code null} and the comparison is true.
	 */
	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, NullableCollatedString other) {
		Objects.requireNonNull(comparison, "comparison");
		Objects.requireNonNull(other, "other");
		if (_isNull || other.isNull()) {
			return NullableBoolean.fromNull();
		} else {
			boolean bCompar = comparison.evaluate(_collation.compare(_str, other.value()));
			return NullableBoolean.from(bCompar);
		}
	}

	@Override
	public String value() {
		if (_isNull) {
			throw new NullValueException("value is null");
		} else {
			return _str;
		}
	}

	@Override
	public int length() {
		return _str.length();
	}

	@Override
	public boolean isEmpty() {
		return _str.isEmpty();
	}

	@Override
	public boolean hasSameCollationAs(NullableCollatedString other) {
		Objects.requireNonNull(other, "other");
		return _collation == other.getCollation();
	}

	@Override
	public boolean hasSameCharsetAs(NullableCollatedString other) {
		Objects.requireNonNull(other, "other");
		return _collation.getCharset() == other.getCollation().getCharset();
	}

	@Override
	public char charAt(int index) {
		if (_isNull) {
			throw new NullValueException("value is null");
		} else {
			return _str.charAt(index);
		}
	}

	@Override
	public int codePointAt(int index) {
		if (_isNull) {
			throw new NullValueException("value is null");
		} else {
			return _str.codePointAt(index);
		}
	}

	@Override
	public int indexOf(String strFind) {
		Objects.requireNonNull(strFind, "strFind");
		if (_isNull) {
			throw new NullValueException("value is null");
		} else {
			return _collation.indexOf(_str, strFind, 0);
		}
	}

	@Override
	public int indexOf(String strFind, int fromIndex) {
		Objects.requireNonNull(strFind, "strFind");
		if (_isNull) {
			throw new NullValueException("value is null");
		} else {
			Objects.checkIndex(fromIndex, _str.length());
			return _collation.indexOf(_str, strFind, fromIndex);
		}
	}

	@Override
	public String[] split(String regex) {
		Objects.requireNonNull(regex, "regex");
		if (_isNull) {
			throw new NullValueException("value is null");
		} else {
			return _str.split(regex);
		}
	}

	@Override
	public String[] split(String regex, int limit) {
		Objects.requireNonNull(regex, "regex");
		if (_isNull) {
			throw new NullValueException("value is null");
		} else {
			return _str.split(regex, limit);
		}
	}

	@Override
	public NullableBoolean matches(String regex, CollationRegexOptions options) {
		Objects.requireNonNull(regex, "regex");
		Objects.requireNonNull(options, "options");
		if (_isNull) {
			return NullableBoolean.fromNull();
		} else {
			Pattern pattern = _collation.compile(regex, options);
			Matcher matcher = pattern.matcher(_str);
			return NullableBoolean.from(matcher.matches());
		}
	}

	@Override
	public NullableBoolean startsWith(String prefix) {
		Objects.requireNonNull(prefix, "prefix");
		if (_isNull) {
			return NullableBoolean.fromNull();
		} else {
			int szPrefix = prefix.length();
			boolean bResult = _collation.regionMatches(_str, 0, prefix, 0, szPrefix);
			return NullableBoolean.from(bResult);
		}
	}

	@Override
	public NullableBoolean startsWith(String prefix, int toffset) {
		Objects.requireNonNull(prefix, "prefix");
		Objects.checkIndex(toffset, _str.length());
		if (_isNull) {
			return NullableBoolean.fromNull();
		} else {
			int szPrefix = prefix.length();
			boolean bResult = _collation.regionMatches(_str, toffset, prefix, 0, szPrefix);
			return NullableBoolean.from(bResult);
		}
	}

	@Override
	public NullableBoolean endsWith(String suffix) {
		Objects.requireNonNull(suffix, "suffix");
		if (_isNull) {
			return NullableBoolean.fromNull();
		} else {
			int szSuffix = suffix.length();
			int strStart = _str.length() - szSuffix;
			boolean bResult = false;
			if (strStart >= 0) {
				bResult = _collation.regionMatches(_str, strStart, suffix, 0, szSuffix);
			}
			return NullableBoolean.from(bResult);
		}
	}

	@Override
	public NullableCollatedString substring(int beginIndex) {
		if (_isNull) {
			return new NullableCollatedStringValue(_collation);
		} else {
			return new NullableCollatedStringValue(_collation, _str.substring(beginIndex));
		}
	}

	@Override
	public NullableCollatedString substring(int beginIndex, int endIndex) {
		if (_isNull) {
			return new NullableCollatedStringValue(_collation);
		} else {
			return new NullableCollatedStringValue(_collation, _str.substring(beginIndex, endIndex));
		}
	}

	@Override
	public NullableCollatedString replace(char oldChar, char newChar) {
		if (_isNull) {
			return new NullableCollatedStringValue(_collation);
		} else {
			return new NullableCollatedStringValue(_collation, _str.replace(oldChar, newChar));
		}
	}

	@Override
	public NullableCollatedString replaceAll(String regex, CollationRegexOptions options, String replacement) {
		Objects.requireNonNull(regex, "regex");
		Objects.requireNonNull(options, "options");
		Objects.requireNonNull(replacement, "replacement");
		if (_isNull) {
			return new NullableCollatedStringValue(_collation);
		} else {
			Pattern pattern = _collation.compile(regex, options);
			Matcher matcher = pattern.matcher(_str);
			return new NullableCollatedStringValue(_collation, matcher.replaceAll(replacement));
		}
	}

	@Override
	public NullableCollatedString replaceFirst(String regex, CollationRegexOptions options, String replacement) {
		Objects.requireNonNull(regex, "regex");
		Objects.requireNonNull(options, "options");
		Objects.requireNonNull(replacement, "replacement");
		if (_isNull) {
			return new NullableCollatedStringValue(_collation);
		} else {
			Pattern pattern = _collation.compile(regex, options);
			Matcher matcher = pattern.matcher(_str);
			return new NullableCollatedStringValue(_collation, matcher.replaceFirst(replacement));
		}
	}

	@Override
	public NullableCollatedString toLowerCase() {
		if (_isNull) {
			return new NullableCollatedStringValue(_collation);
		} else {
			return new NullableCollatedStringValue(_collation, _str.toLowerCase(_collation.getLocale()));
		}
	}

	@Override
	public NullableCollatedString toUpperCase() {
		if (_isNull) {
			return new NullableCollatedStringValue(_collation);
		} else {
			return new NullableCollatedStringValue(_collation, _str.toUpperCase(_collation.getLocale()));
		}
	}

	@Override
	public Comparator<NullableCollatedString> getComparator() {
		return new NullableCollatedStringComparator(_collation);
	}

	@Override
	public int hashCode() {
		if (_isNull) {
			return 0;
		} else {
			return _str.hashCode();
		}
	}

	/**
	 * Test another object for equality to the current object.
	 * @return {@code true} if the other object should be considered equal.
	 *  
	 * This will not return {@code true} if {@code obj} is the same as {@code this}
	 *  and the object is {@code null}.
	 */
	@Override
	public boolean equals(Object obj) {
		if (_isNull || obj == null) {
			return false;
		} else if (obj instanceof NullableCollatedString other) {
			return isEqual(other);
		} else if (obj instanceof String other) {
			return isContentEqual(other);
		} else {
			return false;
		}
	}

	/**
	 * Return a string representation of the current object.
	 * @return Either "null" or the string itself.
	 */
	@Override
	public String toString() {
		if (_isNull) {
			return STR_NULL;
		} else {
			return _str;
		}
	}
}
