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
package srojak.collate.lib;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import srojak.collate.CodePointCompareSession;
import srojak.collate.Collation;
import srojak.collate.CollationComparisonRule;
import srojak.collate.CollationDefinition;
import srojak.collate.CollationRegexOptions;
import srojak.core.logic.FlagsShort;
import srojak.core.logic.FlagsShortTest;

/**
 * @author Stephen
 *
 */
public abstract class AbstractCollation
		implements Collation {
	protected final CollationDefinition _key;
	protected final FlagsShort _flags;
	
	protected AbstractCollation(CollationDefinition definition) {
		Objects.requireNonNull(definition, "definition");
		_key = definition;
		_flags = new FlagsShort();
		if (definition.getCharset().equals(StandardCharsets.ISO_8859_1)) {
			_flags.set(FLAGS_LATIN1);
		}
	}

	@Override
	public boolean isValueNull() {
		return false;
	}

	@Override
	public Collation value() {
		return this;
	}

	@Override
	public CollationDefinition key() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isKeyEqual(CollationDefinition key) {
		return (key == null) ? false : _key.equals(key);
	}

	@Override
	public boolean isEqual(Collation obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (_key.equals(obj.key())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Locale getLocale() {
		return _key.getLocale();
	}

	@Override
	public Charset getCharset() {
		return _key.getCharset();
	}

	@Override
	public CollationComparisonRule getComparisonRule() {
		return _key.getComparisonRule();
	}

	@Override
	public FlagsShortTest flags() {
		return _flags;
	}

	@Override
	public boolean isCaseSensitive() {
		return _key.getComparisonRule().isCaseSensitive();
	}

	@Override
	public boolean isCharsetLatin1() {
		return _flags.test(FLAGS_LATIN1);
	}

	@Override
	public boolean isRuleBasedCollator() {
		return false;
	}

	@Override
	public Pattern compile(String regex, CollationRegexOptions options) {
		Objects.requireNonNull(regex, "regex");
		Objects.requireNonNull(options, "options");
		int flags = options.isMultiline() ? Pattern.MULTILINE : 0;
		if (options.isUnicode()) {
			flags |= Pattern.UNICODE_CHARACTER_CLASS;
			if (!isCaseSensitive()) {
				flags |= Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE;
			}
		} else {
			if (!isCaseSensitive()) {
				flags |= Pattern.CASE_INSENSITIVE;
			}
		}
		return Pattern.compile(regex, flags);
	}

	@Override
	public boolean patternMatches(String source, Pattern pattern) {
		Objects.requireNonNull(source, "source");
		Objects.requireNonNull(pattern, "pattern");
		Matcher matcher = pattern.matcher(source);
		return matcher.matches();
	}

	@Override
	public boolean regionMatches(String source, int toffset, String other, int ooffset, int len) {
		Objects.requireNonNull(source, "source");
		Objects.requireNonNull(other, "other");
		return source.regionMatches(!isCaseSensitive(), toffset, other, ooffset, len);
	}
	
	protected int compareByCodePoints(String source, String target) {
		CodePointCompareSession session = new CodePointCompareSession(source, target);
		int result = session.compareFromIndex(0);
		if (result == 0) {
			result = session.applyLengthDifferences();
		}
		return result;
	}
	
	protected int indexByCodePoint(String source, String strFind, int fromIndex) {
		CodePointCompareSession session = new CodePointCompareSession(source, strFind);
		return session.findIndexFrom(fromIndex);
	}
	
	protected abstract int innerCompare(String source, String target);
	
	protected abstract int innerIndexOf(String source, String find, int fromIndex);

	@Override
	public final int compare(String source, String target) {
		Objects.requireNonNull(source, "source");
		Objects.requireNonNull(target, "target");
		return innerCompare(source, target);
	}

	@Override
	public final int indexOf(String source, String find, int fromIndex) {
		Objects.requireNonNull(source, "source");
		Objects.requireNonNull(find, "find");
		Objects.checkIndex(fromIndex, source.length());
		return innerIndexOf(source, find, fromIndex);
	}

	@Override
	public int hashCode() {
		return _key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Collation other) {
			return isEqual(other);
		} else if (obj instanceof CollationDefinition other) {
			return isKeyEqual(other);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("collation (");
		sb.append(_key);
		sb.append(')');
		return sb.toString();
	}

}
