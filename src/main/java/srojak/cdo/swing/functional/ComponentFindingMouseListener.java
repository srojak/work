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
package srojak.cdo.swing.functional;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * @author Stephen
 *
 */
public class ComponentFindingMouseListener 
		extends MouseAdapter {
	private final int _nButton;
	private final Consumer<JComponent> _clicked;

	/**
	 * 
	 */
	public ComponentFindingMouseListener(int nButton, Consumer<JComponent> clickAction) {
		_nButton = nButton;
		_clicked = clickAction;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		if (e.getButton() == _nButton) {
			Component c = SwingUtilities.getDeepestComponentAt(e.getComponent(), e.getX(), e.getY());
			if (c != null) {
				_clicked.accept((JComponent) c);
			}
		}
	}

}
