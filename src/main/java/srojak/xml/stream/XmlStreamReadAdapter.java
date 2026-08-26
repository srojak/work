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
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;

/**
 * @author Stephen
 *
 */
public class XmlStreamReadAdapter {
	private final XmlStreamParseFunction _fnParse;
	private final XmlStreamInputBuilder _builderStream;
	
	public XmlStreamReadAdapter(XmlStreamParseFunction fnParse) {
		Objects.requireNonNull(fnParse, "fnParse");
		_fnParse = fnParse;
		_builderStream = new XmlStreamInputBuilder();
	}
	
	private void readCommon(InputStream streamIn, XResultStatusCarrier result) {
		try {
			XMLStreamReader reader = _builderStream.createStreamReader(streamIn);
			_fnParse.apply(reader);
			result.setValid();
		} catch (XMLStreamException exc) {
			result.caughtException(exc);
		}
	}
	
	public XResult readFrom(InputStream streamIn) {
		XResultStatusCarrier result = new XResultStatusCarrier();
		readCommon(streamIn, result);
		return result;
	}
	
	public XResult readFrom(Path pathFile) {
		XResultStatusCarrier result = new XResultStatusCarrier();
		InputStream streamIn = null;
		try {
			streamIn = Files.newInputStream(pathFile, StandardOpenOption.READ);
		} catch (IOException e) {
			result.caughtException(e);
			return result;
		}
		readCommon(streamIn, result);
		return result;
	}
	
	public XResult readFrom(String strPath) {
		XResultStatusCarrier result = new XResultStatusCarrier();
		Path pathFile = Path.of(strPath);
		InputStream streamIn = null;
		try {
			streamIn = Files.newInputStream(pathFile, StandardOpenOption.READ);
		} catch (IOException e) {
			result.caughtException(e);
			return result;
		}
		readCommon(streamIn, result);
		return result;
	}
}
