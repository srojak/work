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
package srojak.core.observe;

import java.util.Objects;

import srojak.core.collections.CommonCollectionSize;
import srojak.core.impl.ObservablePassThroughArray;

/**
 * @author Stephen
 *
 * The public interface to a passthrough list.
 */
public interface ObsPassThroughList 
	extends CommonCollectionSize {
	
	/**
	 * Gets a value from the list.
	 * @param index The index for the value to get.
	 * @return The String at the indexed location in the collection.
	 */
	String get(int index);

	
	/**
	 * Create a passthrough list for use with a call to write to a writer.
	 * @param strings A set of strings to include in the passthrough list.
	 * @return A passthrough list containing the input strings, in order.
	 * 
	 * If you encounter errors like this:
	 * Local variable N defined in an enclosing scope must be final or effectively final
	 * The problem can be overcome through use of a passthrough list.
	 */
	static ObsPassThroughList create(String... strings) {
		return new ObservablePassThroughArray(strings);
	}
	
	static ObsPassThroughList createFrom(String[] strings) {
		Objects.requireNonNull(strings);
		return new ObservablePassThroughArray(strings);		
	}
}
