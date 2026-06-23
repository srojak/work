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
import srojak.spatial.S2CompassDirection;
import srojak.spatial.S2Direction;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestEqualsMethods;

/**
 * @author Stephen
 *
 */
public class S2CompassDirectionOpposeTest {

	/**
	 * 
	 */
	public S2CompassDirectionOpposeTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("S2CoordsDirectionTest");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);
		
		UnitTestEqualsMethods<S2Direction> methodEq
			= new UnitTestEqualsMethods<S2Direction>();
		
		series.expectValueEquals("opposite", "North", methodEq, S2CompassDirection.South,
				S2CompassDirection.North.getOppositeDirection());
		series.expectValueEquals("opposite", "NorthEast", methodEq, S2CompassDirection.SouthWest,
				S2CompassDirection.NorthEast.getOppositeDirection());
		series.expectValueEquals("opposite", "East", methodEq, S2CompassDirection.West,
				S2CompassDirection.East.getOppositeDirection());
		series.expectValueEquals("opposite", "SoutEast", methodEq, S2CompassDirection.NorthWest,
				S2CompassDirection.SouthEast.getOppositeDirection());
		series.expectValueEquals("opposite", "South", methodEq, S2CompassDirection.North,
				S2CompassDirection.South.getOppositeDirection());
		series.expectValueEquals("opposite", "SouthWest", methodEq, S2CompassDirection.NorthEast,
				S2CompassDirection.SouthWest.getOppositeDirection());
		series.expectValueEquals("opposite", "West", methodEq, S2CompassDirection.East,
				S2CompassDirection.West.getOppositeDirection());
		series.expectValueEquals("opposite", "NorthWest", methodEq, S2CompassDirection.SouthEast,
				S2CompassDirection.NorthWest.getOppositeDirection());
		
		series.complete();
	}

}
