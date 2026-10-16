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
package srojak.core.impl;

import srojak.core.CycleLimitException;
import srojak.core.safety.CycleIterationStop;

/**
 * @author Stephen
 *
 */
public class CycleIterationStopToken 
		extends StopTokenBase 
		implements CycleIterationStop {
	private int _nCycles;

	/**
	 * @param strName
	 * @param limit
	 */
	public CycleIterationStopToken(String strName, int limit) {
		super(strName, limit);
		_nCycles = 0;
	}

	@Override
	public int getLimit() {
		return _nLimit;
	}

	@Override
	public void reset() {
		_nCycles = 0;	
	}

	@Override
	public void increment() {
		if (++_nCycles > _nLimit) {
			throw new CycleLimitException(_strName, "cycle iteration stop exceeded");
		}
	}

}
