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
package srojak.psq.absrep;

import srojak.psq.PlaneSquare;
import srojak.spatial.S2Coords;

/**
 * @author Stephen
 *
 */
public class PlaneAbsRepSquare
		extends PlaneSquare
		implements PlaneAbsRepFlags {
	private Object _content;

	/**
	 * @param coords
	 */
	public PlaneAbsRepSquare(S2Coords coords) {
		super(coords);
		_content = null;
	}

	@Override
	public boolean canBeOccupied() {
		return !_flags.test(OFF_LIMITS);
	}
	
	public void setFlags(int ... masks) {
		_flags.set(masks);
	}
	
	public void clearFlags(int ... masks) {
		_flags.clear(masks);
	}
	
	public Object getContent() {
		return _content;
	}
	
	public void setContent(Object obContent) {
		_content = obContent;
	}
}
