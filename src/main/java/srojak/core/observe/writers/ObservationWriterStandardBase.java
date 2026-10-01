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

import java.util.Objects;

import srojak.core.InvalidOperationException;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsLevelFilterDecorator;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public abstract class ObservationWriterStandardBase 
		extends ObservationWriterBase {
	private ObsLevelFilterDecorator _filterLevel;

	/**
	 * 
	 */
	public ObservationWriterStandardBase() {
		super();
		_filterLevel = null;
	}

	@Override
	public final boolean isLevelFiltering() {
		return _filterLevel != null;
	}
	
	public final void enableLevelFilter() {
		if (getFlags().test(FLAGS_NO_FILTER)) {
			throw new InvalidOperationException("writer does not allow filtering");
		}
		if (_filterLevel == null) {
			_filterLevel = new ObsLevelFilterDecorator(DEFAULT_FILTERED_LEVEL);
		}
	}

	@Override
	public boolean canWriteAt(ObsLevel level) {
		if (!super.canWriteAt(level)) {
			return false;
		} else if (_filterLevel != null) {
			return _filterLevel.isLevelAtLeast(level);			
		} else {
			return true;
		}
	}

	@Override
	public ObsLevel getObsLevel() {
		if (_filterLevel != null) {
			return _filterLevel.getObsLevel();
		} else {
			return UNFILTERED_LEVEL;
		}
	}

	@Override
	protected boolean trySetObsLevel(ObsLevel level) {
		if (_filterLevel != null) {
			_filterLevel.setObsLevel(level);
			return true;
		} else {
			return false;
		}
	}
	
	protected abstract void innerWrite(ObsLevel level, String strText);
	
	protected abstract void innerWrite(ObsLevel level, SourceLocation locOrigin, String strText);
	
	protected abstract void innerWriteException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc,
			boolean bShowStack);
	
	protected abstract void innerWriteDiagnostic(String strText);
	
	protected abstract void innerWriteDiagnostic(SourceLocation locOrigin, String strText);

	@Override
	public void write(ObsLevel level, String strText) {
		Objects.requireNonNull(level, "level");
		innerWrite(level, strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (isAutoFlush()) {
			flush();
		}
	}

	@Override
	public void write(ObsLevel level, SourceLocation locOrigin, String strText) {
		Objects.requireNonNull(level, "level");
		innerWrite(level, locOrigin, strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (isAutoFlush()) {
			flush();
		}
	}

	@Override
	public void writeException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc,
			boolean bShowStack) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(activity, "activity");
		innerWriteException(level, locOrigin, activity, exc, bShowStack && isShowExceptionStackEnabled());
		if (isAutoFlush()) {
			flush();
		}
	}

	@Override
	public void writeDiagnostic(String strText) {
		innerWriteDiagnostic(strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (isAutoFlush()) {
			flush();
		}
	}

	@Override
	public void writeDiagnostic(SourceLocation locOrigin, String strText) {
		innerWriteDiagnostic(locOrigin, strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (isAutoFlush()) {
			flush();
		}
	}

	@Override
	public void write(SingleObservationCollector collector, SourceLocation locOrigin, String strText) {
		innerWrite(collector.getLevel(), locOrigin, strText);
		if (isAutoFlush()) {
			flush();
		}
	}
}
