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
package srojak.utest.lib;

import srojak.core.io.LogFileLocator;
import srojak.core.main.CoreCentral;
import srojak.core.main.CoreCollectors;
import srojak.core.main.ObservationWriterLogFileSource;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCommonCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterSource;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.props.DebugProperties;
import srojak.core.result.XResult;
import srojak.core.result.XResultOf;

/**
 * @author Stephen
 *
 */
public class TestLoggingAppBase {

	public boolean openLogFile(ObservationCommonCollector collectObs, String strPrefix) {
		DebugProperties propsDebug = CoreCentral.getDebugProperties();
		LogFileLocator locLog = new LogFileLocator(propsDebug, strPrefix);
		XResult resultValid = locLog.validate();
		if (!resultValid.isValid()) {
			collectObs.recordThrowable(ObsLevel.FATAL, ObservedActivity.VALIDATE, resultValid.getException(), false);
			return false;
		}
		ObservationWriterSource source = new ObservationWriterLogFileSource(locLog);
		XResultOf<ObservationWriter> resultWriter = source.createFor(getClass());
		if (resultWriter.isValid()) {
			ObservationWriter writer = resultWriter.getResult();
			writer.setShowLocations(true);
			writer.setShowExceptionStackEnabled(true);
			CoreCollectors outputs = new CoreCollectors();
			outputs.setLogWriter(writer);
		} else {
			collectObs.recordThrowable(ObsLevel.FATAL, new SingleActivity("create log file"), resultWriter.getException(), false);
			return false;
		}
		return true;
	}
}
