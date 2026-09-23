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
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservedActivity;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.xml.stream.factories.XmlStreamInputFactory;
import srojak.xml.stream.factories.XmlStreamParserFactory;
import srojak.xml.stream.parse.XmlStreamParser;

/**
 * @author Stephen
 *
 */
public abstract class XmlStreamContentValidatingReaderBase<P extends XmlStreamParser> 
		extends XmlStreamContentAndSchemaReaderBase<P> {

	/**
	 * @param parser
	 */
	public XmlStreamContentValidatingReaderBase(XmlStreamParserFactory<P> factory) {
		super(factory);
		super.setValidating(true);
	}

	@Override
	protected XmlStreamAdapter createAdapter(XmlStreamInputFactory factoryInput, XResultIntCarrier resultCreate) {
		// check for schema
		if (_schema.isEmpty()) {
			XResultInt resultSchema = tryLoadSchema();
			resultCreate.copyFrom(resultSchema);
			if (!resultSchema.isValid()) {
				_collectObs.write(ObsLevel.ERROR, "failed to load schema");
				return null;
			}
		} else {
			resultCreate.setResult(NO_ACTION_TAKEN);
		}
		return new XmlStreamValidatingReadAdapter(factoryInput, _schema.get(), _parser);
	}
	
	public XResultInt loadSchema() {
		return tryLoadSchema();
	}
}
