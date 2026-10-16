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
package srojak.core.main;

import srojak.core.io.IONodeIdentifier;
import srojak.core.io.IONodeName;
import srojak.core.io.IONodeType;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.writers.ObservationWriterDirectable;

/**
 * @author Stephen
 *
 */
public class PrimaryLogWriter 
		extends ObservationWriterDirectable {
	
	public static final IONodeIdentifier LOG_NAME = new IONodeName(IONodeType.ABSTRACT, "primary log");

	/**
	 * @param writesTo
	 * @param writerDefault
	 */
	PrimaryLogWriter(ObservationWriter writerDefault) {
		super(LOG_NAME, writerDefault);
	}

}
