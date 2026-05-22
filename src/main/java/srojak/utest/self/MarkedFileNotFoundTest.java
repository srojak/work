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

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import srojak.numerics.OrderedComparison;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.instances.UnitTestSupervisedConsumer;

/**
 * @author Stephen
 *
 */
public class MarkedFileNotFoundTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("MarkedFileNotFound");
		
		Path pathFile = Paths.get("notthere.txt");
		MarkedTextFileReader reader = new MarkedTextFileReader();
		ArrayList<String> listMatches = new ArrayList<String>();
		UnitTestSupervisedConsumer<List<String>> instance = 
			series.createConsumerInstance("findMarkedLines", TestOutcome.FAIL, list -> {
				reader.findMarkedLines(pathFile, list);
			});
		instance.expect(NoSuchFileException.class);
		instance.execute(listMatches);
		
		series.expectValue("line count", "reader", OrderedComparison.EQ, 0, reader.getLineCount());
		series.expectValue("match count", "listMatches", OrderedComparison.EQ, 0, listMatches.size());
		
		series.complete();
	}

}
