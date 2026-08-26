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
import srojak.core.observe.HasSingleObservationWriter;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterLevelFilterPrintStream;
import srojak.core.result.XResult;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.core.result.XResultOf;
import srojak.core.result.XResultStatusCarrier;
import srojak.debug.DebugConfigSchema;
import srojak.xml.XmlSchemaTool;
import srojak.xml.stream.XmlParserOptions;
import srojak.xml.stream.XmlStreamValidatingReadAdapter;
import srojak.xml.stream.errors.XmlStreamErrorHandler;
import srojak.xml.stream.errors.XmlStreamParseErrorDescr;

/**
 * @author Stephen
 *
 */
public final class DebugConfigFileReader
		implements HasSingleObservationWriter {
	private final SetOnce<Schema> _schema;
	private final DebugConfigParser _parser;
	private ObservationWriter _writer;
	private boolean _bShowStackOnException;
	
	public DebugConfigFileReader() {
		_schema = new SetOnce<Schema>(SetOnce.DEFAULT);
		_parser = new DebugConfigParser();
		ObservationWriterLevelFilterPrintStream wr = new ObservationWriterLevelFilterPrintStream(System.err);
		wr.setObsLevel(ObsLevel.DETAIL);
		_writer = wr;
		_parser.setObservationWriter(_writer);
		_bShowStackOnException = false;
	}

	@Override
	public ObservationWriter getObservationWriter() {
		return _writer;
	}

	@Override
	public void setObservationWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writer = writer;
		_parser.setObservationWriter(writer);
	}
	
	public XmlParserOptions getParserOptions() {
		return _parser.getOptions();
	}
	
	public boolean hasParseErrors() {
		return _parser.hasParseErrors();
	}
	
	public List<XmlStreamParseErrorDescr> getParseErrors() {
		return _parser.getParseErrors();
	}
	
	private XResultOf<Schema> readSchema() {
		DebugConfigSchema sourceSchema = new DebugConfigSchema();
		InputStream stream = sourceSchema.getResource();
		XmlSchemaTool toolSchema = new XmlSchemaTool();
		return toolSchema.readSchema(new StreamSource(stream));
	}
	
	public XResult initialize() {
		XResultStatusCarrier result = new XResultStatusCarrier();
		if (_schema.hasBeenSet()) {
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
		_writer.write(ObsLevel.FATAL, sb.toString());
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
		if (!_schema.hasBeenSet()) {
			XResultOf<Schema> resultSchema = readSchema();
			if (resultSchema.isValid()) {
				_schema.set(resultSchema.getResult());
			} else {
				result.copyFrom(resultSchema);
				return;
			}
		}
		XmlStreamValidatingReadAdapter adapter = new XmlStreamValidatingReadAdapter(_schema.get(), _parser);
		XmlStreamErrorHandler handlerErrors = adapter.getErrorHandler();
		handlerErrors.setWriter(_writer);
		XResultInt resultParse = adapter.readFrom(pathFile, exists);
		if (resultParse.isValid()) {
			result.setResult(resultParse.getResult());
		} else {
			result.copyFrom(resultParse);
		}
	}
	
	public XResultInt readConfigFile(Path pathFile, FileExistence exists) {
		Objects.requireNonNull(pathFile, "pathFile");
		Objects.requireNonNull(exists, "exists");
		XResultIntCarrier result = new XResultIntCarrier();
		readConfigFileCore(result, pathFile, exists);
		return result;
	}
	
	public XResultInt readConfigFile(String strFile, FileExistence exists) {
		Objects.requireNonNull(strFile, "strFile");
		Objects.requireNonNull(exists, "exists");
		XResultIntCarrier result = new XResultIntCarrier();
		Path pathFile = Path.of(strFile);
		readConfigFileCore(result, pathFile, exists);
		return result;		
	}
}
