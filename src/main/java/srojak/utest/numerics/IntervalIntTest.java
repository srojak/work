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

import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterPrintStream;
import srojak.numerics.IntervalType;
import srojak.numerics.intervals.IntervalInt;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class IntervalIntTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("IntervalIntTest");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);

		IntervalInt intervalClosed = new IntervalInt(IntervalType.CLOSED, 5, 15);
		IntervalInt intervalOpen = new IntervalInt(IntervalType.OPEN, 5, 15);
		
		int nValue = 4;
		series.expectValue("on intervalClosed", String.valueOf(nValue), false,
				intervalClosed.isInInterval(nValue));
		series.expectValue("on intervalOpen", String.valueOf(nValue), false,
				intervalOpen.isInInterval(nValue));
		
		nValue = 5;
		series.expectValue("on intervalClosed", String.valueOf(nValue), true,
				intervalClosed.isInInterval(nValue));
		series.expectValue("on intervalOpen", String.valueOf(nValue), false,
				intervalOpen.isInInterval(nValue));
		
		nValue = 10;
		series.expectValue("on intervalClosed", String.valueOf(nValue), true,
				intervalClosed.isInInterval(nValue));
		series.expectValue("on intervalOpen", String.valueOf(nValue), true,
				intervalOpen.isInInterval(nValue));
		
		nValue = 15;
		series.expectValue("on intervalClosed", String.valueOf(nValue), true,
				intervalClosed.isInInterval(nValue));
		series.expectValue("on intervalOpen", String.valueOf(nValue), false,
				intervalOpen.isInInterval(nValue));
		
		nValue = 30;
		series.expectValue("on intervalClosed", String.valueOf(nValue), false,
				intervalClosed.isInInterval(nValue));
		series.expectValue("on intervalOpen", String.valueOf(nValue), false,
				intervalOpen.isInInterval(nValue));
		
		series.complete();
	}

}
