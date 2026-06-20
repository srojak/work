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
package srojak.cdo.swing.status;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import srojak.cdo.swing.panels.NameTokenTagPanel;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial") // Same-version serialization only
public class StatusBar
		extends NameTokenTagPanel {
	private final ArrayList<StatusBarItemBase> _listItems;
	
	public static final NameToken ClassToken;

	static {
		Class<?> classThis = StatusBar.class;
		ClassToken = NameToken.classNameFactory(classThis);
	}
	
	/**
	 * 
	 */
	public StatusBar(NameToken tokenName) {
		super(tokenName, new FlowLayout(SwingConstants.LEFT));
		_listItems = new ArrayList<StatusBarItemBase>();
		setPreferredSize(new Dimension(100, 30));
		setBorder(new LineBorder(Color.BLACK, 1));
	}
	
	public int getItemCount() {
		return _listItems.size();
	}
	
	public StatusBarItem add(StatusBarItemBase itemStatus) {
		Objects.requireNonNull(itemStatus, "itemStatus");
		super.addImpl(itemStatus.getComponent(),  null, -1);
		itemStatus.setPosition(_listItems.size());
		_listItems.add(itemStatus);
		return itemStatus;
	}

	@Override
	public Component add(Component comp) {
		throw new UnsupportedOperationException("cannot add to status bar");
	}

	@Override
	public Component add(String name, Component comp) {
		throw new UnsupportedOperationException("cannot add to status bar");
	}

	@Override
	public Component add(Component comp, int index) {
		throw new UnsupportedOperationException("cannot add to status bar");
	}

	@Override
	public void add(Component comp, Object constraints) {
		throw new UnsupportedOperationException("cannot add to status bar");
	}

	@Override
	public void add(Component comp, Object constraints, int index) {
		throw new UnsupportedOperationException("cannot add to status bar");
	}
	
	public void addHorizontalSpace(int nWidth) {
		super.add(Box.createHorizontalStrut(nWidth));
	}
	
	public StatusBarItem getItem(int index) {
		return _listItems.get(index);
	}
	
	public void SetItemText(int index, String strText) {
		StatusBarItemBase item = _listItems.get(index);
		if (item instanceof StatusBarTextItem itemText) {
			itemText.setText(strText);
		}
	}
}
