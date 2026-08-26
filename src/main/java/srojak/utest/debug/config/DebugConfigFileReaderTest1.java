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

import srojak.core.io.FileExistence;
import srojak.core.io.IOResultQualifiers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriterLevelFilterPrintStream;
import srojak.core.result.XResult;
import srojak.core.result.XResultInt;
import srojak.debug.AppDebugMethods;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchReader;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.DebugConfigFileReader;
import srojak.debug.config.DebugConfigNames;
import srojak.numerics.OrderedComparison;
import srojak.utest.TestIdentifier;
import srojak.utest.UnitTestConditionInt;
import srojak.utest.UnitTestConditionXResult;
import srojak.utest.UnitTestSeries;
import srojak.xml.stream.XmlParserOptions;

/**
 * @author Stephen
 *
 */
public class DebugConfigFileReaderTest1
		implements IOResultQualifiers {

	private static final String PREFIX_LOG_FILE = "DbgCf";
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus nexus = new DebugNexus(DebugNexus.CONS_NONE);
		_swDebugClass = nexus.getSwitch(DebugSwitchTool.makeClassKey(DebugConfigFileReaderTest1.class));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("ConfigFileReader Test1");
		series.getOptions().setShowStackOnExceptions(true);
		series.getOptions().setStopOnFailure(true);
		TestIdentifier idTest = TestIdentifier.name("readDebugConfig");
		
		ObservationWriterLevelFilterPrintStream writerOut
			= new ObservationWriterLevelFilterPrintStream(System.out);
		writerOut.setObsLevel(ObsLevel.DEBUG);		
		
		XResult result = AppDebugMethods.readDebugPropertiesFromCurrentDir();
		if (!result.isValid()) {
			System.err.println("cannot load properties: " + result.getException().getMessage());
			System.exit(2);
		}
		result = AppDebugMethods.tryCreateLogFile(DebugConfigFileReaderTest1.class, PREFIX_LOG_FILE);
		series.expectValue(TestIdentifier.name("create log file"), "result", true, result.isValid());
		AppDebugMethods.setAutoFlush(true);
		
		DebugConfigFileReader readerDebug = new DebugConfigFileReader();
		result = readerDebug.initialize();
		series.expectResult(idTest, "initialize", UnitTestConditionXResult.passed(), result);
		
		readerDebug.getParserOptions().setFlag(XmlParserOptions.PROPERTY_RECORD_COMMENTS, true);
		XResultInt resultRead = readerDebug.readConfigFile(DebugConfigNames.FILE_SWITCHES, FileExistence.MustExist);
		if (!resultRead.isValid()) {
			_swDebugClass.writeException(ObsLevel.ERROR, resultRead.getException(), true);
		}
		series.expectResult(idTest, "parse", UnitTestConditionXResult.passed(), resultRead);
		series.expectValueWhere(idTest, "qualifier", 
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, COMPLETED), resultRead.getResult());
		
		series.expectValue(idTest, "parseErrors", false, readerDebug.hasParseErrors());
		
		DebugSwitchReader readerSwitch = new DebugSwitchReader(writerOut);
		readerSwitch.enumerateAllSwitchesAndOptions();
		
		// another test
		readerSwitch.enumerateAllSwitchesForPackage(DebugConfigFileReaderTest1.class.getPackageName());
		
		series.complete();
		
	}

}
