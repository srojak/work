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
import srojak.core.observe.ObsLevel;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestOptions;
import srojak.utest.identifiers.TestInstanceIdentifier;

public abstract class UnitTestSeriesBase
		implements Named {
	private final String _strName;
	protected final UnitTestOptions _options;
	private int _nFailed;
	
	public UnitTestSeriesBase(String strName) {
		Objects.requireNonNull(strName, "strName");
		_strName = strName;
		_options = new UnitTestOptions();
		_nFailed = 0;		
	}
	
	@Override
	public String getName() {
		return _strName;
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
	
	void writeMessageDirect(ObsLevel level, String strMessage) {
		((UnitTestOptionsBase)_options).writeMessage(level, strMessage);
	}
		
	/**
	 * write a message line at an observation level to the output destination for the series.
	 * @param level The {@code ObsLevel} for the message.
	 * @param strMessage The text of the message.
	 */
	public void writeMessageLine(ObsLevel level, String strMessage) {
		writeMessageDirect(level, strMessage);
	}
	
	protected void writeOutcomeMessage(TestOutcome outcome, String strLine) {
		((UnitTestOptionsBase)_options).writeOutcomeMessage(outcome, strLine);
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
	
	protected void writeStack(ObsLevel level, Exception exc) {
		((UnitTestOptionsBase)_options).writeStack(level, exc);
	}

}
