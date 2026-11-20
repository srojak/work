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
package srojak.mantle.nvalues;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class NamedPrimitiveValueEncoder 
		extends AbstractNameValueEncoder {

	/**
	 * 
	 */
	public NamedPrimitiveValueEncoder() {
		super();
	}
	
	public NameAndIntList makeListOfStaticIntFields(Class<?> classSource) {
		Objects.requireNonNull(classSource, "classSource");
		List<Field> listFields = getStaticFieldsOfType(classSource, int.class);
		NamedIntValueList list = new NamedIntValueList(listFields.size());
		visitFields(listFields, (n, v) -> {
			int value = (int) v;
			NamedIntValue nvalue = new NamedIntValue(n, value);
			list.add(nvalue);
		});
		return list;
	}
	
	public NameAndByteList makeListOfStaticByteFields(Class<?> classSource) {
		Objects.requireNonNull(classSource, "classSource");
		List<Field> listFields = getStaticFieldsOfType(classSource, byte.class);
		NamedByteValueList list = new NamedByteValueList(listFields.size());
		visitFields(listFields, (n, v) -> {
			byte value = (byte) v;
			NamedByteValue nvalue = new NamedByteValue(n, value);
			list.add(nvalue);
		});
		return list;
	}
}
