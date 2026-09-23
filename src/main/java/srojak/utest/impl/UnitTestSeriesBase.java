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
package srojak.utest.impl;

import java.util.Objects;

import srojak.core.Named;
import srojak.core.observe.HasSingleChangeableObservationCollector;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestOptions;
import srojak.utest.UnitTestSeries;
import srojak.utest.identifiers.TestInstanceIdentifier;

public abstract class UnitTestSeriesBase
		implements Named, HasSingleChangeableObservationCollector {
	private ObservationCollector _collectObs;	
	private final String _strName;
	protected final UnitTestOptions _options;
	private int _nFailed;
	
	public static final ObsLevel OBS_LEVEL_DEFAULT = ObsLevel.INFO;
	
	public UnitTestSeriesBase(String strName) {
		Objects.requireNonNull(strName, "strName");
		_collectObs = ObservationCollector.makeInstance();
		_strName = strName;
		_options = new UnitTestOptions();
		_nFailed = 0;		
		ObservationWriterPrintStream writer = new ObservationWriterPrintStream(System.err);
		writer.enableLevelFilter();
		writer.setObsLevel(OBS_LEVEL_DEFAULT);
		_collectObs.addWriterPermanent(writer);
	}
	
	@Override
	public String getName() {
		return _strName;
	}
	
	/**
	 * Gets the observation collector in use.
	 * @return The {@code ObservationWriter} the tests will use.
	 */
	@Override
	public ObservationCollector getObservationCollector() {
		return _collectObs;
	}
	
	@Override
	public void setObservationCollector(ObservationCollector collector) {
		Objects.requireNonNull(collector, "collector");
		_collectObs = collector;
	}

	public int getFailedTestCount() {
		return _nFailed;
	}
	
	/**
	 * get the options container for this series.
	 * @return the {@UnitTestOptions} object.
	 */
	public UnitTestOptions getOptions() {
		return _options;
	}
		
	/**
	 * write a message line at an observation level to the output destination for the series.
	 * @param level The {@code ObsLevel} for the message.
	 * @param strMessage The text of the message.
	 */
	public void writeMessageLine(ObsLevel level, String strMessage) {
		writeMessage(level, strMessage);
	}
	
	void writeMessageDirect(ObsLevel level, String strMessage) {
		writeMessage(level, strMessage);
	}
	
	protected void checkStopOnFailure(TestInstanceIdentifier idInstance, TestOutcome outcome) {
		if (outcome == TestOutcome.FAIL) {
			_nFailed++;
			if (_options.getStopOnFailure()) {
				StringBuilder sb = new StringBuilder("*STOP: Test series ");
				sb.append(_strName);
				sb.append(' ');
				sb.append(idInstance.getText());
				sb.append(" failed");
				writeMessageDirect(ObsLevel.ALERT, sb.toString());
				System.exit(2);
			}
		}
	}
	
	protected void writeOwnMessage(ObsLevel level, String strText) {
		_collectObs.write(level, strText);
	}
	
	protected void writeOwnOutcomeMessage(TestOutcome outcome, String strLine) {
		writeOutcomeMessage(outcome, strLine);
	}
	
	void writeMessage(ObsLevel level, String strText) {
		_collectObs.write(level, strText);
	}
	
	void writeOutcomeMessage(TestOutcome outcome, String strLine) {
		ObsLevel level = outcome == TestOutcome.PASS 
				? UnitTestSeries.LEVEL_NON_FAILURE : ObsLevel.ERROR;
		_collectObs.write(level, strLine);
	}
	
	void writeStack(ObsLevel level, Exception exc) {
		// TODO integrate with changes in observation collector
		if (_options.getShowStackOnExceptions()) {
			StringBuilder sb = new StringBuilder("stack trace:");
			StackTraceElement[] frames = exc.getStackTrace();
			for (StackTraceElement frame : frames) {
				sb.append("\n    ");
				sb.append(frame);
			}
			_collectObs.write(level, sb.toString());
		}
	}
}
