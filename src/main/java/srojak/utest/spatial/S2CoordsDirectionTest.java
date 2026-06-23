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
import srojak.spatial.S2Coords;
import srojak.spatial.S2Direction;
import srojak.spatial.S2Orientation;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestEqualsMethods;

/**
 * @author Stephen
 *
 */
public class S2CoordsDirectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("S2CoordsDirectionTest");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);
		
		UnitTestEqualsMethods<S2Direction> methodEq
			= new UnitTestEqualsMethods<S2Direction>();

		// try to provoke the error seen earlier
		S2Orientation orientation = S2Orientation.graphics();
		S2Coords coordsFrom = new S2Coords(4, 4);
		S2Coords coordsTo = new S2Coords(5, 4);
		
		series.expectValueEquals("find direction", "1E", methodEq, S2CompassDirection.East,
				coordsFrom.getDirectionTo(orientation, coordsTo));
		
		coordsFrom = coordsTo;
		coordsTo = new S2Coords(6, 5);
		
		series.expectValueEquals("find direction", "2SE", methodEq, S2CompassDirection.SouthEast,
				coordsFrom.getDirectionTo(orientation, coordsTo));
		
		coordsFrom = coordsTo;
		coordsTo = new S2Coords(7, 5);
		
		series.expectValueEquals("find direction", "3E", methodEq, S2CompassDirection.East,
				coordsFrom.getDirectionTo(orientation, coordsTo));
		
		series.complete();
	}

}
