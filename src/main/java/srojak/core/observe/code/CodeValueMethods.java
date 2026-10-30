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
package srojak.core.observe.code;

import java.lang.invoke.MethodType;
import java.text.DecimalFormat;

import srojak.core.observe.ObservationCommon;
import srojak.core.observe.code.values.CodeValueBoolean;
import srojak.core.observe.code.values.CodeValueDouble;
import srojak.core.observe.code.values.CodeValueInt;
import srojak.core.observe.code.values.CodeValueLong;
import srojak.core.observe.code.values.CodeValueMethodType;
import srojak.core.observe.code.values.CodeValueObject;
import srojak.core.observe.code.values.CodeValueString;

/**
 * @author Stephen
 *
 * Methods for callers to capture values for observations.
 */
public class CodeValueMethods
		implements ObservationCommon {
	
	public static final String NAME_RETURN = "*return";

	public static CodeValue capture(String strName, boolean value) {
		return new CodeValueBoolean(strName, value);
	}

	public static CodeValue capture(String strName, int value) {
		return new CodeValueInt(strName, FORMAT_COMMAS, value);
	}

	public static CodeValue capture(String strName, int value, DecimalFormat formatDecimal) {
		return new CodeValueInt(strName, formatDecimal, value);
	}

	public static CodeValue capture(String strName, long value) {
		return new CodeValueLong(strName, FORMAT_COMMAS, value);
	}

	public static CodeValue capture(String strName, long value, DecimalFormat formatDecimal) {
		return new CodeValueLong(strName, formatDecimal, value);
	}

	public static CodeValue capture(String strName, double value) {
		return new CodeValueDouble(strName, FORMAT_3_DEC, value);
	}

	public static CodeValue capture(String strName, double value, DecimalFormat formatDecimal) {
		return new CodeValueDouble(strName, formatDecimal, value);
	}

	public static CodeValue capture(String strName, String value) {
		return new CodeValueString(strName, value);
	}
	
	public static CodeValue capture(String strName, MethodType typeMethod) {
		return new CodeValueMethodType(strName, typeMethod);
	}
	
	public static CodeValue captureObject(String strName, Object value) {
		return new CodeValueObject(strName, value);
	}
	
	public static CodeValue capture(String strName, Class<?> classValue, Object value) {
		if (value == null) {
			return new CodeValueObject(strName, classValue);
		} else {
			return new CodeValueObject(strName, value);
		}
	}
}
