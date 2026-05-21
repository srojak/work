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
package srojak.core.logic;

/**
 * @author Stephen
 *
 */
public class SimpleGate {
	private boolean _bGate;
	
	public SimpleGate() {
		_bGate = false;
	}
	
	public boolean getGateState() {
		return _bGate;
	}
	
	public void setGateState(boolean bState) {
		_bGate = bState;
	}
	
	public boolean and(boolean bInput) {
		return _bGate && bInput;
	}
	
	public boolean or(boolean bInput) {
		return _bGate || bInput;
	}
	
	public boolean andAll(boolean... inputs) {
		if (!_bGate) {
			return false;
		}
		for (boolean bInput : inputs) {
			if (!bInput) {
				return false;
			}
		}
		return true;
	}
	
	public boolean orAny(boolean... inputs) {
		if (!_bGate) {
			return true;
		}
		for (boolean bInput : inputs) {
			if (bInput) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "gate[state=" + _bGate + "]";
	}
}
