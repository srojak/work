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

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Objects;

import srojak.core.InstanceNumberCommon;
import srojak.core.faults.FaultFormat;
import srojak.core.faults.StructuredFault;
import srojak.core.io.IONodeIdentifier;
import srojak.core.observe.HasObsLevelForInstances;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCommonWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.code.CodeFaultObservation;
import srojak.core.observe.code.CodeObservation;
import srojak.debug.DebugSwitchBase;

/**
 * @author Stephen
 *
 */
public abstract class DebugWriteThroughBase 
		implements ObservationCommonWriter, HasObsLevelForInstances {
	private ObsLevel _level;
	
	public DebugWriteThroughBase() {
		_level = ObsLevel.NONE;
	}

	@Override
	public IONodeIdentifier writesTo() {
		return DebugSwitchBase.DEBUG_SWITCH;
	}

	@Override
	public ObsLevel getObsLevel() {
		return _level;
	}

	@Override
	public ObsLevel getObsLevelForInstance(InstanceNumberCommon instance) {
		Objects.requireNonNull(instance, "instance");
		// policy is needed here
		return _level;
	}
	
	@Override
	public void setObsLevel(ObsLevel level) {
		_level = level;
	}

	public boolean isLevelAtLeast(ObsLevel level) {
		ObsLevel.validateEventLevel(level);
		return _level.isLevelAtLeast(level);
	}
	
	protected boolean isLevelForInstanceAtLeast(ObsLevel level, InstanceNumberCommon instance) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(instance, "instance");
		// apply the policy
		return _level.isLevelAtLeast(level);
	}

	public boolean isLevelAccepted(ObsLevel level) {
		ObsLevel.validateEventLevel(level);
		return _level.isLevelAtLeast(level);
	}

	public boolean isLevelAcceptedForInstance(InstanceNumberCommon instance, ObsLevel level) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(instance, "instance");
		// apply the policy
		return _level.isLevelAtLeast(level);
	}
	
	@Override
	public boolean canWrite() {
		return true;
	}

	@Override
	public boolean canWriteAt(ObsLevel level) {
		Objects.requireNonNull(level, "level");
		return _level.isLevelAtLeast(level);
	}

	@Override
	public abstract void write(SingleObservationCollector collector, SourceLocation locOrigin, String strText);

	@Override
	public void write(ObsLevel level, String strText) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeObserved(String strLead, CodeObservation observation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void write(ObsLevel level, SourceLocation locOrigin, String strText) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeObservedTrace(String strLead, CodeObservation observation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeTrace(ObsLevel level, SourceLocation locOrigin, String strText) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc,
			boolean bShowStack) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeObservedFault(String strLead, CodeFaultObservation observation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeFault(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			EnumSet<FaultFormat> formats, String strLead, StructuredFault fault) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeTimeStamp(ObsLevel level, SourceLocation locOrigin, String strLead, LocalDateTime dtObs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeDiagnostic(String strText) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeDiagnostic(SourceLocation locOrigin, String strText) {
		// TODO Auto-generated method stub

	}
}
