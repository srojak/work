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

import srojak.numerics.OrderedComparison;
import srojak.utest.UnitTestSeries;
import srojak.utest.conditions.UnitTestDoubleValueComparison;

/**
 * @author Stephen
 *
 */
public class DoubleComparisonsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("DoubleComparisons");

		double dLarge = 100000.0d;
		double dSmall = 0.5d;
		double dHalf = 1.0d / 2.0d;
		
		series.expectValueWhere("comparison", "dLarge",
				new UnitTestDoubleValueComparison(OrderedComparison.EQ, 100000.0d), dLarge);
		series.expectValueWhere("comparison", "dLarge", 
				new UnitTestDoubleValueComparison(OrderedComparison.GT, 250.0d), dLarge);
		series.expectValueWhere("subtract", "dSmall - dHalf", 
				new UnitTestDoubleValueComparison(OrderedComparison.EQ, 0.0d), dSmall - dHalf);
		series.expectValueWhere("comparison", "dSmall", 
				new UnitTestDoubleValueComparison(OrderedComparison.LE, 0.5d), dHalf);
		
		series.complete();
	}

}
