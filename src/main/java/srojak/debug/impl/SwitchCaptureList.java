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
package srojak.debug.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.tools.ListMethods;

/**
 * @author Stephen
 *
 */
public class SwitchCaptureList {
	private final DebugSwitchContent _swStart;
	private final List<DebugSwitchContent> _list;
	
	/**
	 * 
	 */
	public SwitchCaptureList(DebugSwitchContent swStart) {
		Objects.requireNonNull(swStart, "swStart");
		_swStart = swStart;
		_list = new LinkedList<DebugSwitchContent>();
	}
	
	public DebugSwitchContent getStartSwitch() {
		return _swStart;
	}
	
	public boolean isInList(DebugSwitchContent swContent) {
		return ListMethods.isTrueForAny(_list, e -> e.getKey().equals(swContent.getKey()));
	}
	
	public void addToList(DebugSwitchContent swContent) {
		Objects.requireNonNull(swContent, "swContent");
		_list.add(swContent);
	}
}
