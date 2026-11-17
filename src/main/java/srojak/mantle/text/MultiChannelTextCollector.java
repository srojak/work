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

import srojak.core.text.TextReceiver;

/**
 * @author Stephen
 *
 */
public interface MultiChannelTextCollector {
	
	static final TextChannelNumber CH_ZERO = TextChannelNumber.channel(0);

	boolean isChannelKnown(TextChannelNumber channel);
	void addReceiver(TextReceiver receiver, TextChannelNumber channelFirst, TextChannelNumber ... channelNumbers);
	boolean addChannelsToReceiver(TextReceiver receiver, TextChannelNumber channelFirst, TextChannelNumber ... channelNumbers);
	void removeReceiver(TextReceiver receiver);
	void unloadAllReceivers();
	
	void println(TextChannelNumber channel);
	void println(TextChannelNumber channel, String s);
	void println(TextChannelNumber channel, Object obj);
	void println(TextChannelNumber channel, float fValue);
	void println(TextChannelNumber channel, double dValue);
	void println(TextChannelNumber channel, char[] ca);
	void println(TextChannelNumber channel, boolean bValue);
	void println(TextChannelNumber channel, char c);
	void println(TextChannelNumber channel, int nValue);
	void println(TextChannelNumber channel, long lnValue);
}
