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
package srojak.core.observe;

import java.io.PrintStream;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class ObservationCollectorObj
		implements ObservationCollector, AutoCloseable {
	private final ObservationCommonWriter _writer;
	private final ObsLevel _level;
	private final SourceLocation _locOrigin;
	private final StringBuilder _sb;
	private boolean _bIsLevelOn;
	
	/**
	 * 
	 */
	protected ObservationCollectorObj(ObservationCommonWriter writer, 
			ObsLevel level, SourceLocation locOrigin) {
		Objects.requireNonNull(writer, "writer");
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(locOrigin, "locOrigin");
		_writer = writer;
		_level = level;
		_locOrigin = locOrigin;
		_sb = new StringBuilder();
		_bIsLevelOn = _writer.isLevelAccepted(_level);
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
	public ObservationCollector append(boolean value) {
		_sb.append(value);
		return this;
	}

	@Override
	public ObservationCollector append(char value) {
		_sb.append(value);
		return this;
	}

	@Override
	public ObservationCollector append(int value) {
		_sb.append(value);
		return this;
	}

	@Override
	public ObservationCollector append(long value) {
		_sb.append(value);
		return this;
	}

	@Override
	public ObservationCollector append(float value) {
		_sb.append(value);
		return this;
	}

	@Override
	public ObservationCollector append(double value) {
		_sb.append(value);
		return this;
	}

	@Override
	public ObservationCollector append(String strText) {
		_sb.append(strText);
		return this;
	}

	@Override
	public ObservationCollector append(Object obj) {
		_sb.append(obj);
		return this;
	}

	@Override
	public ObservationCollector append(StringBuffer sbuf) {
		_sb.append(sbuf);
		return this;
	}

	@Override
	public ObservationCollector append(CharSequence cs) {
		_sb.append(cs);
		return this;
	}

	@Override
	public ObservationCollector append(CharSequence cs, int start, int end) {
		_sb.append(cs, start, end);
		return this;
	}

	@Override
	public ObservationCollector append(char[] str) {
		_sb.append(str);
		return this;
	}

	@Override
	public ObservationCollector append(char[] str, int offset, int len) {
		_sb.append(str, offset, len);
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
			_writer.write(this, _locOrigin, _sb.toString());
		}
		_sb.delete(0, _sb.length());
		_bIsLevelOn = false;
	}

	@Override
	public void close() throws Exception {
		if (_sb.length() > 0 && _bIsLevelOn) {
			_writer.writeDiagnostic("Collector created at " + _locOrigin.toString() + " never committed");
		}
	}

}
