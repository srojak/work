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
package srojak.core.backplane;

/**
 * @author Stephen
 *
 */
public class ObservationCommonText {

	static final String ENTERING = " entering";
	static final String RETURNING = " returning";
	static final String VALUE = " value = ";
	static final String STR_NULL = "(null)";
	
	public static StringBuilder startTraceEnter(Class<?> classCaller, boolean bWriteFullName) {
		StringBuilder sb = new StringBuilder("class ");
		if (bWriteFullName) {
			sb.append(classCaller.getName());
		} else {
			sb.append(classCaller.getSimpleName());
		}
		sb.append(ENTERING);
		return sb;
	}
	
	public static StringBuilder startTraceReturn(Class<?> classCaller, boolean bWriteFullName) {
		StringBuilder sb = new StringBuilder("class ");
		if (bWriteFullName) {
			sb.append(classCaller.getName());
		} else {
			sb.append(classCaller.getSimpleName());
		}
		sb.append(RETURNING);
		return sb;
	}
	
	public static StringBuilder startTraceReturnValue(Class<?> classCaller, boolean bWriteFullName, Object objValue) {
		StringBuilder sb = new StringBuilder("class ");
		if (bWriteFullName) {
			sb.append(classCaller.getName());
		} else {
			sb.append(classCaller.getSimpleName());
		}
		sb.append(RETURNING);
		sb.append(VALUE);
		if (objValue == null) {
			sb.append(STR_NULL);
		} else {
			sb.append(objValue);
		}
		return sb;
	}
}
