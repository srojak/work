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

import java.io.InputStream;
import java.nio.file.Path;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

import srojak.core.io.FileExistence;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriterLevelFilterPrintStream;
import srojak.core.result.XResult;
import srojak.core.result.XResultOf;
import srojak.debug.AppDebugMethods;
import srojak.debug.DebugConfigSchema;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchReader;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.DebugConfigNames;
import srojak.debug.config.DebugConfigParser;
import srojak.utest.TestIdentifier;
import srojak.utest.UnitTestConditionXResult;
import srojak.utest.UnitTestSeries;
import srojak.xml.XmlSchemaTool;
import srojak.xml.stream.XmlStreamValidatingReadAdapter;

/**
 * @author Stephen
 *
 */
public class DebugConfigReader2 {

	private static final String PREFIX_LOG_FILE = "DbgCf";
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus nexus = new DebugNexus(DebugNexus.CONS_NONE);
		_swDebugClass = nexus.getSwitch(DebugSwitchTool.makeClassKey(DebugConfigReader2.class));
	}

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
		TestIdentifier idTest = TestIdentifier.name("parse");
	
		XResult result = AppDebugMethods.readDebugPropertiesFromCurrentDir();
		if (!result.isValid()) {
			System.err.println("cannot load properties: " + result.getException().getMessage());
			System.exit(2);
		}
		result = AppDebugMethods.tryCreateLogFile(DebugConfigReader2.class, PREFIX_LOG_FILE);
		series.expectValue(TestIdentifier.name("create log file"), "result", true, result.isValid());
		AppDebugMethods.setAutoFlush(true);
		
		DebugConfigSchema sourceSchema = new DebugConfigSchema();
		InputStream stream = sourceSchema.getResource();
		XmlSchemaTool toolSchema = new XmlSchemaTool();
		XResultOf<Schema> resultSchema = toolSchema.readSchema(new StreamSource(stream));
		if (!resultSchema.isValid()) {
			_swDebugClass.writeException(ObsLevel.ERROR, resultSchema.getException(), false);
			System.err.println("cannot read schema");
			System.exit(2);
		}
		
		DebugConfigParser parser = new DebugConfigParser();
		
		_swDebugClass.write(ObsLevel.NOTICE, "Reading config file");
		XmlStreamValidatingReadAdapter adapter = new XmlStreamValidatingReadAdapter(resultSchema.getResult(), parser);
		Path pathFile = Path.of(DebugConfigNames.FILE_SWITCHES);
		result = adapter.readFrom(pathFile, FileExistence.MustExist);
		series.expectResult(idTest, "read", UnitTestConditionXResult.passed(), result);
		
		_swDebugClass.write(ObsLevel.NOTICE, "Completed reading config file");
		
		DebugSwitchReader readerSwitch = new DebugSwitchReader(writer);
		readerSwitch.enumerateAllSwitchesAndOptions();
		
		series.complete();
	}

}
