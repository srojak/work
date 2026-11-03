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
package srojak.core.observe.impl;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import srojak.core.Announcer;
import srojak.core.data.DataPassThrough;
import srojak.core.faults.FaultFormat;
import srojak.core.faults.FaultMethods;
import srojak.core.faults.StructuredFault;
import srojak.core.logic.FlagsShort;
import srojak.core.logic.FlagsShortTest;
import srojak.core.main.CoreCollectors;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollectorFlags;
import srojak.core.observe.ObservationCommon;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.TextObservation;
import srojak.core.observe.code.CodeFaultObsInstance;
import srojak.core.observe.code.CodeObjectValueObsInstance;
import srojak.core.observe.code.CodeObsInstance;
import srojak.core.observe.code.CodeValue;
import srojak.core.observe.code.CodeValuesObsInstance;
import srojak.core.observe.functional.CodeObservationBuilder;
import srojak.core.observe.functional.CodeObservationBuilderWithInt;
import srojak.core.observe.functional.CodeObservationBuilderWithPassThrough;
import srojak.core.observe.functional.CodeValuesObservationBuilder;
import srojak.core.result.XResult;

/**
 * @author Stephen
 *
 */
public abstract class AbstractObservationCollectorCommonCore
		implements ObservationCollectorFlags, ObservationCommon {
	private final CoreCollectors _collectors;
	protected final FlagsShort _flags;
		
	protected AbstractObservationCollectorCommonCore() {
		_collectors = new CoreCollectors();
		_flags = new FlagsShort();
	}

	@Override
	public FlagsShortTest getFlags() {
		return _flags;
	}

	@Override
	public boolean hasPermanentWriters() {
		return _flags.test(FLAGS_HAS_PERM_WRITER);
	}

	@Override
	public boolean areWritersLocked() {
		return _flags.test(FLAGS_WRITERS_LOCKED);
	}
	
	protected void lockWritersInPlace() {
		_flags.set(FLAGS_WRITERS_LOCKED);
	}

	@Override
	public boolean useFullClassNames() {
		return _flags.test(FLAGS_USE_FULL_CLASS_NAMES);
	}

	public void setUseFullClassNames(boolean bState) {
		_flags.apply(bState, FLAGS_USE_FULL_CLASS_NAMES);
	}
	
	protected void suppressAlerts() {
		_flags.set(FLAGS_SUPPRESS_ALERTS);
	}

	@Override
	public boolean isObsLevelLocked() {
		return _flags.test(FLAGS_OBS_LEVEL_LOCKED);
	}
	
	protected void lockObsLevelAtCurrent() {
		_flags.set(FLAGS_OBS_LEVEL_LOCKED);
	}
	
	@Override
	public boolean showSourceLocations() {
		return !_flags.test(FLAGS_SUPPRESS_SOURCE);
	}
	
	protected void setSuppressSourceLocations(boolean bState) {
		_flags.apply(bState, FLAGS_SUPPRESS_SOURCE);
	}

	public boolean isPrimaryCollector() {
		return false;
	}
	
	protected void escalate(ObsLevel level, SourceLocation location) {
		if (_collectors.shouldAlert(level) && !_flags.test(FLAGS_SUPPRESS_ALERTS)) {
			Announcer ann = _collectors.announcer();
			ann.announce(level, location);
		}
	}
	
	protected void escalateMessage(ObsLevel level, String strMessage) {
		if (_collectors.shouldAlert(level) && !_flags.test(FLAGS_SUPPRESS_ALERTS)) {
			Announcer ann = _collectors.announcer();
			ann.announceMessage(level, this.getClass(), strMessage);
		}
	}
	
	protected void escalateFault(ObsLevel level, SourceLocation location, ObservedActivity activity, StructuredFault fault) {
		if (_collectors.shouldAlert(level) && !_flags.test(FLAGS_SUPPRESS_ALERTS)) {
			Announcer ann = _collectors.announcer();
			ann.announceFault(level, location, activity, fault);
		}		
	}

	protected void record(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, String strText) {
		listWriters.forEach(w -> w.write(level, location, strText));
		escalate(level, location);
	}

	protected void record(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, Supplier<String> message) {
		String strMessage = message.get();
		listWriters.forEach(w -> w.write(level, location, strMessage));
		escalate(level, location);
	}

	protected void record(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, DataPassThrough passThrough, Function<DataPassThrough, String> message) {
		String strMessage = message.apply(passThrough);
		listWriters.forEach(w -> w.write(level, location, strMessage));
		escalate(level, location);
	}

	protected void recordClassOf(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level,
			String strLead, String strName, Object objValue) {
		CodeObjectValueObsInstance instance = new CodeObjectValueObsInstance(level, location, strName, objValue);
		instance.setShowFullClassNames(true);
		listWriters.forEach(w -> w.writeObserved(strLead, instance));
		escalate(level, location);
	}

	protected void recordThrowable(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			ObservedActivity activity, String strLead, Throwable t, boolean bShowStack) {
		EnumSet<FaultFormat> formats = EnumSet.of(FaultFormat.ShowCauses);
		if (bShowStack) {
			formats.add(FaultFormat.ShowStack);
		}
		StructuredFault fault = FaultMethods.process(t);
		listWriters.forEach(w -> w.writeFault(level, location, activity, formats, strLead, fault));
		escalateFault(level, location, activity, fault);
	}

	protected void recordResult(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			String strLead, XResult result, boolean bShowStack) {
		if (result.isValid()) {
			String strMessage = result.getActivity().describe() + " succeeded";
			listWriters.forEach(w -> w.write(level, location, strMessage));
			escalate(level, location);
		} else {
			EnumSet<FaultFormat> formats = EnumSet.of(FaultFormat.ShowCauses);
			if (bShowStack) {
				formats.add(FaultFormat.ShowStack);
			}
			StructuredFault fault = FaultMethods.process(result.getThrowable());
			ObservedActivity activity = result.getActivity();
			listWriters.forEach(w -> w.writeFault(level, location, activity, formats, strLead, fault));
			escalateFault(level, location, activity, fault);
		}
	}

	protected void observe(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			String strLead, CodeObservationBuilder builderMessage) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		builderMessage.build(instance);
		listWriters.forEach(w -> w.writeObserved(strLead, instance));
		escalate(level, location);
	}

	protected void observe(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			String strLead, int i, CodeObservationBuilderWithInt builderMessage) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		builderMessage.build(instance, i);
		listWriters.forEach(w -> w.writeObserved(strLead, instance));
		escalate(level, location);
	}

	protected void observe(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			String strLead, DataPassThrough passThrough, CodeObservationBuilderWithPassThrough builderMessage) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		builderMessage.build(instance, passThrough);
		listWriters.forEach(w -> w.writeObserved(strLead, instance));
		escalate(level, location);
	}
	
	protected void observeWithValues(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			String strLead, CodeValuesObservationBuilder builderObs) {
		CodeValuesObsInstance instance = new CodeValuesObsInstance(level, location);
		builderObs.build(instance);
		listWriters.forEach(w -> w.writeObservedTrace(strLead, instance));
	}

	protected void timeStamp(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, String strLead) {
		final LocalDateTime dtNow = LocalDateTime.now();
		listWriters.forEach(w -> w.writeTimeStamp(level, location, strLead, dtNow));
		escalate(level, location);
	}

	protected void recordDiagnostic(List<ObservationWriter> listWriters, SourceLocation location, String strText) {
		listWriters.forEach(w -> w.writeDiagnostic(location, strText));
	}

	protected void recordNoLocation(List<ObservationWriter> listWriters, ObsLevel level, String strText) {
		listWriters.forEach(w -> w.write(level, strText));
		escalate(level, SourceLocation.redacted());
	}

	protected void recordNoLocation(List<ObservationWriter> listWriters, ObsLevel level, Supplier<String> message) {
		String strMessage = message.get();
		listWriters.forEach(w -> w.write(level, strMessage));
		escalate(level, SourceLocation.redacted());
	}
	
	// TraceCollector methods

	protected void traceEnter(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, String strLead) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.ENTERING);
		listWriters.forEach(w -> w.writeObservedTrace(strLead, instance));
	}

	protected void traceEnter(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			String strLead, String strMessage) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.ENTERING);
		instance.appendContent(", ");
		instance.appendContent(strMessage);
		listWriters.forEach(w -> w.writeObservedTrace(strLead, instance));
	}

	protected void traceTextEnter(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			String strLead, TextObservation observation) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.ENTERING);
		instance.appendContent(", ");
		instance.appendFrom(observation);
		listWriters.forEach(w -> w.writeObservedTrace(strLead, instance));
	}

	protected void traceCodeEnter(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			String strLead, CodeValuesObservationBuilder builderObs) {
		CodeValuesObsInstance instance = new CodeValuesObsInstance(level, location);
		instance.appendContent(ObservationCommonText.ENTERING);
		instance.appendContent(", ");
		builderObs.build(instance);
		listWriters.forEach(w -> w.writeObservedTrace(strLead, instance));
	}

	protected void traceReturn(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, String strLead) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.RETURNING);
		listWriters.forEach(w -> w.writeObservedTrace(strLead, instance));
	}

	protected void traceReturn(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			String strLead, String strMessage) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.RETURNING);
		instance.appendContent(", ");
		instance.appendContent(strMessage);
		listWriters.forEach(w -> w.writeObservedTrace(strLead, instance));
	}

	protected void traceCodeReturn(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			String strLead, CodeValuesObservationBuilder builderObs) {
		CodeValuesObsInstance instance = new CodeValuesObsInstance(level, location);
		instance.appendContent(ObservationCommonText.RETURNING);
		instance.appendContent(", ");
		builderObs.build(instance);
		listWriters.forEach(w -> w.writeObservedTrace(strLead, instance));
	}

	protected void traceTextReturn(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			String strLead, TextObservation observation) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.RETURNING);
		instance.appendContent(", ");
		instance.appendFrom(observation);
		listWriters.forEach(w -> w.writeObservedTrace(strLead, instance));
	}

	protected void traceReturnValue(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			String strLead, CodeValue cdValue) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.RETURNING);
		instance.appendContent(ObservationCommonText.VALUE);
		instance.appendValue(" ", cdValue);
		listWriters.forEach(w -> w.writeObservedTrace(strLead, instance));
	}

	protected void traceThrowing(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, 
			ObservedActivity activity, String strLead, Exception exc) {
		CodeFaultObsInstance instance = new CodeFaultObsInstance(level, location, activity);
		instance.formatFault().add(FaultFormat.Originating);
		instance.setFault(FaultMethods.process(exc));
		listWriters.forEach(w -> w.writeObservedTrace(strLead, instance));
	}
}
