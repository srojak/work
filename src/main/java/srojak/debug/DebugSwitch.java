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
package srojak.debug;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsPassThroughList;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationCommonWriter;
import srojak.core.observe.TraceLevel;
import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 * the readonly interface to a debug switch.
 */
public interface DebugSwitch 
		extends ObservationCommonWriter {
	
	/**
	 * Get the key for the debug switch.
	 * @return The key for this debug switch.
	 */
	DebugSwitchKey getKey();
	
	/**
	 * Get the class locator for the debug switch.
	 * @return The class locator from the key for this debug switch.
	 */
	PackageClassLocator getClassLocator();
	
	/**
	 * Get the observation level assigned to the debug switch.
	 * @return The current observation level assigned to the debug switch.
	 */
	ObsLevel getLevel();
	
	/**
	 * Get the state of the show source locations flag for the debug switch.
	 * @return The state of the show source locations flag for the debug switch.
	 */
	boolean showSourceLocations();
	
	/**
	 * Get the name of the control set tht defined this switch, if it was defined
	 * by a control set.
	 * @return The name of the defining control set, or an empty string.
	 */
	String getDefiningControlSet();
	
	/**
	 * Test if the observation level assigned to the debug switch is at or above
	 *   a given value.
	 * @param level The test level to compare to the level assigned to the debug switch.
	 * @return {@code true} if the debug switch level is at least the given level.
	 */
	boolean isLevelAtLeast(ObsLevel level);
	
	/**
	 * Write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param strMessage The text of the message.
	 */
	void write(ObsLevel level, String strMessage);
	
	/**
	 * Write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param message The supplier of the message.
	 */
	void write(ObsLevel level, Supplier<String> message);
	
	/**
	 * Write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param listPassThrough The observable passthrough list carrying additional data.
	 * @param message The function to write the message.
	 */
	void write(ObsLevel level, ObsPassThroughList listPassThrough, 
			Function<ObsPassThroughList, String> message);
	
	/**
	 * Write an exception at an observation level.
	 * @param level The level at which to write the message.
	 * @param exc The exception whose contents are to be written.
	 * @param bShowStack If {@code true}, the stack trace will be written.
	 */
	void writeException(ObsLevel level, Exception exc, boolean bShowStack);
	
	/**
	 * Write a trace message to enter a method.
	 * @param level The trace level at which to write the message.
	 */
	void writeTraceEnter(TraceLevel level);
	
	/**
	 * Write a trace message to enter a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param message The supplier of the additional text.
	 */
	void writeTraceEnter(TraceLevel level, Supplier<String> message);
	
	/**
	 * Write a trace message to enter a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param listPassThrough The observable passthrough list carrying additional data.
	 * @param message The function to write the message.
	 */
	void writeTraceEnter(TraceLevel level, ObsPassThroughList listPassThrough, 
			Function<ObsPassThroughList, String> message);
	
	/**
	 * Write a trace message to return from a method.
	 * @param level The trace level at which to write the message.
	 */
	void writeTraceReturn(TraceLevel level);
	
	/**
	 * Write a trace message to return from a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param message The supplier of the additional text.
	 */
	void writeTraceReturn(TraceLevel level, Supplier<String> message);
	
	/**
	 * Write a trace message to return from a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param listPassThrough The observable passthrough list carrying additional data.
	 * @param message The function to write the message.
	 */
	void writeTraceReturn(TraceLevel level, ObsPassThroughList listPassThrough, 
			Function<ObsPassThroughList, String> message);
	
	/**
	 * Build and write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param messageBuilder The consumer to build the message.
	 */
	void buildAndWrite(ObsLevel level, Consumer<StringBuilder> messageBuilder);
	
	/**
	 * Build and write a message at a given observation level.
	 * @param level The observation level.
	 * @param i The {@code int} value to pass through to the callback.
	 * @param message The callback to build the message.
	 */
	void buildAndWrite(ObsLevel level, int i, ObjIntConsumer<StringBuilder> messageBuilder);
	
	/**
	 * Build and write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param listPassThrough The debug passthrough list carrying additional data.
	 * @param messageBuilder The consumer to build the message.
	 */
	void buildAndWrite(ObsLevel level, ObsPassThroughList listPassThrough,
			BiConsumer<StringBuilder, ObsPassThroughList> messageBuilder);
	
	/**
	 * Create an observation collector at a given observation level.
	 * @param level The observation level.
	 * @return an observation collector, which will be active if {@code level} is a level
	 * 		for which the switch is accepting.
	 */
	ObservationCollector createCollector(ObsLevel level);
	
	/**
	 * Create a passthrough list for use with a call to write to this debug switch.
	 * @param strings A set of strings to include in the passthrough list.
	 * @return A passthrough list containing the input strings, in order.
	 * 
	 * If you encounter errors like this:
	 * Local variable N defined in an enclosing scope must be final or effectively final
	 * The problem can be overcome through use of a passthrough list.
	 */
	static ObsPassThroughList passThrough(String... strings) {
		return ObsPassThroughList.createFrom(strings);	
	}
}
