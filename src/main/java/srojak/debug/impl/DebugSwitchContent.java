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

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsPassThroughList;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.SourceDetail;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchKey;

/**
 * @author Stephen
 *
 */
public final class DebugSwitchContent 
		implements DebugSwitch {
	private final DebugSwitchKey _key;
	private ObsLevel _level;
	private boolean _bShowSource;
	private SourceDetail _sdetail;
	private SwitchControlSetRecord _setDefinedBy;
	
	/**
	 * 
	 */
	protected DebugSwitchContent(DebugSwitchKey key) {
		Objects.requireNonNull(key, "key");
		_key = key;
		_level = ObsLevel.NONE;
		_bShowSource = false;
		_sdetail = SourceDetail.NONE;
		_setDefinedBy = null;
	}
	
	public DebugSwitchContent(DebugSwitchKey key, SwitchControlSetRecord csr) {
		Objects.requireNonNull(key, "key");
		_key = key;
		_level = ObsLevel.NONE;
		_bShowSource = false;
		_sdetail = SourceDetail.NONE;
		_setDefinedBy = csr;
	}

	@Override
	public DebugSwitchKey getKey() {
		return _key;
	}

	@Override
	public boolean isLevelAccepted(ObsLevel level) {
		return true;
	}

	@Override
	public ObsLevel getLevel() {
		return _level;
	}
	
	public void setLevel(ObsLevel level) {
		_level = level;
	}

	@Override
	public boolean isLevelAtLeast(ObsLevel level) {
		Objects.requireNonNull(level, "level");
		return _level.isLevelAtLeast(level);
	}

	@Override
	public boolean showSourceLocations() {
		return _bShowSource;
	}
	
	public void setShowSourceLocations(boolean bState) {
		_bShowSource = bState;
		_sdetail = bState ? SourceDetail.ALL : SourceDetail.CLASS_ONLY;
	}
	
	@Override
	public String getDefiningControlSet() {
		return _setDefinedBy != null ? _setDefinedBy.getName() : "";
	}

	private void writeSourceLocation(StringBuilder sb, SourceLocation location, SourceDetail detail) {
		sb.append(location.toString(detail));
		sb.append(" ");
	}

	@Override
	public void write(ObsLevel level, String strMessage) {
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			if (strMessage == null) {
				strMessage = "(null)";
				DebugNexusCore.writeDiagnostic("passed null message string at " + location);
			}
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, location, _sdetail);
			sb.append(strMessage);
			DebugNexusCore.writeln(level, sb.toString());
		}
	}

	@Override
	public void write(ObsLevel level, Supplier<String> message) {
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, location, _sdetail);
			sb.append(message.get());
			DebugNexusCore.writeln(level, sb.toString());
		}
	}

	@Override
	public void write(ObsLevel level, ObsPassThroughList listPassThrough,
			Function<ObsPassThroughList, String> message) {
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, location, _sdetail);
			sb.append(message.apply(listPassThrough));
			DebugNexusCore.writeln(level, sb.toString());
		}
	}
	
	@Override
	public void writeException(ObsLevel level, Exception exc, boolean bShowStack) {
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, location, SourceDetail.ALL);
			if (exc == null) {
				sb.append("null exception");
			} else {
				sb.append("caught ");
				sb.append(exc.getClass().getSimpleName());
				sb.append("\n  ");
				sb.append(exc.getMessage());
			}
			DebugNexusCore.writeln(level, sb.toString());
			if (exc != null && bShowStack) {
				DebugNexusCore.writeStackTrace(level, exc);
			}
		}
	}

	private void writeTraceEnter(ObsLevel level, SourceLocation location, 
			Consumer<StringBuilder> messageBuilder) {
		StringBuilder sb = new StringBuilder();
		writeSourceLocation(sb, location, _sdetail);
		sb.append("enter ");
		sb.append(location.getMethodName());
		messageBuilder.accept(sb);
		DebugNexusCore.writeln(level, sb.toString());
	}
	
	private void writeTraceReturn(ObsLevel level, SourceLocation location, 
			Consumer<StringBuilder> messageBuilder) {
		StringBuilder sb = new StringBuilder();
		writeSourceLocation(sb, location, _sdetail);
		sb.append("return from ");
		sb.append(location.getMethodName());
		messageBuilder.accept(sb);
		DebugNexusCore.writeln(ObsLevel.TRACE, sb.toString());
	}

	@Override
	public void writeTraceEnter(TraceLevel level) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			writeTraceEnter(levelObs, location, sb -> { });
		}		
	}

	@Override
	public void writeTraceEnter(TraceLevel level, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			writeTraceEnter(levelObs, location, sb -> {
				sb.append(' ');
				sb.append(message.get());
			});
		}		
	}

	@Override
	public void writeTraceReturn(TraceLevel level) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			writeTraceReturn(levelObs, location, sb -> { });
		}		
	}

	@Override
	public void writeTraceReturn(TraceLevel level, Supplier<String> message) {
		Objects.requireNonNull(level, "level");
		ObsLevel levelObs = level.getObsLevel();
		if (isLevelAtLeast(levelObs)) {
			SourceLocation location = SourceLocation.caller();
			writeTraceReturn(levelObs, location, sb -> { 
				sb.append(' ');
				sb.append(message.get());
			});
		}		
	}

	@Override
	public void buildAndWrite(ObsLevel level, Consumer<StringBuilder> messageBuilder) {
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, location, _sdetail);
			messageBuilder.accept(sb);
			DebugNexusCore.writeln(level, sb.toString());
		}
	}

	@Override
	public void buildAndWrite(ObsLevel level, ObsPassThroughList listPassThrough,
			BiConsumer<StringBuilder, ObsPassThroughList> messageBuilder) {
		if (isLevelAtLeast(level)) {
			SourceLocation location = SourceLocation.caller();
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, location, _sdetail);
			messageBuilder.accept(sb, listPassThrough);
			DebugNexusCore.writeln(level, sb.toString());
		}
	}

	@Override
	public void writeDiagnostic(String strText) {
		DebugNexusCore.writeDiagnostic(strText);		
	}

	@Override
	public ObservationCollector createCollector(ObsLevel level) {
		SourceLocation loc = SourceLocation.caller();
		return new DebugObsCollectorObj(this, loc, level);
	}

	@Override
	public void write(ObservationCollector collector, SourceLocation locOrigin, String strText) {
		if (isLevelAtLeast(collector.getLevel())) {
			StringBuilder sb = new StringBuilder();
			writeSourceLocation(sb, locOrigin, _sdetail);
			sb.append(strText);
			DebugNexusCore.writeln(collector.getLevel(), sb.toString());
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
		StringBuilder sb = new StringBuilder("switch(");
		sb.append(_key);
		sb.append(", ");
		sb.append(_level.getName());
		sb.append(", locs=");
		sb.append(_bShowSource);
		sb.append(')');
		return sb.toString();
	}

}
