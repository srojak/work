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
package srojak.core.containers;

import java.util.Objects;

import srojak.core.NameIdentifiedAndLabeled;

/**
 * @author Stephen
 *
 */
public abstract class NamedAndLabeledBase 
		implements NameIdentifiedAndLabeled {
	private final String _strName;
	private final String _strLabel;
	
	protected NamedAndLabeledBase(String strName, String strLabel) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is empty or blank");
		}
		Objects.requireNonNull(strLabel, "strLabel");
		_strName = strName;
		_strLabel = strLabel;
	}
	
	protected NamedAndLabeledBase(String strName) {
		this(strName, strName);
	}

	@Override
	public String getName() {
		return _strName;
	}

	@Override
	public boolean isNameEqual(String strText) {
		return _strName.equals(strText);
	}
	
	@Override
	public int compareToString(String other) {
		Objects.requireNonNull(other, "other");
		return _strName.compareTo(other);
	}

	@Override
	public String getLabel() {
		return _strLabel;
	}

	@Override
	public int hashCode() {
		return _strName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		else if (obj == null) {
			return false;
		} else if (obj instanceof NamedAndLabeledBase other) {
			return _strName.equals(other._strName);
		} else
			return false;
	}

	protected String makeTaggedName(String strTag) {
		Objects.requireNonNull(strTag, "strTag");
		return strTag + "[" + _strName + "]";
	}
}
