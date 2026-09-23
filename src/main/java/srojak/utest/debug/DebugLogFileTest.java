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

import java.nio.file.Path;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.SourceLocation;
import srojak.core.props.DebugProperties;
import srojak.core.result.XResult;
import srojak.debug.DebugNexus;
import srojak.debug.DebugWriterLogFile;
import srojak.debug.impl.DebugNexusCore;
import srojak.utest.TestIdentifier;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestConditionXResult;
import srojak.utest.UnitTestSeries;
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
		DebugNexus debug = new DebugNexus(DebugNexus.CONS_NONE);
		UnitTestSeries series = new UnitTestSeries("DebugLogFileTest");
		series.getOptions().setStopOnFailure(true);
		series.getOptions().setShowStackOnExceptions(true);
		ObservationWriter writer = null;
		DebugProperties propsDebug = DebugNexusCore.getProperties();
		@SuppressWarnings("unused")
		Path pathCurDir = Path.of(System.getProperty("user.dir"));
		TestIdentifier idTestReadDebug = TestIdentifier.name("read debug properties");
		
		XResult resultRead = propsDebug.loadFromCurrentDirectory(DebugProperties.PROPERTIES_FILE_NAME);
		series.expectResult(idTestReadDebug, "load", UnitTestConditionXResult.passed(), resultRead);
		
		UnitTestSupervisedVoid<ObservationWriter> instance2
			= series.createVoidInstance(TestIdentifier.name("create writer"), TestOutcome.PASS, 
					() -> DebugWriterLogFile.create(debug.getLogDirectory(), 
							DebugLogFileTest.class, DebugWriterLogFile.PREFIX_DEBUG));
		writer = instance2.execute();
		debug.setWriter(writer);
		
		
		writer.write(ObsLevel.NOTICE, SourceLocation.here(), "Test completed");
		
		series.complete();
	}

}
