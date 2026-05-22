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

import srojak.core.observe.ObservationWriterPrintStream;
import srojak.spatial.S2Coords;
import srojak.spatial.S2Formats;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestEqualsMethods;
import srojak.utest.instances.UnitTestSupervisedFunction;

/**
 * @author Stephen
 *
 */
public class ParseS2Coords {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("ParseS2Coords");
		series.getOptions().setObservationWriter(new ObservationWriterPrintStream(System.err));
		
		S2Coords coordsRead = null;
		UnitTestEqualsMethods<S2Coords> methodsCoordsEq
				= new UnitTestEqualsMethods<S2Coords>(SpatialTestCommon.FormatS2Coords);

		S2Coords coords1 = new S2Coords(1, 2);
		String strCoords = coords1.toString(S2Formats.setOf(S2Formats.BASIC));
		// read it back
		UnitTestSupervisedFunction<String, S2Coords> instance = series.createMonadicInstance("parse",
				TestOutcome.PASS, s -> S2Coords.parse(s));
		coordsRead = instance.execute(strCoords);
		series.expectValueEquals("parsed value", "coords1", methodsCoordsEq, coords1, coordsRead);
		
		instance = series.createMonadicInstance("parse", TestOutcome.FAIL, s -> S2Coords.parse(s));
		instance.expect(NumberFormatException.class);
		coordsRead = instance.execute("20");
		
		instance = series.createMonadicInstance("parse", TestOutcome.FAIL, s -> S2Coords.parse(s));
		instance.expect(NumberFormatException.class);
		coordsRead = instance.execute("2, a3");		
		
		strCoords = coords1.toEnclosedString();
		// read it back
		instance = series.createMonadicInstance("parseEnclosed",
				TestOutcome.PASS, s -> S2Coords.parseEnclosed(s));
		coordsRead = instance.execute(strCoords);
		series.expectValueEquals("parsed value", "coords1", methodsCoordsEq, coords1, coordsRead);

		series.complete();
	}

}
