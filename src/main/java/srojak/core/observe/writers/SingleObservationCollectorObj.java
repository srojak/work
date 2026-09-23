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
package srojak.core.observe.writers;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsWriterMethods;
import srojak.core.observe.ObservationCommonWriter;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public class SingleObservationCollectorObj
		implements SingleObservationCollector, AutoCloseable {
	private final List<? extends ObservationCommonWriter> _listWriters;
	private final ObsLevel _level;
	private final SourceLocation _locOrigin;
	private final StringBuilder _sb;
	private boolean _bIsLevelOn;
	
	/**
	 * 
	 */
	public SingleObservationCollectorObj(List<? extends ObservationCommonWriter> listActiveWriters, 
			ObsLevel level, SourceLocation locOrigin) {
		Objects.requireNonNull(listActiveWriters, "listActiveWriters");
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(locOrigin, "locOrigin");
		_listWriters = listActiveWriters;
		_level = level;
		_locOrigin = locOrigin;
		_sb = new StringBuilder();
		_bIsLevelOn = !_listWriters.isEmpty();
	}
	
	public SingleObservationCollectorObj(ObservationCommonWriter writer,
			ObsLevel level, SourceLocation locOrigin) {
		Objects.requireNonNull(writer, "writer");
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(locOrigin, "locOrigin");
		_listWriters = List.of(writer);
		_level = level;
		_locOrigin = locOrigin;
		_sb = new StringBuilder();
		_bIsLevelOn = writer.canWriteAt(level);
	}

	@Override
	public ObsLevel getLevel() {
		return _level;
	}

	@Override
	public boolean isActive() {
		return _bIsLevelOn;
	}

	@Override
	public SingleObservationCollector append(boolean value) {
		_sb.append(value);
		return this;
	}

	@Override
	public SingleObservationCollector append(char value) {
		_sb.append(value);
		return this;
	}

	@Override
	public SingleObservationCollector append(int value) {
		_sb.append(value);
		return this;
	}

	@Override
	public SingleObservationCollector append(long value) {
		_sb.append(value);
		return this;
	}

	@Override
	public SingleObservationCollector append(float value) {
		_sb.append(value);
		return this;
	}

	@Override
	public SingleObservationCollector append(double value) {
		_sb.append(value);
		return this;
	}

	@Override
	public SingleObservationCollector append(String strText) {
		_sb.append(strText);
		return this;
	}

	@Override
	public SingleObservationCollector append(Object obj) {
		_sb.append(obj);
		return this;
	}

	@Override
	public SingleObservationCollector append(StringBuffer sbuf) {
		_sb.append(sbuf);
		return this;
	}

	@Override
	public SingleObservationCollector append(CharSequence cs) {
		_sb.append(cs);
		return this;
	}

	@Override
	public SingleObservationCollector append(CharSequence cs, int start, int end) {
		_sb.append(cs, start, end);
		return this;
	}

	@Override
	public SingleObservationCollector append(char[] str) {
		_sb.append(str);
		return this;
	}

	@Override
	public SingleObservationCollector append(char[] str, int offset, int len) {
		_sb.append(str, offset, len);
		return this;
	}

	@Override
	public SingleObservationCollector appendFormat(String format, Object... args) {
		SourceLocation loc = SourceLocation.caller();
		_sb.append(ObsWriterMethods.formatSafely(loc, format, args));
		return this;
	}

	@Override
	public void alsoWriteTo(PrintStream output) {
		Objects.requireNonNull(output, "output");
		output.println(_sb.toString());
	}

	@Override
	public void commit() {
		if (_bIsLevelOn) {
			String strText = _sb.toString();
			_listWriters.forEach(w -> w.write(this, _locOrigin, strText));
		}
		_sb.delete(0, _sb.length());
		_bIsLevelOn = false;
	}

	@Override
	public void close() throws Exception {
		if (_sb.length() > 0 && _bIsLevelOn) {
			_listWriters.forEach(w -> w.writeDiagnostic(_locOrigin, "collector never committed"));
		}
	}

}
