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
import java.util.function.ObjIntConsumer;

/**
 * @author Stephen
 *
 * Common interface all observation writers must provide.
 */
public interface ObservationWriter
		extends ObservationCommonWriter {

	/**
	 * Write a message at a given observation level.
	 * @param level The observation level.
	 * @param strText The text of the message.
	 */
	void write(ObsLevel level, String strText);
	
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
	 * Create an observation collector at a given observation level.
	 * @param level The observation level.
	 * @return an observation collector, which will be active if {@code level} is a level
	 * 		for which the writer is writing.
	 */
	ObservationCollector createCollector(ObsLevel level);
	
	/**
	 * Flush the writer, if the underlying mechanism supports it.
	 */
	void flush();
}
