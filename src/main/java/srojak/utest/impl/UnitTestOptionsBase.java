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

import srojak.numerics.DoubleComparer;
import srojak.numerics.DoublePrecisionComparer;
import srojak.numerics.FloatComparer;
import srojak.numerics.SinglePrecisionComparer;

/**
 * @author Stephen
 *
 */
public class UnitTestOptionsBase {
	private boolean _bStopOnFailure;
	private boolean _bShowStackOnExcepts;
	private DoublePrecisionComparer _comparerDouble;
	private SinglePrecisionComparer _comparerFloat;

	public UnitTestOptionsBase() {
		_bStopOnFailure = false;	
		_bShowStackOnExcepts = false;
		_comparerDouble = new DoubleComparer(1.0e-10);
		_comparerFloat = new FloatComparer((float) 1.0e-10);
	}
	
	/**
	 * Gets the current stop on failure setting.
	 * @return The current setting.
	 */
	public boolean getStopOnFailure() {
		return _bStopOnFailure;
	}
	
	/**
	 * Sets the the current stop on failure setting.
	 * @param bState The setting the tests will use.
	 */
	public void setStopOnFailure(boolean bState) {
		_bStopOnFailure = bState;
	}
	
	/**
	 * Gets the current setting for printing the stack on a failure exception.
	 * @return The current setting.
	 */
	public boolean getShowStackOnExceptions() {
		return _bShowStackOnExcepts;
	}
	
	/**
	 * Sets the current setting for printing the stack on a failure exception.
	 * @param bState The setting the tests will use.
	 */
	public void setShowStackOnExceptions(boolean bState) {
		_bShowStackOnExcepts = bState;
	}
	
	public DoublePrecisionComparer getDoubleComparer() {
		return _comparerDouble;
	}
	
	public void setDoubleComparer(DoublePrecisionComparer comparer) {
		Objects.requireNonNull(comparer, "comparer");
		_comparerDouble = comparer;
	}
	
	public SinglePrecisionComparer getFloatComparer() {
		return _comparerFloat;
	}
	
	public void setFloatComparer(SinglePrecisionComparer comparer) {
		Objects.requireNonNull(comparer, "comparer");
		_comparerFloat = comparer;
	}
}
