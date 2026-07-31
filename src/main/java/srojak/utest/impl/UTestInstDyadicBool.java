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
import srojak.utest.identifiers.TestInstanceIdentifier;

/**
 * @author Stephen
 *
 */
public class UTestInstDyadicBool
		extends UTestInstValueBase {

	/**
	 * @param utest
	 * @param strInstance
	 * @param strValueName
	 */
	public UTestInstDyadicBool(UnitTestSeries utest, TestInstanceIdentifier idInstance, String strValueName) {
		super(utest, idInstance, strValueName);
	}
	
	public void execute(boolean bExpected, boolean bActual) {
		StringBuilder sb = getInitialString();
		sb.append("is equal?");
		sb.append(" expect=");
		sb.append(bExpected);
		sb.append(", actual=");
		sb.append(bActual);
		setOutcome(TestOutcome.evaluate(() -> bExpected == bActual));
		writeOutcomeMessage(getOutcome(), sb.toString());
	}

}
