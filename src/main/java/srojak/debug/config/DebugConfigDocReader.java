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

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import srojak.xml.dom.XmlDomDocumentBuilder;

/**
 * @author Stephen
 *
 */
public class DebugConfigDocReader {
	private XmlDomDocumentBuilder _builderDocument;
	
	public DebugConfigDocReader() 
			throws ParserConfigurationException {
		_builderDocument = new XmlDomDocumentBuilder();
	}
	
	protected Document parse(File f)
			throws SAXException, IOException {
		return _builderDocument.parse(f);
	}
}
