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
package srojak.events;

import srojak.core.events.CoreEvent;

/**
 * @author Stephen
 *
 */
public class CommandEvent
		extends CoreEvent
		implements CommandEventValues {
	private final int _command;
	private final int _option;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5745947924831764793L;

	public CommandEvent(Object source, int nCommand) {
		super(source);
		_command = nCommand;
		_option = 0;
	}
	
	public CommandEvent(Object source, int nCommand, int nOption) {
		super(source);
		_command = nCommand;
		_option = 0;
	}
	
	public int getCommand() {
		return _command;
		
	}
	
	public int getOption() {
		return _option;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		sb.append(", command=");
		sb.append(_command);
		sb.append(", option=");
		sb.append(_option);
	}

}
