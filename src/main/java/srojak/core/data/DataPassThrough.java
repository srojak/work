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
package srojak.core.data;

import java.util.Objects;

import srojak.core.CommonCollectionSize;
import srojak.core.impl.DataPassThroughArray;

/**
 * @author Stephen
 *
 * The public interface to a passthrough list.
 */
public interface DataPassThrough 
	extends CommonCollectionSize {
	
	/**
	 * Gets a value from the list.
	 * @param index The index for the value to get.
	 * @return The object at the indexed location in the collection.
	 * @throws IndexOutOfBoundsException if index is outside valid range.
	 */
	Object get(int index);

	/**
	 * Gets a value from the list as a String.
	 * @param index The index for the value to get.
	 * @return The String at the indexed location in the collection.
	 * @throws IndexOutOfBoundsException if index is outside valid range.
	 */
	String getAsString(int index);
	
	/**
	 * Gets a value from the list.
	 * The caller is responsible for correctness of the type
	 * @param <T> The type of the value to return.
	 * @param index The index for the value to get.
	 * @return The value at the indexed location in the collection.
	 * @throws IndexOutOfBoundsException if index is outside valid range.
	 * @throws ClassCastException If requested type is incompatible with actual type.
	 */
	<T> T getAs(int index);
	
	/**
	 * Create a passthrough list for use with a call to write to a writer.
	 * @param objects A set of objects to include in the passthrough list.
	 * @return A passthrough list containing the input objects, in order.
	 * 
	 * If you encounter errors like this:
	 * Local variable N defined in an enclosing scope must be final or effectively final
	 * The problem can be overcome through use of a passthrough list.
	 */
	static DataPassThrough create(Object... objects) {
		return new DataPassThroughArray(objects);
	}
	
	static DataPassThrough createFrom(Object[] objects) {
		Objects.requireNonNull(objects);
		return new DataPassThroughArray(objects);		
	}
}
