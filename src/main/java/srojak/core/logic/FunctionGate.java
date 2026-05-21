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

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class FunctionGate {
	private BooleanMonadicFunction _fnGate;
	private boolean _bInput;
	
	public FunctionGate() {
		_fnGate = t -> t;
		_bInput = false;
	}
	
	public boolean evaluate() {
		return _fnGate.apply(_bInput);
	}
	
	public void setGateFunction(BooleanMonadicFunction function) {
		Objects.requireNonNull(function);
		_fnGate = function;
	}
	
	public void setInputState(boolean bState) {
		_bInput = bState;
	}
}
