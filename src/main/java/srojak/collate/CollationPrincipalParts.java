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
package srojak.collate;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 * @author Stephen
 *
 */
public interface CollationPrincipalParts {
	
	/**
	 * Get the locale to use for these data.
	 * @return The selected locale.
	 */
	Locale getLocale();
	
	/**
	 * Get the character set that applies to these data.
	 * @return The selected character set.
	 */
	Charset getCharset();
	
	/**
	 * Get the comparison rule that applies to these data.
	 * @return The {@code CollationComparisonRule} to use for the data.
	 */
	CollationComparisonRule getComparisonRule();
}
