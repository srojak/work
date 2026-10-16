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
package srojak.core.observe.impl;

import srojak.core.observe.ObservationCommon;

/**
 * @author Stephen
 *
 */
public class ObservationCommonText {

	public static final String ENTERING = "entering";
	public static final String RETURNING = "returning";
	public static final String VALUE = " value = ";
	
	public static StringBuilder startTraceEnter() {
		StringBuilder sb = new StringBuilder(ENTERING);
		return sb;
	}
	
	public static StringBuilder startTraceReturn() {
		StringBuilder sb = new StringBuilder(RETURNING);
		return sb;
	}
	
	public static StringBuilder startTraceEnter(Class<?> classCaller, boolean bWriteFullName) {
		StringBuilder sb = new StringBuilder("class ");
		if (bWriteFullName) {
			sb.append(classCaller.getName());
		} else {
			sb.append(classCaller.getSimpleName());
		}
		sb.append(' ');
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
		sb.append(' ');
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
		sb.append(' ');
		sb.append(RETURNING);
		sb.append(VALUE);
		if (objValue == null) {
			sb.append(ObservationCommon.STR_NULL);
		} else {
			sb.append(objValue);
		}
		return sb;
	}
}
