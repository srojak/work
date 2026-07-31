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

import srojak.core.observe.ObsLevel;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.identifiers.TestInstanceIdentifier;

/**
 * @author Stephen
 *
 */
public abstract class UTestInstance {
	private final UnitTestSeries _utest;
	private final TestInstanceIdentifier _idInstance;
	private TestOutcome _outcome;
	
	public UTestInstance(UnitTestSeries utest, TestInstanceIdentifier idInstance) {
		Objects.requireNonNull(utest);
		Objects.requireNonNull(idInstance);
		_utest = utest;
		_idInstance = idInstance;
		_outcome = TestOutcome.NONE;
	}

	public TestInstanceIdentifier getIdentifier() {
		return _idInstance;
	}
	
	public final TestOutcome getOutcome() {
		return _outcome;
	}
	
	protected final void setOutcome(TestOutcome outcome) {
		if (outcome == TestOutcome.NONE) {
			throw new IllegalStateException("not a valid outcome");
		}
		_outcome = outcome;
	}
	
	protected StringBuilder getInitialString() {
		return UTestCommonMessages.startTestMessageLine(_utest, _idInstance);
	}

	protected void checkStopOnFailure() {
		_utest.checkStopOnFailure(_idInstance, _outcome);
	}
	
	protected void writeMessage(ObsLevel level, String strText) {
		_utest.writeMessageLine(level, strText);
	}
	
	protected void writeOutcomeMessage(TestOutcome outcome, String strLine) {
		_utest.writeOutcomeMessage(outcome, strLine);
	}
	
	protected void writeStack(ObsLevel level, Exception exc) {
		_utest.writeStack(level, exc);
	}
}
