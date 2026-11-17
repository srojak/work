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
package srojak.sql.collate;

import srojak.core.keys.NameEquatableKeyBase;
import srojak.sql.SQLCollation;

/**
 * @author Stephen
 *
 */
public final class SQLCollationName 
		extends NameEquatableKeyBase 
		implements SQLCollation {

	/**
	 * 
	 */
	private static final long serialClassUID = -276336690694748715L;
	private static final String nameRoot = SQLCollation.class.getSimpleName();

	/**
	 * @param strName
	 */
	public SQLCollationName(String strName) {
		super(strName);
	}

	@Override
	public int compareTo(SQLCollation o) {
		return commonComparer(o);
	}

	@Override
	protected long getRootId() {
		return serialClassUID;
	}

	@Override
	protected String getRootName() {
		return nameRoot;
	}

}
