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

import java.nio.file.NoSuchFileException;

import srojak.core.AppControl;
import srojak.core.io.FileExistence;
import srojak.core.io.IOResultQualifiers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.reflect.ClassReflector;
import srojak.core.result.XResult;
import srojak.core.result.XResultInt;
import srojak.debug.AppDebugMethods;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.DebugConfigFileReader;
import srojak.numerics.OrderedComparison;
import srojak.utest.TestIdentifier;
import srojak.utest.UnitTestConditionInt;
import srojak.utest.UnitTestConditionXResult;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class DebugConfigNoFileTest
		implements IOResultQualifiers {
	private static final String PREFIX_LOG_FILE = "DbgCf";
	private static final String FILE_NAME = "NotThere.xml";
	private static final ClassReflector _self;
	private static final DebugSwitch _swDebugClass;
	
	static {
		_self = new ClassReflector(DebugConfigNoFileTest.class);
		DebugNexus nexus = new DebugNexus(DebugNexus.CONS_NONE);
		_swDebugClass = nexus.getSwitch(DebugSwitchTool.makeClassKey(_self));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("ConfigFileReader Test1");
		series.getOptions().setShowStackOnExceptions(true);
		series.getOptions().setStopOnFailure(true);
		TestIdentifier idTest = TestIdentifier.name("readDebugConfig");
		
		// use Run Configurations to change command line args 
		FileExistence existDebug = FileExistence.MustExist;
		if (args.length > 0) {
			if (args[0].equals("allowAny")) {
				existDebug = FileExistence.Any;
			}
		}
		
		ObservationCollector collError = ObservationCollector.makeInstance();
		ObservationWriterPrintStream writerErr = new ObservationWriterPrintStream(System.err);
		writerErr.enableLevelFilter();
		writerErr.setObsLevel(ObsLevel.DEBUG);
		collError.addWriter(writerErr);
		series.setObservationCollector(collError);
		series.getOptions().setShowStackOnExceptions(true);
		ObservationCollector collOutput = ObservationCollector.makeInstance();
		ObservationWriterPrintStream writerOut = new ObservationWriterPrintStream(System.out);
		collOutput.addWriter(writerOut);
		
		XResult result = AppControl.startApp(_self.getClass());
		if (!result.isValid()) {
			System.err.println("cannot load properties: " + result.getException().getMessage());
			System.exit(2);
		}
		result = AppDebugMethods.tryCreateLogFile(DebugConfigNoFileTest.class, PREFIX_LOG_FILE);
		series.expectValue(TestIdentifier.name("create log file"), "result", true, result.isValid());
		AppDebugMethods.setAutoFlush(true);

		
		DebugConfigFileReader readerDebug = new DebugConfigFileReader();
		readerDebug.setRecordComments(true);
		XResultInt resultRead = readerDebug.readConfigFile(FILE_NAME, existDebug);
		if (!resultRead.isValid()) {
			_swDebugClass.writeException(ObsLevel.ERROR, DebugConfigFileReader.ACTIVITY_READ, resultRead.getException(), true);
		} else {
			collOutput.write(ObsLevel.INFO, "result qualifier = " + resultRead.getResult());
		}
		if (existDebug.equals(FileExistence.Any)) {
			series.expectResult(idTest, "parse", UnitTestConditionXResult.passed(), resultRead);
			series.expectValueWhere(idTest, "qualifier", 
					UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, NO_FILE_TO_READ), resultRead.getResult());
		} else {
			series.expectResult(idTest, "parse", UnitTestConditionXResult.caughtException(NoSuchFileException.class), resultRead);
		}
		
		series.complete();
	}

}
