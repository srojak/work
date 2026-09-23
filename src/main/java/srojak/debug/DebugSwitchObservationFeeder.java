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

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.writers.ObservationWriterBase;
import srojak.debug.impl.DebugSwitchContent;

/**
 * @author Stephen
 *
 */
public final class DebugSwitchObservationFeeder
		extends ObservationWriterBase {
	private final DebugSwitchContent _swDebug;

	/**
	 * @param bIsFiltered
	 */
	public DebugSwitchObservationFeeder(DebugSwitch swDebug) {
		super();
		Objects.requireNonNull(swDebug, "swDebug");
		_swDebug = (DebugSwitchContent) swDebug;
		setCanWrite(true);
	}

	@Override
	public boolean canWriteAt(ObsLevel level) {
		return _swDebug.isLevelAccepted(level);
	}

	@Override
	public ObsLevel getObsLevel() {
		return _swDebug.getLevel();
	}

	@Override
	protected void innerWrite(ObsLevel level, SourceLocation locOrigin, String strText) {
		_swDebug.writeWithLocation(locOrigin, level, strText);
	}

	@Override
	protected void innerWriteException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			Exception exc, boolean bShowStack) {
		// requires improved interface
		_swDebug.writeException(level, activity, exc, bShowStack);
	}

	@Override
	protected void innerWriteDiagnostic(String strText) {
		_swDebug.writeDiagnostic(SourceLocation.redacted(), strText);
	}

	@Override
	protected void innerWriteDiagnostic(SourceLocation locOrigin, String strText) {
		_swDebug.writeDiagnostic(locOrigin, strText);
	}

}
