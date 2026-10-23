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
package srojak.core.main;

import java.time.LocalDateTime;
import java.util.Objects;

import srojak.core.Announcer;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleListener;
import srojak.core.impl.CoreCentralStore;
import srojak.core.io.AnnouncerPrintStream;
import srojak.core.io.IONodeIdentifier;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationCommon;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.impl.ObservationCollectorLockableInstance;
import srojak.core.observe.writers.ObservationWriterBase;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.props.DebugProperties;
import srojak.core.reflect.ClassReflector;
import srojak.core.tools.EnvTool;

/**
 * @author Stephen
 *
 */
public class CoreCollectors {
	
	private static final Announcer _announcer;
	private static final ObservationCollectorLockableInstance _collectOut;
	private static final ObservationCollectorLockableInstance _collectErr;
	private static final ObservationCollectorLockableInstance _collectLog;
	private static final ObservationWriterBase _writerOut;
	private static final ObservationWriterBase _writerErr;
	private static final PrimaryLogWriter _writerLog;
	
	static {
		_announcer = new AnnouncerPrintStream(System.err);
		
		_collectOut = new ObservationCollectorLockableInstance();
		_collectErr = new ObservationCollectorLockableInstance();
		_collectLog = new ObservationCollectorLockableInstance();
		
		_writerOut = new ObservationWriterPrintStream(IONodeIdentifier.STDOUT, System.out);
		_writerErr = new ObservationWriterPrintStream(IONodeIdentifier.STDERR, System.err);
		_writerOut.setShowLocations(true);
		_writerLog = new PrimaryLogWriter(_writerErr);
		_writerLog.setShowLocations(true);
		_collectOut.addWriter(_writerOut);
		_collectErr.addWriter(_writerErr);
		_collectLog.addWriter(_writerLog);
		_collectOut.lockWriters();
		_collectErr.lockWriters();
		_collectLog.lockWriters();
		
		// TODO: gather function from KernelLogManager
	}

	/**
	 * 
	 */
	public CoreCollectors() {
		
		
	}
	
	public ObservationCollector output() {
		return _collectOut;
	}
	
	public ObservationCollector error() {
		return _collectErr;
	}
	
	public ObservationCollector log() {
		return _collectLog;
	}
	
	public ObservationWriter getLogWriter() {
		return _writerLog;
	}
	
	public void setLogWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		writer.addLifeCycleListener(new LogWriterClosedListener());
		_writerLog.redirectWriter(writer);
	}
	
	public void startLogFile(ObservationWriter writer, LocalDateTime dtNow) {
		ClassReflector _reflectorApp = CoreCentralStore._instance.getAppClass();
		writer.write(ObsLevel.NOTICE, SourceLocation.start(), 
				"log created for " + _reflectorApp.getName() + " on "
						+ ObservationCommon.FORMAT_TIME_STAMP.format(dtNow));
		writer.write(ObsLevel.NOTICE, SourceLocation.start(), 
					"Java version " + EnvTool.getJavaVersion());
	}
	
	public void resetLogWriter() {
		_writerLog.restoreDefaultWriter(true);
	}
	
	public Announcer announcer() {
		return _announcer;
	}
	
	public boolean canHighWriterShowLocations() {
		return _writerErr.canShowLocations();
	}
	
	public void setHighWriterShowLocations(boolean bState) {
		_writerErr.setShowLocations(bState);
	}
	
	private static class LogWriterClosedListener
			implements LifeCycleListener {

		@Override
		public void receive(LifeCycleEvent event) {
			ObservationWriter writerClosed = (ObservationWriter) event.getSource();
			if (_writerLog.isCurrentWriter(writerClosed)) {
				_writerLog.restoreDefaultWriter(false);
				_announcer.announceMessage(ObsLevel.ALERT, CoreCollectors.class, "active primary log writer closed");
			}
			DebugProperties properties = CoreCentral.getDebugProperties();
			if (properties.isDiagCloseEnabled()) {
				_collectErr.recordDiagnostic("previous log writer closed");
			}
			writerClosed.removeLifeCycleListener(this);
		}
		
	}
}
