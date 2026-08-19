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
package srojak.debug.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import srojak.core.reflect.PackageClassLocator;
import srojak.debug.ClassDebugOptions;
import srojak.debug.DebugOptionNameValue;

/**
 * @author Stephen
 *
 */
public class ClassDebugOptionMap
		implements ClassDebugOptions {
	private final PackageClassLocator _locClass;
	private final Map<String, ClassDebugOptionEntry> _mapOptions;
	
	public ClassDebugOptionMap(PackageClassLocator locClass) {
		Objects.requireNonNull(locClass, "locClass");
		_locClass = locClass;
		_mapOptions = new HashMap<String, ClassDebugOptionEntry>();
	}

	@Override
	public PackageClassLocator getOwner() {
		return _locClass;
	}

	@Override
	public boolean hasOption(String strName) {
		return _mapOptions.containsKey(strName);
	}

	@Override
	public int getOptionValue(String strName) {
		ClassDebugOptionEntry entry = _mapOptions.get(strName);
		return entry != null ? entry.getValue() : 0;
	}

	@Override
	public boolean isOptionValueNonZero(String strName) {
		ClassDebugOptionEntry entry = _mapOptions.get(strName);
		return entry != null && entry.getValue() != 0;
	}

	public void putOption(String strName, int nValue) {
		ClassDebugOptionEntry entry = _mapOptions.get(strName);
		if (entry == null) {
			entry = new ClassDebugOptionEntry(strName, nValue);
			_mapOptions.put(entry.getName(), entry);
		} else {
			entry.setValue(nValue);
		}
	}

	@Override
	public List<DebugOptionNameValue> getOptions() {
		return List.copyOf(_mapOptions.values());
	}
}
