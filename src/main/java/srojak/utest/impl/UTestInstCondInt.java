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
import srojak.utest.UnitTestConditionInt;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class UTestInstCondInt
		extends UTestInstValueBase {

	/**
	 * @param utest
	 * @param strInstance
	 * @param strValueName
	 */
	public UTestInstCondInt(UnitTestSeries utest, String strInstance, String strValueName) {
		super(utest, strInstance, strValueName);
	}

	public void execute(UnitTestConditionInt condition, int nActual) {
		StringBuilder sb = getInitialString();
		sb.append(" expect ");
		sb.append(condition.getConditionDesc());
		sb.append(", actual=");
		sb.append(nActual);
		setOutcome(TestOutcome.evaluate(() -> condition.test(nActual)));
		writeOutcomeMessage(getOutcome(), sb.toString());
	}
}
