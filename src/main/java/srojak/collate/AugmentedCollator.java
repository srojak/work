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
import java.text.CollationKey;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import srojak.core.Equatable;

/**
 * @author Stephen
 *
 * @see https://learn.microsoft.com/en-us/sql/relational-databases/collations/collation-and-unicode-support
 * 
 * @deprecated Use {@code Collation}.
 */
@Deprecated(forRemoval = true)
public interface AugmentedCollator
		extends Equatable<AugmentedCollator> {
	
	/**
	 * Get the locale to use for these data.
	 * @return The selected locale.
	 */
	Locale getLocale();
	
	/**
	 * Get the collator to use for these data.
	 * @return The selected collator.
	 */
	Collator getCollator();
	
	/**
	 * Get the character set that applies to these data.
	 * @return The selected character set.
	 */
	Charset getCharset();
	
	/**
	 * Is this string case sensitive?
	 * @return {@code true} if a string using this collator should be processed with case sensitivity.
	 */
	boolean isCaseSensitive();
	
	/**
	 * Should collation methods be used where possible?
	 * @return {@code true} if collation methods should be used in preference to {@code String} class methods.
	 */
	boolean useCollatorMethods();
	
	/**
	 * Is the collator a {@code RuleBasedCollator}?
	 * @return {Code true} if the collator is an instance of the {@code RuleBasedCollator} class.
	 * @see RuleBasedCollator
	 */
	boolean isRuleBasedCollator();
	
	/**
	 * Compare two strings using the comparison rules.
	 * @param source The first string to compare.
	 * @param target The second string to compare.
     * @return The value {@code 0} if the second string is equal to the first string; 
     * 	a value less than {@code 0} if the first string is lexicographically less than the second string;
     *  and a value greater than {@code 0} if the first string is lexicographically greater than the second string.
	 */
	int compare(String source, String target);
	
	/**
	 * Get a reusable {@code CollationKey} for a string.
	 * @param source The string for which to create a key.
	 * @return A {@code CollationKey} for the string.
	 * @see Collator#getCollationKey(String)
	 */
	CollationKey getCollationKey(String source);
	
	/**
	 * Compile a regular expression string using the defined case sensitivity.
	 * @param regex The regular expression string to compile.
	 * @return The compiled pattern.
     * @throws  PatternSyntaxException If the syntax of the argument is invalid
	 */
	Pattern compile(String regex);
	
	/**
	 * Compile a regular expression string using the defined case sensitivity and the
	 * 	@{code Pattern.MULTILINE} flag.
	 * @param regex The regular expression string to compile.
	 * @return The compiled pattern.
     * @throws  PatternSyntaxException If the syntax of the argument is invalid
     * @see Pattern#MULTILINE
	 */
	Pattern compileMultiLine(String regex);
	
	/**
	 * Compare partial regions in two strings.
	 * @param source The first string to compare.
	 * @param toffset The starting offset in the first string.
	 * @param other The second string to compare.
	 * @param ooffset The starting offset in the second string.
	 * @param len The length of the regions.
	 * @return {@code true} if the regions match using the rules for this object.
	 */
	boolean regionMatches(String source, int toffset,
            String other, int ooffset, int len);
	
	/**
	 * Find the next occurrence of a string in another string.
	 * @param source The string to search through.
	 * @param find The string to find.
	 * @param fromIndex The starting index in {@code source}
     * @return the index of the first occurrence of {@code find} in {@code source},
     *          or {@code -1} if there is no such occurrence.
	 */
	int indexOf(String source, String find, int fromIndex);
}
