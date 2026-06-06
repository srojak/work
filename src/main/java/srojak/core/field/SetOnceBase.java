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
package srojak.core.field;

import java.util.Objects;

import srojak.core.NameToken;
import srojak.core.OnceFlag;

/**
 * @author Stephen
 *
 */
public abstract class SetOnceBase 
		implements SetOnceConditions {
	private final OnceFlag _flag;
	
	/**
	 * 
	 */
	public SetOnceBase(NameToken token) {
		Objects.requireNonNull(token, "token");
		_flag = new OnceFlag(token);
	}
	
	public SetOnceBase(String strName) {
		_flag = new OnceFlag(NameToken.factory(strName));
	}

	@Override
	public NameToken getNameToken() {
		return _flag.getNameToken();
	}

	@Override
	public boolean isNameTokenEqual(NameToken token) {
		return token == null ? false : _flag.isNameTokenEqual(token);
	}

	@Override
	public boolean hasBeenSet() {
		return _flag.getState();
	}
	
	public void faultIfAlreadySet() {
		if (_flag.getState()) {
			throw new IllegalStateException("value for " + _flag.getNameToken().getName()
					+ " has already been set");
		}
	}
	
	protected void gettingValue() {
		if (!_flag.getState()) {
			throw new IllegalStateException("value for " + _flag.getNameToken().getName()
					+ " has never been set");
		}
	}

	protected void settingValue() {
		faultIfAlreadySet();
		_flag.set();
	}
}
