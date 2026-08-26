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

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsPassThroughList;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterBase;
import srojak.core.observe.SourceLocation;
import srojak.debug.impl.DebugSwitchContent;

/**
 * @author Stephen
 *
 * An adapter that allows observations to feed through a debug switch.
 */
public class DebugSwitchObservationWriter 
		extends ObservationWriterBase 
		implements ObservationWriter {
	private final DebugSwitchContent _swDebug;

	/**
	 * 
	 */
	public DebugSwitchObservationWriter(DebugSwitch swDebug) {
		Objects.requireNonNull(swDebug, "swDebug");
		_swDebug = (DebugSwitchContent) swDebug;
	}

	@Override
	public boolean isLevelAccepted(ObsLevel level) {
		return _swDebug.isLevelAccepted(level);
	}

	@Override
	public void write(ObservationCollector collector, SourceLocation locOrigin, String strText) {
		_swDebug.write(collector, locOrigin, strText);
	}

	@Override
	public void writeDiagnostic(String strText) {
		_swDebug.writeDiagnostic(strText);
	}

	@Override
	public void write(ObsLevel level, String strText) {
		SourceLocation location = SourceLocation.caller();
		_swDebug.writeWithLocation(location, level, strText);
	}

	@Override
	public void buildAndWrite(ObsLevel level, Consumer<StringBuilder> message) {
		_swDebug.buildAndWrite(level, message);
	}

	@Override
	public void buildAndWrite(ObsLevel level, int i, ObjIntConsumer<StringBuilder> message) {
		_swDebug.buildAndWrite(level, i, message);
	}

	@Override
	public void buildAndWrite(ObsLevel level, ObsPassThroughList listPassThrough,
			BiConsumer<StringBuilder, ObsPassThroughList> messageBuilder) {
		_swDebug.buildAndWrite(level, listPassThrough, messageBuilder);
	}

	@Override
	public void writeTimeStamp(ObsLevel level) {
		_swDebug.write(level, () -> "time " + getDateAndTimeStamp());
	}

	@Override
	public void flush() {
		// has no effect

	}

}
