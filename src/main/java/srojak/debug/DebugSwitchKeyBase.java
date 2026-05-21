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
package srojak.debug;

import java.util.Objects;

import srojak.core.LazyInt;
import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 */
public sealed abstract class DebugSwitchKeyBase 
		implements DebugSwitchKey, Comparable<DebugSwitchKeyBase>
		permits DebugSwitchKeyClass, DebugSwitchKeyClassSubject {
	private final PackageClassLocator _locator;
	private final String _strFullName;
	private LazyInt _nHash;
	
	public DebugSwitchKeyBase(PackageClassLocator locator) {
		Objects.requireNonNull(locator, "locator");
		_locator = locator;
		_strFullName = _locator.getFullName();
		_nHash = new LazyInt(() -> _strFullName.hashCode());
	}
	
	public DebugSwitchKeyBase(Class<?> classOwner) {
		Objects.requireNonNull(classOwner, "classOwner");
		_locator = new PackageClassLocator(classOwner);
		_strFullName = _locator.getFullName();
		_nHash = new LazyInt(() -> _strFullName.hashCode());
	}
	
	public DebugSwitchKeyBase(PackageClassLocator locator, String strExtension) {
		Objects.requireNonNull(locator, "locator");
		Objects.requireNonNull(strExtension, "strExtension");
		if (strExtension.isEmpty()) {
			throw new IllegalArgumentException("strExtension is empty");
		}
		_locator = locator;
		_strFullName = _locator.getFullName() + "!" + strExtension;
		_nHash = new LazyInt(() -> _strFullName.hashCode());
	}
	
	public DebugSwitchKeyBase(Class<?> classOwner, String strExtension) {
		Objects.requireNonNull(classOwner, "classOwner");
		Objects.requireNonNull(strExtension, "strExtension");
		if (strExtension.isEmpty()) {
			throw new IllegalArgumentException("strExtension is empty");
		}
		_locator = new PackageClassLocator(classOwner);
		_strFullName = _locator.getFullName() + "!" + strExtension;
		_nHash = new LazyInt(() -> _strFullName.hashCode());
	}

	@Override
	public PackageClassLocator getClassLocator() {
		return _locator;
	}

	@Override
	public String getFullName() {
		return _strFullName;
	}

	@Override
	public boolean hasSubjectName() {
		return false;
	}

	@Override
	public String getSubjectName() {
		return null;
	}
	
	protected StringBuilder buildClassInfo() {
		StringBuilder sb = new StringBuilder("key(");
		sb.append(_locator);
		return sb;
	}

	@Override
	public int hashCode() {
		return _nHash.get();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) 
			return false;
		else if (obj instanceof DebugSwitchKeyBase other) {
			return _strFullName.equals(other._strFullName);
		}
		else
			return false;
	}
	
	@Override
	public int compareTo(DebugSwitchKeyBase o) {
		if (this == o) {
			return 0;
		} else if (o == null) {
			return 1;
		} else {
			int nCompar = _locator.compareTo(o._locator);
			if (nCompar == 0) {
				if (hasSubjectName()) {
					if (o.hasSubjectName()) {
						nCompar = getSubjectName().compareTo(o.getSubjectName());
					} else {
						nCompar = 1;
					}
				} else {
					if (o.hasSubjectName()) {
						nCompar = -1;
					}
				}
			}
			return nCompar;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = buildClassInfo();
		sb.append(')');
		return sb.toString();
	}
}
