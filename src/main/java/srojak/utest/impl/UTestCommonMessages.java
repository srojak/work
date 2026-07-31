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

import srojak.utest.UnitTestSeries;
import srojak.utest.identifiers.TestInstanceIdentifier;

/**
 * @author Stephen
 *
 */
public class UTestCommonMessages {
	
	public static StringBuilder startMessageLine(UnitTestSeries utest) {
		StringBuilder sb = new StringBuilder("Test series");
		sb.append(utest.getName());
		return sb;
	}

	public static StringBuilder startTestMessageLine(UnitTestSeries utest,
			String strTest) {
		StringBuilder sb = startMessageLine(utest);
		sb.append(", test ");
		sb.append(strTest);
		sb.append(": ");
		return sb;
	}
	
	public static StringBuilder startTestMessageLine(UnitTestSeries utest,
			TestInstanceIdentifier ident) {
		StringBuilder sb = startMessageLine(utest);
		sb.append(", ");
		sb.append(ident.getText());
		sb.append(": ");
		return sb;
	}
}
