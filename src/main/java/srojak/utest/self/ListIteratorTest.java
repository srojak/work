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
package srojak.utest.self;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterPrintStream;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.conditions.StringCondition;
import srojak.utest.instances.UnitTestSupervisedVoid;

/**
 * @author Stephen
 *
 */
public class ListIteratorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("ListIteratorTest");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);
		
		List<String> list = new ArrayList<String>();
		list.add("first");
		list.add("second");
		list.add("third");
		
		Iterator<String> iter1 = list.iterator();
		
		series.expectValue("iter1", "hasNext", true, iter1.hasNext());
		series.expectString("iter1", "next", StringCondition.EQUALS, "first", iter1.next());
		
		list.add("fourth");
		
		UnitTestSupervisedVoid<Boolean> instance2
			= series.createVoidInstance("after add", TestOutcome.FAIL, () -> iter1.hasNext());
		boolean bResult = instance2.execute().booleanValue();
		System.out.println("result is " + bResult);
		
		UnitTestSupervisedVoid<String> instance3
			= series.createVoidInstance("fetch after add", TestOutcome.FAIL, () -> iter1.next());
		instance3.expect(ConcurrentModificationException.class);
		@SuppressWarnings("unused")
		String s = instance3.execute();
		
		series.complete();
	}

}
