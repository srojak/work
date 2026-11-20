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
public class NamedStringValueEncoder
		extends AbstractNameValueEncoder {

	/**
	 * 
	 */
	public NamedStringValueEncoder() {
		super();
	}

	public NameAndStringList makeListOfStaticFields(Class<?> classSource) {
		Objects.requireNonNull(classSource, "classSource");
		List<Field> listFields = getStaticFieldsOfType(classSource, String.class);
		NamedStringValueList list = new NamedStringValueList(listFields.size());
		visitFields(listFields, (n, v) -> {
			String strValue = (String) v;
			NamedStringValue nvalue = new NamedStringValue(n, strValue);
			list.add(nvalue);
		});
		return list;
	}
}
