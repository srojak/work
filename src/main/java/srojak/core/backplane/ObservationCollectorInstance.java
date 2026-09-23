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
package srojak.core.backplane;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsPassThroughList;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.TraceLevel;
import srojak.core.observe.writers.SingleObservationCollectorObj;

/**
 * @author Stephen
 *
 */
public class ObservationCollectorInstance
		extends ObservationCollectorBase {
	
	/**
	 * 
	 */
	public ObservationCollectorInstance() {
		super();
	}

	@Override
	public void writeDiagnostic(String strText) {
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listWriters = getAllWriters();
		listWriters.forEach(w -> w.writeDiagnostic(location, strText));
	}

	@Override
	public void write(ObsLevel level, String strText) {
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		listActive.forEach(w -> w.write(level, location, strText));
	}

	@Override
	public void write(ObsLevel level, Supplier<String> message) {
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			String strMessage = message.get();
			listActive.forEach(w -> w.write(level, location, strMessage));
		}
	}

	@Override
	public void write(ObsLevel level, ObsPassThroughList listPassThrough,
			Function<ObsPassThroughList, String> message) {
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			String strMessage = message.apply(listPassThrough);
			listActive.forEach(w -> w.write(level, location, strMessage));
		}
	}

	@Override
	public void writeTraceEnter(TraceLevel level, Object objTrace) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");		
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceEnter(objTrace.getClass(), false);
			String strMessage = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strMessage));
		}		
	}

	@Override
	public void writeTraceEnter(TraceLevel level, Object objTrace, String strMessage) {
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
	public void writeTraceEnter(TraceLevel level, Object objTrace, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");
		Objects.requireNonNull(message, "message");
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceEnter(objTrace.getClass(), false);
			sb.append(' ');
			sb.append(message.get());
			String strMessage = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strMessage));
		}		
	}

	@Override
	public void writeTraceEnter(TraceLevel level, Object objTrace, ObsPassThroughList listPassThrough,
			Function<ObsPassThroughList, String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");		
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceEnter(objTrace.getClass(), false);
			sb.append(' ');
			sb.append(message.apply(listPassThrough));
			String strMessage = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strMessage));
		}		
	}

	@Override
	public void writeTraceReturn(TraceLevel level, Object objTrace) {
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
	public void writeTraceReturn(TraceLevel level, Object objTrace, String strMessage) {
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
	public void writeTraceReturn(TraceLevel level, Object objTrace, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");		
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceReturn(objTrace.getClass(), false);
			sb.append(' ');
			sb.append(message.get());
			String strMessage = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strMessage));
		}		
	}

	@Override
	public void writeTraceReturn(TraceLevel level, Object objTrace, ObsPassThroughList listPassThrough,
			Function<ObsPassThroughList, String> message) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");		
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceReturn(objTrace.getClass(), false);
			sb.append(' ');
			sb.append(message.apply(listPassThrough));
			String strMessage = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strMessage));
		}		
	}

	@Override
	public void writeTraceReturnValue(TraceLevel level, Object objTrace, Object objValue) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(objTrace, "objTrace");		
		ObsLevel levelObs = level.getObsLevel();
		List<ObservationWriter> listActive = getWriters(levelObs);
		if (!listActive.isEmpty()) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = ObservationCommonText.startTraceReturnValue(objTrace.getClass(), false, objValue);
			String strMessage = sb.toString();
			listActive.forEach(w -> w.write(levelObs, location, strMessage));
		}		
	}

	@Override
	public void buildAndWrite(ObsLevel level, Consumer<StringBuilder> message) {
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			message.accept(sb);
			String strMessage = sb.toString();
			listActive.forEach(w -> w.write(level, location, strMessage));
		}
	}

	@Override
	public void buildAndWrite(ObsLevel level, int i, ObjIntConsumer<StringBuilder> message) {
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			message.accept(sb, i);
			String strMessage = sb.toString();
			listActive.forEach(w -> w.write(level, location, strMessage));
		}
	}

	@Override
	public void buildAndWrite(ObsLevel level, ObsPassThroughList listPassThrough,
			BiConsumer<StringBuilder, ObsPassThroughList> messageBuilder) {
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			messageBuilder.accept(sb, listPassThrough);
			String strMessage = sb.toString();
			listActive.forEach(w -> w.write(level, location, strMessage));
		}
	}

	@Override
	public void writeTimeStamp(ObsLevel level) {
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		if (!listActive.isEmpty()) {
			String strTime = "time: " + FORMAT_TIME_STAMP.format(LocalDateTime.now());;
			listActive.forEach(w -> w.write(level, location, strTime));
		}
	}

	@Override
	public SingleObservationCollector createCollector(ObsLevel level) {
		SourceLocation location = SourceLocation.caller();
		List<ObservationWriter> listActive = getWriters(level);
		return new SingleObservationCollectorObj(listActive, level, location);
	}
}
