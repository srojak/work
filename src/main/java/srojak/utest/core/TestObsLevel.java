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
package srojak.utest.core;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriterPrintStream;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class TestObsLevel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("TestObsLevel");
		series.getOptions().setObservationWriter(new ObservationWriterPrintStream(System.err));

		ObsLevel levelFence = ObsLevel.DEBUG;
		ObsLevel levelCompar = ObsLevel.ERROR;
		series.expectValue("TestAbove", levelCompar.toString(), true,
				levelFence.isLevelAtLeast(levelCompar));
		levelCompar = ObsLevel.DEBUG;
		series.expectValue("TestEqual", levelCompar.toString(), true,
				levelFence.isLevelAtLeast(levelCompar));
		levelCompar = ObsLevel.TRACE;
		series.expectValue("TestBelow", levelCompar.toString(), false,
				levelFence.isLevelAtLeast(levelCompar));

		series.complete();
	}

}
