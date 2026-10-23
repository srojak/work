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
package srojak.core.specialized;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class StatusAccumulator 
		implements StatusCounter {
	private final IntegerCounter _successes;
	private final IntegerCounter _warnings;
	private final IntegerCounter _errors;
	private final List<Throwable> _listFaults;
	
	public StatusAccumulator() {
		_successes = new IntegerCounter();
		_warnings = new IntegerCounter();
		_errors = new IntegerCounter();
		_listFaults = new LinkedList<Throwable>();
	}
	
	public IntegerCounter successCounter() {
		return _successes;
	}
	
	public IntegerCounter warningCounter() {
		return _warnings;
	}
	
	public IntegerCounter errorCounter() {
		return _errors;
	}
	
	public void clearAll() {
		_successes.reset();
		_warnings.reset();
		_errors.reset();
		_listFaults.clear();
	}
	
	public void addFault(Throwable t) {
		Objects.requireNonNull(t, "t");
		_listFaults.add(t);
	}

	@Override
	public int successes() {
		return _successes.value();
	}

	@Override
	public int warnings() {
		return _warnings.value();
	}

	@Override
	public int errors() {
		return _errors.value();
	}

	@Override
	public boolean hasFaults() {
		return !_listFaults.isEmpty();
	}

	@Override
	public List<Throwable> faults() {
		return List.copyOf(_listFaults);
	}
}
