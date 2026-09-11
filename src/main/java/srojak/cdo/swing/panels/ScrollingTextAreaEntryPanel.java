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

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Position;

import srojak.core.NameToken;
import srojak.core.events.StateChangeCodes;
import srojak.core.events.StateChangeListener;
import srojak.core.events.StateChangeOriginator;
import srojak.mantle.quants.TextBlockSize;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ScrollingTextAreaEntryPanel 
		extends ScrollingTextAreaPanelBase 
		implements StateChangeOriginator, StateChangeCodes {

    public static final NameToken PANEL_NAME = NameToken.classNameFactory(ScrollingTextAreaEntryPanel.class);
    
	/**
	 * @param tokenName
	 * @param size
	 */
	public ScrollingTextAreaEntryPanel(NameToken tokenName, TextBlockSize size) {
		super(tokenName, size);
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 * @param size
	 */
	public ScrollingTextAreaEntryPanel(NameToken tokenName, boolean isDoubleBuffered, TextBlockSize size) {
		super(tokenName, isDoubleBuffered, size);
		postConstruct();
	}

	private void postConstruct() {
		_textDocument.addDocumentListener(new DocumentChangeListener());
	}
    
    private void whenContentChanged() {
    	Position pos = _textDocument.getEndPosition();
    	super.sendStateChange(SC_CONTENT, pos.getOffset() > 0);
    }
    
    public boolean isEditable() {
    	return _areaText.isEditable();
    }
    
    public void setEditable(boolean bState) {
    	_areaText.setEditable(bState);
    }
    
    public String getText() {
    	return _areaText.getText();
    }

	private class DocumentChangeListener
			implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			whenContentChanged();			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			whenContentChanged();			
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			whenContentChanged();			
		}
		
	}

	@Override
	public void addStateChangeListener(StateChangeListener listener) {
		_listeners.add(StateChangeListener.class, listener);
	}

	@Override
	public void removeStateChangeListener(StateChangeListener listener) {
		_listeners.remove(StateChangeListener.class, listener);
	}
}
