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

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Stephen
 *
 */
public interface TraceCollector {
	
	/**
	 * Write a trace message to enter a method.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 */
	void writeTraceEnter(TraceLevel level, Object objTrace);
	
	/**
	 * Write a trace message to enter a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 * @param strMessage The text of the message.
	 */
	void writeTraceEnter(TraceLevel level, Object objTrace, String strMessage);
	
	/**
	 * Write a trace message to enter a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 * @param message The supplier of the additional text.
	 */
	void writeTraceEnter(TraceLevel level, Object objTrace, Supplier<String> message);
	
	/**
	 * Write a trace message to enter a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 * @param listPassThrough The observable passthrough list carrying additional data.
	 * @param message The function to write the message.
	 */
	void writeTraceEnter(TraceLevel level, Object objTrace, ObsPassThroughList listPassThrough, 
			Function<ObsPassThroughList, String> message);
	
	/**
	 * Write a trace message to return from a method.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 */
	void writeTraceReturn(TraceLevel level, Object objTrace);
	
	/**
	 * Write a trace message to return from a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 * @param strMessage The text of the message.
	 */
	void writeTraceReturn(TraceLevel level, Object objTrace, String strMessage);
	
	/**
	 * Write a trace message to return from a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 * @param message The supplier of the additional text.
	 */
	void writeTraceReturn(TraceLevel level, Object objTrace, Supplier<String> message);
	
	/**
	 * Write a trace message to return from a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 * @param listPassThrough The observable passthrough list carrying additional data.
	 * @param message The function to write the message.
	 */
	void writeTraceReturn(TraceLevel level, Object objTrace, ObsPassThroughList listPassThrough, 
			Function<ObsPassThroughList, String> message);

	/**
	 * Write a trace message to return from a method with the value being returned.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 * @param objValue The value being returned, which can be null.
	 */
	void writeTraceReturnValue(TraceLevel level, Object objTrace, Object objValue);
}
