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
package srojak.cdo.swing.components;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JPanel;

import srojak.cdo.AWTFormatters;
import srojak.cdo.swing.functional.SwingDelay;
import srojak.core.NameToken;
import srojak.core.NameTokenTagged;
import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;
import srojak.core.field.SetOnce;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.events.ResyncEvent;
import srojak.events.ResyncEventOriginator;
import srojak.events.ResyncListener;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class AnchorMeasure 
		extends JComponent
		implements NameTokenTagged, ResyncEventOriginator {
	private final NameToken _token;
	private final SingleEventListenerStore<ResyncListener> _listeners;
	protected final Dimension _szBox;
	private final SwingDelay _delayResize;
	private final SetOnce<JPanel> _panel;

	private static final DebugSwitch _swDebugClass;

	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = AnchorMeasure.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	/**
	 * 
	 */
	public AnchorMeasure(NameToken tokenName) {
		super();
		Objects.requireNonNull(tokenName, "tokenName");
		_token = tokenName;
		_listeners = new SingleEventListenerList<ResyncListener>();
		_szBox = new Dimension();
		_delayResize = new SwingDelay(800, e -> {
			sendResync();
		});
		_delayResize.setRepeats(false);
		_panel = new SetOnce<JPanel>(NameToken.factory(_token, "Panel"), SetOnce.DEFAULT);
	}

	@Override
	public NameToken getNameTag() {
		return _token;
	}

	@Override
	public boolean isNameTagEqual(NameToken token) {
		return _token.equals(token);
	}

	@Override
	public boolean isNameTagEqual(String strName) {
		return _token.isNameEqual(strName);
	}
	
	protected void recalculate() {
		// base class method does nothing
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> " name = " + _token.getName());
		Container parent = getParent();
		while (parent != null) {
			if (parent instanceof JPanel panel) {
				_panel.set(panel);
				_szBox.setSize(panel.getSize());
				_swDebugClass.write(ObsLevel.DEBUG, "size is now " + AWTFormatters.formatDimension(_szBox));
				panel.addComponentListener(new MeasureComponentListener());
				break;
			}
		}
		if (parent == null) {
			_swDebugClass.write(ObsLevel.WARN, "did not find a parent panel");
		}
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM, () -> "x=" + x + ",y=" + y
				+ ", width=" + width + ", height=" + height);
		super.setBounds(x, y, width, height);
		_szBox.setSize(width, height);
		sendResync();
	}

	@Override
	public void setBounds(Rectangle r) {
		_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM, () -> "r=" + AWTFormatters.formatRectangle(r));
		super.setBounds(r);
		_szBox.setSize(r.getSize());
		sendResync();
	}
	
	public Dimension getBoxSize() {
		return _szBox;
	}
	
	private void sendResync() {
		_swDebugClass.write(ObsLevel.DEBUG, 
			() -> "resync: size is now " + AWTFormatters.formatDimension(_szBox));
		ResyncEvent event = new ResyncEvent(this);
		_listeners.forEach(ls -> ls.receive(event));
	}
	
	@Override
	public void addResyncListener(ResyncListener listener) {
		_listeners.add(listener);
	}

	@Override
	public void removeResyncListener(ResyncListener listener) {
		_listeners.remove(listener);
	}

	private class MeasureComponentListener
			extends ComponentAdapter {

		@Override
		public void componentResized(ComponentEvent e) {
			super.componentResized(e);
			Component c = e.getComponent();
			_szBox.setSize(c.getSize());
			recalculate();
			_delayResize.activate();
		}
		
	}
}
