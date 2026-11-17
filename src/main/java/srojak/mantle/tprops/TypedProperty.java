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
package srojak.mantle.tprops;

import srojak.core.NameEquatable;
import srojak.core.keys.ClassKey;
import srojak.core.logic.FlagsShortTest;

/**
 * @author Stephen
 *
 */
public interface TypedProperty 
		extends NameEquatable {

	ClassKey propertyClass();
	FlagsShortTest flags();
	boolean isRequired();
	boolean isSet();
	boolean allowsNull();
	String valueAsString();
	TypedProperty copy();
	
	static final short FLAGS_IS_REQUIRED = 0x1;
	static final short FLAGS_IS_SET = 0x2;
	static final short FLAGS_BOOL_VALUE = 0x4;
	static final short FLAGS_ALLOWS_NULL = 0x8;
}
