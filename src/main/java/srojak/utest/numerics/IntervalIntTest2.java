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

import java.util.ArrayList;
import java.util.Arrays;

import srojak.core.observe.ObservationWriterPrintStream;
import srojak.numerics.IntervalType;
import srojak.numerics.OrderedComparison;
import srojak.numerics.intervals.IntervalInt;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class IntervalIntTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("IntervalIntTest2");
		series.getOptions().setObservationWriter(new ObservationWriterPrintStream(System.err));
		
		IntervalInt interval1 = new IntervalInt(IntervalType.CLOSED, 5, 15);
		System.out.println("interval1 = " + interval1);
		series.writeNote("interval1 is " + interval1.getIntervalType());
		series.expectValue("interval1 test", "left value", true, interval1.isInInterval(5));
		series.expectValue("interval1 test", "mid value", true, interval1.isInInterval(10));
		series.expectValue("interval1 test", "right value", true, interval1.isInInterval(15));
		series.expectValue("interval1 test", "greater value", false, interval1.isInInterval(25));
		ArrayList<Integer> list = new ArrayList<Integer>();
		interval1.overEachValue(n -> list.add(n));
		int[] array1 = list.stream().mapToInt(n -> n).toArray();
		series.expectValue("array1", "length", OrderedComparison.EQ, 11, array1.length);
		series.writeNote("array1 is " + Arrays.toString(array1));
		
		IntervalInt interval2 = new IntervalInt(5, 15);
		System.out.println("interval2 = " + interval2);
		series.writeNote("interval2 is " + interval2.getIntervalType());
		series.expectValue("interval2 test", "left value", true, interval2.isInInterval(5));
		series.expectValue("interval2 test", "mid value", true, interval2.isInInterval(10));
		series.expectValue("interval2 test", "right value", false, interval2.isInInterval(15));
		series.expectValue("interval2 test", "greater value", false, interval2.isInInterval(25));
		list.clear();
		interval2.overEachValue(n -> list.add(n));
		int[] array2 = list.stream().mapToInt(n -> n).toArray();
		series.expectValue("array2", "length", OrderedComparison.EQ, 10, array2.length);
		series.writeNote("array2 is " + Arrays.toString(array2));
		
		IntervalInt interval3 = new IntervalInt(IntervalType.OPEN, 5, 15);
		System.out.println("interval3 = " + interval3);
		series.writeNote("interval3 is " + interval3.getIntervalType());
		series.expectValue("interval3 test", "left value", false, interval3.isInInterval(5));
		series.expectValue("interval3 test", "mid value", true, interval3.isInInterval(10));
		series.expectValue("interval3 test", "right value", false, interval3.isInInterval(15));
		series.expectValue("interval3 test", "greater value", false, interval3.isInInterval(25));
		list.clear();
		interval3.overEachValue(n -> list.add(n));
		int[] array3 = list.stream().mapToInt(n -> n).toArray();
		series.expectValue("array3", "length", OrderedComparison.EQ, 9, array3.length);
		series.writeNote("array3 is " + Arrays.toString(array3));
		
		series.complete();
	}

}
