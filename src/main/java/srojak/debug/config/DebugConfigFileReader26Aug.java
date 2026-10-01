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
package srojak.debug.config;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

import srojak.core.field.SetOnce;
import srojak.core.io.FileExistence;
import srojak.core.kernel.Kernel;
import srojak.core.observe.HasSingleObservationCollector;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.result.XResult;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.core.result.XResultOf;
import srojak.core.result.XResultStatusCarrier;
import srojak.debug.DebugConfigSchema;
import srojak.xml.XmlSchemaTool;
import srojak.xml.stream.XmlStreamValidatingReadAdapter;
import srojak.xml.stream.errors.XmlStreamErrorHandler;
import srojak.xml.stream.errors.XmlStreamParseErrorDescr;
import srojak.xml.stream.factories.XmlStreamInputFactory;

/**
 * @author Stephen
 *
 * @deprecated use DebugConfigFileReader instead
 */
@Deprecated
public final class DebugConfigFileReader26Aug
		implements HasSingleObservationCollector {
	private final SetOnce<Schema> _schema;
	private final DebugConfigParser _parser;
	private final XmlStreamInputFactory _factoryInput;
	private ObservationCollector _collectObs;
	private ObservationWriter _writer;
	private boolean _bShowStackOnException;
	
	public static final ObservedActivity ACTIVITY_INIT = new SingleActivity("reading debug config schema");
	public static final ObservedActivity ACTIVITY_READ = new SingleActivity("reading debug config");
	
	public DebugConfigFileReader26Aug() {
		_schema = new SetOnce<Schema>(SetOnce.DEFAULT);
		_parser = new DebugConfigParser();
		_factoryInput = new XmlStreamInputFactory(true);
		_collectObs = ObservationCollector.makeInstance();
		_factoryInput.setObservationCollector(Kernel.OBS_KERNEL);
		ObservationWriterPrintStream wr = new ObservationWriterPrintStream(System.err);
		wr.enableLevelFilter();
		wr.setObsLevel(ObsLevel.DETAIL);
		_writer = wr;
		_collectObs.addWriter(_writer);
		//_parser.setObservationWriter(_writer);
		_bShowStackOnException = false;
	}

	@Override
	public ObservationCollector getObservationCollector() {
		return _collectObs;
	}
	
	public boolean hasParseErrors() {
		return _parser.hasParseErrors();
	}
	
	public List<XmlStreamParseErrorDescr> getParseErrors() {
		return _parser.getParseErrors();
	}
	
	private XResultOf<Schema> readSchema() {
		DebugConfigSchema sourceSchema = new DebugConfigSchema();
		InputStream stream = sourceSchema.getResourceStream();
		XmlSchemaTool toolSchema = new XmlSchemaTool();
		return toolSchema.readSchema(new StreamSource(stream));
	}
	
	public XResult initialize() {
		XResultStatusCarrier result = new XResultStatusCarrier(ACTIVITY_INIT);
		if (!_schema.isEmpty()) {
			result.caughtException(new IllegalStateException("already initialized"));
		} else {
			XResultOf<Schema> resultSchema = readSchema();
			if (resultSchema.isValid()) {
				_schema.set(resultSchema.getResult());
				result.setValid();
			} else {
				result.copyFrom(resultSchema);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unused")
	private void trapException(Exception exc) {
		StringBuilder sb = new StringBuilder();
		Class<?> classEx = exc.getClass();
		sb.append(classEx.getSimpleName());
		sb.append(" reading debug config file: ");
		sb.append(exc.getMessage());	
		_collectObs.write(ObsLevel.FATAL, sb.toString());
		// TODO: show stack in writer
		if (_bShowStackOnException) {
			StackTraceElement[] frames = exc.getStackTrace();
			System.err.println("stack trace:");
			for (StackTraceElement frame : frames) {
				System.err.println("  " + frame);
			}
		}
	}
	
	private void readConfigFileCore(XResultIntCarrier result, Path pathFile, FileExistence exists) {
		if (_schema.isEmpty()) {
			XResultOf<Schema> resultSchema = readSchema();
			if (resultSchema.isValid()) {
				_schema.set(resultSchema.getResult());
			} else {
				result.copyFrom(resultSchema);
				return;
			}
		}
		XmlStreamValidatingReadAdapter adapter = new XmlStreamValidatingReadAdapter(_factoryInput, _schema.get(), _parser);
		XmlStreamErrorHandler handlerErrors = adapter.getErrorHandler();
		handlerErrors.getObservationCollector().addWriter(_writer);
		XResult resultParse = adapter.readFrom(result.getActivity(), pathFile, exists);
		result.copyFrom(resultParse);
	}
	
	public XResultInt readConfigFile(Path pathFile, FileExistence exists) {
		Objects.requireNonNull(pathFile, "pathFile");
		Objects.requireNonNull(exists, "exists");
		XResultIntCarrier result = new XResultIntCarrier(ACTIVITY_READ);
		readConfigFileCore(result, pathFile, exists);
		return result;
	}
	
	public XResultInt readConfigFile(String strFile, FileExistence exists) {
		Objects.requireNonNull(strFile, "strFile");
		Objects.requireNonNull(exists, "exists");
		XResultIntCarrier result = new XResultIntCarrier(ACTIVITY_READ);
		Path pathFile = Path.of(strFile);
		readConfigFileCore(result, pathFile, exists);
		return result;		
	}
}
