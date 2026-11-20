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
package srojak.sql.names;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author Stephen
 *
 */
public abstract class AbstractSQLMultiPartName {
	protected final ArrayList<SQLNamePart> _listNames;

	protected AbstractSQLMultiPartName(int parts) {
		_listNames = new ArrayList<SQLNamePart>(parts);
	}
	
	protected String assemble() {
		return _listNames.stream().map(e -> e.toString()).collect(Collectors.joining("."));
	}

	@Override
	public int hashCode() {
		return _listNames.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof AbstractSQLMultiPartName other) {
			return _listNames.equals(other._listNames);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
