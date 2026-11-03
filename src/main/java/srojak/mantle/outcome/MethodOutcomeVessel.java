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
package srojak.mantle.outcome;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.observe.ObsLevel;
import srojak.mantle.collections.GeneralizedObjectMap;
import srojak.mantle.collections.GeneralizedObjectReadOnlyMap;

/**
 * @author Stephen
 *
 */
public class MethodOutcomeVessel 
		implements MethodOutcome {
	private final List<MethodOutcomeMessage> _listMessages;
	private GeneralizedObjectMap _mapData;
	private OutcomeResult _result;

	/**
	 * 
	 */
	public MethodOutcomeVessel() {
		_listMessages = new LinkedList<MethodOutcomeMessage>();
		_mapData = new GeneralizedObjectMap();
		_result = OutcomeResult.UNDEFINED;
	}

	@Override
	public OutcomeResult result() {
		return _result;
	}

	@Override
	public List<MethodOutcomeMessage> messages() {
		return List.copyOf(_listMessages);
	}

	@Override
	public GeneralizedObjectReadOnlyMap data() {
		return _mapData;
	}

	public void setResult(OutcomeResult result) {
		Objects.requireNonNull(result, "result");
		if (!result.isValidEndState()) {
			throw new IllegalArgumentException("result cannot be set to " + result);
		}
	}
	
	public void addMessage(ObsLevel level, String strMessage) {
		MethodOutcomeMessage message = new MethodOutcomeMessage(level, strMessage);
		_listMessages.add(message);
	}
	
	public void putData(Object key, Object value) {
		Objects.requireNonNull(key, "key");
		_mapData.put(key, value);
	}
}
