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

import java.awt.Color;
import java.util.Objects;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import srojak.cdo.swing.CellRendererSettings;
import srojak.cdo.swing.ListComponent;
import srojak.cdo.swing.ScrollableListComponent;
import srojak.cdo.swing.models.ModifiableListModel;
import srojak.core.NameToken;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.specialized.ListIndexRange;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ScrollableListPanel<V, M extends ModifiableListModel<V>>
		extends NameTokenTagPanel
		implements ListComponent<V>, ScrollableListComponent<V> {
	private final CommonEventListenerStore _listeners;
	private final JList<V> _list;
	private final M _model;
	private final JScrollPane _scroll;
	private boolean _bForwardAllListSelectionEvents;

	/**
	 * 
	 */
	public ScrollableListPanel(NameToken tokenName, M model) {
		super(tokenName);
		Objects.requireNonNull(model, "model");
		_listeners = new CommonEventListenerList();
		_model = model;
		_list = new JList<V>(_model);
		_scroll = new JScrollPane(_list);
		add(_scroll);
		postConstruct();
	}

	/**
	 * @param isDoubleBuffered
	 */
	public ScrollableListPanel(NameToken tokenName, boolean isDoubleBuffered, M model) {
		super(tokenName, isDoubleBuffered);
		Objects.requireNonNull(model, "model");
		_listeners = new CommonEventListenerList();
		_model = model;
		_list = new JList<V>(_model);
		_scroll = new JScrollPane(_list);
		add(_scroll);
		postConstruct();
	}

	private void postConstruct() {
		_bForwardAllListSelectionEvents = false;
		_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		_model.addListDataListener(new RelayListDataListener());
		_list.getSelectionModel().addListSelectionListener(new RelayListSelectionListener());
	}
	
	@Override
	public ModifiableListModel<V> getListModel() {
		return _model;
	}
	
	protected JList<V> getList() {
		return _list;
	}
	
	@Override
	public int getSelectionMode() {
		return _list.getSelectionModel().getSelectionMode();
	}
	
	@Override
	public void setSelectionMode(int mode) {
		_list.getSelectionModel().setSelectionMode(mode);
	}
	
	@Override
	public ListSelectionModel getListSelectionModel() {
		return _list.getSelectionModel();
	}
	
	public void setCellRenderer(ListCellRenderer<? super V> cellRenderer) {
		Objects.requireNonNull(cellRenderer, "cellRenderer");
		_list.setCellRenderer(cellRenderer);
		if (cellRenderer instanceof CellRendererSettings crSettings) {
			crSettings.setBackground(getBackground());
		}
	}
	
	public Color getListBackgroundColor() {
		return _list.getBackground();
	}
	
	public void setListBackgroundColor(Color color) {
		Objects.requireNonNull(color, "color");
		_list.setBackground(color);
		ListCellRenderer<?> renderer = _list.getCellRenderer();
		if (renderer instanceof CellRendererSettings crSettings) {
			crSettings.setBackground(color);
		}
	}
	
	@Override
	public ListIndexRange getSelectionRange() {
		ListSelectionModel modelSelect = _list.getSelectionModel();
		ListIndexRange range = new ListIndexRange(_model.getSize(),
				modelSelect.getMinSelectionIndex(),
				modelSelect.getMaxSelectionIndex());
		return range;
	}
	
	@Override
	public int[] getSelectedIndices() {
		return _list.getSelectionModel().getSelectedIndices();
	}

	protected void setHorizontalScrollBarPolicy(int policy) {
		_scroll.setHorizontalScrollBarPolicy(policy);
	}
	
	public void setVisibleRowCount(int rows) {
		_list.setVisibleRowCount(rows);
	}
	
	public V getSingleSelection() {
		int index = _list.getMinSelectionIndex();
		return index < 0 ? null : _model.getElementAt(index);
	}
	
	public void setSelectionBackground(Color color) {
		_list.setSelectionBackground(color);
	}

	@Override
	public V getElementAt(int index) {
		return _model.getElementAt(index);
	}

	@Override
	public boolean isSelectionEmpty() {
		return _list.isSelectionEmpty();
	}
	
	private ListDataEvent copyEvent(ListDataEvent e) {
		return new ListDataEvent(this, e.getType(), e.getIndex0(), e.getIndex1());
	}

	@Override
	public void addListDataListener(ListDataListener listener) {
		_listeners.add(ListDataListener.class, listener);
	}

	@Override
	public void removeListDataListener(ListDataListener listener) {
		_listeners.remove(ListDataListener.class, listener);
	}

	@Override
	public void addListSelectionListener(ListSelectionListener listener) {
		_listeners.add(ListSelectionListener.class, listener);
	}

	@Override
	public void removeListSelectionListener(ListSelectionListener listener) {
		_listeners.remove(ListSelectionListener.class, listener);
	}
	
	private class RelayListDataListener
			implements ListDataListener {

		@Override
		public void intervalAdded(ListDataEvent e) {
			ListDataEvent event = copyEvent(e);
			_listeners.forEach(ListDataListener.class,
					ls -> ls.intervalAdded(event));
		}

		@Override
		public void intervalRemoved(ListDataEvent e) {
			ListDataEvent event = copyEvent(e);
			_listeners.forEach(ListDataListener.class,
					ls -> ls.intervalRemoved(event));
		}

		@Override
		public void contentsChanged(ListDataEvent e) {
			ListDataEvent event = copyEvent(e);
			_listeners.forEach(ListDataListener.class,
					ls -> ls.contentsChanged(event));
		}
		
	}

	private class RelayListSelectionListener
			implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting() || _bForwardAllListSelectionEvents) {
				ListSelectionEvent event = new ListSelectionEvent(ScrollableListPanel.this,
						e.getFirstIndex(), e.getLastIndex(), e.getValueIsAdjusting());
				_listeners.forEach(ListSelectionListener.class,
						ls -> ls.valueChanged(event));
			}
			
		}
		
	}
}
