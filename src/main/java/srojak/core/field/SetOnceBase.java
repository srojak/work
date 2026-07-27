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
import srojak.core.OnceFlagNamed;

/**
 * @author Stephen
 *
 */
public abstract class SetOnceBase 
		implements SetOnceConditions {
	private final OnceFlag _flag;
	
	public SetOnceBase() {
		_flag = new OnceFlag();
	}
	
	public SetOnceBase(NameToken token) {
		Objects.requireNonNull(token, "token");
		_flag = new OnceFlagNamed(token);
	}
	
	public SetOnceBase(String strName) {
		_flag = new OnceFlagNamed(NameToken.factory(strName));
	}

	@Override
	public boolean hasBeenSet() {
		return _flag.getState();
	}
	
	public void faultIfAlreadySet() {
		if (_flag.getState()) {
			throw new IllegalStateException("value has already been set");
		}
	}
	
	protected void gettingValue() {
		if (!_flag.getState()) {
			throw new IllegalStateException("value has never been set");
		}
	}

	protected void settingValue() {
		faultIfAlreadySet();
		_flag.set();
	}
}
