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

import java.util.function.Supplier;

import srojak.core.data.DataPassThrough;

/**
 * @author Stephen
 *
 */
public interface ObservationCollectorCore 
		extends ObservationCommonCollector, TraceCollectorWithClass {
	
	/**
	 * Write a message at a given observation level without location information.
	 * @param level The observation level.
	 * @param strText The text of the message.
	 */
	void recordNoLocation(ObsLevel level, String strText);
	
	/**
	 * Write a message at an observation level without location information.
	 * @param level The level at which to write the message.
	 * @param message The supplier of the message.
	 */
	void recordNoLocation(ObsLevel level, Supplier<String> message);
	
	/**
	 * Create a passthrough list for use with a call to write to this debug switch.
	 * @param objects A set of objects to include in the passthrough list.
	 * @return A passthrough list containing the input objects, in order.
	 * 
	 * If you encounter errors like this:
	 * Local variable N defined in an enclosing scope must be final or effectively final
	 * The problem can be overcome through use of a passthrough list.
	 */
	static DataPassThrough passThrough(Object... objects) {
		return DataPassThrough.createFrom(objects);	
	}
}
