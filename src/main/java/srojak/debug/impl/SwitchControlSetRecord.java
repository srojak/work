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
package srojak.debug.impl;

import java.util.Objects;

import srojak.debug.SwitchControlSet;

/**
 * @author Stephen
 *
 */
public class SwitchControlSetRecord
		implements SwitchControlSet {
	private final String _name;
	
	public SwitchControlSetRecord(String strName) {
		Objects.requireNonNull(strName, "strName");
		_name = strName;
	}

	@Override
	public String getName() {
		return _name;
	}

}
