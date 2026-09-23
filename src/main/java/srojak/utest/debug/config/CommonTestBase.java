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

import java.util.Objects;
import java.util.function.Consumer;

import srojak.core.AppControl;
import srojak.core.io.FileExistence;
import srojak.core.observe.writers.ObservationWriterLevelFilter;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.result.XResult;
import srojak.core.result.XResultInt;
import srojak.debug.AppDebugMethods;
import srojak.debug.config.DebugConfigFileReader;
import srojak.utest.TestIdentifier;
import srojak.utest.UnitTestConditionXResult;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class CommonTestBase {
	private final UnitTestSeries _series;
	private final ObservationWriterPrintStream _writerOut;
	private final ObservationWriterPrintStream _writerErr;
	
	public CommonTestBase(UnitTestSeries series) {
		Objects.requireNonNull(series, "series");
		_series = series;
		_writerOut = new ObservationWriterPrintStream(System.out);
		_writerErr = new ObservationWriterPrintStream(System.err);
		
	}
	
	public UnitTestSeries getTestSeries() {
		return _series;
	}
	
	public ObservationWriterPrintStream getOutputWriter() {
		return _writerOut;
	}
	
	public ObservationWriterPrintStream getErrorWriter() {
		return _writerErr;
	}
	
	public XResult createLogFile(Class<?> classApp, String strPrefix) {
		XResult result = AppDebugMethods.tryCreateLogFile(classApp, strPrefix);
		_series.expectValue(TestIdentifier.name("create log file"), "result", true, result.isValid());
		return result;
	}
	
	public DebugConfigFileReader createDebugReader() {
		DebugConfigFileReader readerDebug = new DebugConfigFileReader();
		XResultInt result = readerDebug.loadSchema();
		_series.expectResult(TestIdentifier.name("debug reader"), "load schema", UnitTestConditionXResult.passed(), result);
		return readerDebug;
	}
	
	public XResultInt tryReadDebugConfigFile(DebugConfigFileReader reader, String strFile, FileExistence exists,
			Consumer<XResultInt> consumerFailure) {
		XResultInt resultRead = reader.readConfigFile(strFile, exists);
		if (!resultRead.isValid()) {
			consumerFailure.accept(resultRead);
		}
		_series.expectResult(TestIdentifier.name("debug parse"), "read", UnitTestConditionXResult.passed(), resultRead);
		return resultRead;
	}
}
