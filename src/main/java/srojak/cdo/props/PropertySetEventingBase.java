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
package srojak.cdo.props;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import srojak.cdo.PropertyChangeEventOriginator;
import srojak.core.props.PropertySetBase;

/**
 * @author Stephen
 *
 */
public abstract class PropertySetEventingBase 
		extends PropertySetBase
		implements PropertyChangeEventOriginator {
	private final PropertyChangeSupport _changer;

	/**
	 * 
	 */
	public PropertySetEventingBase() {
		super();
		_changer = new PropertyChangeSupport(this);
	}

	@Override
	protected void afterSetProperty(String key, Object valueOld, Object valueNew) {
		_changer.firePropertyChange(key, valueOld, valueNew);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		_changer.addPropertyChangeListener(listener);
	}

	@Override
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		_changer.addPropertyChangeListener(propertyName, listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		_changer.removePropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		_changer.removePropertyChangeListener(propertyName, listener);
	}

}
