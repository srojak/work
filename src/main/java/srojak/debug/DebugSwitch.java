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

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import srojak.core.InstanceNumberCommon;
import srojak.core.data.DataPassThrough;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCommonCollector;
import srojak.core.observe.ObservationInstanceCommonCollector;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SingleObservationCollector;

/**
 * @author Stephen
 *
 * the readonly interface to a debug switch.
 */
public interface DebugSwitch 
		extends DebugSwitchBase, ObservationCommonCollector, ObservationInstanceCommonCollector {
	
	boolean isLevelAccepted(ObsLevel level);
	
	boolean isLevelAcceptedForInstance(InstanceNumberCommon instance, ObsLevel level);
	
	/**
	 * Write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param strMessage The text of the message.
	 */
	void record(ObsLevel level, String strMessage);
	
	/**
	 * Write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param message The supplier of the message.
	 */
	void record(ObsLevel level, Supplier<String> message);
	
	/**
	 * Write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param listPassThrough The observable passthrough list carrying additional data.
	 * @param message The function to write the message.
	 */
	void record(ObsLevel level, DataPassThrough listPassThrough, 
			Function<DataPassThrough, String> message);
	
	/**
	 * Write an exception at an observation level.
	 * @param level The level at which to write the message.
	 * @param activity Identifies the activity that produced the exception.
	 * @param exc The exception whose contents are to be written.
	 * @param bShowStack If {@code true}, the stack trace can be written.
	 */
	void recordException(ObsLevel level, ObservedActivity activity, Exception exc, boolean bShowStack);
	
	/**
	 * Write a throwable object at an observation level.
	 * @param level The level at which to write the message.
	 * @param activity Identifies the activity that produced the exception.
	 * @param t The throwable whose contents are to be written.
	 * @param bShowStack If {@code true}, the stack trace can be written.
	 */
	void recordThrowable(ObsLevel level, ObservedActivity activity, Throwable t, boolean bShowStack);
	
	/**
	 * Build and write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param messageBuilder The consumer to build the message.
	 * 
	 * @deprecated Use writeObservation() instead.
	 */
	@Deprecated(forRemoval = true)
	void buildAndWrite(ObsLevel level, Consumer<StringBuilder> messageBuilder);
	
	/**
	 * Create an observation collector at a given observation level.
	 * @param level The observation level.
	 * @return an observation collector, which will be active if {@code level} is a level
	 * 		for which the switch is accepting.
	 */
	SingleObservationCollector createCollector(ObsLevel level);
	
	void visitPolicies(StringBuilder sb);
	
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
