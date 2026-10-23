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
package srojak.kernel.priv;

import java.io.IOException;
import java.util.Objects;

import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleListener;
import srojak.core.io.IONodeIdentifier;
import srojak.core.main.CoreCentral;
import srojak.core.main.CoreCollectors;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationCommon;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.writers.ObservationWriterBase;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.props.DebugProperties;
import srojak.kernel.Kernel;

/**
 * @author Stephen
 *
 */
public class KernelLogManager
		implements ObservationCommon {
	private final ObservationCollector _collectLog;
	private final ObservationWriterBase _writerOut;
	private final ObservationWriterBase _writerErr;
	private ObservationWriter _writerLog;
	private LogWriterClosedListener _listenerClosed;

	KernelLogManager() {
		_writerOut = new ObservationWriterPrintStream(IONodeIdentifier.STDOUT, System.out);
		_writerErr = new ObservationWriterPrintStream(IONodeIdentifier.STDERR, System.err);
		_writerOut.setShowLocations(true);
		
		_writerLog = _writerErr;
		_collectLog = ObservationCollector.makeInstance();
		
		_listenerClosed = new LogWriterClosedListener();
	}
	
	void initialize() {
		CoreCollectors outputs = new CoreCollectors();
		_collectLog.addWriterPermanent(outputs.getLogWriter());
	}
	
	public ObservationWriter getLogWriter() {
		return _writerLog;
	}
	
	public ObservationCollector collectorLog() {
		return _collectLog;
	}
	
	public void setLogWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writerLog.removeLifeCycleListener(_listenerClosed);
		_writerLog = writer;
		if (_writerLog != _writerErr) {
			_writerLog.addLifeCycleListener(_listenerClosed);
		}
	}
	
	void onShutdown(DebugProperties propsDebug) {
		if (propsDebug.isDiagCloseEnabled()) {
			_writerLog.writeDiagnostic("notified of shutdown");
		}
		if (_writerLog != _writerErr) {
			ObservationWriter writerPrior = _writerLog;
			_writerLog = _writerErr;
			writerPrior.removeLifeCycleListener(_listenerClosed);
			try {
				writerPrior.close();
			} catch (IOException exc) {
				_writerLog.writeDiagnostic("on closing debug writer: " + exc.getMessage());
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
				CoreCollectors outputs = new CoreCollectors();				
				outputs.announcer().announceMessage(ObsLevel.ALERT, Kernel.class, "debug writer closed");
				DebugProperties properties = CoreCentral.getDebugProperties();
				if (properties.isDiagCloseEnabled()) {
					outputs.error().recordDiagnostic("previous debug writer closed");
				}
			}
			writerClosed.removeLifeCycleListener(this);
		}
		
	}
}
