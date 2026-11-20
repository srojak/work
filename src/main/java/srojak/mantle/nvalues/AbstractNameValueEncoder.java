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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import srojak.core.SymbolicName;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservedActivity;
import srojak.core.reflect.ReflectionMethods;

/**
 * @author Stephen
 *
 */
public abstract class AbstractNameValueEncoder {
	
	public AbstractNameValueEncoder() {
		
	}
	
	protected List<Field> getStaticFieldsOfType(Class<?> classSource, Class<?> classData) {
		Objects.requireNonNull(classSource, "classSource");
		Objects.requireNonNull(classData, "classData");
		List<Field> list = new LinkedList<Field>();
		Field[] fields = classSource.getDeclaredFields();
		for (Field field : fields) {
			if (!ReflectionMethods.isPublic(field) || !ReflectionMethods.isStatic(field)) {
				continue;
			}
			if (field.getType().equals(classData)) {
				list.add(field);
			}
		}
		return list;
	}
	
	protected void visitFields(List<Field> listFields, BiConsumer<String, Object> consumer) {
		Objects.requireNonNull(listFields, "listFields");
		Objects.requireNonNull(consumer, "consumer");
		Iterator<Field> iterator = listFields.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			String strName = field.getName();
			SymbolicName annotSymbolic = field.getAnnotation(SymbolicName.class);
			if (annotSymbolic != null) {
				strName = annotSymbolic.value();
			}
			Object objValue = null;
			try {
				objValue = field.get(null);
			} catch (IllegalArgumentException | IllegalAccessException exc) {
				CoreObservers.OBS_LOG.recordThrowable(ObsLevel.ERROR, ObservedActivity.INIT, exc, false);
				continue;
			}
			consumer.accept(strName, objValue);
		}
	}
}
