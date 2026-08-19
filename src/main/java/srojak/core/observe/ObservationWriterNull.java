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
 */
public class ObservationWriterNull
		extends ObservationWriterBase
		implements ObservationWriter {

	/**
	 * 
	 */
	public ObservationWriterNull() {
	}

	@Override
	public boolean isLevelAccepted(ObsLevel level) {
		return true;
	}

	@Override
	public void write(ObsLevel level, String strText) {
		// does nothing
	}

	@Override
	public void buildAndWrite(ObsLevel level, Consumer<StringBuilder> message) {
		// does nothing
	}

	@Override
	public void buildAndWrite(ObsLevel level, int i, ObjIntConsumer<StringBuilder> message) {
		// does nothing
	}

	@Override
	public void buildAndWrite(ObsLevel level, ObsPassThroughList listPassThrough,
			BiConsumer<StringBuilder, ObsPassThroughList> messageBuilder) {
		// does nothing		
	}

	@Override
	public void writeDiagnostic(String strText) {
		// does nothing
	}

	@Override
	public void writeTimeStamp(ObsLevel level) {
		// does nothing
	}

	@Override
	public ObservationCollector createCollector(ObsLevel level) {
		// create a collector, but do not make it active
		return new ObservationCollectorObj(this, ObsLevel.NONE, SourceLocation.caller());
	}

	@Override
	public void write(ObservationCollector collector, SourceLocation locOrigin, String strText) {
		// does nothing
	}

	@Override
	public void flush() {
		// does nothing
	}
}
