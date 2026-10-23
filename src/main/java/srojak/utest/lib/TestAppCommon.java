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
package srojak.utest.lib;

import srojak.core.io.IONodeIdentifier;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.writers.ObservationWriterPrintStream;

/**
 * @author Stephen
 *
 */
public class TestAppCommon {
	
	public static final ObservationCollector OBSV_OUTPUT;
	public static final ObservationCollector OBSV_ERROR;
	
	static {
		OBSV_OUTPUT = ObservationCollector.makeInstance();
		ObservationWriterPrintStream writerOut = new ObservationWriterPrintStream(IONodeIdentifier.STDERR, System.out);
		OBSV_OUTPUT.addWriterPermanent(writerOut);
		
		OBSV_ERROR = ObservationCollector.makeInstance();
		ObservationWriterPrintStream writerErr = new ObservationWriterPrintStream(IONodeIdentifier.STDOUT, System.err);
		OBSV_ERROR.addWriterPermanent(writerErr);
	}
}
