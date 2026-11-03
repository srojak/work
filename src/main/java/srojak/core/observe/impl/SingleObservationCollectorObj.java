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

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsWriterMethods;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationCommon;
import srojak.core.observe.ObservationCommonWriter;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.code.CodeValue;
import srojak.core.observe.code.CodeValuesObsInstance;

/**
 * @author Stephen
 *
 */
public class SingleObservationCollectorObj
		implements SingleObservationCollector, ObservationCommon, AutoCloseable {
	private final List<? extends ObservationCommonWriter> _listWriters;
	private final CodeValuesObsInstance _obsCode;
	private final String _strLead;
	private boolean _bIsEnabled;
	private boolean _bCommitted;
	
	/**
	 * 
	 */
	public SingleObservationCollectorObj(List<? extends ObservationCommonWriter> listActiveWriters, 
			ObsLevel level, ObsLevel levelFilter, SourceLocation locOrigin, String strLead) {
		Objects.requireNonNull(listActiveWriters, "listActiveWriters");
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(levelFilter, "levelFilter");
		Objects.requireNonNull(locOrigin, "locOrigin");
		Objects.requireNonNull(strLead, "strLead");
		_obsCode = new CodeValuesObsInstance(level, locOrigin);
		_listWriters = listActiveWriters;
		_strLead = strLead;
		_bIsEnabled = levelFilter.isLevelAtLeast(level) && !_listWriters.isEmpty();
		_bCommitted = false;
	}
	
	public SingleObservationCollectorObj(ObservationCommonWriter writer,
			ObsLevel level, ObsLevel levelFilter, SourceLocation locOrigin, String strLead) {
		Objects.requireNonNull(writer, "writer");
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(levelFilter, "levelFilter");
		Objects.requireNonNull(locOrigin, "locOrigin");
		_listWriters = List.of(writer);
		_obsCode = new CodeValuesObsInstance(level, locOrigin);
		_strLead = strLead;
		_bIsEnabled = levelFilter.isLevelAtLeast(level) && writer.canWriteAt(level);
		_bCommitted = false;
	}

	@Override
	public ObsLevel getObsLevel() {
		return _obsCode.level();
	}

	@Override
	public boolean isActive() {
		return _bIsEnabled;
	}

	@Override
	public SingleObservationCollector append(boolean value) {
		_obsCode.appendContent(value);
		return this;
	}

	@Override
	public SingleObservationCollector append(char value) {
		_obsCode.appendContent(value);
		return this;
	}

	@Override
	public SingleObservationCollector append(int value) {
		_obsCode.appendContent(value);
		return this;
	}

	@Override
	public SingleObservationCollector append(long value) {
		_obsCode.appendContent(value);
		return this;
	}

	@Override
	public SingleObservationCollector append(float value) {
		_obsCode.appendContent(value);
		return this;
	}

	@Override
	public SingleObservationCollector append(double value) {
		_obsCode.appendContent(value);
		return this;
	}

	@Override
	public SingleObservationCollector append(String strText) {
		_obsCode.appendContent(strText);
		return this;
	}

	@Override
	public SingleObservationCollector append(Object obj) {
		_obsCode.appendContent(obj);
		return this;
	}

	@Override
	public SingleObservationCollector append(StringBuffer sbuf) {
		_obsCode.appendContent(sbuf);
		return this;
	}

	@Override
	public SingleObservationCollector append(CharSequence cs) {
		_obsCode.appendContent(cs);
		return this;
	}

	@Override
	public SingleObservationCollector append(CharSequence cs, int start, int end) {
		_obsCode.appendContent(cs, start, end);
		return this;
	}

	@Override
	public SingleObservationCollector append(char[] str) {
		_obsCode.appendContent(str);
		return this;
	}

	@Override
	public SingleObservationCollector append(char[] str, int offset, int len) {
		_obsCode.appendContent(str, offset, len);
		return this;
	}

	@Override
	public SingleObservationCollector appendFormat(String format, Object... args) {
		SourceLocation loc = SourceLocation.caller();
		_obsCode.formatContent(ObsWriterMethods.formatSafely(loc, format, args));
		return this;
	}

	@Override
	public SingleObservationCollector addValue(CodeValue value) {
		
		return null;
	}

	@Override
	public void alsoWriteTo(ObservationCollector collector) {
		Objects.requireNonNull(collector, "collector");
		ObservationCollectorWriterBase cbase = (ObservationCollectorWriterBase) collector;
		cbase.writeCodeObservation(_strLead, _obsCode);
	}

	@Override
	public void commit() {
		if (_bIsEnabled) {
			_listWriters.forEach(w -> w.writeObserved(_strLead, _obsCode));
		}
		_obsCode.releaseContent();
		_bIsEnabled = false;
	}

	@Override
	public void close() throws Exception {
		if (_bIsEnabled && !_bCommitted) {
			_listWriters.forEach(w -> w.writeDiagnostic(_obsCode.origin(), "collector never committed"));
		}
	}
}
