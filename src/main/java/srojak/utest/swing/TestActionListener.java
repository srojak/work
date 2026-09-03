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
package srojak.utest.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class TestActionListener 
		extends AbstractAction {
	private ActionListener _listener;
	
	public TestActionListener(ActionListener listener) {
		_listener = listener;
	}

	/**
	 * @param name
	 */
	public TestActionListener(String name, ActionListener listener) {
		super(name);
		_listener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		_listener.actionPerformed(e);			
	}

}
