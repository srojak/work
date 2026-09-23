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
import java.util.List;

import srojak.core.NameToken;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.utest.TestIdentifier;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestClassElementMethods;

/**
 * @author Stephen
 *
 */
public class ListHavingTestSample1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("ListHavingTestSample");
		ObservationCollector collError = ObservationCollector.makeInstance();
		ObservationWriterPrintStream writerErr = new ObservationWriterPrintStream(System.err);
		collError.addWriter(writerErr);
		series.setObservationCollector(collError);
		
		TestIdentifier ident = TestIdentifier.name("listNameTokens");
		List<NameToken> listTokens = List.of(
				NameToken.factory("route1"),
				NameToken.factory("we2can"),
				NameToken.factory("par3"));
		List<NameToken> listFail = new ArrayList<NameToken>(4);
		listFail.addAll(listTokens);
		listFail.add(NameToken.factory("zero"));

		UnitTestClassElementMethods<NameToken> methodElements
			= new UnitTestClassElementMethods<NameToken>(NameToken.class);
		series.expectAllElementsToHave(ident, "names", methodElements, "digit",
			token -> token.getName().chars().anyMatch(Character::isDigit), listTokens);
		
		ident = TestIdentifier.name("bad list");
		series.expectAllElementsToHave(ident, "names", methodElements, "digit",
				token -> token.getName().chars().anyMatch(Character::isDigit), listFail);
		
		series.complete();
	}

}
