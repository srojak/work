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
import java.util.EnumSet;
import java.util.Objects;

import srojak.core.InvalidOperationException;
import srojak.core.faults.FaultFormat;
import srojak.core.faults.StructuredFault;
import srojak.core.io.IONodeIdentifier;
import srojak.core.observe.ObsFormatOption;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsLevelFilterDecorator;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.code.CodeFaultObservation;
import srojak.core.observe.code.CodeObservation;

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
	
	public ObservationWriterStandardBase(IONodeIdentifier writesTo) {
		super(writesTo);
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
	
	protected abstract void innerWriteObserved(CodeObservation observation, EnumSet<ObsFormatOption> optionsFormat);
	
	protected abstract void innerWrite(ObsLevel level, SourceLocation locOrigin, boolean bIsTrace, String strText);
	
	protected abstract void innerWriteException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc,
			boolean bShowStack);
	
	protected abstract void innerWriteObservedFault(CodeFaultObservation observation, EnumSet<ObsFormatOption> optionsFormat);
	
	protected abstract void innerWriteFault(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			EnumSet<FaultFormat> formats, StructuredFault fault);
	
	protected abstract void innerWriteTimeStamp(ObsLevel level, SourceLocation locOrigin, LocalDateTime dtObs);
	
	protected abstract void innerWriteDiagnostic(String strText);
	
	protected abstract void innerWriteDiagnostic(SourceLocation locOrigin, String strText);

	@Override
	public void write(ObsLevel level, String strText) {
		ObsLevel.validateEventLevel(level);
		innerWrite(level, strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (isAutoFlush()) {
			flush();
		}
	}

	@Override
	public void writeObserved(CodeObservation observation) {
		Objects.requireNonNull(observation, "observation");
		EnumSet<ObsFormatOption> optionsFormat = EnumSet.noneOf(ObsFormatOption.class);
		if (canShowLocations()) {
			optionsFormat.add(ObsFormatOption.CanShowSourceLocations);
		}
		innerWriteObserved(observation, optionsFormat);
		if (isAutoFlush()) {
			flush();
		}
	}

	@Override
	public void write(ObsLevel level, SourceLocation locOrigin, String strText) {
		Objects.requireNonNull(level, "level");
		innerWrite(level, locOrigin, false, strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (isAutoFlush()) {
			flush();
		}
	}

	@Override
	public void writeObservedTrace(CodeObservation observation) {
		Objects.requireNonNull(observation, "observation");
		EnumSet<ObsFormatOption> optionsFormat 
			= EnumSet.of(ObsFormatOption.CanShowSourceLocations, ObsFormatOption.ShowFullSourceLocations);
		innerWriteObserved(observation, optionsFormat);
		if (isAutoFlush()) {
			flush();
		}
	}

	@Override
	public void writeTrace(ObsLevel level, SourceLocation locOrigin, String strText) {
		Objects.requireNonNull(level, "level");
		innerWrite(level, locOrigin, true, strText == null ? ObsWriterTextMethods.STR_NULL : strText);
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
	public void writeObservedFault(CodeFaultObservation observation) {
		Objects.requireNonNull(observation, "observation");
		EnumSet<ObsFormatOption> optionsFormat = EnumSet.noneOf(ObsFormatOption.class);
		if (canShowLocations()) {
			optionsFormat.add(ObsFormatOption.CanShowSourceLocations);
		} if (isShowExceptionStackEnabled()) {
			optionsFormat.add(ObsFormatOption.CanShowThrowableStack);
		}
	}

	@Override
	public void writeFault(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			EnumSet<FaultFormat> formats, StructuredFault fault) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(activity, "activity");
		Objects.requireNonNull(formats, "formats");
		Objects.requireNonNull(fault, "fault");
		innerWriteFault(level, locOrigin, activity, formats, fault);
		if (isAutoFlush()) {
			flush();
		}
	}

	@Override
	public void writeTimeStamp(ObsLevel level, SourceLocation locOrigin, LocalDateTime dtObs) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(dtObs, "dtObs");
		innerWriteTimeStamp(level, locOrigin, dtObs);
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
		Objects.requireNonNull(locOrigin, "locOrigin");
		innerWriteDiagnostic(locOrigin, strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (isAutoFlush()) {
			flush();
		}
	}

	@Override
	public void write(SingleObservationCollector collector, SourceLocation locOrigin, String strText) {
		innerWrite(collector.getObsLevel(), locOrigin, false, strText);
		if (isAutoFlush()) {
			flush();
		}
	}
}
