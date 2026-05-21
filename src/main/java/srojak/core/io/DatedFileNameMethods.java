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
package srojak.core.io;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author Stephen
 *
 * Methods to form file names containing dates.
 */
public class DatedFileNameMethods {
	
	public static final DateTimeFormatter FORMATTER_DATE_AND_TIME;
	public static final DateTimeFormatter FORMATTER_DATE;
	
	static {
		FORMATTER_DATE_AND_TIME = DateTimeFormatter.ofPattern("yyMMdd-HHmmss");
		FORMATTER_DATE = DateTimeFormatter.ofPattern("yyMMdd");
	}
	
	/**
	 * Form a file name using the creation time already obtained.
	 * @param strPrefix The file name prefix.
	 * @param strExtension The file name extension.
	 * @param bUseDateAndTime If {@code true}, the time as well as the date will be in the file name.
	 * @param dtCreate The creation time.
	 * @return A string containing the file name.
	 */
	public static String formFileName(String strPrefix, String strExtension,
			boolean bUseDateAndTime, LocalDateTime dtCreate)
	{
		Objects.requireNonNull(strPrefix, "strPrefix");
		Objects.requireNonNull(strExtension, "strExtension");
		if (strExtension.isEmpty() || strExtension.isBlank()) {
			throw new IllegalArgumentException("strExtension is empty");
		}
		StringBuilder sbName = new StringBuilder(strPrefix);
		if (bUseDateAndTime) {
			FORMATTER_DATE_AND_TIME.formatTo(dtCreate, sbName);
		} else {
			FORMATTER_DATE.formatTo(dtCreate, sbName);
		}
		if (!strExtension.startsWith(".")) {
			sbName.append('.');
		}
		sbName.append(strExtension);
		return sbName.toString();
	}
	
	/**
	 * Form a file name using the current date and time.
	 * @param strPrefix The file name prefix.
	 * @param strExtension The file name extension.
	 * @param bUseDateAndTime If {@code true}, the time as well as the date will be in the file name.
	 * @return A string containing the file name.
	 */
	public static String formFileName(String strPrefix, String strExtension,
			boolean bUseDateAndTime) {
		return formFileName(strPrefix, strExtension, bUseDateAndTime, LocalDateTime.now());
	}
}
