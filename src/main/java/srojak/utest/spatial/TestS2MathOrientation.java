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

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterPrintStream;
import srojak.spatial.S2CompassDirection;
import srojak.spatial.S2Direction;
import srojak.spatial.S2Offset;
import srojak.spatial.S2Orientation;
import srojak.spatial.impl.S2OrientationValidator;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestEqualsMethods;

/**
 * @author Stephen
 *
 */
public class TestS2MathOrientation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("S2MathOrientation");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);	
		series.getOptions().setStopOnFailure(true);
		
		UnitTestEqualsMethods<S2Direction> methodDirEq
			= new UnitTestEqualsMethods<S2Direction>();
		UnitTestEqualsMethods<S2Offset> methodOffsetEq
			= new UnitTestEqualsMethods<S2Offset>();
		
		S2Orientation orient = S2Orientation.math();
		series.expectValueEquals("orientation", "increasing direction", methodDirEq,
				S2CompassDirection.NorthEast, orient.getIncreasingDirection());
		
		S2OrientationValidator validator = new S2OrientationValidator(orient);
		validator.setObservationWriter(writer);
		series.expectValue("validator", "validate", true, validator.validateOffsets());
		
		S2Offset offset = new S2Offset(2, 0);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.East, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.East, orient.findNearestDirection(offset));
		
		offset = new S2Offset(10, 1);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.NorthEast, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.East, orient.findNearestDirection(offset));
		
		offset = new S2Offset(5, 5);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.NorthEast, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.NorthEast, orient.findNearestDirection(offset));
		
		offset = new S2Offset(1, 10);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.NorthEast, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.North, orient.findNearestDirection(offset));
		
		offset = new S2Offset(0, 3);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.North, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.North, orient.findNearestDirection(offset));
		
		offset = new S2Offset(-1, 10);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.NorthWest, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.North, orient.findNearestDirection(offset));
		
		offset = new S2Offset(-5, 5);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.NorthWest, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.NorthWest, orient.findNearestDirection(offset));
		
		offset = new S2Offset(-10, 1);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.NorthWest, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.West, orient.findNearestDirection(offset));
		
		offset = new S2Offset(-6, 0);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.West, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.West, orient.findNearestDirection(offset));
		
		offset = new S2Offset(-10, -1);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.SouthWest, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.West, orient.findNearestDirection(offset));
		
		offset = new S2Offset(-5, -5);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.SouthWest, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.SouthWest, orient.findNearestDirection(offset));
		
		offset = new S2Offset(-1, -10);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.SouthWest, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.South, orient.findNearestDirection(offset));
		
		offset = new S2Offset(0, -3);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.South, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.South, orient.findNearestDirection(offset));
		
		offset = new S2Offset(1, -10);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.SouthEast, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.South, orient.findNearestDirection(offset));
		
		offset = new S2Offset(5, -5);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.SouthEast, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.SouthEast, orient.findNearestDirection(offset));
		
		offset = new S2Offset(10, -1);
		series.writeMessageLine(ObsLevel.INFO, "offset = " + offset);
		series.expectValueEquals("orientation", "direction", methodDirEq,
				S2CompassDirection.SouthEast, orient.findDirection(offset));
		series.expectValueEquals("orientation", "nearest direction", methodDirEq,
				S2CompassDirection.East, orient.findNearestDirection(offset));
				
		series.expectValueEquals("orientation", "zero offset", methodOffsetEq,
				new S2Offset(0, 0), orient.offset(S2CompassDirection.East, 0));
		
		series.expectValueEquals("orientation", "offset", methodOffsetEq,
				new S2Offset(4, 0), orient.offset(S2CompassDirection.East, 4));
		
		series.expectValueEquals("orientation", "offset", methodOffsetEq,
				new S2Offset(7, 7), orient.offset(S2CompassDirection.NorthEast, 7));
		
		series.expectValueEquals("orientation", "offset", methodOffsetEq,
				new S2Offset(0, 5), orient.offset(S2CompassDirection.North, 5));
		
		series.expectValueEquals("orientation", "offset", methodOffsetEq,
				new S2Offset(-7, 7), orient.offset(S2CompassDirection.NorthWest, 7));
		
		series.expectValueEquals("orientation", "offset", methodOffsetEq,
				new S2Offset(-4, 0), orient.offset(S2CompassDirection.West, 4));
		
		series.expectValueEquals("orientation", "offset", methodOffsetEq,
				new S2Offset(-7, -7), orient.offset(S2CompassDirection.SouthWest, 7));
		
		series.expectValueEquals("orientation", "offset", methodOffsetEq,
				new S2Offset(0, -5), orient.offset(S2CompassDirection.South, 5));
		
		series.expectValueEquals("orientation", "offset", methodOffsetEq,
				new S2Offset(7, -7), orient.offset(S2CompassDirection.SouthEast, 7));
				
		series.complete();
	}

}
