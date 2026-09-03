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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import srojak.cdo.GridBagConstraintsTool;
import srojak.cdo.ModelAttribCodes;
import srojak.cdo.events.ModelChangeEvent;
import srojak.cdo.events.ModelChangeListener;
import srojak.cdo.events.TextContentListener;
import srojak.cdo.events.TextContentOriginator;
import srojak.cdo.swing.FileChooserAction;
import srojak.cdo.swing.components.FileChooserConfirming;
import srojak.cdo.swing.functional.CDOControlModelManager;
import srojak.cdo.swing.models.SingleFileSelectDefaultModel;
import srojak.cdo.swing.models.SingleFileSelectModel;
import srojak.core.NameToken;
import srojak.core.concurrent.StopBarrier;
import srojak.core.concurrent.StopGate;
import srojak.core.events.ActionStatusCodes;
import srojak.core.events.ActionStatusListener;
import srojak.core.events.ActionStatusOriginator;
import srojak.core.events.ObjectOwnershipEvent;
import srojak.core.events.ObjectOwnershipListener;
import srojak.core.events.StateChangeCodes;
import srojak.core.io.FileExistence;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class FileSelectPanel 
		extends NameTokenTagCommonEventPanel
		implements ActionStatusOriginator, TextContentOriginator, ModelAttribCodes,
			ActionStatusCodes, StateChangeCodes {
	private final CDOControlModelManager<SingleFileSelectModel> _model;
	private final StopGate _gateTextChange;
	private final JTextField _txName;
	private final JButton _bnBrowse;
	private ModelListener _listenerModel;
	
	@SuppressWarnings("unused")
	private static final DebugSwitch _swDebugClass;

	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = FileSelectPanel.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	/**
	 * @param tokenName
	 */
	public FileSelectPanel(NameToken tokenName) {
		super(tokenName, new GridBagLayout());
		_model = new CDOControlModelManager<SingleFileSelectModel>();
		_gateTextChange = new StopGate(tokenName);
		_txName = new JTextField();
		_bnBrowse = new JButton("Browse");
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public FileSelectPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, new GridBagLayout(), isDoubleBuffered);
		_model = new CDOControlModelManager<SingleFileSelectModel>();
		_gateTextChange = new StopGate(tokenName);
		_txName = new JTextField();
		_bnBrowse = new JButton("Browse");
		postConstruct();
	}

	private void postConstruct() {
		_model.addObjectOwnershipListener(new ObjectOwnershipListener() {

			@Override
			public void acquire(ObjectOwnershipEvent event) {
				SingleFileSelectModel model = event.getValueAs();
				_listenerModel = new ModelListener();
				addActionStatusListener(model);
				model.addModelChangeListener(_listenerModel);
			}

			@Override
			public void release(ObjectOwnershipEvent event) {
				SingleFileSelectModel model = event.getValueAs();
				removeActionStatusListener(model);
				model.removeModelChangeListener(_listenerModel);
			}
			
		});
		
		_model.setModel(new SingleFileSelectDefaultModel());
		
		_txName.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				sendContentChange();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				sendContentChange();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				sendContentChange();
			}
			
		});
		
		GridBagConstraintsTool toolGBC = new GridBagConstraintsTool();
		toolGBC.setFill(0);
		toolGBC.setGridSize(1,  1);
		toolGBC.setAnchor(GridBagConstraints.WEST);
		toolGBC.setInsets(5, 5, 5, 0);
		toolGBC.setGridPosition(0, 0);
		//_txName.setColumns(50);
		add(_txName, toolGBC.snap());
		
		toolGBC.setGridPosition(1, 0);
		add(_bnBrowse, toolGBC.snap());
		_bnBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				browse();
			}
			
		});
	}
	
	private void sendContentChange() {
		if (_gateTextChange.isClear()) {
			SingleFileSelectModel model = _model.getModel();
			// must propagate outside of the current change cycle
			// or throws IllegalStateException with "Attempt to mutate in notification"
			// see javax.swing.textAbstractDocument.writeLock
			SwingUtilities.invokeLater(() -> model.setFileName(_txName.getText()));
		}
	}
	
	private void browse() {
		FileChooserConfirming dlgFile = new FileChooserConfirming();
		dlgFile.setFileExistenceBehavior(FileChooserAction.Custom, FileExistence.MustExist);
		_model.getModel().prepare(dlgFile);
		int nResult = dlgFile.showDialog(this, "Select");
		File fileSelected;
		switch (nResult) {
		case JFileChooser.APPROVE_OPTION:
			break;
			
		case JFileChooser.CANCEL_OPTION:
		case JFileChooser.ERROR_OPTION:
			sendActionStatus(SC_OPERATION, ASTATUS_CANCELLED);
			return;
		}
		
		fileSelected = dlgFile.getSelectedFile();
		_txName.setText(fileSelected.getPath());
		sendActionStatus(SC_OPERATION, ASTATUS_COMPLETED);
	}
	
	public SingleFileSelectModel getModel() {
		return _model.getModel();
	}
	
	public void setModel(SingleFileSelectModel model) {
		_model.setModel(model);
	}
	
	public int getTextColumns() {
		return _txName.getColumns();
	}
	
	public void setTextColumns(int nColumns) {
		_txName.setColumns(nColumns);
	}
	
	public String getFileName() {
		return _txName.getText();
	}
	
	public void setFileName(String strName) {
		_txName.setText(strName);
	}
	
	private void setFileNameFromModel(SingleFileSelectModel model) {
		StopBarrier barrier = _gateTextChange.addStop(model);
		_txName.setText(model.getFileName());
		barrier.dispose();
	}
	
	public ButtonModel getBrowseButtonModel() {
		return _bnBrowse.getModel();
	}

	@Override
	public void addActionStatusListener(ActionStatusListener listener) {
		_listeners.add(ActionStatusListener.class, listener);
	}

	@Override
	public void removeActionStatusListener(ActionStatusListener listener) {
		_listeners.remove(ActionStatusListener.class, listener);
	}

	@Override
	public void addTextContentListener(TextContentListener listener) {
		_listeners.add(TextContentListener.class, listener);
	}

	@Override
	public void removeTextContentListener(TextContentListener listener) {
		_listeners.remove(TextContentListener.class, listener);
	}
	
	private class ModelListener
			implements ModelChangeListener {

		@Override
		public void attribChanged(ModelChangeEvent event) {
			SingleFileSelectModel model = _model.getModel();
			switch (event.getAttribute()) {
			case MA_ENABLED:
				setEnabled(model.isEnabled());
				break;
				
			case MA_CONTENT:
				setFileNameFromModel(model);
				break;
			}
			
		}
	}
}
