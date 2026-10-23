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
package srojak.core.sequence;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Stephen
 *
 */
public class SimpleSequencer 
		extends SequencerBase {
	private final AtomicLong _sequence;

	/**
	 * @param idSeries
	 */
	public SimpleSequencer(long idSeries) {
		super(idSeries);
		_sequence = new AtomicLong(0L);
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public long current() {
		return _sequence.get();
	}

	@Override
	public long next() {
		return _sequence.incrementAndGet();
	}

}
