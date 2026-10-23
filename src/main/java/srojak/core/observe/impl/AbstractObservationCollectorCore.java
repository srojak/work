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

import srojak.core.faults.FaultFormat;
import srojak.core.faults.FaultMethods;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.TextObservation;
import srojak.core.observe.code.CodeFaultObsInstance;
import srojak.core.observe.code.CodeObsInstance;
import srojak.core.observe.code.CodeValue;
import srojak.core.observe.code.CodeValuesObsInstance;
import srojak.core.observe.functional.CodeValuesObservationBuilder;

/**
 * @author Stephen
 *
 */
public class AbstractObservationCollectorCore 
		extends AbstractObservationCollectorCommonCore {

	/**
	 * 
	 */
	protected AbstractObservationCollectorCore() {
		super();
	}


	protected void traceEnter(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, Class<?> classTrace) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		if (useFullClassNames()) {
			instance.setShowFullClassNames(true);
		}
		instance.setOriginClass(classTrace);
		instance.appendContent(ObservationCommonText.ENTERING);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceEnter(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, Class<?> classTrace, String strMessage) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		if (useFullClassNames()) {
			instance.setShowFullClassNames(true);
		}
		instance.setOriginClass(classTrace);
		instance.appendContent(ObservationCommonText.ENTERING);
		instance.appendContent(", ");
		instance.appendContent(strMessage);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceTextEnter(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, Class<?> classTrace, TextObservation observation) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		if (useFullClassNames()) {
			instance.setShowFullClassNames(true);
		}
		instance.setOriginClass(classTrace);
		instance.appendContent(ObservationCommonText.ENTERING);
		instance.appendContent(", ");
		instance.appendFrom(observation);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceCodeEnter(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, Class<?> classTrace, CodeValuesObservationBuilder builderObs) {
		CodeValuesObsInstance instance = new CodeValuesObsInstance(level, location);
		if (useFullClassNames()) {
			instance.setShowFullClassNames(true);
		}
		instance.setOriginClass(classTrace);
		instance.appendContent(ObservationCommonText.ENTERING);
		instance.appendContent(", ");
		builderObs.build(instance);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceReturn(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, Class<?> classTrace) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		if (useFullClassNames()) {
			instance.setShowFullClassNames(true);
		}
		instance.setOriginClass(classTrace);
		instance.appendContent(ObservationCommonText.RETURNING);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceReturn(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, Class<?> classTrace, String strMessage) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		if (useFullClassNames()) {
			instance.setShowFullClassNames(true);
		}
		instance.setOriginClass(classTrace);
		instance.appendContent(ObservationCommonText.RETURNING);
		instance.appendContent(", ");
		instance.appendContent(strMessage);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceTextReturn(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, Class<?> classTrace, TextObservation observation) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		if (useFullClassNames()) {
			instance.setShowFullClassNames(true);
		}
		instance.setOriginClass(classTrace);
		instance.appendContent(ObservationCommonText.RETURNING);
		instance.appendContent(", ");
		instance.appendFrom(observation);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceCodeReturn(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, Class<?> classTrace, CodeValuesObservationBuilder builderObs) {
		CodeValuesObsInstance instance = new CodeValuesObsInstance(level, location);
		if (useFullClassNames()) {
			instance.setShowFullClassNames(true);
		}
		instance.setOriginClass(classTrace);
		instance.appendContent(ObservationCommonText.RETURNING);
		instance.appendContent(", ");
		builderObs.build(instance);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceReturnValue(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, Class<?> classTrace, CodeValue cdValue) {
		CodeObsInstance instance = new CodeObsInstance(level, location);
		if (useFullClassNames()) {
			instance.setShowFullClassNames(true);
		}
		instance.setOriginClass(classTrace);
		instance.appendContent(ObservationCommonText.RETURNING);
		instance.appendContent(ObservationCommonText.VALUE);
		instance.appendValue("", cdValue);
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}

	protected void traceThrowing(List<ObservationWriter> listWriters, SourceLocation location, ObsLevel level, Class<?> classTrace, ObservedActivity activity, Exception exc) {
		CodeFaultObsInstance instance = new CodeFaultObsInstance(level, location, activity);
		if (useFullClassNames()) {
			instance.setShowFullClassNames(true);
		}
		instance.setOriginClass(classTrace);
		instance.formatFault().add(FaultFormat.Originating);
		instance.setFault(FaultMethods.process(exc));
		listWriters.forEach(w -> w.writeObservedTrace(instance));
	}
}
