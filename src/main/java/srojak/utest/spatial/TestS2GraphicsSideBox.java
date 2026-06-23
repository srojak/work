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
import srojak.spatial.S2FieldSize;
import srojak.spatial.S2Offset;
import srojak.spatial.S2Orientation;
import srojak.spatial.S2Rect;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestEqualsMethods;

/**
 * @author Stephen
 *
 */
public class TestS2GraphicsSideBox {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("S2GraphicsOrientation");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);	
		series.getOptions().setStopOnFailure(true);
		
		UnitTestEqualsMethods<S2Rect> methodRectEq
			= new UnitTestEqualsMethods<S2Rect>();
		
		S2Orientation orient = S2Orientation.graphics();
		S2FieldSize szField = new S2FieldSize(80, 64);
		S2Rect rectExpect = new S2Rect(0, 0, 10, szField.height);
		S2CompassDirection side = S2CompassDirection.West;
		series.expectValueEquals("side rect", side.getName(), methodRectEq, rectExpect,
				orient.getSideRect(side, szField, 10, 10));
		
		side = S2CompassDirection.North;
		rectExpect = new S2Rect(0, 0, szField.width, 10);
		series.expectValueEquals("side rect", side.getName(), methodRectEq, rectExpect,
				orient.getSideRect(side, szField, 10, 10));
		
		side = S2CompassDirection.East;
		rectExpect = new S2Rect(szField.width - 10, 0, 10, szField.height);
		series.expectValueEquals("side rect", side.getName(), methodRectEq, rectExpect,
				orient.getSideRect(side, szField, 10, 10));
		
		side = S2CompassDirection.South;
		rectExpect = new S2Rect(0, szField.height - 10, szField.width, 10);
		series.expectValueEquals("side rect", side.getName(), methodRectEq, rectExpect,
				orient.getSideRect(side, szField, 10, 10));
		
		side = S2CompassDirection.NorthWest;
		rectExpect = new S2Rect(0, 0, 10, 10);
		series.expectValueEquals("side rect", side.getName(), methodRectEq, rectExpect,
				orient.getSideRect(side, szField, 10, 10));
		
		series.complete();
	}

}
