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
import srojak.core.observe.ObservationCommonWriter;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.code.CodeValuesObsInstance;
import srojak.core.observe.code.CodeObsInstance;
import srojak.core.observe.code.CodeValue;

/**
 * @author Stephen
 *
 */
public class SingleObservationCollectorObj
		implements SingleObservationCollector, AutoCloseable {
	private final List<? extends ObservationCommonWriter> _listWriters;
	private final CodeValuesObsInstance _obsCode;
	private boolean _bIsLevelOn;
	private boolean _bCommitted;
	
	/**
	 * 
	 */
	public SingleObservationCollectorObj(List<? extends ObservationCommonWriter> listActiveWriters, 
			ObsLevel level, SourceLocation locOrigin) {
		Objects.requireNonNull(listActiveWriters, "listActiveWriters");
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(locOrigin, "locOrigin");
		_obsCode = new CodeValuesObsInstance(level, locOrigin);
		_listWriters = listActiveWriters;
		_bIsLevelOn = !_listWriters.isEmpty();
		_bCommitted = false;
	}
	
	public SingleObservationCollectorObj(ObservationCommonWriter writer,
			ObsLevel level, SourceLocation locOrigin) {
		Objects.requireNonNull(writer, "writer");
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(locOrigin, "locOrigin");
		_listWriters = List.of(writer);
		_obsCode = new CodeValuesObsInstance(level, locOrigin);
		_bIsLevelOn = writer.canWriteAt(level);
		_bCommitted = false;
	}

	@Override
	public ObsLevel getObsLevel() {
		return _obsCode.level();
	}

	@Override
	public boolean isActive() {
		return _bIsLevelOn;
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
		ObservationCollectorBase cbase = (ObservationCollectorBase) collector;
		cbase.writeCodeObservation(_obsCode);
	}

	@Override
	public void commit() {
		if (_bIsLevelOn) {
			_listWriters.forEach(w -> w.writeObserved(_obsCode));
		}
		_obsCode.releaseContent();
		_bIsLevelOn = false;
	}

	@Override
	public void close() throws Exception {
		if (_bIsLevelOn && !_bCommitted) {
			_listWriters.forEach(w -> w.writeDiagnostic(_obsCode.origin(), "collector never committed"));
		}
	}
}
