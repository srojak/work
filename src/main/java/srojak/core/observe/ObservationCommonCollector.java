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

import srojak.core.data.DataPassThrough;
import srojak.core.observe.functional.CodeObservationBuilder;
import srojak.core.observe.functional.CodeObservationBuilderWithInt;
import srojak.core.observe.functional.CodeObservationBuilderWithPassThrough;
import srojak.core.result.XResult;

/**
 * @author Stephen
 *
 */
public interface ObservationCommonCollector 
		extends TraceCollector {
	/**
	 * Write a message at a given observation level.
	 * @param level The observation level.
	 * @param strText The text of the message.
	 */
	void record(ObsLevel level, String strText);
	
	/**
	 * Write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param message The supplier of the message.
	 */
	void record(ObsLevel level, Supplier<String> message);
	
	/**
	 * Write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param passThrough The observable passthrough list carrying additional data.
	 * @param message The function to write the message.
	 */
	void record(ObsLevel level, DataPassThrough passThrough, 
			Function<DataPassThrough, String> message);
	
	/**
	 * Record the class of a named object.
	 * This always writes the full class name.
	 * 
	 * If the value of {@code objValue} is {@code null}, 
	 * an instance of the {@code Unknown} class will be substituted.
	 * 
	 * @param level The level at which to write the message.
	 * @param strName The name of the object.
	 * @param objValue The object to describe.
	 */
	void recordClassOf(ObsLevel level, String strName, Object objValue);
	
	/**
	 * Record the class of a named object.
	 * This always writes the full class name.
	 * 
	 * If the value of {@code objValue} is {@code null}, 
	 * an instance of the {@code Unknown} class will be substituted.
	 * 
	 * @param level The level at which to write the message if the object is not {@code null}.
	 * @param levelIfNull The level at which to write the message if the object is {@code null}.
	 * @param strName The name of the object.
	 * @param objValue The object to describe.
	 */
	void recordClassOf(ObsLevel level, ObsLevel levelIfNull, String strName, Object objValue);
	
	/**
	 * Write a throwable object at an observation level.
	 * @param level The level at which to write the message.
	 * @param activity Identifies the activity that produced the exception.
	 * @param t The throwable whose contents are to be written.
	 * @param bShowStack If {@code true}, the stack trace can be written.
	 */
	void recordThrowable(ObsLevel level, ObservedActivity activity, Throwable t, boolean bShowStack);
	
	/**
	 * Record a result, which may contain a throwable.
	 * @param level The level at which to write the message.
	 * @param levelIfFaulted The level at which to write the message if the result contains a throwable.
	 * @param result The {@code result} to analyze
	 * @param bShowStack If {@code true}, the stack trace can be written.
	 */
	void recordResult(ObsLevel level, ObsLevel levelIfFaulted, XResult result, boolean bShowStack);
	
	/**
	 * Build a message and write it at a given observation level.
	 * @param level The observation level.
	 * @param builderMessage The callback to build the message.
	 */
	void observe(ObsLevel level, CodeObservationBuilder builderMessage);
	
	/**
	 * Build a message and write it at a given observation level.
	 * @param level The observation level.
	 * @param i The {@code int} value to pass through to the callback.
	 * @param builderMessage The callback to build the message.
	 */
	void observe(ObsLevel level, int i, CodeObservationBuilderWithInt builderMessage);
	
	/**
	 * Build and write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param passThrough The observation passthrough list carrying additional data.
	 * @param builderMessage The consumer to build the message.
	 */
	void observe(ObsLevel level, DataPassThrough passThrough,
			CodeObservationBuilderWithPassThrough builderMessage);
	
	/**
	 * Write a time stamp at a given observation level.
	 * @param level The observation level.
	 */
	void timeStamp(ObsLevel level);
	
	/**
	 * Write a diagnostic message.
	 * @param strText The text of the message.
	 */
	void recordDiagnostic(String strText);
	
	/**
	 * Create an observation collector at a given observation level.
	 * @param level The observation level.
	 * @return an observation collector, which will be active if {@code level} is a level
	 * 		for which the writer is writing.
	 */
	SingleObservationCollector createCollector(ObsLevel level);
}
