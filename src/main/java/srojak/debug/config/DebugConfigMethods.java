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

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Objects;

import javax.xml.stream.XMLStreamException;

import org.xml.sax.SAXException;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.result.XResult;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;
import srojak.core.result.XResultStatusCarrier;
import srojak.debug.DebugNexus;

/**
 * @author Stephen
 *
 */
@Deprecated
public class DebugConfigMethods {
		
	private static final DebugNexus _nexus = new DebugNexus();
	private static boolean _bShowStackOnException = false;
	
	public static void setShowStackOnException(boolean bState) {
		_bShowStackOnException = bState;
	}	
	
	public static XResultOf<DebugConfigReader2Pass> createReader() {
		XResultCarrierOf<DebugConfigReader2Pass> result = new XResultCarrierOf<DebugConfigReader2Pass>();
		try {
			DebugConfigReader2Pass reader = new DebugConfigReader2Pass();
			result.setResult(reader);
		} catch (SAXException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	private static void trapException(ObservationWriter writer, Exception exc) {
		StringBuilder sb = new StringBuilder();
		Class<?> classEx = exc.getClass();
		sb.append(classEx.getSimpleName());
		sb.append(" reading debug config file: ");
		sb.append(exc.getMessage());	
		writer.write(ObsLevel.FATAL, sb.toString());
		System.err.println(sb.toString());
		if (_bShowStackOnException) {
			StackTraceElement[] frames = exc.getStackTrace();
			System.err.println("stack trace:");
			for (StackTraceElement frame : frames) {
				System.err.println("  " + frame);
			}
		}
	}

	public static XResult readConfigFileTwoPass(String strFile, boolean bFileMustExist) {
		Objects.requireNonNull(strFile, "strFile");
		XResultStatusCarrier result = new XResultStatusCarrier();
		ObservationWriter writer = _nexus.getWriter();
		try {
			DebugConfigReader2Pass reader = new DebugConfigReader2Pass();
			reader.readFrom(strFile);
			result.setValid();
		} catch (NoSuchFileException exc) {
			if (bFileMustExist) {
				writer.write(ObsLevel.FATAL, strFile + " does not exist");
				result.caughtException(exc);
			} else {
				result.setValid();
			}
		} catch (IOException exc) {
			trapException(writer, exc);
			result.caughtException(exc);
		} catch (XMLStreamException exc) {
			trapException(writer, exc);
			result.caughtException(exc);
		} catch (SAXException exc) {
			trapException(writer, exc);
			result.caughtException(exc);
		}
		return result;
	}
}
