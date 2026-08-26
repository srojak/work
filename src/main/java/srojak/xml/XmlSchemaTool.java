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
package srojak.xml;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;
/**
 * @author Stephen
 *
 */
public class XmlSchemaTool {
	private SchemaFactory _factory;
	
	public XmlSchemaTool() {
		_factory = SchemaFactory.newDefaultInstance();
	}
	
	public Schema readSchemaDirect(StreamSource source) 
			throws SAXException {
		return _factory.newSchema(source);
	}
	
	public XResultOf<Schema> readSchema(StreamSource source) {
		XResultCarrierOf<Schema> result = new XResultCarrierOf<Schema>();
		try {
			result.setResult(_factory.newSchema(source));
		} catch (SAXException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public Validator readAndCreateValidator(StreamSource source)
			throws SAXException {
		Schema schema = _factory.newSchema(source);
		return schema.newValidator();
	}
}
