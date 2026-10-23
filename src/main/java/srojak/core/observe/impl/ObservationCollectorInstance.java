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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import srojak.core.data.DataPassThrough;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationCommon;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.TextObservation;
import srojak.core.observe.TraceLevel;
import srojak.core.observe.code.CodeValue;
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
		extends AbstractObservationCollectorWithWriters
		implements ObservationCollector, ObservationCommon {
	
	/**
	 * 
	 */
	public ObservationCollectorInstance() {
		super();
	}

	@Override
	public void record(ObsLevel level, String strText) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(strText, "strText");
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		record(listActive, location, level, strText);
	}

	@Override
	public void record(ObsLevel level, Supplier<String> message) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(message, "message");
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			record(listActive, location, level, message);
		}
	}

	@Override
	public void record(ObsLevel level, DataPassThrough passThrough, 
			Function<DataPassThrough, String> message) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(message, "message");
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			record(listActive, location, level, passThrough, message);
		}
	}
	
	@Override
	public void recordClassOf(ObsLevel level, String strName, Object objValue) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(strName, "strName");
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			recordClassOf(listActive, location, level, strName, objValue);
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
			recordClassOf(listActive, location, level, strName, objValue);
		}
	}

	@Override
	public void recordThrowable(ObsLevel level, ObservedActivity activity, Throwable t, boolean bShowStack) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(activity, "activity");
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			recordThrowable(listActive, location, level, activity, t, bShowStack);
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
			recordResult(listActive, location, level, result, bShowStack);
		}
	}

	@Override
	public void observe(ObsLevel level, CodeObservationBuilder builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(builderMessage, "builderMessage");		
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			observe(listActive, location, level, builderMessage);
		}
	}

	@Override
	public void observe(ObsLevel level, int i, CodeObservationBuilderWithInt builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(builderMessage, "builderMessage");		
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			observe(listActive, location, level, i, builderMessage);
		}
	}

	@Override
	public void observe(ObsLevel level, DataPassThrough passThrough,
			CodeObservationBuilderWithPassThrough builderMessage) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(builderMessage, "builderMessage");		
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			observe(listActive, location, level, passThrough, builderMessage);
		}
	}

	@Override
	public void timeStamp(ObsLevel level) {
		ObsLevel.validateEventLevel(level);
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			timeStamp(listActive, location, level);
		}
	}

	@Override
	public void recordDiagnostic(String strText) {
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listWriters = getAllWriters();
		recordDiagnostic(listWriters, location, strText);
	}

	@Override
	public SingleObservationCollector createCollector(ObsLevel level) {
		ObsLevel.validateEventLevel(level);
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		return new SingleObservationCollectorObj(listActive, level, location);
	}

	@Override
	public void recordNoLocation(ObsLevel level, String strText) {
		ObsLevel.validateEventLevel(level);
		List<ObservationWriter> listActive = getWriters(level);
		recordNoLocation(listActive, level, strText);
		listActive.forEach(w -> w.write(level, strText));
	}

	@Override
	public void recordNoLocation(ObsLevel level, Supplier<String> message) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(message, "message");
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			recordNoLocation(listActive, level, message);
		}
	}
	
	// TraceCollector methods

	@Override
	public void traceEnter(TraceLevel level) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceEnter(listActive, location, levelObs);
		}		
	}

	@Override
	public void traceEnter(TraceLevel level, String strMessage) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(strMessage, "strMessage");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();			
			traceEnter(listActive, location, levelObs, strMessage);
		}		
	}

	@Override
	public void traceEnter(TraceLevel level, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceEnter(listActive, location, levelObs, message.get());
		}		
	}

	@Override
	public void traceTextEnter(TraceLevel level, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();		
			traceTextEnter(listActive, location, levelObs, observation);
		}		
	}

	@Override
	public void traceEnter(TraceLevel level, DataPassThrough passThrough,
			Function<DataPassThrough, String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceEnter(listActive, location, levelObs, message.apply(passThrough));
		}		
	}

	@Override
	public void traceCodeEnter(TraceLevel level, CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(builderObs, "builderObs");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceCodeEnter(listActive, location, levelObs, builderObs);
		}		
	}

	@Override
	public void traceReturn(TraceLevel level) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceReturn(listActive, location, levelObs);
		}		
	}

	@Override
	public void traceReturn(TraceLevel level, String strMessage) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(strMessage, "strMessage");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceReturn(listActive, location, levelObs, strMessage);
		}		
	}

	@Override
	public void traceReturn(TraceLevel level, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceReturn(listActive, location, levelObs, message.get());
		}		
	}

	@Override
	public void traceReturn(TraceLevel level, DataPassThrough passThrough,
			Function<DataPassThrough, String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(passThrough, "passThrough");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceReturn(listActive, location, levelObs, message.apply(passThrough));
		}		
	}

	@Override
	public void traceCodeReturn(TraceLevel level, CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(builderObs, "builderObs");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();		
			traceCodeReturn(listActive, location, levelObs, builderObs);
		}		
	}

	@Override
	public void traceTextReturn(TraceLevel level, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceTextReturn(listActive, location, levelObs, observation);
		}		
	}

	@Override
	public void traceReturnValue(TraceLevel level, CodeValue cdValue) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(cdValue, "cdValue");		
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceReturnValue(listActive, location, levelObs, cdValue);
		}		
	}

	@Override
	public void traceThrowing(TraceLevel level, ObservedActivity activity, Exception exc) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(activity, "activity");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceThrowing(listActive, location, levelObs, activity, exc);
		}		
	}
	
	// TraceCollectorWithClass methods

	@Override
	public void traceEnter(TraceLevel level, Class<?> classTrace) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(classTrace, "classTrace");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceEnter(listActive, location, levelObs, classTrace);
		}
	}

	@Override
	public void traceEnterObject(TraceLevel level, Object objTrace) {
		Objects.requireNonNull(level, "level");
		if (objTrace == null) {
			objTrace = new NullObject();
		}
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceEnter(listActive, location, levelObs, objTrace.getClass());
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
			traceEnter(listActive, location, levelObs, classTrace, strMessage);
		}
	}

	@Override
	public void traceEnterObject(TraceLevel level, Object objTrace, String strMessage) {
		Objects.requireNonNull(level, "level");
		if (objTrace == null) {
			objTrace = new NullObject();
		}
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceEnter(listActive, location, levelObs, objTrace.getClass(), strMessage);
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
			traceTextEnter(listActive, location, levelObs, classTrace, observation);
		}
	}

	@Override
	public void traceTextEnterObject(TraceLevel level, Object objTrace, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		if (objTrace == null) {
			objTrace = new NullObject();
		}
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceTextEnter(listActive, location, levelObs, objTrace.getClass(), observation);
		}
	}

	@Override
	public void traceCodeEnter(TraceLevel level, Class<?> classTrace, CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(classTrace, "classTrace");
		Objects.requireNonNull(builderObs, "builderObs");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceCodeEnter(listActive, location, levelObs, classTrace, builderObs);
		}		
	}

	@Override
	public void traceCodeEnterObject(TraceLevel level, Object objTrace, CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		if (objTrace == null) {
			objTrace = new NullObject();
		}
		Objects.requireNonNull(builderObs, "builderObs");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceCodeEnter(listActive, location, levelObs, objTrace.getClass(), builderObs);
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
			traceReturn(listActive, location, levelObs, classTrace);
		}
	}

	@Override
	public void traceReturnObject(TraceLevel level, Object objTrace) {
		Objects.requireNonNull(level, "level");
		if (objTrace == null) {
			objTrace = new NullObject();
		}
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceReturn(listActive, location, levelObs, objTrace.getClass());
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
			traceReturn(listActive, location, levelObs, classTrace);
		}
	}

	@Override
	public void traceReturnObject(TraceLevel level, Object objTrace, String strMessage) {
		Objects.requireNonNull(level, "level");
		if (objTrace == null) {
			objTrace = new NullObject();
		}
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceReturn(listActive, location, levelObs, objTrace.getClass());
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
			traceTextReturn(listActive, location, levelObs, classTrace, observation);
		}
	}

	@Override
	public void traceTextReturnObject(TraceLevel level, Object objTrace, TextObservation observation) {
		Objects.requireNonNull(level, "level");
		if (objTrace == null) {
			objTrace = new NullObject();
		}
		Objects.requireNonNull(observation, "observation");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceTextReturn(listActive, location, levelObs, objTrace.getClass(), observation);
		}
	}

	@Override
	public void traceCodeReturn(TraceLevel level, Class<?> classTrace, CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(classTrace, "classTrace");
		Objects.requireNonNull(builderObs, "builderObs");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceCodeReturn(listActive, location, levelObs, classTrace, builderObs);
		}		
	}

	@Override
	public void traceCodeReturnObject(TraceLevel level, Object objTrace, CodeValuesObservationBuilder builderObs) {
		Objects.requireNonNull(level, "level");
		if (objTrace == null) {
			objTrace = new NullObject();
		}
		Objects.requireNonNull(builderObs, "builderObs");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceCodeReturn(listActive, location, levelObs, objTrace.getClass(), builderObs);
		}		
	}

	@Override
	public void traceReturnValue(TraceLevel level, Class<?> classTrace,  CodeValue cdValue) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(classTrace, "classTrace");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceReturnValue(listActive, location, levelObs, classTrace, cdValue);
		}		
	}

	@Override
	public void traceReturnObjectValue(TraceLevel level, Object objTrace,  CodeValue cdValue) {
		Objects.requireNonNull(level, "level");
		if (objTrace == null) {
			objTrace = new NullObject();
		}
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceReturnValue(listActive, location, levelObs, objTrace.getClass(), cdValue);
		}		
	}

	@Override
	public void traceThrowing(TraceLevel level, Class<?> classTrace, ObservedActivity activity, Exception exc) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(classTrace, "classTrace");
		Objects.requireNonNull(activity, "activity");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceThrowing(listActive, location, levelObs, classTrace, activity, exc);
		}		
	}

	@Override
	public void traceObjectThrowing(TraceLevel level, Object objTrace, ObservedActivity activity, Exception exc) {
		Objects.requireNonNull(level, "level");
		if (objTrace == null) {
			objTrace = new NullObject();
		}
		Objects.requireNonNull(activity, "activity");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			traceThrowing(listActive, location, levelObs, objTrace.getClass(), activity, exc);
		}		
	}
}
