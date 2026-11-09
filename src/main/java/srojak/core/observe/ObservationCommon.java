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
package srojak.core.observe;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

/**
 * @author Stephen
 *
 */
public interface ObservationCommon {

	static final String STR_NULL = "(null)";
	static final String STR_EMPTY = "";
	static final ThreadLocal<DateTimeFormatter> FORMAT_DATE_TIME_STAMP
		= ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"));
	static final ThreadLocal<DateTimeFormatter> FORMAT_TIME_STAMP
		= ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("HH:mm:ss"));
	static final ThreadLocal<DecimalFormat> FORMAT_COMMAS
		= ThreadLocal.withInitial(() -> new DecimalFormat("#,###"));
	static final ThreadLocal<DecimalFormat> FORMAT_3_DEC
		= ThreadLocal.withInitial(() -> new DecimalFormat("#.000"));
}
