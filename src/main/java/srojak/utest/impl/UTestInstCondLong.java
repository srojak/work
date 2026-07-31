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
import srojak.utest.UnitTestConditionLong;
import srojak.utest.UnitTestSeries;
import srojak.utest.identifiers.TestInstanceIdentifier;

/**
 * @author Stephen
 *
 */
public class UTestInstCondLong
		extends UTestInstValueBase {

	/**
	 * @param utest
	 * @param strInstance
	 * @param strValueName
	 */
	public UTestInstCondLong(UnitTestSeries utest, TestInstanceIdentifier idInstance, String strValueName) {
		super(utest, idInstance, strValueName);
		// TODO Auto-generated constructor stub
	}

	public void execute(UnitTestConditionLong condition, long lnActual) {
		StringBuilder sb = getInitialString();
		sb.append(" expect ");
		sb.append(condition.getConditionDesc());
		sb.append(", actual=");
		sb.append(lnActual);
		setOutcome(TestOutcome.evaluate(() -> condition.test(lnActual)));
		writeOutcomeMessage(getOutcome(), sb.toString());
	}
}
