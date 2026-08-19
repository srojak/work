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

/**
 * @author Stephen
 *
 * Provides three levels of tracing.
 */
public enum TraceLevel {
	HIGH(1, ObsLevel.TRACE),
	MEDIUM(2, ObsLevel.TRACE2),
	LOW(3, ObsLevel.TRACE3);
	
	private final int _nOrdinal;
	private final ObsLevel _levelObs;
	
	private TraceLevel(int nOrdinal, ObsLevel levelObs) {
		_nOrdinal = nOrdinal;
		_levelObs = levelObs;
	}
	
	public int getOrdinal() {
		return _nOrdinal;
	}
	
	public ObsLevel getObsLevel() {
		return _levelObs;
	}
}
