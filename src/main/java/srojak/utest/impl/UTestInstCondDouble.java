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

import srojak.numerics.DoublePrecisionComparer;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestConditionDouble;
import srojak.utest.UnitTestSeries;
import srojak.utest.identifiers.TestInstanceIdentifier;

/**
 * @author Stephen
 *
 */
public class UTestInstCondDouble
			extends UTestInstValueBase {
	private final DoublePrecisionComparer _comparer;

	/**
	 * @param utest
	 * @param strInstance
	 * @param strValueName
	 */
	public UTestInstCondDouble(UnitTestSeries utest, TestInstanceIdentifier idInstance,
			String strValueName, DoublePrecisionComparer comparer) {
		super(utest, idInstance, strValueName);
		Objects.requireNonNull(comparer, "comparer");
		_comparer = comparer;
	}
	
	public void execute(UnitTestConditionDouble condition, double dActual) {
		StringBuilder sb = getInitialString();
		sb.append(String.format("(ctol=%e)", _comparer.getEpsilon()));
		sb.append(" expect ");
		sb.append(condition.getConditionDesc());
		sb.append(", actual=");
		sb.append(dActual);
		setOutcome(TestOutcome.evaluate(() -> condition.test(dActual, _comparer)));
		writeOutcomeMessage(getOutcome(), sb.toString());
	}
}
