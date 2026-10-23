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

import srojak.core.observe.code.CodeValue;
import srojak.core.observe.functional.CodeValuesObservationBuilder;

/**
 * @author Stephen
 *
 * Methods providing tracing with class identification.
 * 
 * Note that for methods taking an {@code objTrace} parameter, this parameter may be {@code null}.
 * In that case, the class for the object will be {@code NullObject}.
 */
public interface TraceCollectorWithClass {
	
	/**
	 * Write a trace message to enter a method.
	 * @param level The trace level at which to write the message.
	 * @param classTrace The class of the method writing the message.
	 */
	void traceEnter(TraceLevel level, Class<?> classTrace);
	
	/**
	 * Write a trace message to enter a method.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 */
	void traceEnterObject(TraceLevel level, Object objTrace);

	/**
	 * Write a trace message to enter a method with additional content.
	 * @param level The trace level at which to write the message.
	 * @param classTrace The class of the method writing the message.
	 * @param strMessage The text of the message.
	 */
	void traceEnter(TraceLevel level, Class<?> classTrace, String strMessage);

	/**
	 * Write a trace message to enter a method with additional content.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 * @param strMessage The text of the message.
	 */
	void traceEnterObject(TraceLevel level, Object objTrace, String strMessage);

	void traceTextEnter(TraceLevel level, Class<?> classTrace, TextObservation observation);
	
	void traceTextEnterObject(TraceLevel level, Object objTrace, TextObservation observation);
	
	void traceCodeEnter(TraceLevel level, Class<?> classTrace, CodeValuesObservationBuilder builderObs);
	
	void traceCodeEnterObject(TraceLevel level, Object objTrace, CodeValuesObservationBuilder builderObs);

	/**
	 * Write a trace message to return from a method.
	 * @param level The trace level at which to write the message.
	 * @param classTrace The class of the method writing the message.
	 */
	void traceReturn(TraceLevel level, Class<?> classTrace);
	
	/**
	 * Write a trace message to return from a method.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 */
	void traceReturnObject(TraceLevel level, Object objTrace);
	
	/**
	 * Write a trace message to return from a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param classTrace The class of the method writing the message.
	 * @param strMessage The text of the message.
	 */
	void traceReturn(TraceLevel level, Class<?> classTrace, String strMessage);
	
	/**
	 * Write a trace message to return from a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 * @param strMessage The text of the message.
	 */
	void traceReturnObject(TraceLevel level, Object objTrace, String strMessage);
	
	/**
	 * Write a trace message to return from a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 * @param message The supplier of the additional text.
	 */
	void traceTextReturn(TraceLevel level, Class<?> classTrace, TextObservation observation);
	
	/**
	 * Write a trace message to return from a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 * @param listPassThrough The observable passthrough list carrying additional data.
	 * @param message The function to write the message.
	 */
	void traceTextReturnObject(TraceLevel level, Object objTrace, TextObservation observation);
	
	void traceCodeReturn(TraceLevel level, Class<?> classTrace, CodeValuesObservationBuilder builderObs);
	
	void traceCodeReturnObject(TraceLevel level, Object objTrace, CodeValuesObservationBuilder builderObs);

	void traceReturnValue(TraceLevel level, Class<?> classTrace, CodeValue cdValue);

	/**
	 * Write a trace message to return from a method with the value being returned.
	 * @param level The trace level at which to write the message.
	 * @param objTrace The object writing the message.
	 * @param cdValue The captured value being returned.
	 */
	void traceReturnObjectValue(TraceLevel level, Object objTrace, CodeValue cdValue);
	
	void traceThrowing(TraceLevel level, Class<?> classTrace, ObservedActivity activity, Exception exc);
	
	void traceObjectThrowing(TraceLevel level, Object objTrace, ObservedActivity activity, Exception exc);
}
