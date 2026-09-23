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

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

import srojak.core.backplane.ObservationCollectorInstance;
import srojak.core.logic.FlagsShortTest;

/**
 * @author Stephen
 *
 * Common interface all observation collectors must provide.
 */
public interface ObservationCollector
	extends TraceCollector {
	
	FlagsShortTest getFlags();

	/**
	 * Write a message at a given observation level.
	 * @param level The observation level.
	 * @param strText The text of the message.
	 */
	void write(ObsLevel level, String strText);
	
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
	 * Build a message and write it at a given observation level.
	 * @param level The observation level.
	 * @param message The callback to build the message.
	 */
	void buildAndWrite(ObsLevel level, Consumer<StringBuilder> message);
	
	/**
	 * Build a message and write it at a given observation level.
	 * @param level The observation level.
	 * @param i The {@code int} value to pass through to the callback.
	 * @param message The callback to build the message.
	 */
	void buildAndWrite(ObsLevel level, int i, ObjIntConsumer<StringBuilder> message);
	
	/**
	 * Build and write a message at an observation level.
	 * @param level The level at which to write the message.
	 * @param listPassThrough The observation passthrough list carrying additional data.
	 * @param messageBuilder The consumer to build the message.
	 */
	void buildAndWrite(ObsLevel level, ObsPassThroughList listPassThrough,
			BiConsumer<StringBuilder, ObsPassThroughList> messageBuilder);
	
	/**
	 * Write a time stamp at a given observation level.
	 * @param level The observation level.
	 */
	void writeTimeStamp(ObsLevel level);
	
	/**
	 * Write a diagnostic message.
	 * @param strText The text of the message.
	 */
	void writeDiagnostic(String strText);
	
	/**
	 * Create an observation collector at a given observation level.
	 * @param level The observation level.
	 * @return an observation collector, which will be active if {@code level} is a level
	 * 		for which the writer is writing.
	 */
	SingleObservationCollector createCollector(ObsLevel level);
	
	boolean hasPermanentWriters();
	
	void addWriter(ObservationWriter writer);
	
	void addWriterPermanent(ObservationWriter writer);
	
	void removeWriter(ObservationWriter writer);
	
	void replaceAllWriters(ObservationWriter writer);
	
	public static ObservationCollector makeInstance() {
		return new ObservationCollectorInstance();
	}
}
