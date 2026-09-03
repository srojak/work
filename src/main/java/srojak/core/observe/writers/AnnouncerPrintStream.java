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

import java.io.PrintStream;
import java.util.Objects;

import srojak.core.observe.Announcer;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.SourceDetail;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public class AnnouncerPrintStream 
		implements Announcer {
	private final PrintStream _streamOut;
	
	public AnnouncerPrintStream(PrintStream stream) {
		Objects.requireNonNull(stream, "stream");
		if (stream.checkError())
			throw new IllegalArgumentException("stream is not valid");
		_streamOut = stream;		
	}

	@Override
	public void announce(ObsLevel level, SourceLocation location) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(location, "location");
		StringBuilder sb = new StringBuilder("Observation level=");
		sb.append(level);
		sb.append(", location=");
		sb.append(location.toString(SourceDetail.PACKAGE_CLASS_METHOD));
		_streamOut.println(sb.toString());
	}

	@Override
	public void announceException(ObsLevel level, SourceLocation location, Exception exception) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(location, "location");
		StringBuilder sb = new StringBuilder("Exception (type=");
		if (exception == null) {
			sb.append('?');
		} else {
			sb.append(exception.getClass().getSimpleName());
		}
		sb.append(") level=");
		sb.append(level);
		sb.append(", location=");
		sb.append(location.toString(SourceDetail.PACKAGE_CLASS_METHOD));
		_streamOut.println(sb.toString());
	}
}
