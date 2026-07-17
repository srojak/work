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

import java.util.Objects;

import srojak.core.CycleDepthException;
import srojak.core.CycleDepthMonitor;
import srojak.core.CycleDepthStop;

/**
 * @author Stephen
 *
 */
public class CycleDepthMonitorToken 
		implements CycleDepthMonitor {
	private final CycleDepthStop _stop;
	private final int _depth;
	
	private CycleDepthMonitorToken(CycleDepthStop stop, int nDepth) {
		Objects.requireNonNull(stop, "stop");
		_stop = stop;
		_depth = nDepth;
		if (_depth >= _stop.getLimit()) {
			throw new CycleDepthException(_stop.getName(), "cycle depth stop exceeded");
		}
	}
	
	public CycleDepthMonitorToken(CycleDepthStop stop) {
		this(stop, 0);
	}

	@Override
	public int getDepth() {
		return _depth;
	}

	@Override
	public CycleDepthMonitor increment() {
		return new CycleDepthMonitorToken(_stop, _depth + 1);
	}

}
