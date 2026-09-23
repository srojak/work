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
package srojak.utest.self;

import srojak.core.observe.SourceLocation;
import srojak.numerics.OrderedComparison;
import srojak.utest.TestLocationIdentifier;
import srojak.utest.UnitTestConditionInt;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class TestLocationIdentifierTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("TestLocationIdentifier Test");

		TestLocationIdentifier ident = TestLocationIdentifier.name("trial");
		
		System.out.println(ident.getText());
		
		ident.setLocation(SourceLocation.here());
		
		System.out.println(ident.getText());
		
		int nValue = 2;
		
		series.expectValueWhere(ident.setLocation(SourceLocation.here()), "sample",
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, 2), nValue);
		
		series.expectValueWhere(ident.setCallingLocation(), "sample 2",
				UnitTestConditionInt.makeValueCondition(OrderedComparison.LT, 3), nValue);
		
		series.complete();
	}

}
