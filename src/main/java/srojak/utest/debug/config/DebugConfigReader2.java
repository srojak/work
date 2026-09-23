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

import srojak.core.AppControl;
import srojak.core.io.FileExistence;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.reflect.ClassReflector;
import srojak.core.result.XResult;
import srojak.core.result.XResultOf;
import srojak.debug.AppDebugMethods;
import srojak.debug.DebugConfigSchema;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugContentReader;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.DebugConfigFileReader;
import srojak.debug.config.DebugConfigNames;
import srojak.debug.config.DebugConfigParser;
import srojak.utest.TestIdentifier;
import srojak.utest.UnitTestConditionXResult;
import srojak.utest.UnitTestSeries;
import srojak.xml.XmlSchemaTool;
import srojak.xml.stream.XmlStreamValidatingReadAdapter;
import srojak.xml.stream.factories.XmlStreamInputFactory;

/**
 * @author Stephen
 *
 */
public class DebugConfigReader2 {

	private static final String PREFIX_LOG_FILE = "DbgCf";
	private static final ClassReflector _self;
	private static final DebugSwitch _swDebugClass;
	
	static {
		_self = new ClassReflector(DebugConfigReader2.class);
		DebugNexus nexus = new DebugNexus(DebugNexus.CONS_NONE);
		_swDebugClass = nexus.getSwitch(DebugSwitchTool.makeClassKey(_self));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("ConfigReader");
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
		TestIdentifier idTest = TestIdentifier.name("parse");
	
		XResult result = AppControl.startApp(_self.getClass());
		if (!result.isValid()) {
			System.err.println("cannot load properties: " + result.getException().getMessage());
			System.exit(2);
		}
		result = AppDebugMethods.tryCreateLogFile(DebugConfigReader2.class, PREFIX_LOG_FILE);
		series.expectValue(TestIdentifier.name("create log file"), "result", true, result.isValid());
		AppDebugMethods.setAutoFlush(true);
		
		DebugConfigSchema sourceSchema = new DebugConfigSchema();
		InputStream stream = sourceSchema.getResourceStream();
		XmlSchemaTool toolSchema = new XmlSchemaTool();
		XResultOf<Schema> resultSchema = toolSchema.readSchema(new StreamSource(stream));
		if (!resultSchema.isValid()) {
			_swDebugClass.writeException(ObsLevel.ERROR, DebugConfigFileReader.ACTIVITY_READ, resultSchema.getException(), false);
			System.err.println("cannot read schema");
			System.exit(2);
		}
		
		DebugConfigParser parser = new DebugConfigParser();
		
		_swDebugClass.write(ObsLevel.NOTICE, "Reading config file");
		XmlStreamInputFactory factoryInput = new XmlStreamInputFactory(true);
		XmlStreamValidatingReadAdapter adapter = new XmlStreamValidatingReadAdapter(factoryInput, resultSchema.getResult(), parser);
		Path pathFile = Path.of(DebugConfigNames.FILE_SWITCHES);
		result = adapter.readFrom(DebugConfigFileReader.ACTIVITY_READ, pathFile, FileExistence.MustExist);
		series.expectResult(idTest, "read", UnitTestConditionXResult.passed(), result);
		
		_swDebugClass.write(ObsLevel.NOTICE, "Completed reading config file");
		
		DebugContentReader readerSwitch = new DebugContentReader();
		readerSwitch.enumerateAllSwitchesAndOptions(collOutput);
		
		series.complete();
	}

}
