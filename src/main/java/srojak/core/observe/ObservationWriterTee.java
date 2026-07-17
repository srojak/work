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

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Stephen
 *
 */
public class ObservationWriterTee
		extends ObservationWriterBase
		implements ObservationWriter {
	private final ObservationWriter _writer1;
	private final ObservationWriter _writer2;
	
	public ObservationWriterTee(ObservationWriter writer1, ObservationWriter writer2) {
		Objects.requireNonNull(writer1, "writer1");
		Objects.requireNonNull(writer2, "writer2");
		_writer1 = writer1;
		_writer2 = writer2;
	}

	@Override
	public boolean isLevelAccepted(ObsLevel level) {
		// always accepts input to pass down
		return true;
	}

	@Override
	public void write(ObsLevel level, String strText) {
		_writer1.write(level, strText);
		_writer2.write(level, strText);
	}

	@Override
	public void buildAndWrite(ObsLevel level, Consumer<StringBuilder> message) {
		StringBuilder sb = new StringBuilder();
		sb.append(": ");
		message.accept(sb);
		_writer1.write(level, sb.toString());
		_writer2.write(level, sb.toString());
	}

	@Override
	public void buildAndWrite(ObsLevel level, ObsPassThroughList listPassThrough,
			BiConsumer<StringBuilder, ObsPassThroughList> messageBuilder) {
		StringBuilder sb = new StringBuilder(level.getName());
		sb.append(": ");
		messageBuilder.accept(sb, listPassThrough);
		_writer1.write(level, sb.toString());
		_writer2.write(level, sb.toString());
	}

	@Override
	public void writeDiagnostic(String strText) {
		_writer1.writeDiagnostic(strText);
		_writer2.writeDiagnostic(strText);
	}

	@Override
	public void writeTimeStamp(ObsLevel level) {
		StringBuilder sb = new StringBuilder();
		sb.append(level.getName());
		sb.append( ": time ");
		sb.append(getDateAndTimeStamp());
		_writer1.write(level, sb.toString());
		_writer2.write(level, sb.toString());
	}

	@Override
	public ObservationCollector createCollector(ObsLevel level) {
		return new ObservationCollectorObj(this, level, SourceLocation.caller());
	}

	@Override
	public void write(ObservationCollector collector, SourceLocation locOrigin, String strText) {
		_writer1.write(collector.getLevel(), strText);
		_writer2.write(collector.getLevel(), strText);
	}

	@Override
	public void flush() {
		_writer1.flush();
		_writer2.flush();
	}
}
