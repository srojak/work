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
package srojak.utest.impl;

import java.util.Objects;

import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public abstract class UTestInstValueBase
		extends UTestInstance {
	private final String _strValueName;
	
	public UTestInstValueBase(UnitTestSeries utest, String strInstance, String strValueName) {
		super(utest, strInstance);
		Objects.requireNonNull(strValueName);
		_strValueName = strValueName;
	}
	
	@Override
	protected StringBuilder getInitialString() {
		StringBuilder sb = super.getInitialString();
		sb.append(_strValueName);
		sb.append(' ');
		return sb;
	}
}
