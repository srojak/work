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
package srojak.core.backplane;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Objects;

import srojak.core.OnceFlag;
import srojak.core.observe.Announcer;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterSource;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.observe.writers.AnnouncerPrintStream;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.props.DebugProperties;
import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;

/**
 * @author Stephen
 *
 */
public class ApplicationBackplane {

	private static final Runtime _runtime;
	private static final LinkedList<Runnable> _queueShutdown;
	private static final OnceFlag _flagStart;
	private static final Announcer _announceError;
	private static final ObservationWriter _writerOut;
	private static final ObservedActivity ACTIVITY_INIT;	
	private static final DebugProperties _propsDebug;
	
	public static final ObservationWriter WRITER_STD_ERR;
	
	static {
		_runtime = Runtime.getRuntime();
		_queueShutdown = new LinkedList<Runnable>();
		_runtime.addShutdownHook(new Thread(ApplicationBackplane::onShutdown));
		_flagStart = new OnceFlag();
		_announceError = new AnnouncerPrintStream(System.err);
		_writerOut = new ObservationWriterPrintStream(System.out);
		_writerOut.setShowLocations(false);
		_propsDebug = new DebugProperties();
		ACTIVITY_INIT = new SingleActivity("app init");
		WRITER_STD_ERR = new ObservationWriterPrintStream(System.err);
	}
	
	public static XResult startApp() {
		if (_flagStart.getState()) {
			XResultStatusCarrier result = new XResultStatusCarrier(ACTIVITY_INIT);
			result.setValid();
			return result;
		} else {
			Path pathCurDir = Path.of(System.getProperty("user.dir"));
			XResult result = _propsDebug.loadFrom(pathCurDir, DebugProperties.PROPERTIES_FILE_NAME);
			return result;
		}
	}
	
	private static void onShutdown() {
		while (!_queueShutdown.isEmpty()) {
			Runnable action = _queueShutdown.pop();
			action.run();
		}
	}
	
	public static void addShutdownAction(Runnable action) {
		Objects.requireNonNull(action, "action");
		_queueShutdown.add(action);
	}
	
	public static DebugProperties getDebugProperties() {
		return _propsDebug;
	}
	
	public static void cannotCreateWriter(ObservationWriterSource source, Exception exc) {
		_announceError.announceMessage(ObsLevel.ALERT, source.getClass(), exc.getMessage());
	}
	
	public static void writerInvalidated(ObservationWriter writer) {
		_announceError.announceMessage(ObsLevel.ALERT, writer.getClass(), "writer invalidated");
	}
	
	public static void writeToOutput(ObsLevel level, String strMessage) {
		_writerOut.write(level, SourceLocation.redacted(), strMessage);
	}
}
