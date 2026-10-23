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
package srojak.utest.lib.debug;

import java.util.List;

import srojak.core.io.FileExistence;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.result.XResult;
import srojak.debug.DebugSwitch;
import srojak.debug.config.DebugConfigNames;
import srojak.debug.config.DebugConfigReaderImpl;
import srojak.utest.lib.TestAppCommon;
import srojak.xml.stream.errors.XmlStreamParseErrorDescr;

/**
 * @author Stephen
 *
 */
public class TestAppDebug {
	
	public static void analyzeDebugConfigParse(DebugSwitch swDebug, DebugConfigReaderImpl reader) {
		List<XmlStreamParseErrorDescr> listErrors = reader.getParseErrors();
		SingleObservationCollector collector = swDebug.createCollector(ObsLevel.NOTICE);
		collector.append("has ");
		collector.append(listErrors.size());
		collector.append(" parse errors");
		for(XmlStreamParseErrorDescr error : listErrors) {
			collector.append("\n  ");
			collector.append(error);
		}
		collector.alsoWriteTo(TestAppCommon.OBSV_ERROR);
		collector.commit();
	}
	
	public static boolean tryReadDebugConfigFile(DebugSwitch swDebug) {
		DebugConfigReaderImpl readerDebug = new DebugConfigReaderImpl();
		readerDebug.setRecordComments(true);
		XResult resultRead = readerDebug.readConfigFile(DebugConfigNames.FILE_SWITCHES, FileExistence.Any);
		if (!resultRead.isValid()) {
			swDebug.recordException(ObsLevel.ERROR, DebugConfigReaderImpl.ACTIVITY_READ, resultRead.getException(), true);
			return false;
		}
		if (readerDebug.hasParseErrors()) {
			analyzeDebugConfigParse(swDebug, readerDebug);
		}
		return true;
	}
}
