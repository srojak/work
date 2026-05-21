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

import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestConditionDyadic;

/**
 * @author Stephen
 *
 */
public class UTestInstDyadic<T> 
		extends UTestInstValueBase {
	private final UnitTestConditionDyadic<T> _condition;

	/**
	 * @param utest
	 * @param strInstance
	 */
	public UTestInstDyadic(UnitTestSeries utest, String strInstance,
			String strValueName, UnitTestConditionDyadic<T> condition) {
		super(utest, strInstance, strValueName);
		_condition = condition;
	}

	public void execute(T expected, T actual) {
		StringBuilder sb = getInitialString();
		sb.append("compare(");
		sb.append(_condition.getConditionDesc());
		sb.append(" expect=");
		sb.append(expected);
		sb.append(", actual=");
		sb.append(actual);
		sb.append(')');
		setOutcome(TestOutcome.evaluate(() -> _condition.test(expected, actual)));
		writeOutcomeMessage(getOutcome(), sb.toString());
	}
}
