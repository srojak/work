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
import srojak.numerics.DoubleMethods;
import srojak.numerics.IntervalType;
import srojak.numerics.OrderedComparison;
import srojak.numerics.intervals.IntervalDouble;
import srojak.spatial.PolarCoords;
import srojak.spatial.S2Coords;
import srojak.utest.UnitTestSeries;
import srojak.utest.conditions.UnitTestDoubleIntervalComparison;
import srojak.utest.conditions.UnitTestDoubleValueComparison;
import srojak.utest.helpers.UnitTestEqualsMethods;

/**
 * @author Stephen
 *
 */
public class TestS2ToPolar {
	
	private static final double _sqrt50 = Math.sqrt(50.0d);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("S2ToPolar");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);
		
		UnitTestEqualsMethods<PolarCoords> methodEq
			= new UnitTestEqualsMethods<PolarCoords>((e, a) -> 
					DoubleMethods.areEqual(e.getRadius(), a.getRadius())
					&& DoubleMethods.areEqual(e.getTheta(), a.getTheta()));
		
		S2Coords coordsEast = new S2Coords(5, 0);
		PolarCoords polarExpected = new PolarCoords(5.0d, 0.0d);
		PolarCoords polarActual = PolarCoords.convertFrom(coordsEast);
		series.expectValueEquals("convert to polar", "coordsEast", methodEq, polarExpected, polarActual);
		series.expectValueWhere("angle in degrees", "coordsEast", 
				new UnitTestDoubleValueComparison(OrderedComparison.EQ, 0.0d), 
					polarActual.getThetaInDegrees());
		
		S2Coords coordsSouth = new S2Coords(0, 4);
		polarExpected = new PolarCoords(4.0d, Math.PI / 2.0);
		polarActual = PolarCoords.convertFrom(coordsSouth);
		series.expectValueEquals("convert to polar", "coordsSouth", methodEq, polarExpected, polarActual);
		series.expectValueWhere("angle in degrees", "coordsSouth", 
				new UnitTestDoubleValueComparison(OrderedComparison.EQ,	90.0d),
					polarActual.getThetaInDegrees());
		
		S2Coords coordsWest = new S2Coords(-5, 0);
		polarExpected = new PolarCoords(5.0d, Math.PI);
		polarActual = PolarCoords.convertFrom(coordsWest);
		series.expectValueEquals("convert to polar", "coordsWest", methodEq, polarExpected, polarActual);
		series.expectValueWhere("angle in degrees", "coordsWest", 
				new UnitTestDoubleValueComparison(OrderedComparison.EQ,	180.0d), 
					polarActual.getThetaInDegrees());
		
		S2Coords coordsNorth = new S2Coords(0, -4);
		polarExpected = new PolarCoords(4.0d, 3.0 * Math.PI / 2.0);
		polarActual = PolarCoords.convertFrom(coordsNorth);
		series.expectValueEquals("convert to polar", "coordsNorth", methodEq, polarExpected, polarActual);
		series.expectValueWhere("angle in degrees", "coordsNorth", 
				new UnitTestDoubleValueComparison(OrderedComparison.EQ, 270.0d), 
					polarActual.getThetaInDegrees());
		
		S2Coords coordsSouthEast = new S2Coords(5, 5);
		polarExpected = new PolarCoords(_sqrt50, Math.PI / 4.0);
		polarActual = PolarCoords.convertFrom(coordsSouthEast);
		series.expectValueEquals("convert to polar", "coordsSouthEast", methodEq, polarExpected, polarActual);
		series.expectValueWhere("angle in degrees", "coordsSouthEast", 
				new UnitTestDoubleValueComparison(OrderedComparison.EQ,	45.0d),
					polarActual.getThetaInDegrees());
		
		S2Coords coordsSouthWest = new S2Coords(-5, 5);
		polarExpected = new PolarCoords(_sqrt50, 3.0 * Math.PI / 4.0);
		polarActual = PolarCoords.convertFrom(coordsSouthWest);
		series.expectValueEquals("convert to polar", "coordsSouthWest", methodEq, polarExpected, polarActual);		
		series.expectValueWhere("angle in degrees", "coordsEast", 
				new UnitTestDoubleValueComparison(OrderedComparison.EQ,	135.0d),
					polarActual.getThetaInDegrees());
	
		S2Coords coordsNorthEast = new S2Coords(5, -5);
		polarExpected = new PolarCoords(_sqrt50, - Math.PI / 4.0);
		polarActual = PolarCoords.convertFrom(coordsNorthEast);
		series.expectValueEquals("convert to polar", "coordsNorthEast", methodEq, polarExpected, polarActual);		
		series.expectValueWhere("angle in degrees", "coordsNorthEast", 
				new UnitTestDoubleValueComparison(OrderedComparison.EQ, 315.0d),
					polarActual.getThetaInDegrees());
		
		S2Coords coordsNorthWest = new S2Coords(-5, -5);
		polarExpected = new PolarCoords(_sqrt50, -3.0 * Math.PI / 4.0);
		polarActual = PolarCoords.convertFrom(coordsNorthWest);
		series.expectValueEquals("convert to polar", "coordsNorthWest", methodEq, polarExpected, polarActual);
		series.expectValueWhere("angle in degrees", "coordsNorthWest", 
				new UnitTestDoubleValueComparison(OrderedComparison.EQ,	225.0d),
					polarActual.getThetaInDegrees());
	
		S2Coords coordsAcute = new S2Coords(4, 3);
		polarExpected = new PolarCoords(5.0d, Math.atan(0.75d));
		polarActual = PolarCoords.convertFrom(coordsAcute);
		series.expectValueEquals("convert to polar", "coordsAcute", methodEq, polarExpected, polarActual);
		series.expectValueWhere("angle in degrees", "coordsAcute",
				new UnitTestDoubleIntervalComparison(ConditionSense.IS,
						new IntervalDouble(IntervalType.OPEN, 30.0d, 45.0d)),
					polarActual.getThetaInDegrees());
		
		series.complete();
	}

}
