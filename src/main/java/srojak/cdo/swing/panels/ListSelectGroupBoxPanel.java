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
package srojak.cdo.swing.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;

import srojak.cdo.ActionEventOriginator;
import srojak.cdo.swing.functional.RadioButtonMethods;
import srojak.core.NameToken;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ListSelectGroupBoxPanel
		extends GroupBoxPanel
		implements ActionEventOriginator {
	private final CommonEventListenerStore _listeners;
	private final JRadioButton _rbSingle;
	private final JRadioButton _rbSingleIntv;
	private final JRadioButton _rbMultiIntv;
	private final ButtonGroup _bgMode;
	private int _modeListSelect;

	public static final NameToken PANEL_NAME 
			= NameToken.classNameFactory(ListSelectGroupBoxPanel.class);
			
	/**
	 * 
	 */
	public ListSelectGroupBoxPanel(NameToken tokenName) {
		super(tokenName);
		_listeners = new CommonEventListenerList();
		_bgMode = new ButtonGroup();
		_rbSingle = RadioButtonMethods.createAndAssign(_bgMode, "Single");
		_rbSingleIntv = RadioButtonMethods.createAndAssign(_bgMode, "Single Interval");
		_rbMultiIntv = RadioButtonMethods.createAndAssign(_bgMode, "Multiple Interval");
		postConstruct();
	}

	/**
	 * @param isDoubleBuffered
	 */
	public ListSelectGroupBoxPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, isDoubleBuffered);
		_listeners = new CommonEventListenerList();
		_bgMode = new ButtonGroup();
		_rbSingle = RadioButtonMethods.createAndAssign(_bgMode, "Single");
		_rbSingleIntv = RadioButtonMethods.createAndAssign(_bgMode, "Single Interval");
		_rbMultiIntv = RadioButtonMethods.createAndAssign(_bgMode, "Multiple Interval");
		postConstruct();
	}

	private void postConstruct() {
		_modeListSelect = ListSelectionModel.SINGLE_SELECTION;
		_bgMode.setSelected(_rbSingle.getModel(), true);
		add(_rbSingle);
		add(_rbSingleIntv);
		add(_rbMultiIntv);
		_rbSingle.addActionListener(
				new RadioButtonActionListener(ListSelectionModel.SINGLE_SELECTION));
		_rbSingleIntv.addActionListener(
				new RadioButtonActionListener(ListSelectionModel.SINGLE_INTERVAL_SELECTION));
		_rbMultiIntv.addActionListener(
				new RadioButtonActionListener(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION));
	}
	
	public int getSelectionMode() {
		return _modeListSelect;
	}
	
	public void setSelectionMode(int modeSelect) {
		JRadioButton rb = null;
		switch (modeSelect) {
		case ListSelectionModel.SINGLE_SELECTION:
			rb = _rbSingle;
			break;
			
		case ListSelectionModel.MULTIPLE_INTERVAL_SELECTION:
			rb = _rbMultiIntv;
			break;
			
		case ListSelectionModel.SINGLE_INTERVAL_SELECTION:
			rb = _rbSingleIntv;
			break;
			
		default:
            throw new IllegalArgumentException("invalid modeSelect");
		
		}
		
		_bgMode.setSelected(rb.getModel(), true);
	}
	
	@Override
	public void addActionListener(ActionListener listener) {
		_listeners.add(ActionListener.class, listener);
	}

	@Override
	public void removeActionListener(ActionListener listener) {
		_listeners.remove(ActionListener.class, listener);
	}
	
	private void raiseChangeSelectionAction(String strAction) {
		ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, strAction);
		_listeners.forEach(ActionListener.class, ls -> ls.actionPerformed(event));
	}

	private class RadioButtonActionListener
			implements ActionListener {
		private final int _mode;
		
		public RadioButtonActionListener(int mode) {
			_mode = mode;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton rb = (JRadioButton)e.getSource();
			if (rb.isSelected()) {
				_modeListSelect = _mode;
				raiseChangeSelectionAction(rb.getActionCommand());
			}
		}
	}
}
