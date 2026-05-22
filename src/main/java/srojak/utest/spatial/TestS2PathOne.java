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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import srojak.core.observe.ObservationWriterPrintStream;
import srojak.spatial.S2CompassDirection;
import srojak.spatial.S2Coords;
import srojak.spatial.S2Direction;
import srojak.spatial.S2Formats;
import srojak.spatial.S2Formatter;
import srojak.spatial.S2Line;
import srojak.spatial.S2LinePath;
import srojak.spatial.S2LinePathCalc;
import srojak.spatial.S2Orientation;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestClassElementMethods;
import srojak.utest.helpers.UnitTestEqualsMethods;
import srojak.utest.instances.UnitTestCollectionComparer;

/**
 * @author Stephen
 *
 */
public class TestS2PathOne {
	private final S2Orientation _orientation;
	private S2LinePath _path;
	
	public static final UnitTestClassElementMethods<S2Coords> methodsCoordsElements
			= new UnitTestClassElementMethods<S2Coords>(S2Coords.class, SpatialTestCommon.FormatS2Coords);
	
	public TestS2PathOne(S2Orientation orientation) {
		Objects.requireNonNull(orientation, "orientation");
		_orientation = orientation;
		_path = new S2LinePathCalc(orientation);
		// be explicit
		_path.setTieBreaker((c1, c2) -> c1);
	}
	
	public S2Orientation getOrientation() {
		return _orientation;
	}
	
	public void TestHorizontalPath(UnitTestSeries series, S2Coords coordsStart, int length) {
		S2Coords coordsEnd = coordsStart.getOffsetCoords(length, 0);
		ArrayList<S2Coords> listExpected = new ArrayList<S2Coords>();
		listExpected.add(coordsStart);
		for (int i = 1; i <= length; i++) {
			listExpected.add(coordsStart.getOffsetCoords(i, 0));
		}
		S2Line line = new S2Line(coordsStart, coordsEnd);
		List<S2Coords> listActual = _path.getCoordsOnLine(line, false);
		UnitTestCollectionComparer<S2Coords> instance
				= series.createCollectionComparer("horizontal", "line", methodsCoordsElements);
		instance.compare(listExpected, listActual);
	}
	
	public void TestVerticalPath(UnitTestSeries series, S2Coords coordsStart, int length) {
		S2Coords coordsEnd = coordsStart.getOffsetCoords(0, length);
		ArrayList<S2Coords> listExpected = new ArrayList<S2Coords>();
		listExpected.add(coordsStart);
		for (int i = 1; i <= length; i++) {
			listExpected.add(coordsStart.getOffsetCoords(0, i));
		}
		S2Line line = new S2Line(coordsStart, coordsEnd);
		List<S2Coords> listActual = _path.getCoordsOnLine(line, false);
		UnitTestCollectionComparer<S2Coords> instance
				= series.createCollectionComparer("vertical", "line", methodsCoordsElements);
		instance.compare(listExpected, listActual);
	}
	
	public void TestPath(UnitTestSeries series, String strTest,
			S2Coords coordsStart, S2Coords coordsEnd, S2Coords[] expected) {
		S2Line line = new S2Line(coordsStart, coordsEnd);
		List<S2Coords> listActual = _path.getCoordsOnLine(line, false);
		System.out.println(S2Formatter.formatListOfCoords(strTest, 6,
				S2Formats.setOf(S2Formats.ENCLOSED), listActual));
		System.out.flush();
		UnitTestCollectionComparer<S2Coords> instance
		= series.createCollectionComparer(strTest, "line", methodsCoordsElements);
		instance.compare(expected, listActual);
	}
	
	public void TestShortDiagonalPath(UnitTestSeries series, String strTest,
			S2Coords coordsStart, S2Coords coordsEnd, S2Coords[] expected) {
		S2Line line = new S2Line(coordsStart, coordsEnd);
		List<S2Coords> listActual = _path.getCoordsOnLine(line, true);
		System.out.println(S2Formatter.formatListOfCoords(strTest + "s", 6,
				S2Formats.setOf(S2Formats.ENCLOSED), listActual));
		System.out.flush();
		UnitTestCollectionComparer<S2Coords> instance
		= series.createCollectionComparer(strTest, "short line", methodsCoordsElements);
		instance.compare(expected, listActual);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestS2PathOne app = new TestS2PathOne(S2Orientation.graphics());
		
		UnitTestSeries series = new UnitTestSeries("S2LinePath");
		series.getOptions().setObservationWriter(new ObservationWriterPrintStream(System.err));
			
		app.TestHorizontalPath(series, new S2Coords(6, 5), 4);
		
		app.TestVerticalPath(series,  new S2Coords(7, 8), 5);
		
		UnitTestEqualsMethods<S2Direction> methodsDirectionEquals
				= new UnitTestEqualsMethods<S2Direction>();
		
		S2Coords coordsStart = new S2Coords(0, 0);
		S2Coords coordsEnd = new S2Coords(2, 6);
		series.expectValueEquals("diagonal 1", "direction", methodsDirectionEquals,
				S2CompassDirection.SouthEast,
				coordsStart.getDirectionTo(app.getOrientation(), coordsEnd));
		
		S2Coords[] expected = new S2Coords[] {
				coordsStart,
				new S2Coords(0, 1),
				new S2Coords(1, 1),
				new S2Coords(1, 2),
				new S2Coords(1, 3),
				new S2Coords(1, 4),
				new S2Coords(2, 4),
				new S2Coords(2, 5),
				coordsEnd };
		
		app.TestPath(series, "diagonal 1", coordsStart, coordsEnd, expected);
		
		coordsStart = new S2Coords(1, 6);
		coordsEnd = new S2Coords(6, 3);
		series.expectValueEquals("diagonal 2", "direction", methodsDirectionEquals, 
				S2CompassDirection.NorthEast,
				coordsStart.getDirectionTo(app.getOrientation(), coordsEnd));
		
		expected = new S2Coords[] {
				coordsStart,
				new S2Coords(2, 6),
				new S2Coords(2, 5),
				new S2Coords(3, 5),
				new S2Coords(4, 5),
				new S2Coords(4, 4),
				new S2Coords(5, 4),
				new S2Coords(5, 3),
				coordsEnd };
		
		app.TestPath(series, "diagonal 2", coordsStart, coordsEnd, expected);
		
		coordsStart = new S2Coords(0, 0);
		coordsEnd = new S2Coords(4, 4);
		series.expectValueEquals("diagonal 3", "direction", methodsDirectionEquals,
				S2CompassDirection.SouthEast,
				coordsStart.getDirectionTo(app.getOrientation(), coordsEnd));
		
		ArrayList<S2Coords> listLong = new ArrayList<S2Coords>();
		ArrayList<S2Coords> listShort = new ArrayList<S2Coords>();
		for (int i = 0; i < 4; i++) {
			S2Coords coords1 = new S2Coords(i, i);
			listShort.add(coords1);
			listLong.add(coords1);
			listLong.add(new S2Coords(i + 1, i));
		}
		listShort.add(coordsEnd);
		listLong.add(coordsEnd);
		
		expected = listLong.toArray(new S2Coords[0]);
		app.TestPath(series, "diagonal 3", coordsStart, coordsEnd, expected);
		
		expected = listShort.toArray(new S2Coords[0]);
		app.TestShortDiagonalPath(series, "diagonal 3", coordsStart, coordsEnd, expected);
		
		series.complete();
	}

}
