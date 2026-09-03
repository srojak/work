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

import java.util.Collection;
import java.util.Objects;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import srojak.core.NameToken;
import srojak.core.NameTokenTagged;
import srojak.core.tools.CollectionMethods;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class CommonItemComboBox<T> 
		extends JComboBox<T> 
		implements NameTokenTagged {
	private final NameToken _token;
	private final Vector<T> _data;

	/**
	 * 
	 */
	public CommonItemComboBox(NameToken tokenName, Collection<T> data) {
		Objects.requireNonNull(tokenName, "tokenName");
		Objects.requireNonNull(data, "data");
		_token = tokenName;
		_data = new Vector<T>(data);
		ComboBoxModel<T> model = new DefaultComboBoxModel<T>(_data);
		super.setModel(model);
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
	
	protected T findInData(Predicate<T> predicate) {
		return CollectionMethods.findFirstIn(_data, predicate);
	}
	
	protected int getDataSize() {
		return _data.size();
	}
	
	protected Stream<T> getDataAsStream() {
		return _data.stream();
	}
	
	public boolean hasSelectedValue() {
		return getSelectedIndex() >= 0;
	}

	public T getSelectedValue() {
		int index = getSelectedIndex();
		return index >= 0 ? _data.get(index) : null;
	}
	
	public void setSelectedValue(T value) {
		if (value == null) {
			this.setSelectedIndex(-1);
		} else {
			this.setSelectedItem(value);
		}
	}
}
