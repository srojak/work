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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

import srojak.core.InstanceNumberCommon;
import srojak.core.data.DataPassThrough;
import srojak.core.faults.FaultFormat;
import srojak.core.faults.FaultMethods;
import srojak.core.faults.StructuredFault;
import srojak.core.observe.ObsFormatOption;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsLevelInstancePolicyHolder;
import srojak.core.observe.ObservationCollectorWriteThrough;
import srojak.core.observe.ObservationCommon;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceDetail;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.TextObservation;
import srojak.core.observe.TraceLevel;
import srojak.core.observe.code.CodeFaultObsInstance;
import srojak.core.observe.code.CodeObjectValueObsInstance;
import srojak.core.observe.code.CodeObsInstance;
import srojak.core.observe.code.CodeObservation;
import srojak.core.observe.code.CodeValue;
import srojak.core.observe.code.CodeValuesObsInstance;
import srojak.core.observe.functional.CodeObservationBuilder;
import srojak.core.observe.functional.CodeObservationBuilderWithInt;
import srojak.core.observe.functional.CodeObservationBuilderWithPassThrough;
import srojak.core.observe.functional.CodeValuesObservationBuilder;
import srojak.core.observe.impl.ObservationCommonText;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.result.XResult;
import srojak.debug.DebugSwitchKey;
import srojak.debug.control.DebugSwitchMutable;

/**
 * @author Stephen
 *
 */
