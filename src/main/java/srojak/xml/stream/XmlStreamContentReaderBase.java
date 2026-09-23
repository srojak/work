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
package srojak.xml.stream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

import srojak.core.io.FileExistence;
import srojak.core.io.IOResultQualifiers;
import srojak.core.io.ResourceStreamProvider;
import srojak.core.logic.FlagsInt;
import srojak.core.observe.HasSingleObservationCollector;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.TraceLevel;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.xml.stream.errors.XmlStreamParseErrorDescr;
import srojak.xml.stream.factories.XmlStreamInputFactory;
import srojak.xml.stream.factories.XmlStreamParserFactory;
import srojak.xml.stream.parse.XmlStreamParser;
import srojak.xml.stream.parse.XmlStreamParserOptions;

/**
 * @author Stephen
 *
 */
public abstract class XmlStreamContentReaderBase<P extends XmlStreamParser> 
		implements HasSingleObservationCollector, XmlStreamParserOptions, IOResultQualifiers {
	protected final P _parser;
	protected final ObservationCollector _collectObs;
	private final Class<?> _classParser;
	private final FlagsInt _flags;
	
	protected static final int FLAGS_INIT_DONE = 0x1;
	protected static final int FLAGS_SHOW_STACK = 0x2;
	protected static final int FLAGS_VALIDATE = 0x4;
	
	protected static final ObservedActivity ACTIVITY_PREPARE_ADAPTER = new SingleActivity("prepare read adapter");
	
	public XmlStreamContentReaderBase(XmlStreamParserFactory<P> factory) {
		_parser = factory.create();
		_classParser = factory.getProductClass();
		_collectObs = ObservationCollector.makeInstance();
		_flags = new FlagsInt();
	}
	
	public Class<?> getParserClass() {
		return _classParser;
	}

	@Override
	public ObservationCollector getObservationCollector() {
		return _collectObs;
	}
	
	protected boolean isInitialized() {
		return _flags.test(FLAGS_INIT_DONE);
	}
	
	public boolean showStackOnException() {
		return _flags.test(FLAGS_SHOW_STACK);
	}
	
	public boolean isValidating() {
		return _flags.test(FLAGS_VALIDATE);
	}
	
	public void setShowStackOnException(boolean bState) {
		_flags.apply(bState, FLAGS_SHOW_STACK);
	}
	
	protected void setValidating(boolean bState) {
		_flags.apply(bState, FLAGS_VALIDATE);
	}
		
	@Override
	public boolean recordComments() {
		return _parser.recordComments();
	}

	@Override
	public void setRecordComments(boolean bState) {
		_parser.setRecordComments(bState);
	}

	@Override
	public boolean removeLeadingWhiteSpace() {
		return _parser.removeLeadingWhiteSpace();
	}

	@Override
	public void setRemoveLeadingWhiteSpace(boolean bState) {
		_parser.setRemoveLeadingWhiteSpace(bState);
	}

	public boolean hasParseErrors() {
		return _parser.hasParseErrors();
	}
	
	public List<XmlStreamParseErrorDescr> getParseErrors() {
		return _parser.getParseErrors();
	}
	
	protected boolean intialize() {
		// base class method does nothing, successfully
		return true; 
	}
	
	public boolean initializeIfNotDone() {
		boolean result = true;
		if (!isInitialized()) {
			result = intialize();
			if (result) {
				_flags.set(FLAGS_INIT_DONE);
			}
		}
		return result;
	}
	
	protected XmlStreamAdapter createAdapter(XmlStreamInputFactory factoryInput, XResultIntCarrier resultCreate) {
		XmlStreamAdapter adapter = new XmlStreamReadAdapter(factoryInput, _parser);
		resultCreate.setResult(NO_ACTION_TAKEN);
		return adapter;
	}
	
	public XResultInt parseFromFile(XmlStreamInputFactory factoryInput, ObservedActivity activityRead, 
			Path pathFile, FileExistence exists) {
		Objects.requireNonNull(factoryInput, "factoryInput");
		Objects.requireNonNull(activityRead, "activityRead");
		Objects.requireNonNull(pathFile, "pathFile");
		Objects.requireNonNull(exists, "exists");
		_collectObs.writeTraceEnter(TraceLevel.HIGH, this, "path = " + pathFile);
		XResultIntCarrier resultPrepare = new XResultIntCarrier(SourceLocation.here(), ACTIVITY_PREPARE_ADAPTER);
		XmlStreamAdapter adapter = createAdapter(factoryInput, resultPrepare);
		if (!resultPrepare.isValid()) {
			return resultPrepare;
		}
		XResultInt result = adapter.openAndRead(activityRead, exists != FileExistence.MustExist, 
				() -> Files.newInputStream(pathFile, StandardOpenOption.READ));
		_collectObs.writeTraceReturnValue(TraceLevel.HIGH, this, result);
		return result;
	}
	
	public XResultInt parseFromFile(XmlStreamInputFactory factoryInput, ObservedActivity activityRead, 
			String strPath, FileExistence exists) {
		Objects.requireNonNull(factoryInput, "factoryInput");
		Objects.requireNonNull(activityRead, "activityRead");
		Objects.requireNonNull(strPath, "strPath");
		Objects.requireNonNull(exists, "exists");
		_collectObs.writeTraceEnter(TraceLevel.HIGH, this, "path = " + strPath);
		Path pathFile = Path.of(strPath);
		XResultIntCarrier resultPrepare = new XResultIntCarrier(SourceLocation.here(), ACTIVITY_PREPARE_ADAPTER);
		XmlStreamAdapter adapter = createAdapter(factoryInput, resultPrepare);
		if (!resultPrepare.isValid()) {
			return resultPrepare;
		}
		XResultInt result = adapter.openAndRead(activityRead, exists != FileExistence.MustExist, 
				() -> Files.newInputStream(pathFile, StandardOpenOption.READ));
		_collectObs.writeTraceReturnValue(TraceLevel.HIGH, this, result);
		return result;
	}
	
	public XResultInt parseFromResource(XmlStreamInputFactory factoryInput, ObservedActivity activityRead, ResourceStreamProvider resource) {
		Objects.requireNonNull(resource, "resource");
		_collectObs.writeTraceEnter(TraceLevel.HIGH, this, "resource = " + resource.getResourceName());
		XResultIntCarrier resultPrepare = new XResultIntCarrier(SourceLocation.here(), ACTIVITY_PREPARE_ADAPTER);
		XmlStreamAdapter adapter = createAdapter(factoryInput, resultPrepare);
		if (!resultPrepare.isValid()) {
			return resultPrepare;
		}
		XResultInt result = adapter.openAndRead(activityRead, false, () -> resource.getResourceStream());
		_collectObs.writeTraceReturnValue(TraceLevel.HIGH, this, result);
		return result;
	}
}
