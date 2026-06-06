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
package srojak.debug.tools;

import java.util.EventListener;
import java.util.Objects;

import srojak.core.NameToken;
import srojak.core.NameTokenBearing;
import srojak.core.observe.ObsLevel;
import srojak.debug.DebugSwitch;

/**
 * @author Stephen
 *
 */
public abstract class DebuggingListenerRelayBase
		implements EventListener, NameTokenBearing {
	protected final NameToken _tokenName;
	protected final DebugSwitch _swDebug;
	protected final ObsLevel _levelObs;

	/**
	 * 
	 */
	public DebuggingListenerRelayBase(NameToken tokenName, DebugSwitch swDebug, ObsLevel level) {
		Objects.requireNonNull(tokenName, "tokenName");
		Objects.requireNonNull(swDebug, "swDebug");
		Objects.requireNonNull(level, "level");
		_tokenName = tokenName;
		_swDebug = swDebug;
		_levelObs = level;
		Class<?> classSelf = getClass();
		_swDebug.write(ObsLevel.TRACE, () -> "constructing " + classSelf.getSimpleName()
				+ ", name=" + _tokenName.getName());
	}

	@Override
	public NameToken getNameToken() {
		return _tokenName;
	}

	@Override
	public boolean isNameTokenEqual(NameToken token) {
		if (token == null) {
			return false;
		} else {
			return _tokenName.equals(token);
		}
	}

}
