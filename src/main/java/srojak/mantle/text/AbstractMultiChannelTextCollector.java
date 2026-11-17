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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import srojak.core.collections.TQueue;
import srojak.core.text.TextReceiver;

/**
 * @author Stephen
 *
 */
public abstract class AbstractMultiChannelTextCollector 
		implements MultiChannelTextCollector, TextChannelOutputDestination {
	private final List<ChannelTextReceiver> _listReceivers;
	
	protected AbstractMultiChannelTextCollector() {
		_listReceivers = new LinkedList<ChannelTextReceiver>();
	}
	
	protected final synchronized List<ChannelTextReceiver> subsetFor(TextChannelNumber channel) {
		return _listReceivers.stream().filter(r -> r.isForChannel(channel)).toList();
	}
	
	private void removeActiveChannelsFromSet(Set<TextChannelNumber> setVerify) {
		Iterator<ChannelTextReceiver> iterator = _listReceivers.iterator();
		while (iterator.hasNext() && !setVerify.isEmpty()) {
			ChannelTextReceiver rch = iterator.next();
			setVerify.removeAll(rch.allChannels());
		}
	}
	
	protected abstract void addChannel(TextChannelNumber channel);
	
	protected abstract void removeChannel(TextChannelNumber channel);
	
	protected abstract void removeChannels(Collection<TextChannelNumber> channels);
	
	protected abstract void removeAllChannels();
	
	private synchronized void processRemovals(TQueue<ChannelTextReceiver> queueRemoval) {
		Set<TextChannelNumber> setVerify = new HashSet<TextChannelNumber>();
		while (!queueRemoval.isEmpty()) {
			ChannelTextReceiver rch = queueRemoval.dequeue();
			if (_listReceivers.remove(rch)) {
				setVerify.addAll(rch.allChannels());
			}
		}
		if (!setVerify.isEmpty()) {
			removeActiveChannelsFromSet(setVerify);
		}
		if (!setVerify.isEmpty()) {
			removeChannels(setVerify);
		}
	}
	
	@Override
	public final void writeToAll(TextChannelNumber channel, String strText, boolean bImpliedNewline) {
		List<ChannelTextReceiver> listSnap = subsetFor(channel);
		TQueue<ChannelTextReceiver> queueRemoval = new TQueue<ChannelTextReceiver>();
		listSnap.forEach(r -> {
			if (r.isOpen()) {
				r.receive(strText, bImpliedNewline);
			} else {
				queueRemoval.enqueue(r);
			}
		});
		if (!queueRemoval.isEmpty()) {
			processRemovals(queueRemoval);
		}
	}
	
	private ChannelTextReceiver findEntryFor(TextReceiver receiver) {
		Iterator<ChannelTextReceiver> iterator = _listReceivers.iterator();
		while (iterator.hasNext()) {
			ChannelTextReceiver rch = iterator.next();
			if (rch.isReceiverEqual(receiver)) {
				return rch;
			}
		}
		return null;
	}

	@Override
	public final synchronized void addReceiver(TextReceiver receiver, TextChannelNumber channelFirst,
			TextChannelNumber... channelNumbers) {
		Objects.requireNonNull(receiver, "receiver");
		Objects.requireNonNull(channelFirst, "channelFirst");
		Objects.requireNonNull(channelNumbers, "channelNumbers");
		ChannelTextReceiver rch = findEntryFor(receiver);
		if (rch == null) {
			rch = new ChannelTextReceiver(receiver);
			_listReceivers.add(rch);
		}
		rch.addChannel(channelFirst);
		addChannel(channelFirst);
		for (TextChannelNumber channel : channelNumbers) {
			rch.addChannel(channel);
			addChannel(channel);
		}
	}

	@Override
	public final synchronized boolean addChannelsToReceiver(TextReceiver receiver, TextChannelNumber channelFirst,
			TextChannelNumber... channelNumbers) {
		Objects.requireNonNull(receiver, "receiver");
		Objects.requireNonNull(channelFirst, "channelFirst");
		Objects.requireNonNull(channelNumbers, "channelNumbers");
		ChannelTextReceiver rch = findEntryFor(receiver);
		if (rch == null) {
			return false;
		}
		rch.addChannel(channelFirst);
		addChannel(channelFirst);
		for (TextChannelNumber channel : channelNumbers) {
			rch.addChannel(channel);
			addChannel(channel);
		}
		return true;
	}

	@Override
	public final synchronized void removeReceiver(TextReceiver receiver) {
		Set<TextChannelNumber> setVerify = new HashSet<TextChannelNumber>();
		Iterator<ChannelTextReceiver> iterator = _listReceivers.iterator();
		while (iterator.hasNext()) {
			ChannelTextReceiver rch = iterator.next();
			if (rch.isReceiverEqual(receiver)) {
				iterator.remove();
				rch.allChannels().forEach(c -> setVerify.add(c));
			}
		}
		if (!setVerify.isEmpty()) {
			removeActiveChannelsFromSet(setVerify);
		}
		if (!setVerify.isEmpty()) {
			removeChannels(setVerify);
		}
	}

	@Override
	public final synchronized void unloadAllReceivers() {
		removeAllChannels();
		_listReceivers.clear();
	}
}
