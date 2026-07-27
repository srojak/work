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
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import srojak.cdo.swing.base.GraphicsControlBase;
import srojak.core.events.OperationCodes;
import srojak.core.events.OperationStateChangeEvent;
import srojak.core.events.OperationStateChangeListener;
import srojak.core.events.OperationStateChangeOriginator;
import srojak.core.logic.BooleanLatch;
import srojak.core.tools.BitMethods;

/**
 * @author Stephen
 *
 * A control that recognizes when the owning component is made a child of a
 * 		{@code JScrollPane} and provides information and events to the child about the parent.
 */
public class ScrollableParentControl
			extends GraphicsControlBase
			implements OperationStateChangeOriginator {
	private final JComponent _owner;
	private final BooleanLatch _latchIsScrolling;
	private AdjustmentListener _listenerAdjust;
	private JScrollPane _scrollPane;
	private JViewport _viewport;

	/**
	 * 
	 */
	public ScrollableParentControl(JComponent owner) {
		Objects.requireNonNull(owner, "owner");
		_owner = owner;
		_latchIsScrolling = new BooleanLatch();
		_scrollPane = null;
		_viewport = null;
		_owner.addHierarchyListener(new ScrollParentListener());
		_listenerAdjust = null;
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
	
	public boolean isScrolling() {
		return _latchIsScrolling.getState();
	}
	
	private void sendOperationStateChange(int opcode, boolean bState) {
		OperationStateChangeEvent event = new OperationStateChangeEvent(this, opcode, bState);
		forEachListener(OperationStateChangeListener.class, ls -> ls.operationStateChanged(event));
	}

	@Override
	public void addOperationStateChangeListener(OperationStateChangeListener listener) {
		addListener(OperationStateChangeListener.class, listener);	
	}

	@Override
	public void removeOperationStateChangeListener(OperationStateChangeListener listener) {
		removeListener(OperationStateChangeListener.class, listener);			
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
					_listenerAdjust = new ScrollAdjustmentListener();
					_scrollPane.getHorizontalScrollBar().addAdjustmentListener(_listenerAdjust);
					_scrollPane.getVerticalScrollBar().addAdjustmentListener(_listenerAdjust);
				} else {
					_scrollPane.getHorizontalScrollBar().removeAdjustmentListener(_listenerAdjust);
					_scrollPane.getVerticalScrollBar().removeAdjustmentListener(_listenerAdjust);
					_scrollPane = null;
					_viewport = null;
				}
			}
		}
		
	}
	
	private class ScrollAdjustmentListener
			implements AdjustmentListener, OperationCodes {

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			if (e.getValueIsAdjusting()) {
				if (_latchIsScrolling.setState(true)) {
					sendOperationStateChange(SCROLL, false);
				}
			} else {
				if (_latchIsScrolling.setState(false)) {
					sendOperationStateChange(SCROLL, true);
				}
			}
		}
		
	}

}
