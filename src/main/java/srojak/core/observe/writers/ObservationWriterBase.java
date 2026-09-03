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
package srojak.core.observe.writers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public abstract class ObservationWriterBase
		implements ObservationWriter {
	
	protected static final DateTimeFormatter FORMAT_TIME_STAMP;

	static {
		FORMAT_TIME_STAMP = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");		
	}

	public ObservationWriterBase() {
		
	}

	@Override
	public ObservationCollector createCollector(ObsLevel level) {
		return new ObservationCollectorObj(this, level, SourceLocation.caller());
	}
	
	protected String getDateAndTimeStamp() {
		return FORMAT_TIME_STAMP.format(LocalDateTime.now());
	}
}
