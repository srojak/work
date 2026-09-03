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
package srojak.cdo.events;

import java.awt.AWTEvent;

/**
 * @author Stephen
 *
 */
public class ScaleChangeEvent
		extends AWTEvent
		implements CDOEventID {

	private final double _dScale;
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 831805643664647004L;

	/**
	 * @param source
	 * @param id
	 */
	public ScaleChangeEvent(Object source, double dScale) {
		super(source, SCALE_CHANGED);
		_dScale = dScale;
	}
	
	public double getScale() {
		return _dScale;
	}

}
