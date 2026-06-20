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

import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import srojak.cdo.swing.base.GraphicsControlBase;
import srojak.core.tools.BitMethods;

/**
 * @author Stephen
 *
 */
public class ScrollableParentControl
			extends GraphicsControlBase {
	private final JComponent _owner;
	private JScrollPane _scrollPane;
	private JViewport _viewport;

	/**
	 * 
	 */
	public ScrollableParentControl(JComponent owner) {
		Objects.requireNonNull(owner, "owner");
		_owner = owner;
		_scrollPane = null;
		_viewport = null;
		_owner.addHierarchyListener(new ScrollParentListener());
	}
	
	public boolean hasScrollPane() {
		return _scrollPane != null;
	}
	
	public boolean hasViewport() {
		return _viewport != null;
	}
	
	public Point getViewPosition() {
		return _viewport != null ? _viewport.getViewPosition() : new Point();
	}
	
	public Rectangle getViewRect() {
		return _viewport != null ? _viewport.getViewRect() : new Rectangle(0, 0, 0, 0);
	}
	
	public void moveViewport(Point ptCorner) {
		if (_viewport != null) {
			_viewport.setViewPosition(ptCorner);
		}
	}
	
	private class ScrollParentListener
			implements HierarchyListener {

		@Override
		public void hierarchyChanged(HierarchyEvent e) {
			if (BitMethods.test(e.getChangeFlags(), HierarchyEvent.PARENT_CHANGED)) {
				Container parent = _owner.getParent();
				if (parent instanceof JViewport viewport) {
					_viewport = viewport;
					_scrollPane = (JScrollPane) viewport.getParent();
				} else {
					_scrollPane = null;
					_viewport = null;
				}
			}
		}
		
	}

}
