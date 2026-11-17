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
import srojak.core.Equatable;
import srojak.numerics.NullableValue;
import srojak.numerics.nullable.NullableBoolean;

/**
 * @author Stephen
 *
 */
public sealed interface NullableCollatedString
		extends NullableValue, Equatable<NullableCollatedString>,
			Comparable<NullableCollatedString> 
		permits NullableCollatedStringBox {
	
	String value();
	int length();
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
	Collation getCollation();
	NullableCollatedString substring(int beginIndex);
	NullableCollatedString substring(int beginIndex, int endIndex);
	NullableCollatedString replace(char oldChar, char newChar);
	NullableCollatedString replaceAll(String regex, CollationRegexOptions options, String replacement);
	NullableCollatedString replaceFirst(String regex, CollationRegexOptions options, String replacement);
	NullableCollatedString toLowerCase();
	NullableCollatedString toUpperCase();
	
	public static NullableCollatedString from(Collation collation, String strContent) {
		Objects.requireNonNull(collation, "collation");
		return new NullableCollatedStringBox(collation, strContent);
	}
	
	public static NullableCollatedString from(Collation collation, StringBuilder sbContent) {
		Objects.requireNonNull(collation, "collation");
		return new NullableCollatedStringBox(collation, sbContent);
	}
	
	/**
	 * Create a nullable object representing {@code null}.
	 * @param collation The collation to associate with the object.
	 * @return The {@code NullableCollatedString} object representing {@code null}.
	 */
	public static NullableCollatedString fromNull(Collation collation) {
		Objects.requireNonNull(collation, "collation");
		return new NullableCollatedStringBox(collation);
	}
	
	public static NullableCollatedString join(Collation collation, CharSequence delimiter, CharSequence ... sequences) {
		Objects.requireNonNull(collation, "collation");
		Objects.requireNonNull(delimiter, "delimiter");
		Objects.requireNonNull(sequences, "sequences");
		return new NullableCollatedStringBox(collation, String.join(delimiter, sequences));
	}
	
	public static NullableCollatedString join(Collation collation, CharSequence delimiter,
			Iterable<? extends CharSequence> sequences) {
		Objects.requireNonNull(collation, "collation");
		Objects.requireNonNull(delimiter, "delimiter");
		Objects.requireNonNull(sequences, "sequences");
		return new NullableCollatedStringBox(collation, String.join(delimiter, sequences));
	}
	
	public static NullableCollatedString format(Collation collation, String format, Object ... args) {
		Objects.requireNonNull(collation, "collation");
		Objects.requireNonNull(format, "format");
		Objects.requireNonNull(args, "args");
		return new NullableCollatedStringBox(collation, String.format(collation.getLocale(), format, args));
	}
	
	public static NullableCollatedString decodeFrom(Collation collation, byte[] bytes, int offset, int length) {
		Objects.requireNonNull(collation, "collation");
		return new NullableCollatedStringBox(collation, bytes, offset, length);
	}
}
