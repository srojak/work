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

import srojak.collate.Collation;
import srojak.collate.CollationRegexOptions;
import srojak.collate.HasCollation;
import srojak.numerics.NullableComparable;
import srojak.numerics.OrderedComparison;
import srojak.numerics.nullable.NullableBoolean;

/**
 * @author Stephen
 *
 */
public sealed interface NullableCollatedString
		extends NullableComparable<NullableCollatedString>, HasCollation
		permits NullableCollatedStringValue {
	
	String value();
	int length();

	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param strValue The value to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, String strValue);

	boolean isEmpty();
	boolean hasSameCollationAs(NullableCollatedString other);
	boolean hasSameCharsetAs(NullableCollatedString other);
	char charAt(int index);
	int codePointAt(int index);
	int indexOf(String strFind);
	int indexOf(String strFind, int fromIndex);
	String[] split(String regex);
	String[] split(String regex, int limit);
	NullableBoolean matches(String regex, CollationRegexOptions options);
	NullableBoolean startsWith(String prefix);
	NullableBoolean startsWith(String prefix, int toffset);
	NullableBoolean endsWith(String suffix);
	NullableCollatedString substring(int beginIndex);
	NullableCollatedString substring(int beginIndex, int endIndex);
	NullableCollatedString replace(char oldChar, char newChar);
	NullableCollatedString replaceAll(String regex, CollationRegexOptions options, String replacement);
	NullableCollatedString replaceFirst(String regex, CollationRegexOptions options, String replacement);
	NullableCollatedString toLowerCase();
	NullableCollatedString toUpperCase();
	
	public static NullableCollatedString from(Collation collation, String strContent) {
		Objects.requireNonNull(collation, "collation");
		return new NullableCollatedStringValue(collation, strContent);
	}
	
	public static NullableCollatedString from(Collation collation, StringBuilder sbContent) {
		Objects.requireNonNull(collation, "collation");
		return new NullableCollatedStringValue(collation, sbContent);
	}
	
	/**
	 * Create a nullable object representing {@code null}.
	 * @param collation The collation to associate with the object.
	 * @return The {@code NullableCollatedString} object representing {@code null}.
	 */
	public static NullableCollatedString fromNull(Collation collation) {
		Objects.requireNonNull(collation, "collation");
		return new NullableCollatedStringValue(collation);
	}
	
	public static NullableCollatedString join(Collation collation, CharSequence delimiter, CharSequence ... sequences) {
		Objects.requireNonNull(collation, "collation");
		Objects.requireNonNull(delimiter, "delimiter");
		Objects.requireNonNull(sequences, "sequences");
		return new NullableCollatedStringValue(collation, String.join(delimiter, sequences));
	}
	
	public static NullableCollatedString join(Collation collation, CharSequence delimiter,
			Iterable<? extends CharSequence> sequences) {
		Objects.requireNonNull(collation, "collation");
		Objects.requireNonNull(delimiter, "delimiter");
		Objects.requireNonNull(sequences, "sequences");
		return new NullableCollatedStringValue(collation, String.join(delimiter, sequences));
	}
	
	public static NullableCollatedString format(Collation collation, String format, Object ... args) {
		Objects.requireNonNull(collation, "collation");
		Objects.requireNonNull(format, "format");
		Objects.requireNonNull(args, "args");
		return new NullableCollatedStringValue(collation, String.format(collation.getLocale(), format, args));
	}
	
	public static NullableCollatedString decodeFrom(Collation collation, byte[] bytes, int offset, int length) {
		Objects.requireNonNull(collation, "collation");
		return new NullableCollatedStringValue(collation, bytes, offset, length);
	}
}
