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
package srojak.utest.numerics;

import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterPrintStream;
import srojak.numerics.CompassDegrees;
import srojak.numerics.OrderedComparison;
import srojak.utest.UnitTestConditionInt;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class CompassDegreesTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("CompassDegreesTest");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);

		CompassDegrees cdNorth = new CompassDegrees(0);
		// go left
		CompassDegrees cdLeft = cdNorth.subtractAndNormalize(90);
		series.expectValueWhere("cdLeft", "result",
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, 270),
					cdLeft.getValue());
		
		// opposite direction
		CompassDegrees cdBack = cdLeft.addAndNormalize(180);
		series.expectValueWhere("cdBack", "result",
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, 90),
					cdBack.getValue());
		
		CompassDegrees cdx = cdNorth.subtract(450);
		System.out.println("cdx = " + cdx);
		series.expectValue("cdx", "isNormalized", false, cdx.isNormalized());
		cdx.normalize();
		System.out.println("cdx = " + cdx);
		
		series.complete();
	}

}
