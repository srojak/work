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
import java.util.HashMap;
import java.util.Map;

import srojak.core.CommonPrintWriter;

/**
 * @author Stephen
 *
 */
public abstract class AbstractMultiChannelBufferedTextCollector 
		extends AbstractMultiChannelTextCollector {
	private final Map<TextChannelNumber, ChannelWriterBuffer> _mapBuffers;

	private static final int SIZE_BUFFER_START = 120;

	/**
	 * 
	 */
	public AbstractMultiChannelBufferedTextCollector() {
		super();
		_mapBuffers = new HashMap<TextChannelNumber, ChannelWriterBuffer>();
	}

	@Override
	public boolean isChannelKnown(TextChannelNumber channel) {
		return _mapBuffers.containsKey(channel);
	}

	@Override
	protected void addChannel(TextChannelNumber channel) {
		_mapBuffers.computeIfAbsent(channel, 
				c -> new ChannelWriterBuffer(this, c, SIZE_BUFFER_START));
	}

	@Override
	protected void removeChannel(TextChannelNumber channel) {
		ChannelWriterBuffer cwb = _mapBuffers.remove(channel);
		if (cwb != null) {
			cwb.dispose();
		}
	}

	@Override
	protected void removeChannels(Collection<TextChannelNumber> channels) {
		channels.forEach(c -> removeChannel(c));
		
	}

	@Override
	protected void removeAllChannels() {
		_mapBuffers.keySet().forEach(c -> removeChannel(c));
	}
	
	protected CommonPrintWriter getWriterForChannel(TextChannelNumber channel) {
		return _mapBuffers.get(channel);
	}
}
