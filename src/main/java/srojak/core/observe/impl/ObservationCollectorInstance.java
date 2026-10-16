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
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import srojak.core.data.DataPassThrough;
import srojak.core.faults.FaultFormat;
import srojak.core.faults.FaultMethods;
import srojak.core.faults.StructuredFault;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.TextObservation;
import srojak.core.observe.TraceLevel;
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
public class ObservationCollectorInstance
		extends ObservationCollectorBase
		implements ObservationCollector {
	
	/**
	 * 
	 */
	public ObservationCollectorInstance() {
		super();
	}

	@Override
	public void recordDiagnostic(String strText) {
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listWriters = getAllWriters();
		listWriters.forEach(w -> w.writeDiagnostic(location, strText));
	}

	@Override
	public void record(ObsLevel level, String strText) {
		ObsLevel.validateEventLevel(level);
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		listActive.forEach(w -> w.write(level, location, strText));
	}

	@Override
	public void recordNoLocation(ObsLevel level, String strText) {
		ObsLevel.validateEventLevel(level);
		List<ObservationWriter> listActive = getWriters(level);
		listActive.forEach(w -> w.write(level, strText));
	}

	@Override
	public void record(ObsLevel level, Supplier<String> message) {
		ObsLevel.validateEventLevel(level);
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			String strMessage = message.get();
			listActive.forEach(w -> w.write(level, location, strMessage));
		}
	}

	@Override
	public void recordNoLocation(ObsLevel level, Supplier<String> message) {
		ObsLevel.validateEventLevel(level);
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			String strMessage = message.get();
			listActive.forEach(w -> w.write(level, strMessage));
		}
	}

	@Override
	public void record(ObsLevel level, DataPassThrough listPassThrough,
			Function<DataPassThrough, String> message) {
		ObsLevel.validateEventLevel(level);
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			String strMessage = message.apply(listPassThrough);
			listActive.forEach(w -> w.write(level, location, strMessage));
		}
	}
	
	@Override
	public void recordClassOf(ObsLevel level, String strName, Object objValue) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(strName, "strName");
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			CodeObjectValueObsInstance instance = new CodeObjectValueObsInstance(level, location, strName, objValue);
			if (useFullClassNames()) {
				instance.setShowFullClassNames(true);
			}
			listActive.forEach(w -> w.writeObserved(instance));
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
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			CodeObjectValueObsInstance instance = new CodeObjectValueObsInstance(level, location, strName, objValue);
			if (useFullClassNames()) {
				instance.setShowFullClassNames(true);
			}
			listActive.forEach(w -> w.writeObserved(instance));
		}
	}

	@Override
	public void recordThrowable(ObsLevel level, ObservedActivity activity, Throwable t, boolean bShowStack) {
		ObsLevel.validateEventLevel(level);
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			EnumSet<FaultFormat> formats = EnumSet.of(FaultFormat.ShowCauses);
			if (bShowStack) {
				formats.add(FaultFormat.ShowStack);
			}
			StructuredFault fault = FaultMethods.process(t);
			listActive.forEach(w -> w.writeFault(level, location, activity, formats, fault));
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
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			final ObsLevel level2 = level;
			if (result.isValid()) {
				String strMessage = result.getActivity().describe() + " succeeded";
				listActive.forEach(w -> w.write(level2, location, strMessage));
			} else {
				EnumSet<FaultFormat> formats = EnumSet.of(FaultFormat.ShowCauses);
				if (bShowStack) {
					formats.add(FaultFormat.ShowStack);
				}
				StructuredFault fault = FaultMethods.process(result.getThrowable());
				listActive.forEach(w -> w.writeFault(level2, location, result.getActivity(), formats, fault));
			}
		}
	}

	@Override
	public void traceEnter(TraceLevel level) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceEnter();
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceEnter(TraceLevel level, String strMessage) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceEnter();
			sb.append(' ');
			sb.append(strMessage);
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceEnter(TraceLevel level, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceEnter();
			sb.append(' ');
			sb.append(message.get());
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceTextEnter(TraceLevel level, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceEnter();
			sb.append(' ');
			observation.formatToText(sb);
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceEnter(TraceLevel level, DataPassThrough listPassThrough,
			Function<DataPassThrough, String> message) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceEnter();
			sb.append(' ');
			sb.append(message.apply(listPassThrough));
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceCodeEnter(TraceLevel level, CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();			
			CodeValuesObsInstance instance = new CodeValuesObsInstance(levelObs, location);
			instance.appendContent(ObservationCommonText.ENTERING);
			instance.appendContent(" ");
			builderObs.build(instance);
			listActive.forEach(w -> w.writeObserved(instance));
		}		
	}

	@Override
	public void traceReturn(TraceLevel level) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceReturn();
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceReturn(TraceLevel level, String strMessage) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceReturn();
			sb.append(' ');
			sb.append(strMessage);
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceReturn(TraceLevel level, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceReturn();
			sb.append(' ');
			sb.append(message.get());
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceReturn(TraceLevel level, DataPassThrough listPassThrough,
			Function<DataPassThrough, String> message) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceReturn();
			sb.append(' ');
			sb.append(message.apply(listPassThrough));
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceTextReturn(TraceLevel level, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceReturn();
			sb.append(' ');
			observation.formatToText(sb);
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceCodeReturn(TraceLevel level, CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();			
			CodeValuesObsInstance instance = new CodeValuesObsInstance(levelObs, location);
			instance.appendContent(ObservationCommonText.RETURNING);
			instance.appendContent(" ");
			builderObs.build(instance);
			listActive.forEach(w -> w.writeObserved(instance));
		}		
	}

	@Override
	public void traceReturnValue(TraceLevel level, CodeValue cdValue) {
		// TODO complete this class
		
	}

	@Override
	public void traceThrowing(TraceLevel level, ObservedActivity activity, Exception exc) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(activity, "activity");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			CodeFaultObsInstance instance = new CodeFaultObsInstance(levelObs, location, activity);
			instance.formatFault().add(FaultFormat.Originating);
			instance.setFault(FaultMethods.process(exc));
			listActive.forEach(w -> w.writeObserved(instance));
		}		
	}

	@Override
	public void traceEnterObject(TraceLevel level, Object objTrace) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");		
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceEnter(objTrace.getClass(), false);
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceEnterObject(TraceLevel level, Object objTrace, String strMessage) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");
		Objects.requireNonNull(strMessage, "strMessage");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceEnter(objTrace.getClass(), false);
			sb.append(' ');
			sb.append(strMessage);
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceEnter(TraceLevel level, Class<?> classTrace) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(classTrace, "classTrace");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(levelObs, location);
			if (useFullClassNames()) {
				instance.setShowFullClassNames(true);
			}
			instance.setOriginClass(classTrace);
			instance.appendContent(ObservationCommonText.ENTERING);
			listActive.forEach(w -> w.writeObserved(instance));
		}
	}

	@Override
	public void traceEnter(TraceLevel level, Class<?> classTrace, String strMessage) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(classTrace, "classTrace");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(levelObs, location);
			if (useFullClassNames()) {
				instance.setShowFullClassNames(true);
			}
			instance.setOriginClass(classTrace);
			instance.appendContent(ObservationCommonText.ENTERING);
			instance.appendContent(" ");
			instance.appendContent(strMessage);
			listActive.forEach(w -> w.writeObserved(instance));
		}
	}

	@Override
	public void traceTextEnter(TraceLevel level, Class<?> classTrace, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(classTrace, "classTrace");
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(levelObs, location);
			if (useFullClassNames()) {
				instance.setShowFullClassNames(true);
			}
			instance.setOriginClass(classTrace);
			instance.appendContent(ObservationCommonText.ENTERING);
			instance.appendContent(" ");
			instance.appendFrom(observation);
			listActive.forEach(w -> w.writeObserved(instance));
		}
	}

	@Override
	public void traceTextEnterObject(TraceLevel level, Object objTrace, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(levelObs, location);
			if (useFullClassNames()) {
				instance.setShowFullClassNames(true);
			}
			instance.setOriginClass(objTrace.getClass());
			instance.appendContent(ObservationCommonText.ENTERING);
			instance.appendContent(" ");
			instance.appendFrom(observation);
			listActive.forEach(w -> w.writeObserved(instance));
		}
	}

	@Override
	public void traceReturnObject(TraceLevel level, Object objTrace) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");		
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceReturn(objTrace.getClass(), false);
			String strMessage = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strMessage));
		}		
	}
	
	@Override
	public void traceReturnObject(TraceLevel level, Object objTrace, String strMessage) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");
		Objects.requireNonNull(strMessage, "strMessage");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceReturn(objTrace.getClass(), false);
			sb.append(' ');
			sb.append(strMessage);
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceReturn(TraceLevel level, Class<?> classTrace) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(classTrace, "classTrace");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceReturn(classTrace, false);
			String strText = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strText));
		}		
	}

	@Override
	public void traceReturn(TraceLevel level, Class<?> classTrace, String strMessage) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(classTrace, "classTrace");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(levelObs, location);
			if (useFullClassNames()) {
				instance.setShowFullClassNames(true);
			}
			instance.setOriginClass(classTrace);
			instance.appendContent(ObservationCommonText.RETURNING);
			instance.appendContent(" ");
			instance.appendContent(strMessage);
			listActive.forEach(w -> w.writeObserved(instance));
		}
	}

	@Override
	public void traceTextReturn(TraceLevel level, Class<?> classTrace, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(classTrace, "classTrace");
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(levelObs, location);
			if (useFullClassNames()) {
				instance.setShowFullClassNames(true);
			}
			instance.setOriginClass(classTrace);
			instance.appendContent(ObservationCommonText.RETURNING);
			instance.appendContent(" ");
			instance.appendFrom(observation);
			listActive.forEach(w -> w.writeObserved(instance));
		}
	}

	@Override
	public void traceTextReturnObject(TraceLevel level, Object objTrace, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(levelObs, location);
			if (useFullClassNames()) {
				instance.setShowFullClassNames(true);
			}
			instance.setOriginClass(objTrace.getClass());
			instance.appendContent(ObservationCommonText.RETURNING);
			instance.appendContent(" ");
			instance.appendFrom(observation);
			listActive.forEach(w -> w.writeObserved(instance));
		}
	}

	@Override
	public void traceCodeEnter(TraceLevel level, Class<?> classTrace, CodeValuesObservationBuilder builderObs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceCodeEnterObject(TraceLevel level, Object objTrace, CodeValuesObservationBuilder builderObs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceCodeReturn(TraceLevel level, Class<?> classTrace, CodeValuesObservationBuilder builderObs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceCodeReturnObject(TraceLevel level, Object objTrace, CodeValuesObservationBuilder builderObs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceReturnValue(TraceLevel level, Class<?> classTrace, CodeValue cdValue) {
		// TODO unfinished
		
	}

	@Override
	public void traceThrowing(TraceLevel level, Class<?> classTrace, ObservedActivity activity, Exception exc) {
		// TODO unfinished
		
	}

	@Override
	public void traceReturnObjectValue(TraceLevel level, Object objTrace, CodeValue cdValue) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");		
		Objects.requireNonNull(cdValue, "cdValue");		
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceReturnValue(objTrace.getClass(), false, cdValue.format());
			String strMessage = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strMessage));
		}		
	}

	@Override
	public void traceObjectThrowing(TraceLevel level, Object objTrace, ObservedActivity activity, Exception exc) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");		
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			EnumSet<FaultFormat> formats = EnumSet.of(FaultFormat.Originating, FaultFormat.ShowCauses);
			StructuredFault fault = FaultMethods.process(exc);
			listActive.forEach(w -> w.writeFault(levelObs, location, activity, formats, fault));
		}
	}

	@Override
	public void observe(ObsLevel level, CodeObservationBuilder builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(builderMessage, "builderMessage");		
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(level, location);
			builderMessage.build(instance);
			listActive.forEach(w -> w.writeObserved(instance));
		}
	}

	@Override
	public void observe(ObsLevel level, int i, CodeObservationBuilderWithInt builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(builderMessage, "builderMessage");		
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(level, location);
			builderMessage.build(instance, i);
			listActive.forEach(w -> w.writeObserved(instance));
		}
	}

	@Override
	public void observe(ObsLevel level, DataPassThrough passThrough,
			CodeObservationBuilderWithPassThrough builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(builderMessage, "builderMessage");		
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			CodeObsInstance instance = new CodeObsInstance(level, location);
			builderMessage.build(instance, passThrough);
			listActive.forEach(w -> w.writeObserved(instance));
		}
	}

	@Override
	public void timeStamp(ObsLevel level) {
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			final LocalDateTime dtNow = LocalDateTime.now();
			listActive.forEach(w -> w.writeTimeStamp(level, location, dtNow));
		}
	}

	@Override
	public SingleObservationCollector createCollector(ObsLevel level) {
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		return new SingleObservationCollectorObj(listActive, level, location);
	}
}
