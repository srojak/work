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
package srojak.utest.debug;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriterLevelFilterPrintStream;
import srojak.core.specialized.IntegerCounter;
import srojak.debug.AppDebugMethods;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitchTool;
import srojak.numerics.OrderedComparison;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class DebugCascadingBaseClassTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("ConfigReader");
		series.getOptions().setShowStackOnExceptions(true);
		ObservationWriterLevelFilterPrintStream writer
			= new ObservationWriterLevelFilterPrintStream(System.out);
		writer.setObsLevel(ObsLevel.DEBUG);
		series.getOptions().setObservationWriter(writer);
		
		AppDebugMethods.readDebugPropertiesFromCurrentDir(2);
		boolean bCreated = AppDebugMethods.tryCreateLogFile(DebugCascadingBaseClassTest.class);
		series.expectValue("create log file", "result", true, bCreated);
		AppDebugMethods.setAutoFlush(true);
		
		@SuppressWarnings("unused")
		TestSpecializedTarget target = new TestSpecializedTarget();
		DebugNexus debug = new DebugNexus(DebugNexus.CONS_NONE);
		debug.enableBaseClassSwitches(DebugSwitchTool.makeClassKey(TestSpecializedTarget.class));
		
		StringBuilder sb = new StringBuilder("Switches");
		IntegerCounter counter = new IntegerCounter();
		debug.forEachSwitch(ds -> {
			sb.append("\n  ");
			sb.append(ds);
			counter.increment(1);
		});
		series.writeMessageLine(ObsLevel.NOTICE, sb.toString());
		series.expectValue("switch count", "# switches", OrderedComparison.EQ, 3, counter.getValue());

		series.complete();
	}

}
