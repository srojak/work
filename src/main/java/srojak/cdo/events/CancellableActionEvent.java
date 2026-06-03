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
package srojak.cdo.events;

import java.awt.event.ActionEvent;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class CancellableActionEvent 
		extends ActionEvent {
	private boolean _bCancelled;

	/**
	 * @param source
	 * @param id
	 * @param command
	 */
	public CancellableActionEvent(Object source, int id, String command) {
		super(source, id, command);
		_bCancelled = false;
	}

	/**
	 * @param source
	 * @param id
	 * @param command
	 * @param modifiers
	 */
	public CancellableActionEvent(Object source, int id, String command, int modifiers) {
		super(source, id, command, modifiers);
		_bCancelled = false;
	}

	/**
	 * @param source
	 * @param id
	 * @param command
	 * @param when
	 * @param modifiers
	 */
	public CancellableActionEvent(Object source, int id, String command, long when, int modifiers) {
		super(source, id, command, when, modifiers);
		_bCancelled = false;
	}
	
	public CancellableActionEvent(ActionEvent eventOrigin) {
		this(eventOrigin.getSource(), eventOrigin.getID(), eventOrigin.getActionCommand(),
				eventOrigin.getModifiers());
	}

	public boolean isCancelled() {
		return _bCancelled;
	}
	
	public void cancel() {
		_bCancelled = true;
	}
}
