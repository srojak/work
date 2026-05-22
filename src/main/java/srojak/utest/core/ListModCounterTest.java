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
import java.util.LinkedList;
import java.util.List;

import srojak.core.collections.ListModCountTracker;
import srojak.core.observe.ExceptionAnalyzer;
import srojak.core.observe.ExceptionAnalyzerByClass;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterPrintStream;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class ListModCounterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("TestListModCounter");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);
		ExceptionAnalyzer analyzeExcs = new ExceptionAnalyzerByClass(writer);

		List<String> list1 = new ArrayList<String>();
		list1.add("spade");
		list1.add("heart");
		list1.add("diamond");
		list1.add("club");
		
		ListModCountTracker lmct1 = new ListModCountTracker(list1, analyzeExcs);
		series.expectValue("hasModCount", "ArrayList", true, lmct1.hasModCount());
		
		List<String> list2 = new LinkedList<String>();
		list2.add("first");
		list2.add("second");
		list2.add("third");
		
		ListModCountTracker lmct2 = new ListModCountTracker(list2, analyzeExcs);
		series.expectValue("hasModCount", "LinkedList", true, lmct2.hasModCount());
		
		series.complete();
	}
}
