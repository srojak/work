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
package srojak.core.decorated;

import java.util.Objects;

import srojak.core.InvalidOperationException;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
public abstract class DecoratorBase
		implements Decorator {
	private final NameToken _name;
	private boolean _bLocked;
	
	public DecoratorBase(NameToken token) {
		Objects.requireNonNull(token, "token");
		_name = token;
		_bLocked = false;
	}

	@Override
	public NameToken getNameToken() {
		return _name;
	}
	
	@Override
	public boolean isNameTokenEqual(NameToken token) {
		return token == null ? false : _name.equals(token);
	}

	@Override
	public boolean isLocked() {
		return _bLocked;
	}
	
	public void lockValue() {
		_bLocked = true;
	}
	
	protected void testLock() {
		if (_bLocked) {
			throw new InvalidOperationException(_name.getName(), "decorator is locked");
		}
	}
	
	protected abstract Object getValueAsObject();
	
	protected abstract String getStringValue();

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return _name.equals(obj);
	}

	@Override
	public String toString() {
		return "decorator[name=" + _name.getName() + ", value=" + getStringValue() + "]";
	}
}
