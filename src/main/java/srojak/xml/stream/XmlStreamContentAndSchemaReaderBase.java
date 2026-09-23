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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import srojak.core.field.SetOnce;
import srojak.core.io.ResourceStreamProvider;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.TraceLevel;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.xml.stream.factories.XmlStreamParserFactory;
import srojak.xml.stream.parse.XmlStreamParser;

/**
 * @author Stephen
 *
 */
public abstract class XmlStreamContentAndSchemaReaderBase<P extends XmlStreamParser>
		extends XmlStreamContentReaderBase<P> {
	protected final SetOnce<Schema> _schema;
	private final SchemaFactory _factorySchema;

	/**
	 * @param factory
	 */
	public XmlStreamContentAndSchemaReaderBase(XmlStreamParserFactory<P> factory) {
		super(factory);
		_schema = new SetOnce<Schema>(SetOnce.DEFAULT);
		_factorySchema = SchemaFactory.newDefaultInstance();
	}
	
	protected void readSchemaCore(XResultIntCarrier result, InputStream streamIn) {
		Schema schema;
		try {
			schema = _factorySchema.newSchema(new StreamSource(streamIn));
			_schema.set(schema);
			result.setResult(COMPLETED);
		} catch (SAXException exc) {
			result.caughtException(exc);
		}
	}
	
	protected XResultInt readSchemaFrom(ObservedActivity activityGetSchema, ResourceStreamProvider resource) {
		Objects.requireNonNull(activityGetSchema, "activityGetSchema");
		Objects.requireNonNull(resource, "resource");
		_collectObs.writeTraceEnter(TraceLevel.HIGH, this, "resource = " + resource.getResourceName());
		XResultIntCarrier result = new XResultIntCarrier(activityGetSchema);
		_collectObs.write(ObsLevel.DETAIL, "reading schema from resource " + resource.getResourceName());
		try (InputStream streamIn = resource.getResourceStream()) {
			readSchemaCore(result, streamIn);
		} catch (IOException exc) {
			if (result.isValid()) {
				// the exception must have been thrown on close
				result.setResult(EXCEPT_ON_CLOSE);
			} else {
				result.caughtException(exc);
			}
		}
		_collectObs.writeTraceReturnValue(TraceLevel.HIGH, this, result);
		return result;
	}
	
	protected XResultInt readSchemaFrom(ObservedActivity activityGetSchema, Path pathFile) {
		Objects.requireNonNull(activityGetSchema, "activityGetSchema");
		Objects.requireNonNull(pathFile, "pathFile");
		_collectObs.writeTraceEnter(TraceLevel.HIGH, this, "path = " + pathFile);
		XResultIntCarrier result = new XResultIntCarrier(activityGetSchema);
		_collectObs.write(ObsLevel.DETAIL, "reading schema from file " + pathFile);
		try (InputStream streamIn = Files.newInputStream(pathFile, StandardOpenOption.READ)) {
			readSchemaCore(result, streamIn);
		} catch (NoSuchFileException exc) {
			result.caughtException(exc);
		} catch (IOException exc) {
			if (result.isValid()) {
				// the exception must have been thrown on close
				result.setResult(EXCEPT_ON_CLOSE);
			} else {
				result.caughtException(exc);
			}
		}
		_collectObs.writeTraceReturnValue(TraceLevel.HIGH, this, result);
		return result;
	}
	
	protected abstract XResultInt tryLoadSchema();
}
