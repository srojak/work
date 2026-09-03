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
package srojak.cdo.swing.interact;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;

import srojak.cdo.ControlModelManager;

/**
 * @author Stephen
 *
 */
public class DocumentModelAdapter 
		extends ComponentModelAdapterBase 
		implements Document {
	private ControlModelManager<Document> _model;

	/**
	 * 
	 */
	public DocumentModelAdapter() {
		_model = new ControlModelManager<Document>();
	}

	@Override
	public int getLength() {
		return _model.isEmpty() ? 0 : _model.getModel().getLength();
	}

	@Override
	public void addDocumentListener(DocumentListener listener) {
		_listeners.add(DocumentListener.class, listener);
	}

	@Override
	public void removeDocumentListener(DocumentListener listener) {
		_listeners.remove(DocumentListener.class, listener);
	}

	@Override
	public void addUndoableEditListener(UndoableEditListener listener) {
		_listeners.add(UndoableEditListener.class, listener);
	}

	@Override
	public void removeUndoableEditListener(UndoableEditListener listener) {
		_listeners.remove(UndoableEditListener.class, listener);
	}

	@Override
	public Object getProperty(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putProperty(Object key, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(int offs, int len) throws BadLocationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getText(int offset, int length) throws BadLocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getText(int offset, int length, Segment txt) throws BadLocationException {
		// TODO Auto-generated method stub

	}

	@Override
	public Position getStartPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position getEndPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position createPosition(int offs) throws BadLocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element[] getRootElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getDefaultRootElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(Runnable r) {
		// TODO Auto-generated method stub

	}

}
