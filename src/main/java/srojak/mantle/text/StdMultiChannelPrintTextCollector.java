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
package srojak.mantle.text;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Stephen
 *
 */
public class StdMultiChannelPrintTextCollector
		extends AbstractMultiChannelTextCollector
		implements MultiChannelTextCollector {
	private final Set<TextChannelNumber> _setKnownChannels;
	// does not require a buffer

	/**
	 * 
	 */
	public StdMultiChannelPrintTextCollector() {
		super();
		_setKnownChannels = new HashSet<TextChannelNumber>();
	}

	@Override
	public synchronized boolean isChannelKnown(TextChannelNumber channel) {
		return _setKnownChannels.contains(channel);
	}

	@Override
	protected void addChannel(TextChannelNumber channel) {
		_setKnownChannels.add(channel);
	}

	@Override
	protected void removeChannel(TextChannelNumber channel) {
		_setKnownChannels.remove(channel);
	}

	@Override
	protected void removeChannels(Collection<TextChannelNumber> channels) {
		_setKnownChannels.removeAll(channels);
	}

	@Override
	protected void removeAllChannels() {
		_setKnownChannels.clear();
	}

	@Override
	public void println(TextChannelNumber channel) {
		Objects.requireNonNull(channel, "channel");
		if (!isChannelKnown(channel)) {
			throw new IllegalArgumentException("channel has no receivers");
		}
		writeToAll(channel, "", true);
	}

	@Override
	public void println(TextChannelNumber channel, String s) {
		Objects.requireNonNull(channel, "channel");
		if (!isChannelKnown(channel)) {
			throw new IllegalArgumentException("channel has no receivers");
		}
		writeToAll(channel, s, true);
	}

	@Override
	public void println(TextChannelNumber channel, Object obj) {
		Objects.requireNonNull(channel, "channel");
		if (!isChannelKnown(channel)) {
			throw new IllegalArgumentException("channel has no receivers");
		}
		writeToAll(channel, obj.toString(), true);
	}

	@Override
	public void println(TextChannelNumber channel, float fValue) {
		Objects.requireNonNull(channel, "channel");
		if (!isChannelKnown(channel)) {
			throw new IllegalArgumentException("channel has no receivers");
		}
		writeToAll(channel, String.valueOf(fValue), true);
	}

	@Override
	public void println(TextChannelNumber channel, double dValue) {
		Objects.requireNonNull(channel, "channel");
		if (!isChannelKnown(channel)) {
			throw new IllegalArgumentException("channel has no receivers");
		}
		writeToAll(channel, String.valueOf(dValue), true);
	}

	@Override
	public void println(TextChannelNumber channel, char[] ca) {
		Objects.requireNonNull(channel, "channel");
		if (!isChannelKnown(channel)) {
			throw new IllegalArgumentException("channel has no receivers");
		}
		writeToAll(channel, String.valueOf(ca), true);
	}

	@Override
	public void println(TextChannelNumber channel, boolean bValue) {
		Objects.requireNonNull(channel, "channel");
		if (!isChannelKnown(channel)) {
			throw new IllegalArgumentException("channel has no receivers");
		}
		writeToAll(channel, String.valueOf(bValue), true);
	}

	@Override
	public void println(TextChannelNumber channel, char c) {
		Objects.requireNonNull(channel, "channel");
		if (!isChannelKnown(channel)) {
			throw new IllegalArgumentException("channel has no receivers");
		}
		writeToAll(channel, String.valueOf(c), true);
	}

	@Override
	public void println(TextChannelNumber channel, int nValue) {
		Objects.requireNonNull(channel, "channel");
		if (!isChannelKnown(channel)) {
			throw new IllegalArgumentException("channel has no receivers");
		}
		writeToAll(channel, String.valueOf(nValue), true);
	}

	@Override
	public void println(TextChannelNumber channel, long lnValue) {
		Objects.requireNonNull(channel, "channel");
		if (!isChannelKnown(channel)) {
			throw new IllegalArgumentException("channel has no receivers");
		}
		writeToAll(channel, String.valueOf(lnValue), true);
	}

}
