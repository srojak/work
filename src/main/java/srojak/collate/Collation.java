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

import java.text.RuleBasedCollator;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import srojak.core.Equatable;
import srojak.core.KeyValue;
import srojak.core.logic.FlagsShortTest;

/**
 * @author Stephen
 *
 */
public interface Collation 
		extends CollationPrincipalParts, Equatable<Collation>,
			KeyValue<CollationDefinition, Collation> {
	
	/**
	 * Get the definition of this collation.
	 * @return The {@code CollationDefinition} that defines this collation.
	 */
	CollationDefinition key();
	
	FlagsShortTest flags();
	
	/**
	 * Is this string case sensitive?
	 * @return {@code true} if a string using this collator should be processed with case sensitivity.
	 */
	boolean isCaseSensitive();
	
	/**
	 * Is the character set Latin1?
	 * 
	 * Java likes Latin1 natively.
	 * Other character sets must be encoded and decoded.
	 * 
	 * @return {@code true} if the character set is Latin1.
	 */
	boolean isCharsetLatin1();
	
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
	 * Compile a regular expression string using the defined case sensitivity.
	 * @param regex The regular expression string to compile.
	 * @param options The options for the string against which the pattern will be applied.
	 * @return The compiled pattern.
     * @throws  PatternSyntaxException If the syntax of the argument is invalid
	 */
	Pattern compile(String regex, CollationRegexOptions options);
	
	/**
	 * Applies a compiled regular expression pattern and tests for matching.
	 * @param source The string to which to apply the pattern.
	 * @param pattern The compiled pattern, which should come from {@code compile()}.
	 * @return {@code true} if the pattern is matched in the string.
	 */
	boolean patternMatches(String source, Pattern pattern);
	
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
	
	static final short FLAGS_LATIN1 = 0x1;
}
