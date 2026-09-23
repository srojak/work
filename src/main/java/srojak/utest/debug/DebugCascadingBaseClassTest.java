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

import srojak.core.AppControl;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.reflect.ClassReflector;
import srojak.core.result.XResult;
import srojak.debug.AppDebugMethods;
import srojak.debug.DebugContentReader;
import srojak.debug.DebugContentVisitor;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitchTool;
import srojak.debug.tools.DebugContentCountingVisitor;
import srojak.numerics.OrderedComparison;
import srojak.utest.TestIdentifier;
import srojak.utest.TestStandardObservers;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class DebugCascadingBaseClassTest 
		implements TestStandardObservers {

	private static final ClassReflector _self = new ClassReflector(DebugCascadingBaseClassTest.class);
	private static final String PREFIX_LOG_FILE = "DbgT";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("ConfigReader");
		series.getOptions().setShowStackOnExceptions(true);
		ObservationWriterPrintStream writer
			= new ObservationWriterPrintStream(System.out);
		writer.enableLevelFilter();
		writer.setObsLevel(ObsLevel.DEBUG);
		series.setObservationCollector(TEST_OBSV_ERR);
		
		XResult result = AppControl.startApp(_self.getClass());
		if (!result.isValid()) {
			System.err.println("cannot load properties: " + result.getException().getMessage());
			System.exit(2);
		}
		result = AppDebugMethods.tryCreateLogFile(_self.getClass(), PREFIX_LOG_FILE);
		series.expectValue(TestIdentifier.name("create log file"), "result", true, result.isValid());
		AppDebugMethods.setAutoFlush(true);
		
		@SuppressWarnings("unused")
		TestSpecializedTarget target = new TestSpecializedTarget();
		DebugNexus debug = new DebugNexus(DebugNexus.CONS_NONE);
		debug.enableBaseClassSwitches(DebugSwitchTool.makeClassKey(TestSpecializedTarget.class));
		
	    DebugContentVisitor visitor = new DebugContentTestVisitor1(TEST_OBSV_OUT);
	    DebugContentReader readerDebug = new DebugContentReader();
	    readerDebug.visitAllContent(visitor);
	    
	    DebugContentCountingVisitor visitorCount = new DebugContentCountingVisitor();
	    readerDebug.visitAllContent(visitorCount);
		
		series.expectValue(TestIdentifier.name("switch count"), "# class switches",
				OrderedComparison.EQ, 3, visitorCount.totalClassSwitches());

		series.complete();
	}

}
