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
package srojak.core.observe;

import java.io.PrintStream;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

/**
 * @author Stephen
 *
 */
public class ObservationWriterPrintStream
		extends ObservationWriterBase
		implements ObservationWriter {
	private final PrintStream _streamOut;

	/**
	 * 
	 */
	public ObservationWriterPrintStream(PrintStream stream) {
		Objects.requireNonNull(stream, "stream");
		if (stream.checkError())
			throw new IllegalArgumentException("stream is not valid");
		_streamOut = stream;
	}

	@Override
	public boolean isLevelAccepted(ObsLevel level) {
		return true;
	}

	@Override
	public void write(ObsLevel level, String strText) {
		_streamOut.println(level.getName() + ": " + strText);			
	}

	@Override
	public void buildAndWrite(ObsLevel level, Consumer<StringBuilder> message) {
		StringBuilder sb = new StringBuilder(level.getName());
		sb.append(": ");
		message.accept(sb);
		_streamOut.println(sb.toString());
	}

	@Override
	public void buildAndWrite(ObsLevel level, int i, ObjIntConsumer<StringBuilder> message) {
		StringBuilder sb = new StringBuilder(level.getName());
		sb.append(": ");
		message.accept(sb, i);
		_streamOut.println(sb.toString());
	}

	@Override
	public void buildAndWrite(ObsLevel level, ObsPassThroughList listPassThrough,
			BiConsumer<StringBuilder, ObsPassThroughList> messageBuilder) {
		StringBuilder sb = new StringBuilder(level.getName());
		sb.append(": ");
		messageBuilder.accept(sb, listPassThrough);
		_streamOut.println(sb.toString());
	}

	@Override
	public void writeDiagnostic(String strText) {
		_streamOut.println("*DIAG: " + strText);	
	}

	@Override
	public void writeTimeStamp(ObsLevel level) {
		_streamOut.println(level.getName() + ": time " + getDateAndTimeStamp());
	}

	@Override
	public void write(ObservationCollector collector, SourceLocation locOrigin, String strText) {
		write(collector.getLevel(), strText);
	}

	@Override
	public void flush() {
		_streamOut.flush();
	}
}
