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

import java.text.CollationKey;
import java.text.Collator;

/**
 * @author Stephen
 *
 */
public interface CollationUsingCollator 
		extends Collation {
	
	/**
	 * Get the collator to use for these data.
	 * @return The selected collator.
	 */
	Collator getCollator();
	
	/**
	 * Get a reusable {@code CollationKey} for a string.
	 * @param source The string for which to create a key.
	 * @return A {@code CollationKey} for the string.
	 * @see Collator#getCollationKey(String)
	 */
	CollationKey getCollationKey(String source);
}
