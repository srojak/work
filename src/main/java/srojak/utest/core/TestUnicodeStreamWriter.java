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

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import srojak.core.io.UnicodeOutputStreamWriter;
import srojak.core.observe.ObsLevel;
import srojak.utest.TestStandardObservers;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class TestUnicodeStreamWriter 
		implements TestStandardObservers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitTestSeries series = new UnitTestSeries("UnicodeStreamWriter Test");
		series.setObservationCollector(TEST_OBSV_ERR);
		
		Path pathFile = Path.of("Unitext.txt");
		try {
			OutputStream streamOut = Files.newOutputStream(pathFile);
			UnicodeOutputStreamWriter writer = new UnicodeOutputStreamWriter(streamOut);
			writer.write(LocalDateTime.now().toString());
			writer.writeln();
			writer.write("Copyright © 2026 Stephen Rojak");
			writer.writeln();
			writer.flush();
			streamOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TEST_OBSV_OUT.write(ObsLevel.INFO, "done");
	}

}
