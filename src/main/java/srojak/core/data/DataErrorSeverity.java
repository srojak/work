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
package srojak.core.data;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public enum DataErrorSeverity {

	FATAL(10),
	ERROR(5),
	WARN(3),
	INFO(1);
	
	private final int _ordinal;
	
	private DataErrorSeverity(int ordinal) {
		_ordinal = ordinal;
	}
	
	public int getOrdinal() {
		return _ordinal;
	}
	
	public boolean isSeverityAtLeast(DataErrorSeverity severityRef) {
		Objects.requireNonNull(severityRef, "severityRef");
		return _ordinal >= severityRef._ordinal;
	}
}
