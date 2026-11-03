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

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import srojak.core.InstanceNumberCommon;
import srojak.core.data.DataPassThrough;
import srojak.core.main.CoreCollectors;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsLevelInstancePolicyHolder;
import srojak.core.observe.ObservationCommon;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceDetail;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.TextObservation;
import srojak.core.observe.TraceLevel;
import srojak.core.observe.code.CodeValue;
import srojak.core.observe.functional.CodeObservationBuilder;
import srojak.core.observe.functional.CodeObservationBuilderWithInt;
import srojak.core.observe.functional.CodeObservationBuilderWithPassThrough;
import srojak.core.observe.functional.CodeValuesObservationBuilder;
import srojak.core.observe.impl.AbstractObservationCollectorCommonCore;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.result.XResult;
import srojak.debug.DebugSwitchKey;
import srojak.debug.control.DebugSwitchMutable;

/**
 * @author Stephen
 *
 */
public final class DebugSwitchContent
		extends AbstractObservationCollectorCommonCore
		implements DebugSwitchMutable, ObservationCommon {
	final DebugSwitchKey _key;
	private final ObsLevelInstancePolicyHolder _policyInstances;
	// TODO look at how DebugSwitchContentPrior used _sdetail;
	// how can that be blended in?
	private SourceDetail _sdetail;
	private SwitchControlSetRecord _setDefinedBy;
	
	private static final List<ObservationWriter> _writers;
	
	static {
		CoreCollectors collectors = new CoreCollectors();
		_writers = List.of(collectors.getLogWriter());
	}

	/**
	 * 
	 */
	public DebugSwitchContent(DebugSwitchKey key) {
		Objects.requireNonNull(key, "key");
		_key = key;
		_policyInstances = new ObsLevelInstancePolicyHolder(ObsLevel.NONE);
		setSuppressSourceLocations(true);
		_sdetail = SourceDetail.NONE;
		_setDefinedBy = null;
	}
	
	public DebugSwitchContent(DebugSwitchKey key, SwitchControlSetRecord csr) {
		Objects.requireNonNull(key, "key");
		_key = key;
		_policyInstances = new ObsLevelInstancePolicyHolder(ObsLevel.NONE);
		setSuppressSourceLocations(true);
		_sdetail = SourceDetail.NONE;
		_setDefinedBy = csr;
	}

	@Override
	public void applyInstancePolicy(ObsLevelInstancePolicyHolder policy) {
		_policyInstances.copyFrom(policy);
	}

	@Override
	public boolean isLevelAccepted(ObsLevel level) {
		ObsLevel levelFilter = _policyInstances.getDefaultObsLevel();
		return levelFilter.isLevelAtLeast(level);
	}
	
	@Override
	public boolean isLevelAcceptedForInstance(InstanceNumberCommon instance, ObsLevel level) {
		ObsLevel levelFilter = _policyInstances.getLevelForInstance(instance);
		return levelFilter.isLevelAtLeast(level);
	};
	
	protected List<ObservationWriter> getAllWriters() {
		return _writers;
	}
	
	public ObservationWriter getWriterForSwitch() {
		return DebugNexusCore.getDebugLogWriter();
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
	public ObsLevel getObsLevel() {
		return _policyInstances.getDefaultObsLevel();
	}

	@Override
	public ObsLevel getObsLevelForInstance(InstanceNumberCommon instance) {
		Objects.requireNonNull(instance, "instance");
		return _policyInstances.getLevelForInstance(instance);
	}

	@Override
	public void setObsLevel(ObsLevel level) {
		Objects.requireNonNull(level, "level");
		_policyInstances.setDefaultObsLevel(level);
	}
	
	@Override
	public void setShowSourceLocations(boolean bState) {
		setSuppressSourceLocations(!bState);
		_sdetail = bState ? SourceDetail.ALL : SourceDetail.CLASS_ONLY;
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
	public boolean isLevelAtLeast(ObsLevel level) {
		return isLevelAccepted(level);
	}
	
	protected String makeInstanceLeader(InstanceNumberCommon instance) {
		return "[i" + instance.toString() + "]";
	}

	@Override
	public void visitPolicies(StringBuilder sb) {
		sb.append("filtering level = ");
		sb.append(_policyInstances.getDefaultObsLevel());
		if (_policyInstances.hasInstanceOverrides()) {
			sb.append("\n  overrides:");
			_policyInstances.streamAllOverrides().forEach(p -> {
				sb.append("\n    instance ");
				sb.append(p.key().number());
				sb.append(", level ");
				sb.append(p.value());
			});
		}
	}

	@Override
	public void record(ObsLevel level, String strMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(strMessage, "strMessage");
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			record(getAllWriters(), location, level, strMessage);
		}
	}

	@Override
	public void record(ObsLevel level, Supplier<String> message) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(message, "message");
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			String strMessage = message.get();
			record(getAllWriters(), location, level, strMessage);
		}
	}

	@Override
	public void record(ObsLevel level, DataPassThrough passThrough, Function<DataPassThrough, String> message) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(message, "message");
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			String strMessage = message.apply(passThrough);
			record(getAllWriters(), location, level, strMessage);
		}
	}

	@Override
	public void recordException(ObsLevel level, ObservedActivity activity, Exception exc, boolean bShowStack) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(activity, "activity");
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			recordThrowable(getAllWriters(), location, level, activity, STR_EMPTY, exc, bShowStack);
		}
	}

	@Override
	public void recordThrowable(ObsLevel level, ObservedActivity activity, Throwable t, boolean bShowStack) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(activity, "activity");
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			recordThrowable(getAllWriters(), location, level, activity, STR_EMPTY, t, bShowStack);
		}
	}

	@Override
	public void buildAndWrite(ObsLevel level, Consumer<StringBuilder> messageBuilder) {
		ObsLevel.validateEventLevel(level);
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			StringBuilder sb = new StringBuilder();
			messageBuilder.accept(sb);
			record(getAllWriters(), location, level, sb.toString());
		}
	}

	@Override
	public SingleObservationCollector createCollector(ObsLevel level) {
		ObsLevel.validateEventLevel(level);
		SourceLocation loc = SourceLocation.caller();
		return new DebugObsCollectorObj(this, loc, level, getObsLevel(), STR_EMPTY);
	}

	@Override
	public void recordClassOf(ObsLevel level, String strName, Object objValue) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(strName, "strName");
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			recordClassOf(getAllWriters(), location, level, STR_EMPTY, strName, objValue);
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
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			recordClassOf(getAllWriters(), location, level, STR_EMPTY, strName, objValue);
		}
	}

	@Override
	public void recordResult(ObsLevel level, ObsLevel levelIfFaulted, XResult result, boolean bShowStack) {
		ObsLevel.validateEventLevel(level);
		ObsLevel.validateEventLevel(levelIfFaulted, "levelIfFaulted");
		Objects.requireNonNull(result, "result");
		SourceLocation location = SourceLocation.caller();
		if (!result.isValid()) {
			level = levelIfFaulted;
		}
		if (isLevelAccepted(level)) {
			recordResult(getAllWriters(), location, level, STR_EMPTY, result, bShowStack);
		}
	}
	
	@Override
	public void observe(ObsLevel level, CodeObservationBuilder builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(builderMessage, "builderMessage");		
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			observe(getAllWriters(), location, level, STR_EMPTY, builderMessage);
		}
	}

	@Override
	public void observe(ObsLevel level, int i, CodeObservationBuilderWithInt builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(builderMessage, "builderMessage");		
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			observe(getAllWriters(), location, level, STR_EMPTY, i, builderMessage);
		}
	}

	@Override
	public void observe(ObsLevel level, DataPassThrough passThrough,
			CodeObservationBuilderWithPassThrough builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(builderMessage, "builderMessage");		
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			observe(getAllWriters(), location, level, STR_EMPTY, passThrough, builderMessage);
		}
	}

	@Override
	public void observeWithValues(ObsLevel level, CodeValuesObservationBuilder builderObs) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(builderObs, "builderObs");		
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			observeWithValues(getAllWriters(), location, level, STR_EMPTY, builderObs);
		}
	}

	@Override
	public void timeStamp(ObsLevel level) {
		ObsLevel.validateEventLevel(level);
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(level)) {
			timeStamp(getAllWriters(), location, level, STR_EMPTY);
		}
	}

	@Override
	public void recordDiagnostic(String strText) {
		Objects.requireNonNull(strText, "strText");
		SourceLocation location = SourceLocation.caller();
		recordDiagnostic(getAllWriters(), location, strText);
	}
	
	// instance-identified methods
	
	@Override
	public void record(ObsLevel level, InstanceNumberCommon instance, String strText) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(strText, "strText");
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, level)) {
			record(getAllWriters(), location, level, makeInstanceLeader(instance) + strText);
		}
	}

	@Override
	public void record(ObsLevel level, InstanceNumberCommon instance, Supplier<String> message) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(message, "message");
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, level)) {
			record(getAllWriters(), location, level, makeInstanceLeader(instance) + message.get());
		}
	}

	@Override
	public void record(ObsLevel level, InstanceNumberCommon instance, DataPassThrough passThrough,
			Function<DataPassThrough, String> message) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(message, "message");
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, level)) {
			String strMessage = makeInstanceLeader(instance) + message.apply(passThrough);
			record(getAllWriters(), location, level, strMessage);
		}
	}

	@Override
	public void recordClassOf(ObsLevel level, InstanceNumberCommon instance, String strName, Object objValue) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(strName, "strName");
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, level)) {
			recordClassOf(getAllWriters(), location, level, makeInstanceLeader(instance), strName, objValue);
		}
	}

	@Override
	public void recordClassOf(ObsLevel level, ObsLevel levelIfNull, InstanceNumberCommon instance, String strName,
			Object objValue) {
		ObsLevel.validateEventLevel(level);
		ObsLevel.validateEventLevel(levelIfNull, "levelIfNull");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(strName, "strName");
		if (objValue == null) {
			level = levelIfNull;
		}
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, level)) {
			recordClassOf(getAllWriters(), location, level, makeInstanceLeader(instance), strName, objValue);
		}
	}

	@Override
	public void recordThrowable(ObsLevel level, InstanceNumberCommon instance, ObservedActivity activity, Throwable t,
			boolean bShowStack) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(activity, "activity");
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, level)) {
			recordThrowable(getAllWriters(), location, level, activity, makeInstanceLeader(instance), t, bShowStack);
		}
	}

	@Override
	public void recordResult(ObsLevel level, ObsLevel levelIfFaulted, InstanceNumberCommon instance, XResult result,
			boolean bShowStack) {
		ObsLevel.validateEventLevel(level);
		ObsLevel.validateEventLevel(levelIfFaulted, "levelIfFaulted");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(result, "result");
		SourceLocation location = SourceLocation.caller();
		if (!result.isValid()) {
			level = levelIfFaulted;
		}
		if (isLevelAcceptedForInstance(instance, level)) {
			recordResult(getAllWriters(), location, level, makeInstanceLeader(instance), result, bShowStack);
		}
	}

	@Override
	public void observe(ObsLevel level, InstanceNumberCommon instance, CodeObservationBuilder builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(builderMessage, "builderMessage");		
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, level)) {
			observe(getAllWriters(), location, level, makeInstanceLeader(instance), builderMessage);
		}
	}

	@Override
	public void observe(ObsLevel level, InstanceNumberCommon instance, int i,
			CodeObservationBuilderWithInt builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(builderMessage, "builderMessage");		
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, level)) {
			observe(getAllWriters(), location, level, makeInstanceLeader(instance), i, builderMessage);
		}
	}

	@Override
	public void observe(ObsLevel level, InstanceNumberCommon instance, DataPassThrough passThrough,
			CodeObservationBuilderWithPassThrough builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(builderMessage, "builderMessage");		
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, level)) {
			observe(getAllWriters(), location, level, makeInstanceLeader(instance), passThrough, builderMessage);
		}
	}

	@Override
	public void observeWithValues(ObsLevel level, InstanceNumberCommon instance,
			CodeValuesObservationBuilder builderObs) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(builderObs, "builderObs");		
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, level)) {
			observeWithValues(getAllWriters(), location, level, makeInstanceLeader(instance), builderObs);
		}
	}

	@Override
	public void timeStamp(ObsLevel level, InstanceNumberCommon instance) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(instance, "instance");
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, level)) {
			timeStamp(getAllWriters(), location, level, makeInstanceLeader(instance));
		}
	}

	@Override
	public void recordDiagnostic(InstanceNumberCommon instance, String strText) {
		Objects.requireNonNull(instance, "instance");
		SourceLocation location = SourceLocation.caller();
		recordDiagnostic(getAllWriters(), location, makeInstanceLeader(instance));
	}

	@Override
	public SingleObservationCollector createCollector(ObsLevel level, InstanceNumberCommon instance) {
		ObsLevel.validateEventLevel(level);
		SourceLocation loc = SourceLocation.caller();
		return new DebugObsCollectorObj(this, loc, level, getObsLevelForInstance(instance), makeInstanceLeader(instance));
	}
	
	// Trace methods

	@Override
	public void traceEnter(TraceLevel level) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceEnter(getAllWriters(), location, levelObs, STR_EMPTY);
		}
	}

	@Override
	public void traceEnter(TraceLevel level, String strMessage) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(strMessage, "strMessage");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceEnter(getAllWriters(), location, levelObs, STR_EMPTY, strMessage);
		}
	}

	@Override
	public void traceEnter(TraceLevel level, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceEnter(getAllWriters(), location, levelObs, STR_EMPTY, message.get());
		}
	}

	@Override
	public void traceTextEnter(TraceLevel level, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceTextEnter(getAllWriters(), location, levelObs, STR_EMPTY, observation);
		}
	}

	@Override
	public void traceEnter(TraceLevel level, DataPassThrough passThrough, Function<DataPassThrough, String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceEnter(getAllWriters(), location, levelObs, STR_EMPTY, message.apply(passThrough));
		}		
	}

	@Override
	public void traceCodeEnter(TraceLevel level, CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(builderObs, "builderObs");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceCodeEnter(getAllWriters(), location, levelObs, STR_EMPTY, builderObs);
		}		
	}

	@Override
	public void traceReturn(TraceLevel level) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceReturn(getAllWriters(), location, levelObs, STR_EMPTY);
		}
	}

	@Override
	public void traceReturn(TraceLevel level, String strMessage) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(strMessage, "strMessage");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceReturn(getAllWriters(), location, levelObs, STR_EMPTY, strMessage);
		}
	}

	@Override
	public void traceReturn(TraceLevel level, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceReturn(getAllWriters(), location, levelObs, STR_EMPTY, message.get());
		}
	}

	@Override
	public void traceReturn(TraceLevel level, DataPassThrough passThrough, Function<DataPassThrough, String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceReturn(getAllWriters(), location, levelObs, STR_EMPTY, message.apply(passThrough));
		}		
	}

	@Override
	public void traceCodeReturn(TraceLevel level, CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(builderObs, "builderObs");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceCodeReturn(getAllWriters(), location, levelObs, STR_EMPTY, builderObs);
		}		
	}

	@Override
	public void traceTextReturn(TraceLevel level, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceTextReturn(getAllWriters(), location, levelObs, STR_EMPTY, observation);
		}
	}

	@Override
	public void traceReturnValue(TraceLevel level, CodeValue cdValue) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(cdValue, "cdValue");		
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceReturnValue(getAllWriters(), location, levelObs, STR_EMPTY, cdValue);
		}		
	}

	@Override
	public void traceThrowing(TraceLevel level, ObservedActivity activity, Exception exc) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(activity, "activity");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAccepted(levelObs)) {
			traceThrowing(getAllWriters(), location, levelObs, activity, STR_EMPTY, exc);
		}		
	}
	
	// Trace methods for instances

	@Override
	public void traceEnter(TraceLevel level, InstanceNumberCommon instance) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceEnter(getAllWriters(), location, levelObs, makeInstanceLeader(instance));
		}
	}

	@Override
	public void traceEnter(TraceLevel level, InstanceNumberCommon instance, String strMessage) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(strMessage, "strMessage");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceEnter(getAllWriters(), location, levelObs, makeInstanceLeader(instance), strMessage);
		}
		
	}

	@Override
	public void traceEnter(TraceLevel level, InstanceNumberCommon instance, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceEnter(getAllWriters(), location, levelObs, makeInstanceLeader(instance), message.get());
		}
	}

	@Override
	public void traceTextEnter(TraceLevel level, InstanceNumberCommon instance, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceTextEnter(getAllWriters(), location, levelObs, makeInstanceLeader(instance), observation);
		}
	}

	@Override
	public void traceEnter(TraceLevel level, InstanceNumberCommon instance, DataPassThrough passThrough,
			Function<DataPassThrough, String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceEnter(getAllWriters(), location, levelObs, makeInstanceLeader(instance), message.apply(passThrough));
		}		
	}

	@Override
	public void traceCodeEnter(TraceLevel level, InstanceNumberCommon instance,
			CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(builderObs, "builderObs");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceCodeEnter(getAllWriters(), location, levelObs, makeInstanceLeader(instance), builderObs);
		}		
	}

	@Override
	public void traceReturn(TraceLevel level, InstanceNumberCommon instance) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceReturn(getAllWriters(), location, levelObs, makeInstanceLeader(instance));
		}
	}

	@Override
	public void traceReturn(TraceLevel level, InstanceNumberCommon instance, String strMessage) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(strMessage, "strMessage");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceReturn(getAllWriters(), location, levelObs, makeInstanceLeader(instance), strMessage);
		}
	}

	@Override
	public void traceReturn(TraceLevel level, InstanceNumberCommon instance, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceReturn(getAllWriters(), location, levelObs, makeInstanceLeader(instance), message.get());
		}
	}

	@Override
	public void traceReturn(TraceLevel level, InstanceNumberCommon instance, DataPassThrough passThrough,
			Function<DataPassThrough, String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceReturn(getAllWriters(), location, levelObs, makeInstanceLeader(instance), message.apply(passThrough));
		}		
	}

	@Override
	public void traceCodeReturn(TraceLevel level, InstanceNumberCommon instance,
			CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(builderObs, "builderObs");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceCodeReturn(getAllWriters(), location, levelObs, makeInstanceLeader(instance), builderObs);
		}		
	}

	@Override
	public void traceTextReturn(TraceLevel level, InstanceNumberCommon instance, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceTextReturn(getAllWriters(), location, levelObs, makeInstanceLeader(instance), observation);
		}
	}

	@Override
	public void traceReturnValue(TraceLevel level, InstanceNumberCommon instance, CodeValue cdValue) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(cdValue, "cdValue");		
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceReturnValue(getAllWriters(), location, levelObs, makeInstanceLeader(instance), cdValue);
		}		
	}

	@Override
	public void traceThrowing(TraceLevel level, InstanceNumberCommon instance, ObservedActivity activity,
			Exception exc) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(activity, "activity");
		ObsLevel levelObs = level.getObsLevel();
		SourceLocation location = SourceLocation.caller();
		if (isLevelAcceptedForInstance(instance, levelObs)) {
			traceThrowing(getAllWriters(), location, levelObs, activity, makeInstanceLeader(instance), exc);
		}		
	}	
}
