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
package srojak.cdo.swing.models;

import java.util.Objects;

import srojak.cdo.swing.base.ActionControlModelBase;
import srojak.core.NameToken;
import srojak.events.ObjectValueChangeEvent;
import srojak.events.ObjectValueChangeListener;
import srojak.numerics.compass.CompassPoint;

/**
 * @author Stephen
 *
 */
public class DefaultCompassControlModel 
		extends ActionControlModelBase
		implements CompassControlModel {
	private CompassPoint _value;
	
	public DefaultCompassControlModel(NameToken tokenName) {
		super(tokenName);
		_value = CompassPoint.N;
	}

	@Override
	public CompassPoint getCurrentValue() {
		return _value;
	}

	@Override
	public void setCurrentValue(CompassPoint cpoint) {
		Objects.requireNonNull(cpoint, "cpoint");
		if (_value != cpoint) {
			_value = cpoint;
			ObjectValueChangeEvent event = new ObjectValueChangeEvent(this, _value);
			_listeners.forEach(ObjectValueChangeListener.class, ls -> ls.update(event));
		}
	}

	@Override
	public void addObjectValueChangeListener(ObjectValueChangeListener listener) {
		_listeners.add(ObjectValueChangeListener.class, listener);		
	}

	@Override
	public void removeObjectValueChangeListener(ObjectValueChangeListener listener) {
		_listeners.remove(ObjectValueChangeListener.class, listener);		
	}
	
}
