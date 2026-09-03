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
import java.util.function.ObjIntConsumer;

/**
 * @author Stephen
 *
 */
public class ObservationWriterHolder 
		implements ObservationWriter {
	private ObservationWriter _writer;
	
	public ObservationWriterHolder(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writer = writer;
	}
	
	public ObservationWriter getWriter() {
		return _writer;
	}
	
	public void setWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writer = writer;
	}

	@Override
	public boolean isLevelAccepted(ObsLevel level) {
		return _writer.isLevelAccepted(level);
	}

	@Override
	public void write(ObservationCollector collector, SourceLocation locOrigin, String strText) {
		_writer.write(collector, locOrigin, strText);
	}

	@Override
	public void writeDiagnostic(String strText) {
		_writer.writeDiagnostic(strText);
	}

	@Override
	public void write(ObsLevel level, String strText) {
		_writer.write(level, strText);
	}

	@Override
	public void buildAndWrite(ObsLevel level, Consumer<StringBuilder> message) {
		_writer.buildAndWrite(level, message);
	}

	@Override
	public void buildAndWrite(ObsLevel level, int i, ObjIntConsumer<StringBuilder> message) {
		_writer.buildAndWrite(level, i, message);
	}

	@Override
	public void buildAndWrite(ObsLevel level, ObsPassThroughList listPassThrough,
			BiConsumer<StringBuilder, ObsPassThroughList> messageBuilder) {
		_writer.buildAndWrite(level, listPassThrough, messageBuilder);
	}

	@Override
	public void writeTimeStamp(ObsLevel level) {
		_writer.writeTimeStamp(level);
	}

	@Override
	public ObservationCollector createCollector(ObsLevel level) {
		return _writer.createCollector(level);
	}

	@Override
	public void flush() {
		_writer.flush();
	}

}
