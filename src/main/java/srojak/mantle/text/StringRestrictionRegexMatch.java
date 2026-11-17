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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Stephen
 *
 */
public class StringRestrictionRegexMatch 
		extends StringRestrictionCommonBase {
	private final Pattern _pattern;

	public StringRestrictionRegexMatch(Pattern pattern) {
		super("matches pattern");
		Objects.requireNonNull(pattern, "pattern");
		_pattern = pattern;
	}

	@Override
	public boolean validate(String strText) {
		Matcher matcher = _pattern.matcher(strText);
		return matcher.matches();
	}

}
