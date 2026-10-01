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
package srojak.core.observe.writers;

import java.io.IOException;
import java.util.Objects;

import srojak.core.CommonPrintWriter;
import srojak.core.field.SetOnce;

/**
 * @author Stephen
 *
 */
public class ObservationWriterCommonPrint
		extends ObservationWriterCommonTextBase {
	private final SetOnce<CommonPrintWriter> _writer;

	/**
	 * 
	 */
	public ObservationWriterCommonPrint() {
		super();
		_writer = new SetOnce<CommonPrintWriter>(SetOnce.DEFAULT);
	}
	
	public ObservationWriterCommonPrint(CommonPrintWriter writer) {
		super();
		_writer = new SetOnce<CommonPrintWriter>(SetOnce.DEFAULT);
		assignWriter(writer);
	}
	
	public void assignWriter(CommonPrintWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writer.set(writer);
		setCanWrite(true);
	}

	@Override
	protected void writeln(String strText) throws IOException {
		_writer.get().println(strText);
	}

	@Override
	protected void flushOutput() throws IOException {
		_writer.get().flush();
	}

}
