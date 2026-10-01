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
package srojak.core.kernel.priv;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import srojak.core.Announcer;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleListener;
import srojak.core.io.AnnouncerPrintStream;
import srojak.core.kernel.Kernel;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.writers.ObservationWriterBase;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.observe.writers.ObservationWriterSwitching;
import srojak.core.props.DebugProperties;

/**
 * @author Stephen
 *
 */
public class KernelOutput {
	private final ObservationCollector _collect;
	private final ObservationWriterSwitching _writer;
	private final ObservationWriterBase _writerOut;
	private final ObservationWriterBase _writerErr;
	private final Announcer _announcer;
	private ObservationWriter _writerLog;
	private LogWriterClosedListener _listenerClosed;

	// TODO: find a common home for this
	public static final DateTimeFormatter FORMAT_TIME_STAMP;

	static {
		FORMAT_TIME_STAMP = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
	}
	
	KernelOutput() {
		_collect = ObservationCollector.makeInstance();
		_writerOut = new ObservationWriterPrintStream(System.out);
		_writerErr = new ObservationWriterPrintStream(System.err);
		_writerOut.setShowLocations(true);
		_writer = new ObservationWriterSwitching(_writerOut, _writerErr);
		_writer.setObsLevel(ObsLevel.WARN);
		_collect.addWriterPermanent(_writer);
		
		_announcer = new AnnouncerPrintStream(System.err);
		
		_writerLog = _writerErr;
		_listenerClosed = new LogWriterClosedListener();
	}
	
	public Announcer announcer() {
		return _announcer;
	}
	
	public ObservationCollector collector() {
		return _collect;
	}

	public ObsLevel getHighObsLevel() {
		return _writer.getObsLevel();
	}

	public void setHighObsLevel(ObsLevel level) {
		_writer.setObsLevel(level);
	}
	
	public boolean canHighWriterShowLocations() {
		return _writerErr.canShowLocations();
	}
	
	public void setHighWriterShowLocations(boolean bState) {
		_writerErr.setShowLocations(bState);
	}
	
	public ObservationWriter getLogWriter() {
		return _writerLog;
	}
	
	public void setLogWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writerLog.removeLifeCycleListener(_listenerClosed);
		_writerLog = writer;
		if (_writer != _writerErr) {
			_writer.addLifeCycleListener(_listenerClosed);
		}
	}
	
	void onShutdown(DebugProperties propsDebug) {
		if (propsDebug.isDiagCloseEnabled()) {
			_writerLog.writeDiagnostic("notified of shutdown");
		}
		if (_writerLog != _writerErr) {
			ObservationWriter writerPrior = _writer;
			_writerLog = _writerErr;
			writerPrior.removeLifeCycleListener(_listenerClosed);
			try {
				writerPrior.close();
			} catch (IOException exc) {
				_writer.writeDiagnostic("on closing debug writer: " + exc.getMessage());
			}
		}
	}
	
	private class LogWriterClosedListener
			implements LifeCycleListener {

		@Override
		public void receive(LifeCycleEvent event) {
			ObservationWriter writerClosed = (ObservationWriter) event.getSource();
			if (_writerLog == writerClosed) {
				_writerLog = _writerErr;
				_announcer.announceMessage(ObsLevel.ALERT, Kernel.class, "debug writer closed");
				DebugProperties properties = KernelStore._propsDebug;
				if (properties.isDiagCloseEnabled()) {
					_writer.writeDiagnostic("previous debug writer closed");
				}
			}
			writerClosed.removeLifeCycleListener(this);
		}
		
	}
}
