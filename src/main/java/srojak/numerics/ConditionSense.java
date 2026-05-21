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
package srojak.numerics;

/**
 * @author Stephen
 *
 */
public enum ConditionSense {
	IS(true),
	IS_NOT(false);
	
	private final boolean _bExpect;
	private final String _strVerb;
	
	private ConditionSense(boolean bExpect) {
		_bExpect = bExpect;
		_strVerb = bExpect ? "is" : "is not";
	}
	
	public boolean getExpectedResult() {
		return _bExpect;
	}
	
	public boolean isExpectedResult(boolean bResult) {
		return _bExpect == bResult;
	}
	
	public String getVerb() {
		return _strVerb;
	}
}
