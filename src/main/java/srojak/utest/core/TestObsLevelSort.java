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

import java.util.ArrayList;
import java.util.List;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterPrintStream;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestEqualsMethods;

/**
 * @author Stephen
 *
 */
public class TestObsLevelSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("TestObsLevel");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);
		UnitTestEqualsMethods<ObsLevel> methodEq = new UnitTestEqualsMethods<ObsLevel>();
		
		List<ObsLevel> list = new ArrayList<ObsLevel>(ObsLevel.getAllKnown());
		list.sort(null);
		
		series.expectValueEquals("sorted list", "first", methodEq, ObsLevel.NONE, list.get(0));
		
		System.out.println("Sorted list:");
		list.forEach(i -> System.out.println("  " + i));
		
		series.complete();
	}

}
