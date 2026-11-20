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
package srojak.sql.mapobj.factories;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephen
 *
 */
public class MapObjPropertyFactoryStore {

	
	private static final Map<Class<?>, MappedObjectPropertyFactory> _mapFactories
		= new HashMap<Class<?>, MappedObjectPropertyFactory>();
	
	public static void initialize() {
		if (_mapFactories.isEmpty()) {
			// TODO: implement self-load
			MappedObjectPropertyFactory[] factories = {
					new NullableBooleanPropertyFactory(),
					new NullableIntPropertyFactory(),
					new NullableSmallintPropertyFactory(),
					new NullableTinyintPropertyFactory(),
					new NullableLongPropertyFactory(),
					new NullableDatePropertyFactory(),
					new NullableDateTimePropertyFactory(),
					new NullableCollatedStringPropertyFactory(),
			};
			
			for (MappedObjectPropertyFactory pf : factories) {
				_mapFactories.put(pf.forClass().getReferencedClass(), pf);
			}		
		}
	}
	
	public static MappedObjectPropertyFactory get(Class<?> classFor) {
		return _mapFactories.get(classFor);
	}
}
