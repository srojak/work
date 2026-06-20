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
package srojak.core.tools;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * @author Stephen
 *
 */
public class StringMethods {
	
	private static final String _strQuote = "\"";
	
	public static String encloseInQuotes(String strText) {
		Objects.requireNonNull(strText, "strText");
		return _strQuote + strText + _strQuote;
	}
	
	public static String makeIndent(int chars) {
		if (chars < 0) {
			throw new IllegalArgumentException("chars is negative");
		}
		if (chars == 0) {
			return "";
		} else {
			return(new String(" ").repeat(chars));
		}
	}
	
	public static String leftPadToSize(String strSource, char charPad, int szMinimum) {
		Objects.requireNonNull(strSource, "strSource");
		if (strSource.length() < szMinimum) {
			String strPad = String.valueOf(charPad).repeat(szMinimum - strSource.length());
			return strPad + strSource;
		} else {
			return strSource;
		}
	}
	
	public static boolean forAnyOfArray(String strText, BiPredicate<String, String> predicate, 
			String[] strValues) {
		Objects.requireNonNull(strText, "strText");
		Objects.requireNonNull(predicate, "predicate");
		Objects.requireNonNull(strValues, "strValues");
		for (String strValue : strValues) {
			if (strValue != null) {
				if (predicate.test(strText, strValue)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean forAllOfArray(String strText, BiPredicate<String, String> predicate, 
			String[] strValues) {
		Objects.requireNonNull(strText, "strText");
		Objects.requireNonNull(predicate, "predicate");
		Objects.requireNonNull(strValues, "strValues");
		for (String strValue : strValues) {
			if (strValue != null) {
				if (!predicate.test(strText, strValue)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean forAnyOf(String strText, BiPredicate<String, String> predicate,
			String... args) {
		return forAnyOfArray(strText, predicate, args);
	}
	
	public static boolean forAllOf(String strText, BiPredicate<String, String> predicate,
			String... args) {
		return forAllOfArray(strText, predicate, args);
	}
	
	public static boolean forAnyOf(String strText, BiPredicate<String, String> predicate,
			Collection<String> collValues) {
		Objects.requireNonNull(strText, "strText");
		Objects.requireNonNull(predicate, "predicate");
		Objects.requireNonNull(collValues, "collValues");
		for (String strValue : collValues) {
			if (predicate.test(strText, strValue)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean forAllOf(String strText, BiPredicate<String, String> predicate,
			Collection<String> collValues) {
		Objects.requireNonNull(strText, "strText");
		Objects.requireNonNull(predicate, "predicate");
		Objects.requireNonNull(collValues, "collValues");
		for (String strValue : collValues) {
			if (!predicate.test(strText, strValue)) {
				return false;
			}
		}
		return true;
	}
}
