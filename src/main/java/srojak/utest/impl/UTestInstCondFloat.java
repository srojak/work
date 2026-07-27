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

import srojak.numerics.SinglePrecisionComparer;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestConditionFloat;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class UTestInstCondFloat
		extends UTestInstValueBase {
	private final SinglePrecisionComparer _comparer;

	/**
	 * @param utest
	 * @param strInstance
	 * @param strValueName
	 */
	public UTestInstCondFloat(UnitTestSeries utest, String strInstance, 
			String strValueName, SinglePrecisionComparer comparer) {
		super(utest, strInstance, strValueName);
		Objects.requireNonNull(comparer, "comparer");
		_comparer = comparer;
	}
	
	public void execute(UnitTestConditionFloat condition, float fActual) {
		StringBuilder sb = getInitialString();
		sb.append(String.format("(ctol=%e)", _comparer.getEpsilon()));
		sb.append(" expect ");
		sb.append(condition.getConditionDesc());
		sb.append(", actual=");
		sb.append(fActual);
		setOutcome(TestOutcome.evaluate(() -> condition.test(fActual, _comparer)));
		writeOutcomeMessage(getOutcome(), sb.toString());
	}
}
