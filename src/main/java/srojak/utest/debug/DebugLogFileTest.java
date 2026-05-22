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
import srojak.core.observe.ObservationWriter;
import srojak.debug.DebugNexus;
import srojak.debug.DebugWriterLogFile;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.instances.UnitTestSupervisedConsumer;
import srojak.utest.instances.UnitTestSupervisedVoid;

/**
 * @author Stephen
 *
 */
public class DebugLogFileTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DebugLogFileTest app = new DebugLogFileTest();
		DebugNexus debug = new DebugNexus();
		UnitTestSeries series = new UnitTestSeries("DebugLogFileTest");
		series.getOptions().setStopOnFailure(true);
		series.getOptions().setShowStackOnExceptions(true);
		ObservationWriter writer = null;
		
		UnitTestSupervisedConsumer<Object> instance1 
				= series.createConsumerInstance("read debug properties",
						TestOutcome.PASS, obj -> debug.loadPropertiesFromCurrentDir());
		instance1.execute(new Object());
		
		UnitTestSupervisedVoid<ObservationWriter> instance2
			= series.createVoidInstance("create writer", TestOutcome.PASS, 
					() -> DebugWriterLogFile.create(debug.getLogDirectory(), app));
		writer = instance2.execute();
		debug.setWriter(writer);
		
		
		writer.write(ObsLevel.NOTICE, "Test completed");
		
		series.complete();
	}

}
