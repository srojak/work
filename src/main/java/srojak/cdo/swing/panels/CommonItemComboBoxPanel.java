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
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Objects;

import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;

import srojak.cdo.ActionEventOriginator;
import srojak.core.NameToken;
import srojak.core.tools.CollectionMethods;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class CommonItemComboBoxPanel<T>
		extends NameTokenTagPanel 
		implements ActionEventOriginator {
	private final T[] _data;
	protected final JComboBox<T> _cbox;

	/**
	 * @param tokenName
	 */
	public CommonItemComboBoxPanel(NameToken tokenName, Class<T> classData,	Collection<T> data) {
		super(tokenName);
		Objects.requireNonNull(data, "data");
		_data = CollectionMethods.createArray(classData, data);
		_cbox = new JComboBox<T>(_data);
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param layout
	 */
	public CommonItemComboBoxPanel(NameToken tokenName, LayoutManager layout,
			Class<T> classData,	Collection<T> data) {
		super(tokenName, layout);
		Objects.requireNonNull(data, "data");
		_data = CollectionMethods.createArray(classData, data);
		_cbox = new JComboBox<T>(_data);
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public CommonItemComboBoxPanel(NameToken tokenName, boolean isDoubleBuffered,
			Class<T> classData,	Collection<T> data) {
		super(tokenName, isDoubleBuffered);
		Objects.requireNonNull(data, "data");
		_data = CollectionMethods.createArray(classData, data);
		_cbox = new JComboBox<T>(_data);
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public CommonItemComboBoxPanel(NameToken tokenName, LayoutManager layout, 
			boolean isDoubleBuffered, Class<T> classData, Collection<T> data) {
		super(tokenName, layout, isDoubleBuffered);
		Objects.requireNonNull(data, "data");
		_data = CollectionMethods.createArray(classData, data);
		_cbox = new JComboBox<T>(_data);
		postConstruct();
	}
	
	private void postConstruct() {
		add(_cbox);
	}
	
	public void setRenderer(ListCellRenderer<T> renderer) {
		Objects.requireNonNull(renderer, "renderer");
		_cbox.setRenderer(renderer);
	}

	public void setComboBoxBackground(Color bg) {
		_cbox.setBackground(bg);
	}
	
	public boolean hasSelectedValue() {
		return _cbox.getSelectedIndex() >= 0;
	}

	public T getSelectedValue() {
		int index = _cbox.getSelectedIndex();
		return index >= 0 ? _data[index] : null;
	}
	
	public void setSelectedValue(T value) {
		if (value == null) {
			_cbox.setSelectedIndex(-1);
		} else {
			_cbox.setSelectedItem(value);
		}
	}

	@Override
	public void addActionListener(ActionListener listener) {
		_cbox.addActionListener(listener);
	}

	@Override
	public void removeActionListener(ActionListener listener) {
		_cbox.removeActionListener(listener);

	}

}