public final class DebugSwitchContentPrior
		extends DebugWriteThroughBase
		implements DebugSwitchMutable, ObservationCommon, ObservationCollectorWriteThrough {
	final DebugSwitchKey _key;
	private boolean _bShowSource;
	private boolean _bShowFullClass;
	private SourceDetail _sdetail;
	private SwitchControlSetRecord _setDefinedBy;
	
	/**
	 * 
	 */
	protected DebugSwitchContentPrior(DebugSwitchKey key) {
		Objects.requireNonNull(key, "key");
		_key = key;
		_bShowSource = false;
		_sdetail = SourceDetail.NONE;
		_setDefinedBy = null;
	}
	
	public DebugSwitchContentPrior(DebugSwitchKey key, SwitchControlSetRecord csr) {
		Objects.requireNonNull(key, "key");
		_key = key;
		_bShowSource = false;
		_sdetail = SourceDetail.NONE;
		_setDefinedBy = csr;
	}

	@Override
	public void applyInstancePolicy(ObsLevelInstancePolicyHolder policy) {
		//_policyInstances.copyFrom(policy);
	}

	@Override
	public DebugSwitchKey getKey() {
		return _key;
	}

	@Override
	public PackageClassLocator getClassLocator() {
		return _key.getClassLocator();
	}

	@Override
	public boolean showSourceLocations() {
		return _bShowSource;
	}
	
	@Override
	public void setShowSourceLocations(boolean bState) {
		_bShowSource = bState;
		_sdetail = bState ? SourceDetail.ALL : SourceDetail.CLASS_ONLY;
	}
	
	@Override
	public void setUseFullClassNames(boolean bState) {
		_bShowFullClass = bState;
	}

	@Override
	public String getDefiningControlSet() {
		return _setDefinedBy != null ? _setDefinedBy.getName() : "";
	}

	@Override
	public boolean isPrimaryCollector() {
		return true;
	}

	@Override
	public void visitPolicies(StringBuilder sb) {
		sb.append("filtering level = ");
		sb.append(getObsLevel());
	}

	@Override
	public List<ObservationWriter> getAllWriters() {
		List<ObservationWriter> writers = new LinkedList<ObservationWriter>();
		writers.add(DebugNexusCore.getDebugLogWriter());
		return writers;
	}

	@Override
	public List<ObservationWriter> getWriters(ObsLevel level) {
		List<ObservationWriter> writers = new LinkedList<ObservationWriter>();
		if (isLevelAtLeast(level)) {
			writers.add(DebugNexusCore.getDebugLogWriter());
		}
		return writers;
	}

	private void writeSourceLocation(StringBuilder sb, SourceLocation location, SourceDetail detail) {
		sb.append(location.toString(detail));
		sb.append(" ");
	}
	
	private void write(SourceLocation locOrigin, ObsLevel level, String strMessage) {
		if (isLevelAtLeast(level)) {
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			if (strMessage == null) {
				strMessage = "(null)";
				writer.writeDiagnostic("passed null message string at " + locOrigin);
			}
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, locOrigin, _sdetail);
			sb.append(strMessage);
			writer.write(level, locOrigin, sb.toString());
		}
	}
	
	private void write(CodeObservation observation, EnumSet<ObsFormatOption> optionsFormat) {
		if (isLevelAtLeast(observation.level())) {
			StringBuilder sb = new StringBuilder();
			observation.writeSelfTo(sb, STR_EMPTY, optionsFormat);
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			writer.writeObserved(STR_EMPTY, observation);
		}
	}

	@Override
	public void record(ObsLevel level, String strMessage) {
		ObsLevel.validateEventLevel(level);
		SourceLocation location = SourceLocation.caller();
		write(location, level, strMessage);
	}

	@Override
	public void record(ObsLevel level, Supplier<String> message) {
		ObsLevel.validateEventLevel(level);
		if (isLevelAtLeast(level)) {
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, location, _sdetail);
			sb.append(message.get());
			writer.write(level, location, sb.toString());
		}
	}

	@Override
	public void record(ObsLevel level, DataPassThrough listPassThrough,
			Function<DataPassThrough, String> message) {
		ObsLevel.validateEventLevel(level);
		if (isLevelAtLeast(level)) {
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, location, _sdetail);
			sb.append(message.apply(listPassThrough));
			writer.write(level, location, sb.toString());
		}
	}
	
	@Override
	public void recordClassOf(ObsLevel level, String strName, Object objValue) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(strName, "strName");
		if (isLevelAtLeast(level)) {
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			SourceLocation location = SourceLocation.caller();
			CodeObjectValueObsInstance instance = new CodeObjectValueObsInstance(level, location, strName, objValue);
			instance.setShowFullClassNames(true);
			writer.writeObserved(STR_EMPTY, instance);
		}
	}

	@Override
	public void recordClassOf(ObsLevel level, ObsLevel levelIfNull, String strName, Object objValue) {
		ObsLevel.validateEventLevel(level);
		ObsLevel.validateEventLevel(levelIfNull, "levelIfNull");
		Objects.requireNonNull(strName, "strName");
		if (objValue == null) {
			level = levelIfNull;
		}
		if (isLevelAtLeast(level)) {
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			SourceLocation location = SourceLocation.caller();
			CodeObjectValueObsInstance instance = new CodeObjectValueObsInstance(level, location, strName, objValue);
			instance.setShowFullClassNames(true);
			writer.writeObserved(STR_EMPTY, instance);
		}
	}

	@Override
	public void observe(ObsLevel level, CodeObservationBuilder builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(builderMessage, "builderMessage");		
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(level, location);
			builderMessage.build(instance);
			EnumSet<ObsFormatOption> optionsFormat = EnumSet.noneOf(ObsFormatOption.class);
			if (_bShowSource) {
				optionsFormat.add(ObsFormatOption.CanShowSourceLocations);
			}
			write(instance, optionsFormat);
		}
	}

	@Override
	public void observe(ObsLevel level, int i, CodeObservationBuilderWithInt builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(builderMessage, "builderMessage");		
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(level, location);
			builderMessage.build(instance, i);
			EnumSet<ObsFormatOption> optionsFormat = EnumSet.noneOf(ObsFormatOption.class);
			if (_bShowSource) {
				optionsFormat.add(ObsFormatOption.CanShowSourceLocations);
			}
			write(instance, optionsFormat);
		}
	}

	@Override
	public void observe(ObsLevel level, DataPassThrough passThrough,
			CodeObservationBuilderWithPassThrough builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(builderMessage, "builderMessage");		
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(level, location);
			builderMessage.build(instance, passThrough);
			EnumSet<ObsFormatOption> optionsFormat = EnumSet.noneOf(ObsFormatOption.class);
			if (_bShowSource) {
				optionsFormat.add(ObsFormatOption.CanShowSourceLocations);
			}
			write(instance, optionsFormat);
		}
	}

	@Override
	public void timeStamp(ObsLevel level) {
		ObsLevel.validateEventLevel(level);
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			final LocalDateTime dtNow = LocalDateTime.now();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			writer.writeTimeStamp(level, location, STR_EMPTY, dtNow);
		}
	}

	@Override
	public void recordDiagnostic(String strText) {
		ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
		writer.writeDiagnostic(strText);
	}

	@Override
	public void recordException(ObsLevel level, ObservedActivity activity, Exception exc, boolean bShowStack) {
		ObsLevel.validateEventLevel(level);
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, location, SourceDetail.ALL);
			sb.append("during ");
			if (activity == null) {
				sb.append("??");
			} else {
				sb.append(activity.describe());
			}
			EnumSet<FaultFormat> formats = EnumSet.of(FaultFormat.ShowCauses);
			if (bShowStack) {
				formats.add(FaultFormat.ShowStack);
			}
			StructuredFault fault = FaultMethods.process(exc);
			writer.writeFault(level, location, activity, formats, STR_EMPTY, fault);
		}
	}

	@Override
	public void recordThrowable(ObsLevel level, ObservedActivity activity, Throwable t, boolean bShowStack) {
		ObsLevel.validateEventLevel(level);
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, location, SourceDetail.ALL);
			sb.append("during ");
			if (activity == null) {
				sb.append("??");
			} else {
				sb.append(activity.describe());
			}
			EnumSet<FaultFormat> formats = EnumSet.of(FaultFormat.ShowCauses);
			if (bShowStack) {
				formats.add(FaultFormat.ShowStack);
			}
			StructuredFault fault = FaultMethods.process(t);
			writer.writeFault(level, location, activity, formats, STR_EMPTY, fault);
		}
	}

	@Override
	public void recordResult(ObsLevel level, ObsLevel levelIfFaulted, XResult result, boolean bShowStack) {
		ObsLevel.validateEventLevel(level);
		ObsLevel.validateEventLevel(levelIfFaulted, "levelIfFaulted");
		SourceLocation location = SourceLocation.caller();
		if (!result.isValid()) {
				level = levelIfFaulted;
		}
		if (isLevelAtLeast(level)) {
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			if (result.isValid()) {
				String strMessage = result.getActivity().describe() + " succeeded";
				writer.write(level, location, strMessage);
			} else {
				EnumSet<FaultFormat> formats = EnumSet.of(FaultFormat.ShowCauses);
				if (bShowStack) {
					formats.add(FaultFormat.ShowStack);
				}
				StructuredFault fault = FaultMethods.process(result.getThrowable());
				writer.writeFault(level, location, result.getActivity(), formats, STR_EMPTY, fault);
			}
		}
	}
	
	public void writeFormattedThrowable(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, 
			EnumSet<FaultFormat> formats, StructuredFault fault) {
		ObsLevel.validateEventLevel(level);
		if (isLevelAtLeast(level)) {
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			writer.writeFault(level, locOrigin, activity, formats, STR_EMPTY, fault);
		}
	}
	
	private CodeObsInstance writeTraceEnterObs(ObsLevel level, SourceLocation location) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		if (_bShowFullClass) {
			instance.setShowFullClassNames(true);
		}
		instance.appendContent(location.getMethodName());
		instance.appendContent(' ');
		instance.appendContent(ObservationCommonText.ENTERING);
		return instance;
	}
	
	private CodeObsInstance writeTraceReturnObs(ObsLevel level, SourceLocation location) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		if (_bShowFullClass) {
			instance.setShowFullClassNames(true);
		}
		instance.appendContent(location.getMethodName());
		instance.appendContent(' ');
		instance.appendContent(ObservationCommonText.RETURNING);
		return instance;
	}

	@Override
	public void traceEnter(TraceLevel level) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeObsInstance instance = writeTraceEnterObs(levelObs, location);
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	@Override
	public void traceEnter(TraceLevel level, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeObsInstance instance = writeTraceEnterObs(levelObs, location);
			instance.appendContent(", ");
			instance.appendContent(message.get());
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	@Override
	public void traceEnter(TraceLevel level, DataPassThrough passThrough,
			Function<DataPassThrough, String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeObsInstance instance = writeTraceEnterObs(levelObs, location);
			instance.appendContent(", ");
			instance.appendContent(message.apply(passThrough));
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}
	
	@Override
	public void traceEnter(TraceLevel level, String strMessage) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeObsInstance instance = writeTraceEnterObs(levelObs, location);
			instance.appendContent(", ");
			instance.appendContent(strMessage != null ? strMessage : STR_NULL);
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	@Override
	public void traceTextEnter(TraceLevel level, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();			
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeObsInstance instance = writeTraceEnterObs(levelObs, location);
			instance.appendContent(", ");
			instance.appendFrom(observation);
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	@Override
	public void traceCodeEnter(TraceLevel level, CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(builderObs, "builderObs");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();			
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeValuesObsInstance instance = new CodeValuesObsInstance(levelObs, location);
			instance.appendContent(", ");
			builderObs.build(instance);
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	@Override
	public void traceReturn(TraceLevel level) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeObsInstance instance = writeTraceReturnObs(levelObs, location);
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	@Override
	public void traceReturn(TraceLevel level, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeObsInstance instance = writeTraceReturnObs(levelObs, location);
			instance.appendContent(", ");
			instance.appendContent(message.get());
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	@Override
	public void traceReturn(TraceLevel level, DataPassThrough passThrough,
			Function<DataPassThrough, String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeObsInstance instance = writeTraceReturnObs(levelObs, location);
			instance.appendContent(", ");
			instance.appendContent(message.apply(passThrough));
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	@Override
	public void traceReturn(TraceLevel level, String strMessage) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeObsInstance instance = writeTraceReturnObs(levelObs, location);
			instance.appendContent(", ");
			instance.appendContent(strMessage != null ? strMessage : STR_NULL);
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	@Override
	public void traceCodeReturn(TraceLevel level, CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(builderObs, "builderObs");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();			
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeValuesObsInstance instance = new CodeValuesObsInstance(levelObs, location);
			instance.appendContent(", ");
			builderObs.build(instance);
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	@Override
	public void traceTextReturn(TraceLevel level, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();			
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeObsInstance instance = writeTraceReturnObs(levelObs, location);
			instance.appendContent(", ");
			instance.appendFrom(observation);
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	@Override
	public void traceReturnValue(TraceLevel level, CodeValue cdValue) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeObsInstance instance = writeTraceReturnObs(levelObs, location);
			instance.appendContent(ObservationCommonText.VALUE);
			instance.appendValue("", cdValue);
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	@Override
	public void traceThrowing(TraceLevel level, ObservedActivity activity, Exception exc) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(activity, "activity");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			CodeFaultObsInstance instance = new CodeFaultObsInstance(levelObs, location, activity);
			instance.formatFault().add(FaultFormat.Originating);
			instance.setFault(FaultMethods.process(exc));
			writer.writeObservedTrace(STR_EMPTY, instance);
		}		
	}

	private void buildAndWrite(SourceLocation locOrigin, ObsLevel level, Consumer<StringBuilder> messageBuilder) {
		if (isLevelAtLeast(level)) {
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, locOrigin, _sdetail);
			messageBuilder.accept(sb);
			writer.write(level, locOrigin, sb.toString());
		}
	}

	@Override
	@Deprecated(forRemoval = true)
	public void buildAndWrite(ObsLevel level, Consumer<StringBuilder> messageBuilder) {
		ObsLevel.validateEventLevel(level);
		SourceLocation location = SourceLocation.caller();
		buildAndWrite(location, level, messageBuilder);
	}

	@Deprecated(forRemoval = true)
	public void buildAndWrite(ObsLevel level, int i, ObjIntConsumer<StringBuilder> messageBuilder) {
		ObsLevel.validateEventLevel(level);
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, location, _sdetail);
			messageBuilder.accept(sb, i);
			writer.write(level, location, sb.toString());
		}
	}

	@Override
	public void writeDiagnostic(SourceLocation locOrigin, String strText) {
		ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
		writer.writeDiagnostic(locOrigin, strText);		
	}

	@Override
	public SingleObservationCollector createCollector(ObsLevel level) {
		ObsLevel.validateEventLevel(level);
		SourceLocation loc = SourceLocation.caller();
		//return new DebugObsCollectorObj(this, loc, level);
		return null;
	}

	@Override
	public void write(SingleObservationCollector collector, SourceLocation locOrigin, String strText) {
		if (isLevelAtLeast(collector.getObsLevel())) {
			ObservationWriter writer = DebugNexusCore.getDebugLogWriter();
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, locOrigin, _sdetail);
			sb.append(strText);
			writer.write(collector.getObsLevel(), locOrigin, sb.toString());
		}
	}

	@Override
	public int hashCode() {
		return _key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return _key.equals(obj);
	}

	@Override
	public String toString() {
		ObsLevel level = getObsLevel();
		StringBuilder sb = new StringBuilder("switch(");
		sb.append(_key);
		sb.append(", ");
		sb.append(level.getName());
		sb.append(", locs=");
		sb.append(_bShowSource);
		sb.append(')');
		return sb.toString();
	}

	// methods for DebugSwitchObservationWriter

	public void writeWithLocation(SourceLocation locOrigin, ObsLevel level, String strMessage) {
		Objects.requireNonNull(locOrigin, "locOrigin");
		ObsLevel.validateEventLevel(level);
		write(locOrigin, level, strMessage);
	}
	
	public void buildAndWriteWithLocation(SourceLocation locOrigin, ObsLevel level, Consumer<StringBuilder> messageBuilder) {
		Objects.requireNonNull(locOrigin, "locOrigin");
		ObsLevel.validateEventLevel(level);
		buildAndWrite(locOrigin, level, messageBuilder);
	}

	@Override
	public void record(ObsLevel level, InstanceNumberCommon instance, String strText) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void record(ObsLevel level, InstanceNumberCommon instance, Supplier<String> message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void record(ObsLevel level, InstanceNumberCommon instance, DataPassThrough passThrough,
			Function<DataPassThrough, String> message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recordClassOf(ObsLevel level, InstanceNumberCommon instance, String strName, Object objValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recordClassOf(ObsLevel level, ObsLevel levelIfNull, InstanceNumberCommon instance, String strName,
			Object objValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recordThrowable(ObsLevel level, InstanceNumberCommon instance, ObservedActivity activity, Throwable t,
			boolean bShowStack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recordResult(ObsLevel level, ObsLevel levelIfFaulted, InstanceNumberCommon instance, XResult result,
			boolean bShowStack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void observe(ObsLevel level, InstanceNumberCommon instance, CodeObservationBuilder builderMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void observe(ObsLevel level, InstanceNumberCommon instance, int i,
			CodeObservationBuilderWithInt builderMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void observe(ObsLevel level, InstanceNumberCommon instance, DataPassThrough passThrough,
			CodeObservationBuilderWithPassThrough builderMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void timeStamp(ObsLevel level, InstanceNumberCommon instance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recordDiagnostic(InstanceNumberCommon instance, String strText) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SingleObservationCollector createCollector(ObsLevel level, InstanceNumberCommon instance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void traceEnter(TraceLevel level, InstanceNumberCommon instance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceEnter(TraceLevel level, InstanceNumberCommon instance, String strMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceEnter(TraceLevel level, InstanceNumberCommon instance, Supplier<String> message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceTextEnter(TraceLevel level, InstanceNumberCommon instance, TextObservation observation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceEnter(TraceLevel level, InstanceNumberCommon instance, DataPassThrough passThrough,
			Function<DataPassThrough, String> message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceReturn(TraceLevel level, InstanceNumberCommon instance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceReturn(TraceLevel level, InstanceNumberCommon instance, String strMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceReturn(TraceLevel level, InstanceNumberCommon instance, Supplier<String> message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceReturn(TraceLevel level, InstanceNumberCommon instance, DataPassThrough passThrough,
			Function<DataPassThrough, String> message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceCodeReturn(TraceLevel level, InstanceNumberCommon instance,
			CodeValuesObservationBuilder builderObs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceTextReturn(TraceLevel level, InstanceNumberCommon instance, TextObservation observation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceReturnValue(TraceLevel level, InstanceNumberCommon instance, CodeValue cdValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceThrowing(TraceLevel level, InstanceNumberCommon instance, ObservedActivity activity,
			Exception exc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceCodeEnter(TraceLevel level, InstanceNumberCommon instance,
			CodeValuesObservationBuilder builderObs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void observeWithValues(ObsLevel level, CodeValuesObservationBuilder builderObs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void observeWithValues(ObsLevel level, InstanceNumberCommon instance,
			CodeValuesObservationBuilder builderObs) {
		// TODO Auto-generated method stub
		
	}
}
