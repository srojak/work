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
package srojak.debug.impl;

import java.time.LocalDateTime;
import java.util.EnumSet;

import srojak.core.Announcer;
import srojak.core.faults.FaultFormat;
import srojak.core.faults.StructuredFault;
import srojak.core.io.IONodeIdentifier;
import srojak.core.io.IONodeName;
import srojak.core.io.IONodeType;
import srojak.core.main.CoreCollectors;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.code.CodeFaultObservation;
import srojak.core.observe.code.CodeObservation;
import srojak.core.observe.writers.AbstractLogWriterBase;

/**
 * @author Stephen
 *
 */
public class DebugLogWriter
		extends AbstractLogWriterBase {
	private final Announcer _announcer;
	private final ObservationWriter _writerLog;
	
	public static final IONodeIdentifier ID_DEBUG_LOG = new IONodeName(IONodeType.ABSTRACT, "debug log");

	/**
	 * @param writesTo
	 */
	public DebugLogWriter() {
		super(ID_DEBUG_LOG);
		setAutoFlush(true);
		CoreCollectors outputs = new CoreCollectors();
		_announcer = outputs.announcer();
		_writerLog = outputs.getLogWriter();
	}

	@Override
	protected ObservationWriter getCurrentLogWriter() {
		return _writerLog;
	}

	@Override
	public void write(SingleObservationCollector collector, SourceLocation locOrigin, String strText) {
		super.write(collector, locOrigin, strText);
		ObsLevel level = collector.getObsLevel();
		if (DebugNexusCore.isLevelAtAnnounceLevel(level)) {
			_announcer.announce(level, locOrigin);
		}
	}

	@Override
	public void write(ObsLevel level, String strText) {
		super.write(level, strText);
		if (DebugNexusCore.isLevelAtAnnounceLevel(level)) {
			_announcer.announceMessage(level, getClass(), strText);
		}
	}

	@Override
	public void writeObserved(String strLead, CodeObservation observation) {
		super.writeObserved(strLead, observation);
		ObsLevel level = observation.level();
		if (DebugNexusCore.isLevelAtAnnounceLevel(level)) {
			_announcer.announce(level, observation.origin());
		}
	}

	@Override
	public void writeObservedTrace(String strLead, CodeObservation observation) {
		super.writeObservedTrace(strLead, observation);
		ObsLevel level = observation.level();
		if (DebugNexusCore.isLevelAtAnnounceLevel(level)) {
			_announcer.announce(level, observation.origin());
		}
	}

	@Override
	public void write(ObsLevel level, SourceLocation locOrigin, String strText) {
		super.write(level, locOrigin, strText);
		if (DebugNexusCore.isLevelAtAnnounceLevel(level)) {
			_announcer.announce(level, locOrigin);
		}
	}

	@Override
	public void writeTrace(ObsLevel level, SourceLocation locOrigin, String strText) {
		super.writeTrace(level, locOrigin, strText);
		if (DebugNexusCore.isLevelAtAnnounceLevel(level)) {
			_announcer.announce(level, locOrigin);
		}
	}

	@Override
	public void writeException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc,
			boolean bShowStack) {
		super.writeException(level, locOrigin, activity, exc, bShowStack);
		if (DebugNexusCore.isLevelAtAnnounceLevel(level)) {
			_announcer.announceException(level, locOrigin, exc);
		}
	}

	@Override
	public void writeObservedFault(String strLead, CodeFaultObservation observation) {
		super.writeObservedFault(strLead, observation);
		ObsLevel level = observation.level();
		if (DebugNexusCore.isLevelAtAnnounceLevel(level)) {
			_announcer.announceFault(level, observation.origin(), observation.activity(), observation.fault());
		}
	}

	@Override
	public void writeFault(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			EnumSet<FaultFormat> formats, String strLead, StructuredFault fault) {
		super.writeFault(level, locOrigin, activity, formats, strLead, fault);
		if (DebugNexusCore.isLevelAtAnnounceLevel(level)) {
			_announcer.announceFault(level, locOrigin, activity, fault);
		}
	}

	@Override
	public void writeTimeStamp(ObsLevel level, SourceLocation locOrigin, String strLead, LocalDateTime dtObs) {
		super.writeTimeStamp(level, locOrigin, strLead, dtObs);
		if (DebugNexusCore.isLevelAtAnnounceLevel(level)) {
			_announcer.announce(level, locOrigin);
		}
	}
}
