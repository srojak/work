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
package srojak.sql.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import srojak.core.keys.ClassKey;
import srojak.sql.mapobj.ObjectMapping;

/**
 * @author Stephen
 *
 */
public class MappedObjectStore {

	private static final Map<ClassKey, ObjectMapping> _mapObjects
			= new HashMap<ClassKey, ObjectMapping>();
	
	public static boolean isKnown(ClassKey key) {
		return _mapObjects.containsKey(key);
	}
	
	public static ObjectMapping getObjectMappingFor(ClassKey key) {
		return _mapObjects.get(key);
	}
	
	public static void register(ClassKey key, ObjectMapping mapping) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(mapping, "mapping");
		_mapObjects.put(key, mapping);
	}
}
