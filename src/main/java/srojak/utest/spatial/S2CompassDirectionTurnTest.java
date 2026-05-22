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
package srojak.utest.spatial;

import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterPrintStream;
import srojak.numerics.ConditionSense;
import srojak.spatial.S2CompassDirection;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestEqualsMethods;

/**
 * @author Stephen
 *
 */
public class S2CompassDirectionTurnTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("S2CompassDirectionTurn");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);
		
		UnitTestEqualsMethods<S2CompassDirection> methodEq
			= new UnitTestEqualsMethods<S2CompassDirection>();

		S2CompassDirection dirStart = S2CompassDirection.North;
		S2CompassDirection dir1
				= S2CompassDirection.findDirectionFor(dirStart.getDegrees().subtractAndNormalize(90));
		series.expectNull("find direction", "dir1", ConditionSense.IS_NOT, dir1);
		series.expectValueEquals("find direction", "dir1", methodEq, S2CompassDirection.West, dir1);
		
		series.complete();
	}

}
