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
package srojak.mantle.impl;

import java.util.Objects;

import srojak.mantle.CycleDepthMonitor;
import srojak.mantle.CycleDepthStop;

/**
 * @author Stephen
 *
 */
public class CycleDepthStopToken
		implements CycleDepthStop {
	private final String _strName;
	private final int _nLimit;

	/**
	 * 
	 */
	public CycleDepthStopToken(String strName, int limit) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		if (limit <= 0) {
			throw new IllegalArgumentException("limit must be positive");
		}
		_strName = strName;
		_nLimit = limit;
	}

	@Override
	public String getName() {
		return _strName;
	}

	@Override
	public int getLimit() {
		return _nLimit;
	}

	@Override
	public CycleDepthMonitor createMonitor() {
		return new CycleDepthMonitorToken(this);
	}

}
