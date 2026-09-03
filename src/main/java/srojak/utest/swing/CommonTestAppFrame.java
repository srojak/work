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
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import srojak.cdo.swing.frames.AppFrameContainer;
import srojak.cdo.swing.frames.CommonMessageAppFrame;
import srojak.cdo.swing.status.StatusBar;
import srojak.cdo.swing.status.StatusBarTextItem;
import srojak.core.logic.BooleanLatch;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.debug.cdo.RelayListDebugSwitchesActionListener;
import srojak.debug.config.DebugConfigFileReader;
import srojak.events.CancellableEvent;
import srojak.events.CancellableEventListener;
import srojak.xml.stream.errors.XmlStreamParseErrorDescr;

/**
 * @author Stephen
 *
 */
public class CommonTestAppFrame
		extends CommonMessageAppFrame {
	private final BooleanLatch _latchCanClose;
	private final StatusBarTextItem _sbStatus;

	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = CommonTestAppFrame.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis),
				ObsLevel.DETAIL, false);
	}
	
	public static void analyzeDebugConfigParse(DebugConfigFileReader reader) {
		List<XmlStreamParseErrorDescr> listErrors = reader.getParseErrors();
		ObservationCollector collector = _swDebugClass.createCollector(ObsLevel.NOTICE);
		collector.append("has ");
		collector.append(listErrors.size());
		collector.append(" parse errors");
		for(XmlStreamParseErrorDescr error : listErrors) {
			collector.append("\n  ");
			collector.append(error);
		}
		collector.alsoWriteTo(System.err);
		collector.commit();
	}
	
	/**
	 * @param strAppName
	 */
	public CommonTestAppFrame(String strAppName) {
		super(strAppName);
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "strAppName = " + strAppName);
		_latchCanClose = new BooleanLatch(true);
		getExitControl().addClosingListener(new ClosingListener());
		
		StatusBar sbar = getStatusBar();
        _sbStatus = new StatusBarTextItem(30, "Ready");
        sbar.add(_sbStatus);
	}
	
	public void setStatusText(String strText) {
		_sbStatus.setText(strText);
	}
	
	protected boolean getCanCloseState() {
		return _latchCanClose.getState();
	}
	
	protected void setCanCloseState(boolean bState) {
		_latchCanClose.setState(bState);
	}
	
	protected void addItemsToTestMenu(JMenu menuTest) {
		
	}
	
	protected void addMenus() {
		
	}

	@Override
	public void buildMenus() {
		JMenu menu = new JMenu("Test");
		addMenu(menu);
		
		JMenuItem itemMenu = new JMenuItem("List Debug Switches");
		menu.add(itemMenu);
		itemMenu.addActionListener(new RelayListDebugSwitchesActionListener(getMessageOut()));
		
		itemMenu = new JMenuItem("Write Text to Log");
		menu.add(itemMenu);
		itemMenu.addActionListener(
				new WriteTextToLogActionListener(getAppFrameContainer(), ObsLevel.NOTICE));
		
		addItemsToTestMenu(menu);
		
		itemMenu = new JMenuItem("Force Repaint");
		menu.add(itemMenu);
		itemMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_swDebugClass.write(ObsLevel.NOTICE, "repainting content as directed from menu");
				repaintContent();
			}
			
		});
		menu.addSeparator();
		
		itemMenu = createExitMenuItem();
		menu.add(itemMenu);
		
		addMenus();
		
		addTextMenu();
	}
	
	protected String formatException(Exception exc, boolean bUseSimpleName) {
		StringBuilder sb = new StringBuilder("caught ");	
		sb.append(bUseSimpleName ? exc.getClass().getSimpleName() : exc.getClass().getName());
		sb.append(": ");
		sb.append(exc.getMessage());
		return sb.toString();
	}

	protected boolean promptForExit() {
		AppFrameContainer ctnrFrame = getAppFrameContainer();
		int nResult = ctnrFrame.showConfirmDialog("Continue?",
				"Exit Application", JOptionPane.YES_NO_OPTION);
		return nResult == JOptionPane.YES_OPTION;
	}
	
	protected class ClosingListener
			implements CancellableEventListener {

		@Override
		public void initiated(CancellableEvent event) {
			if (!_latchCanClose.getState()) {
				if (!promptForExit()) {
					event.cancel();
				}
			}
		}
	}
}
