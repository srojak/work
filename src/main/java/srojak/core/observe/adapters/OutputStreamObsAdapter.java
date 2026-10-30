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
package srojak.core.observe.adapters;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import srojak.core.observe.HasObsLevel;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollectorCore;

/**
 * @author Stephen
 *
 */
public class OutputStreamObsAdapter
		extends OutputStream 
		implements HasObsLevel {
	private final StringBuilder _buf;
	private final ObservationCollectorCore _collectObs;
	private ObsLevel _level;
	
	/**
	 * 
	 */
	public OutputStreamObsAdapter(ObservationCollectorCore collector, ObsLevel level) {
		Objects.requireNonNull(collector, "collector");
		ObsLevel.validateEventLevel(level);
		_buf = new StringBuilder();
		_collectObs = collector;
		_level = level;
	}

	public OutputStreamObsAdapter(ObservationCollectorCore collector, ObsLevel level, int initialCapacity) {
		Objects.requireNonNull(collector, "collector");
		ObsLevel.validateEventLevel(level);
		_buf = new StringBuilder(initialCapacity);
		_collectObs = collector;
		_level = level;
	}

	@Override
	public ObsLevel getObsLevel() {
		return _level;
	}

	@Override
	public void setObsLevel(ObsLevel level) {
		ObsLevel.validateEventLevel(level);
		_level = level;
	}
	
	private void orderlyFlush() {
		_collectObs.recordNoLocation(_level, _buf.toString());
		_buf.delete(0, _buf.length());
	}

	@Override
	public void write(int b) throws IOException {
		byte bb = (byte) b;
		if (bb == '\n') {
			orderlyFlush();
		} else {
			_buf.append((char) bb);
		}
	}

	@Override
	public void flush() 
			throws IOException {
		if (_buf.length() > 0) {
			orderlyFlush();
		}
	}

	@Override
	public void close() 
			throws IOException {
		if (_buf.length() > 0) {
			orderlyFlush();
			_buf.trimToSize();
		}
	}

}
