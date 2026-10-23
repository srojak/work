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
package srojak.utest;

import java.io.PrintStream;

import srojak.core.io.IONodeIdentifier;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.writers.ObservationWriterPrintStream;

/**
 * @author Stephen
 *
 */
public interface TestStandardObservers {
	
	public static final ObservationCollector TEST_OBSV_OUT = makeStandardObserver(IONodeIdentifier.STDOUT, System.out);
	public static final ObservationCollector TEST_OBSV_ERR = makeStandardObserver(IONodeIdentifier.STDERR, System.err);
	
	private static ObservationWriter makePrintStreamWriter(IONodeIdentifier writesTo, PrintStream stream, boolean bEnableLevelFilter) {
		ObservationWriterPrintStream writer = new ObservationWriterPrintStream(writesTo, stream);
		if (bEnableLevelFilter) {
			writer.enableLevelFilter();
		}
		return writer;
	}

	private static ObservationCollector makeStandardObserver(IONodeIdentifier writesTo, PrintStream stream) {
		ObservationCollector collector = ObservationCollector.makeInstance();
		ObservationWriter writer = makePrintStreamWriter(writesTo, stream, false);
		collector.addWriterPermanent(writer);
		return collector;
	}
}
