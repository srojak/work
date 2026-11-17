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
package srojak.collate.impl;

import java.nio.charset.Charset;
import java.text.Collator;
import java.util.Locale;
import java.util.Objects;

import srojak.collate.CollationComparisonRule;
import srojak.collate.CollationDefinition;
import srojak.core.excepts.InvalidOperationException;

/**
 * @author Stephen
 *
 */
public final class CollationDefinitionBox 
		implements CollationDefinition {
	protected final Locale _locale;
	protected final Charset _charset;
	protected final CollationComparisonRule _ruleCompar;
	
	public CollationDefinitionBox(Locale locale, Charset charset, 
			CollationComparisonRule ruleComparison) {
		Objects.requireNonNull(locale, "locale");
		Objects.requireNonNull(charset, "charset");
		Objects.requireNonNull(ruleComparison, "ruleComparison");
		_locale = locale;
		_charset = charset;
		_ruleCompar = ruleComparison;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public Charset getCharset() {
		return _charset;
	}

	@Override
	public CollationComparisonRule getComparisonRule() {
		return _ruleCompar;
	}

	@Override
	public boolean isEqual(CollationDefinition obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (_locale == obj.getLocale()
				&& _charset == obj.getCharset()
				&& _ruleCompar == obj.getComparisonRule()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collator createCollator() {
		switch (_ruleCompar.basis()) {
			case COLLATOR:
			case CODE_PAGE:
				break;
				
			default:
				throw new InvalidOperationException("no collator defined for this comparison");
		}
		Collator collator = Collator.getInstance(_locale);
		if (_ruleCompar.isIdentical()) {
			collator.setStrength(Collator.IDENTICAL);
		} else if (_ruleCompar.isCaseSensitive()) {
			collator.setStrength(Collator.TERTIARY);
		} else if (_ruleCompar.isAccentSensitive()) {
			collator.setStrength(Collator.SECONDARY);
		} else {
			collator.setStrength(Collator.PRIMARY);
		}
		return collator;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_locale, _charset, _ruleCompar);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CollationDefinition other) {
			return isEqual(other);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("(");
		sb.append(_charset.name());
		sb.append(", ");
		sb.append(_ruleCompar.getName());
		sb.append(')');
		return sb.toString();
	}
}
