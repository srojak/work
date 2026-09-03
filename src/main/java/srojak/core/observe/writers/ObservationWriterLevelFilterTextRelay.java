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

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

import srojak.core.TextMessageRelay;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsPassThroughList;

/**
 * @author Stephen
 *
 */
public class ObservationWriterLevelFilterTextRelay 
		extends ObservationWriterLevelFilterBase {
	private final TextMessageRelay _relay;

	/**
	 * 
	 */
	public ObservationWriterLevelFilterTextRelay(TextMessageRelay relayText) {
		Objects.requireNonNull(relayText, "relayText");
		_relay = relayText;
	}

	@Override
	public void write(ObsLevel level, String strText) {
		if (isObsLevelAtLeast(level)) {
			_relay.writeln(level.getName() + ": " + strText);			
		}
	}

	@Override
	public void buildAndWrite(ObsLevel level, Consumer<StringBuilder> message) {
		if (isObsLevelAtLeast(level)) {
			StringBuilder sb = new StringBuilder(level.getName());
			sb.append(": ");
			message.accept(sb);
			_relay.writeln(sb.toString());
		}
	}

	@Override
	public void buildAndWrite(ObsLevel level, int i, ObjIntConsumer<StringBuilder> message) {
		if (isObsLevelAtLeast(level)) {
			StringBuilder sb = new StringBuilder(level.getName());
			sb.append(": ");
			message.accept(sb, i);
			_relay.writeln(sb.toString());
		}
	}

	@Override
	public void buildAndWrite(ObsLevel level, ObsPassThroughList listPassThrough,
			BiConsumer<StringBuilder, ObsPassThroughList> messageBuilder) {
		if (isObsLevelAtLeast(level)) {
			StringBuilder sb = new StringBuilder(level.getName());
			sb.append(": ");
			messageBuilder.accept(sb, listPassThrough);
			_relay.writeln(sb.toString());
		}
	}

	@Override
	public void writeTimeStamp(ObsLevel level) {
		if (isObsLevelAtLeast(level)) {
			_relay.writeln(level.getName() + ": time " + getDateAndTimeStamp());
		}
	}

	@Override
	public void writeDiagnostic(String strText) {
		_relay.writeln("*DIAG: " + strText);	
	}

	@Override
	public void flush() {
		// does nothing
	}

}
