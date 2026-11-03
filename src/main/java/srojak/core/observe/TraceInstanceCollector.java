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

import srojak.core.InstanceNumberCommon;
import srojak.core.data.DataPassThrough;
import srojak.core.observe.code.CodeValue;
import srojak.core.observe.functional.CodeValuesObservationBuilder;

/**isNumericallyEqual
 * @author Stephen
 *
 */
public interface TraceInstanceCollector {

	/**
	 * Write a trace message to enter a method.
	 * @param level The trace level at which to write the message.
	 * @param instance The instance calling the method.
	 */
	void traceEnter(TraceLevel level, InstanceNumberCommon instance);
	
	/**
	 * Write a trace message to enter a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param instance The instance calling the method.
	 * @param strMessage The text of the message.
	 */
	void traceEnter(TraceLevel level, InstanceNumberCommon instance, String strMessage);
	
	/**
	 * Write a trace message to enter a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param instance The instance calling the method.
	 * @param message The supplier of the additional text.
	 */
	void traceEnter(TraceLevel level, InstanceNumberCommon instance, Supplier<String> message);
	
	void traceTextEnter(TraceLevel level, InstanceNumberCommon instance, TextObservation observation);
	
	/**
	 * Write a trace message to enter a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param instance The instance calling the method.
	 * @param passThrough The observable passthrough list carrying additional data.
	 * @param message The function to write the message.
	 */
	void traceEnter(TraceLevel level, InstanceNumberCommon instance, DataPassThrough passThrough, 
			Function<DataPassThrough, String> message);
	
	void traceCodeEnter(TraceLevel level, InstanceNumberCommon instance, CodeValuesObservationBuilder builderObs);
	
	/**
	 * Write a trace message to return from a method.
	 * @param level The trace level at which to write the message.
	 * @param instance The instance calling the method.
	 */
	void traceReturn(TraceLevel level, InstanceNumberCommon instance);
	
	/**
	 * Write a trace message to return from a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param instance The instance calling the method.
	 * @param strMessage The text of the message.
	 */
	void traceReturn(TraceLevel level, InstanceNumberCommon instance, String strMessage);
	
	/**
	 * Write a trace message to return from a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param instance The instance calling the method.
	 * @param message The supplier of the additional text.
	 */
	void traceReturn(TraceLevel level, InstanceNumberCommon instance, Supplier<String> message);
	
	/**
	 * Write a trace message to return from a method with additional text.
	 * @param level The trace level at which to write the message.
	 * @param instance The instance calling the method.
	 * @param passThrough The observable passthrough list carrying additional data.
	 * @param message The function to write the message.
	 */
	void traceReturn(TraceLevel level, InstanceNumberCommon instance, DataPassThrough passThrough, 
			Function<DataPassThrough, String> message);

	void traceCodeReturn(TraceLevel level, InstanceNumberCommon instance, CodeValuesObservationBuilder builderObs);
	
	void traceTextReturn(TraceLevel level, InstanceNumberCommon instance, TextObservation observation);
	
	/**
	 * Write a trace message to return from a method with the value being returned.
	 * @param level The trace level at which to write the message.
	 * @param instance The instance calling the method.
	 * @param cdValue The captured value being returned.
	 */
	void traceReturnValue(TraceLevel level, InstanceNumberCommon instance, CodeValue cdValue);
	
	void traceThrowing(TraceLevel level, InstanceNumberCommon instance, ObservedActivity activity, Exception exc);
}
