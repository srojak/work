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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.text.TextReceiver;

/**
 * @author Stephen
 *
 */
class ChannelTextReceiver 
		implements TextReceiver {
	private final LinkedList<TextChannelNumber> _listChannels;
	private final TextReceiver _receiver;
	
	public ChannelTextReceiver(TextReceiver receiver) {
		Objects.requireNonNull(receiver, "receiver");
		_receiver = receiver;
		_listChannels = new LinkedList<TextChannelNumber>();
	}
	
	public boolean isReceiverEqual(TextReceiver receiver) {
		return _receiver == receiver;
	}

	@Override
	public boolean isOpen() {
		return _receiver.isOpen();
	}

	@Override
	public void receive(String strText, boolean bImpliedNewline) {
		_receiver.receive(strText, bImpliedNewline);
	}

	@Override
	public boolean isAutoFlush() {
		return _receiver.isAutoFlush();
	}

	@Override
	public void flush() {
		_receiver.flush();
	}
	
	public void addChannel(TextChannelNumber channel) {
		Objects.requireNonNull(channel, "channel");
		if (!_listChannels.contains(channel)) {
			_listChannels.add(channel);
		}
	}
	
	public boolean isForChannel(TextChannelNumber channel) {
		return _listChannels.contains(channel);
	}
	
	public Collection<TextChannelNumber> allChannels() {
		return List.copyOf(_listChannels);
	}
}
