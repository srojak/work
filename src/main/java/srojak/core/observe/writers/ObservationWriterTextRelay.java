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

import srojak.core.TextMessageRelay;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceDetail;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public class ObservationWriterTextRelay 
		extends ObservationWriterBase {
	private final TextMessageRelay _relay;

	/**
	 * 
	 */
	public ObservationWriterTextRelay(TextMessageRelay relayText) {
		Objects.requireNonNull(relayText, "relayText");
		_relay = relayText;
		setCanWrite(true);
	}

	@Override
	protected void innerWrite(ObsLevel level, SourceLocation locOrigin, String strText) {
		StringBuilder sb = new StringBuilder(level.getName());
		if (canShowLocations()) {
			sb.append(' ');
			sb.append(locOrigin.toString(SourceDetail.CLASS_METHOD));
		}
		sb.append(": ");
		sb.append(strText);
		_relay.writeln(sb.toString());
	}

	@Override
	protected void innerWriteException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			Exception exc, boolean bShowStack) {
		StringBuilder sb = new StringBuilder(level.getName());
		if (canShowLocations()) {
			sb.append(' ');
			sb.append(locOrigin.toString(SourceDetail.CLASS_METHOD));
		}
		sb.append(": when ");
		sb.append(activity.describe());
		ObsWriterTextMethods.formatException(sb, exc, bShowStack);
		_relay.writeln(sb.toString());
	}

	@Override
	protected void innerWriteDiagnostic(String strText) {
		StringBuilder sb = new StringBuilder(ObsWriterTextMethods.LEADER_DIAG);
		sb.append(": ");
		sb.append(strText);
		_relay.writeln(sb.toString());
	}

	@Override
	protected void innerWriteDiagnostic(SourceLocation locOrigin, String strText) {
		StringBuilder sb = new StringBuilder(ObsWriterTextMethods.LEADER_DIAG);
		if (canShowLocations()) {
			sb.append(' ');
			sb.append(locOrigin.toString(SourceDetail.CLASS_METHOD));
		}
		sb.append(": ");
		sb.append(strText);
		_relay.writeln(sb.toString());
	}
}
