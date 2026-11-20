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
package srojak.sql.mapobj;

import java.util.Objects;

import srojak.core.keys.ClassKey;
import srojak.sql.MappedObjectException;
import srojak.sql.impl.MappedObjectStore;
import srojak.sql.impl.ObjectMappingCarrier;

/**
 * @author Stephen
 *
 */
public class MappedObjectCatalog {

	public static boolean isKnown(Class<?> classMapped) {
		Objects.requireNonNull(classMapped, "classMapped");
		ClassKey key = new ClassKey(classMapped);
		return MappedObjectStore.isKnown(key);
	}
	
	public static ObjectMapping getMappingFor(Class<?> classMapped) 
			throws MappedObjectException {
		Objects.requireNonNull(classMapped, "classMapped");
		ClassKey key = new ClassKey(classMapped);
		ObjectMapping mapping = MappedObjectStore.getObjectMappingFor(key);
		if (mapping == null) {
			mapping = new ObjectMappingCarrier(key.getReferencedClass());
			mapping.process();
			MappedObjectStore.register(key, mapping);
		}
		return mapping;
	}
}
