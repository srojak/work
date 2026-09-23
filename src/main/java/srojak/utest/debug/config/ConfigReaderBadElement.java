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

import java.util.List;

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
import srojak.debug.DebugContentReader;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.DebugConfigFileReader;
import srojak.numerics.OrderedComparison;
import srojak.utest.TestIdentifier;
import srojak.utest.UnitTestConditionInt;
import srojak.utest.UnitTestConditionXResult;
import srojak.utest.UnitTestSeries;
import srojak.xml.stream.errors.XmlStreamParseErrorDescr;

/**
 * @author Stephen
 *
 */
public class ConfigReaderBadElement
		implements IOResultQualifiers {
	
	private static final String PREFIX_LOG_FILE = "DbgCf";
	private static final String FILE_NAME = "badelem.xml";
	private static final ClassReflector _self;
	private static final DebugSwitch _swDebugClass;
	
	static {
		_self = new ClassReflector(ConfigReaderBadElement.class);
		DebugNexus nexus = new DebugNexus(DebugNexus.CONS_NONE);
		_swDebugClass = nexus.getSwitch(DebugSwitchTool.makeClassKey(_self));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("ConfigReaderBadElement");
		ObservationCollector collError = ObservationCollector.makeInstance();
		ObservationWriterPrintStream writerErr
				= new ObservationWriterPrintStream(System.err);
		writerErr.enableLevelFilter();
		writerErr.setObsLevel(ObsLevel.DEBUG);
		collError.addWriter(writerErr);
		series.setObservationCollector(collError);
		series.getOptions().setShowStackOnExceptions(true);
		TestIdentifier idTest = TestIdentifier.name("parse");
		ObservationCollector collOutput = ObservationCollector.makeInstance();
		ObservationWriterPrintStream writerOut
				= new ObservationWriterPrintStream(System.out);
		collOutput.addWriter(writerOut);
		
		XResult result = AppControl.startApp(_self.getClass());
		if (!result.isValid()) {
			System.err.println("cannot load properties: " + result.getException().getMessage());
			System.exit(2);
		}
		result = AppDebugMethods.tryCreateLogFile(ConfigReaderBadElement.class, PREFIX_LOG_FILE);
		series.expectValue(TestIdentifier.name("create log file"), "result", true, result.isValid());
		AppDebugMethods.setAutoFlush(true);

		DebugConfigFileReader readerDebug = new DebugConfigFileReader();
		XResultInt resultSchema = readerDebug.loadSchema();
		series.expectResult(idTest, "load schema", UnitTestConditionXResult.passed(), resultSchema);
		
		_swDebugClass.write(ObsLevel.NOTICE, "Reading config file");
		XResultInt resultRead = readerDebug.readConfigFile(FILE_NAME, FileExistence.MustExist);
		if (!resultRead.isValid()) {
			_swDebugClass.writeException(ObsLevel.ERROR, DebugConfigFileReader.ACTIVITY_READ, resultRead.getException(), true);
		}
		series.expectResult(idTest, "read", UnitTestConditionXResult.passed(), resultRead);
		
		_swDebugClass.write(ObsLevel.NOTICE, "Completed reading config file");
		series.expectValueWhere(idTest, "qualifier", 
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, COMPLETED), resultRead.getResult());
		
		series.expectValue(idTest, "parseErrors", true, readerDebug.hasParseErrors());
		// get the errors
		List<XmlStreamParseErrorDescr> listErrors = readerDebug.getParseErrors();
		collOutput.write(ObsLevel.NOTICE, String.valueOf(listErrors.size()) + " parse errors");
		for (XmlStreamParseErrorDescr error : listErrors) {
			collOutput.write(error.getSeverity().mapToObsLevel(), error.toString());
		}
		series.expectValueWhere(idTest, "# errors", 
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, 1), listErrors.size());
		
		DebugContentReader readerSwitch = new DebugContentReader();
		int nSwitches = readerSwitch.enumerateAllSwitches(collOutput);
		series.expectValue(TestIdentifier.name("switch count"), "# switches", 
				OrderedComparison.GT, 0, nSwitches);

		series.complete();
	}

}
