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
package srojak.debug.impl;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.writers.ObservationWriterBase;

/**
 * @author Stephen
 *
 */
public class DebugWriterForwarder
		extends ObservationWriterBase {

	/**
	 * 
	 */
	public DebugWriterForwarder() {
		// pass all exception details to the debug writer
		setShowExceptionStackEnabled(true);
		setCanWrite(true);
	}

	@Override
	public boolean canWriteAt(ObsLevel level) {
		// use the value from the debug writer
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		return writerDebug.canWriteAt(level);
	}

	@Override
	protected void innerWrite(ObsLevel level, SourceLocation locOrigin, String strText) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		writerDebug.write(level, locOrigin, strText);
	}

	@Override
	protected void innerWriteException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			Exception exc, boolean bShowStack) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		writerDebug.writeException(level, locOrigin, activity, exc, bShowStack);
	}

	@Override
	protected void innerWriteDiagnostic(String strText) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		writerDebug.writeDiagnostic(strText);
	}

	@Override
	protected void innerWriteDiagnostic(SourceLocation locOrigin, String strText) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		writerDebug.writeDiagnostic(strText);
	}

}
