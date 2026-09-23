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
package srojak.xml;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class XNamespace {
	private final String _namespace;

	/**
	 * 
	 */
	public XNamespace(String strNamespace) {
		Objects.requireNonNull(strNamespace, "strNamespace");
		_namespace = strNamespace;
	}
	
	public String getText() {
		return _namespace;
	}
	
	@Override
	public int hashCode() {
		return _namespace.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		else if (obj instanceof XNamespace other) {
			return _namespace.equals(other._namespace);
		} else
			return false;
	}

	@Override
	public String toString() {
		return "namespace[" + _namespace + "]";
	}
	
}
