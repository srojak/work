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
package srojak.core.observe.code;

import java.util.EnumSet;
import java.util.Objects;

import srojak.core.faults.FaultFormat;
import srojak.core.faults.FaultMethods;
import srojak.core.faults.StructuredFault;
import srojak.core.observe.ObsFormatOption;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public class CodeFaultObsInstance 
		extends CodeObsInstance 
		implements CodeFaultObservation, CodeFaultObservationWriting {
	private final ObservedActivity _activity;
	private final EnumSet<FaultFormat> _formatFault;
	private StructuredFault _fault;

	/**
	 * @param level
	 * @param location
	 */
	public CodeFaultObsInstance(ObsLevel level, SourceLocation location, ObservedActivity activity) {
		super(level, location);
		Objects.requireNonNull(activity, "activity");
		_activity = activity;
		_formatFault = EnumSet.noneOf(FaultFormat.class);
		_fault = null;
	}
	
	@Override
	public void setFault(StructuredFault fault) {
		Objects.requireNonNull(fault, "fault");
		_fault = fault;
	}

	@Override
	public ObservedActivity activity() {
		return _activity;
	}

	@Override
	public EnumSet<FaultFormat> formatFault() {
		return _formatFault;
	}

	@Override
	public StructuredFault fault() {
		return _fault;
	}

	@Override
	public void writeSelfTo(StringBuilder sb, EnumSet<ObsFormatOption> optionsFormat) {
		Objects.requireNonNull(sb, "sb");
		Objects.requireNonNull(optionsFormat, "optionsFormat");
		sb.append(_level.getName());
		innerWriteOrigin(sb, optionsFormat);
		innerWriteOriginClass(sb, optionsFormat);
		sb.append(": when ");
		sb.append(_activity.describe());
		sb.append('\n');
		if (_sbContent.length() > 0) {
			sb.append(_sbContent.toString());
			sb.append('\n');
		}
		if (_formatFault.contains(FaultFormat.Originating)) {
			sb.append("throwing ");
		}
		if (_fault == null) {
			sb.append("NULL fault");
		} else {
			EnumSet<FaultFormat> formatThisFault = EnumSet.copyOf(_formatFault);
			if (!optionsFormat.contains(ObsFormatOption.CanShowThrowableStack)) {
				formatThisFault.remove(FaultFormat.ShowStack);
			}
			FaultMethods.formatFaultTo(sb, formatThisFault, _fault);
		}
	}
}
