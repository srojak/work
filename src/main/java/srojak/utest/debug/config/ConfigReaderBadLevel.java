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
package srojak.utest.debug.config;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriterLevelFilterPrintStream;
import srojak.debug.DebugNexus;
import srojak.debug.config.DebugConfigReader2Pass;
import srojak.utest.TestIdentifier;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.instances.UnitTestSupervisedVoid;

/**
 * @author Stephen
 *
 */
public class ConfigReaderBadLevel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		DebugNexus debug = new DebugNexus();
		UnitTestSeries series = new UnitTestSeries("ConfigReaderBadLevel");
		series.getOptions().setShowStackOnExceptions(true);
		ObservationWriterLevelFilterPrintStream writerSysout = new ObservationWriterLevelFilterPrintStream(System.out);
		writerSysout.setObsLevel(ObsLevel.DEBUG);
		series.getOptions().setObservationWriter(writerSysout);
		
		UnitTestSupervisedVoid<DebugConfigReader2Pass> test1
			= series.<DebugConfigReader2Pass>createVoidInstance(TestIdentifier.name("read config file"), 
					TestOutcome.PASS, () -> {
						DebugConfigReader2Pass reader = new DebugConfigReader2Pass();
						reader.setObservationWriter(writerSysout);
						reader.readFrom("badlevel.xml");		
						return reader;
					});
		test1.execute();
		
		series.complete();

	}

}
