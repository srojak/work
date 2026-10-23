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

import srojak.core.data.DataPassThrough;
import srojak.core.faults.FaultFormat;
import srojak.core.faults.FaultMethods;
import srojak.core.faults.StructuredFault;
import srojak.core.logic.FlagsShort;
import srojak.core.logic.FlagsShortTest;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollectorFlags;
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
		implements ObservationCollectorFlags {
	protected final FlagsShort _flags;
		
	protected AbstractObservationCollectorCommonCore() {
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
	public boolean areWritesLocked() {
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
	
	public boolean isPrimaryCollector() {
		return false;
	}

	protected void record(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, String strText) {
		listWriters.forEach(w -> w.write(level, location, strText));
	}

	protected void record(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, Supplier<String> message) {
		String strMessage = message.get();
		listWriters.forEach(w -> w.write(level, location, strMessage));
	}

	protected void record(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, DataPassThrough passThrough, Function<DataPassThrough, String> message) {
		String strMessage = message.apply(passThrough);
		listWriters.forEach(w -> w.write(level, location, strMessage));
	}

	protected void recordClassOf(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, String strName, Object objValue) {
		CodeObjectValueObsInstance instance = new CodeObjectValueObsInstance(level, location, strName, objValue);
		instance.setShowFullClassNames(true);
		listWriters.forEach(w -> w.writeObserved(instance));
	}

	protected void recordThrowable(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, ObservedActivity activity, Throwable t, boolean bShowStack) {
		EnumSet<FaultFormat> formats = EnumSet.of(FaultFormat.ShowCauses);
		if (bShowStack) {
			formats.add(FaultFormat.ShowStack);
		}
		StructuredFault fault = FaultMethods.process(t);
		listWriters.forEach(w -> w.writeFault(level, location, activity, formats, fault));
	}

	protected void recordResult(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, XResult result, boolean bShowStack) {
		if (result.isValid()) {
			String strMessage = result.getActivity().describe() + " succeeded";
			listWriters.forEach(w -> w.write(level, location, strMessage));
		} else {
			EnumSet<FaultFormat> formats = EnumSet.of(FaultFormat.ShowCauses);
			if (bShowStack) {
				formats.add(FaultFormat.ShowStack);
			}
			StructuredFault fault = FaultMethods.process(result.getThrowable());
			listWriters.forEach(w -> w.writeFault(level, location, result.getActivity(), formats, fault));
		}
	}

	protected void observe(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, CodeObservationBuilder builderMessage) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		builderMessage.build(instance);
		listWriters.forEach(w -> w.writeObserved(instance));
	}

	protected void observe(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, int i, CodeObservationBuilderWithInt builderMessage) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		builderMessage.build(instance, i);
		listWriters.forEach(w -> w.writeObserved(instance));
	}

	protected void observe(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, DataPassThrough passThrough,
			CodeObservationBuilderWithPassThrough builderMessage) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		builderMessage.build(instance, passThrough);
		listWriters.forEach(w -> w.writeObserved(instance));
	}

	protected void timeStamp(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level) {
		final LocalDateTime dtNow = LocalDateTime.now();
		listWriters.forEach(w -> w.writeTimeStamp(level, location, dtNow));
	}

	protected void recordDiagnostic(List<ObservationWriter> listWriters, SourceLocation location, String strText) {
		listWriters.forEach(w -> w.writeDiagnostic(location, strText));
	}

	protected void recordNoLocation(List<ObservationWriter> listWriters, ObsLevel level, String strText) {
		listWriters.forEach(w -> w.write(level, strText));
	}

	protected void recordNoLocation(List<ObservationWriter> listWriters, ObsLevel level, Supplier<String> message) {
		String strMessage = message.get();
		listWriters.forEach(w -> w.write(level, strMessage));
	}
	
	// TraceCollector methods

	protected void traceEnter(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.ENTERING);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceEnter(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, String strMessage) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.ENTERING);
		instance.appendContent(", ");
		instance.appendContent(strMessage);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceTextEnter(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, TextObservation observation) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.ENTERING);
		instance.appendContent(", ");
		instance.appendFrom(observation);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceCodeEnter(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, CodeValuesObservationBuilder builderObs) {
		CodeValuesObsInstance instance = new CodeValuesObsInstance(level, location);
		instance.appendContent(ObservationCommonText.ENTERING);
		instance.appendContent(", ");
		builderObs.build(instance);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceReturn(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.RETURNING);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceReturn(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, String strMessage) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.RETURNING);
		instance.appendContent(", ");
		instance.appendContent(strMessage);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceCodeReturn(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, CodeValuesObservationBuilder builderObs) {
		CodeValuesObsInstance instance = new CodeValuesObsInstance(level, location);
		instance.appendContent(ObservationCommonText.RETURNING);
		instance.appendContent(", ");
		builderObs.build(instance);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceTextReturn(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, TextObservation observation) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.RETURNING);
		instance.appendContent(", ");
		instance.appendFrom(observation);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceReturnValue(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, CodeValue cdValue) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		instance.appendContent(ObservationCommonText.RETURNING);
		instance.appendContent(ObservationCommonText.VALUE);
		instance.appendValue(" ", cdValue);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceThrowing(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, ObservedActivity activity, Exception exc) {
		CodeFaultObsInstance instance = new CodeFaultObsInstance(level, location, activity);
		instance.formatFault().add(FaultFormat.Originating);
		instance.setFault(FaultMethods.process(exc));
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}
}
