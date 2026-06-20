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
package srojak.cdo.swing.base;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import srojak.cdo.DialogResult;
import srojak.cdo.swing.panels.DialogBaseButtonPanel;
import srojak.core.NameToken;
import srojak.core.collections.NameTokenAndValueList;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class DialogBase
		extends JDialog {
	private DialogResult _result;
	protected final NameTokenAndValueList<ButtonModel> _listButtonModels;

	/**
	 * 
	 */
	public DialogBase() {
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 */
	public DialogBase(Frame owner) {
		super(owner);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 */
	public DialogBase(Dialog owner) {
		super(owner);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 */
	public DialogBase(Window owner) {
		super(owner);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 * @param modal
	 */
	public DialogBase(Frame owner, boolean modal) {
		super(owner, modal);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 * @param title
	 */
	public DialogBase(Frame owner, String title) {
		super(owner, title);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 * @param modal
	 */
	public DialogBase(Dialog owner, boolean modal) {
		super(owner, modal);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 * @param title
	 */
	public DialogBase(Dialog owner, String title) {
		super(owner, title);
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 * @param modalityType
	 */
	public DialogBase(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 * @param title
	 */
	public DialogBase(Window owner, String title) {
		super(owner, title);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 */
	public DialogBase(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 */
	public DialogBase(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modalityType
	 */
	public DialogBase(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 */
	public DialogBase(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 */
	public DialogBase(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}

	/**
	 * @param owner
	 * @param title
	 * @param modalityType
	 * @param gc
	 */
	public DialogBase(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
		_result = DialogResult.Unassigned;
		_listButtonModels = new NameTokenAndValueList<ButtonModel>();
	}
	
	public DialogResult getResult() {
		return _result;
	}
	
	protected void setResult(DialogResult result) {
		_result = result;
	}

	@Override
	protected JRootPane createRootPane() {
		JRootPane root = new JRootPane();
		root.setLayout(new BorderLayout());
		return root;
	}
	
	protected void mapEscapeToCancel() {
		// catch Escape and treat like cancel
		JRootPane root = getRootPane();
		KeyStroke ks = KeyStroke.getKeyStroke("ESCAPE");
		Action actListener = new ActionListenerResultButton(DialogResult.Cancel);
		InputMap mapInput = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		mapInput.put(ks, "ESCAPE");
		root.getActionMap().put("ESCAPE", actListener);	
	}

	protected void createOKButtonPanel() {
		JRootPane root = getRootPane();
		
		DialogBaseButtonPanel panelButton
			= new DialogBaseButtonPanel(DialogBaseButtonPanel.PANEL_NAME);
		root.add(panelButton, BorderLayout.SOUTH);
		
		JButton bnOK = new JButton("OK");
		ButtonModel modelButton = bnOK.getModel();
		modelButton.addActionListener(
				new ActionListenerResultButton(DialogResult.OK));
		/*
		panelButton.add(bnOK, builderGBC.getConstraints());
		*/
		panelButton.addButton(bnOK, 0);
		root.setDefaultButton(bnOK);
		_listButtonModels.add(ButtonModelKeys.OK, modelButton);
	}
    
    protected void createOKCancelButtonPanel() {
		JRootPane root = getRootPane();
   	
		DialogBaseButtonPanel panelButton = new DialogBaseButtonPanel(DialogBaseButtonPanel.PANEL_NAME);
		root.add(panelButton, BorderLayout.SOUTH);
		
		JButton bnOK = new JButton("OK");
		ButtonModel modelButton = bnOK.getModel();
		modelButton.addActionListener(
				new ActionListenerResultButton(DialogResult.OK));
		panelButton.addButton(bnOK, 0);
		root.setDefaultButton(bnOK);
		_listButtonModels.add(ButtonModelKeys.OK, modelButton);
		
		JButton bnCancel = new JButton("Cancel");
		modelButton = bnCancel.getModel();
		modelButton.addActionListener(
				new ActionListenerResultButton(DialogResult.Cancel));
		panelButton.addButton(bnCancel, 1);
		_listButtonModels.add(ButtonModelKeys.CANCEL, modelButton);
    }
	
	protected void prepareToShow() {
		pack();
	}
	
	protected class ActionListenerResultButton
		extends AbstractAction {
    		private DialogResult _resultToSet;
    		
    		public ActionListenerResultButton(DialogResult result) {
    			super();
    			_resultToSet = result;
    		}
    		
			@Override
			public void actionPerformed(ActionEvent event) {
				setResult(_resultToSet);
				setVisible(false);
			}
	}
    
    protected class ButtonModelKeys {
    	public static final NameToken OK = NameToken.factory("OK");
    	public static final NameToken CANCEL = NameToken.factory("Cancel");
    }
}
