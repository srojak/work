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
package srojak.utest.core;

import srojak.core.observe.SourceLocation;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.numerics.OrderedComparison;
import srojak.utest.TestIdentifier;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class TestSourceLocation {

	public SourceLocation MethodOne(int nArg) {
		return SourceLocation.here();
	}
	
	public SourceLocation MethodTwo() {
		return SourceLocation.caller();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("TestObsLevel");
		series.getOptions().setObservationWriter(new ObservationWriterPrintStream(System.err));
		
		TestSourceLocation app = new TestSourceLocation();

		SourceLocation loc = app.MethodOne(1);
		System.out.println("at " + loc);
		series.expectValue(TestIdentifier.name("location"), "class", OrderedComparison.EQ,
				"srojak.utest.core.TestSourceLocation", loc.getClassName());
		series.expectValue(TestIdentifier.name("location"), "method", OrderedComparison.EQ, 
				"MethodOne", loc.getMethodName());
		series.expectValue(TestIdentifier.name("location"), "line", 
				OrderedComparison.GE, 15, loc.getLineNumber());
		
		loc = app.MethodTwo();
		System.out.println("at " + loc);
		
		series.complete();
	}

}
