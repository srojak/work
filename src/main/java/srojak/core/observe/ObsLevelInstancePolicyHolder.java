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
package srojak.core.observe;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import srojak.core.ClassInstanceNumber;
import srojak.core.InstanceNumber;
import srojak.core.InstanceNumberCommon;
import srojak.core.KeyValue;
import srojak.core.containers.KeyValueContainer;

/**
 * @author Stephen
 *
 */
public class ObsLevelInstancePolicyHolder 
		implements ObsLevelInstancePolicy {
	private final Map<InstanceNumber, ObsLevel> _mapOverrides;
	private ObsLevel _levelDefault;
	
	public ObsLevelInstancePolicyHolder(ObsLevel levelDefault) {
		Objects.requireNonNull(levelDefault, "levelDefault");
		_mapOverrides = new HashMap<InstanceNumber, ObsLevel>();
		_levelDefault = levelDefault;
	}
	
	public void copyFrom(ObsLevelInstancePolicyHolder source) {
		Objects.requireNonNull(source, "source");
		_mapOverrides.clear();
		_mapOverrides.putAll(source._mapOverrides);
		_levelDefault = source._levelDefault;
	}

	public ObsLevel getDefaultObsLevel() {
		return _levelDefault;
	}

	public void setDefaultObsLevel(ObsLevel level) {
		Objects.requireNonNull(level, "level");
		_levelDefault = level;
	}

	@Override
	public boolean hasInstanceOverrides() {
		return !_mapOverrides.isEmpty();
	}

	@Override
	public ObsLevel getLevelForInstance(InstanceNumberCommon number) {
		Objects.requireNonNull(number, "number");
		InstanceNumber n2 = number.getAbstractKey();
		ObsLevel level = _mapOverrides.get(n2);
		if (level == null) {
			level = _levelDefault;
		}
		return level;
	}
	
	public void setOverride(InstanceNumber number, ObsLevel level) {
		Objects.requireNonNull(number, "number");
		Objects.requireNonNull(level, "level");
		_mapOverrides.put(number, level);
	}
	
	public void setOverride(ClassInstanceNumber number, ObsLevel level) {
		Objects.requireNonNull(number, "number");
		Objects.requireNonNull(level, "level");
		_mapOverrides.put(number.getAbstractKey(), level);
	}
	
	public Stream<KeyValue<InstanceNumber, ObsLevel>> streamAllOverrides() {
		return _mapOverrides.keySet().stream().map(n -> new KeyValueContainer<InstanceNumber, ObsLevel>(n, _mapOverrides.get(n)));
	}
}
