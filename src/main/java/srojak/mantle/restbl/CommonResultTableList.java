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
package srojak.mantle.restbl;

import java.util.LinkedList;

import srojak.core.tools.ListMethods;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class CommonResultTableList 
		extends LinkedList<ResultTableCommon> {

	public CommonResultTableList() {
		super();
	}
	
	public ResultTableCommon getTable(String strName) {
		return ListMethods.findInList(this, t -> t.getName().equals(strName));
	}
	
	public <T> ResultTable<T> getTableAs(String strName) {
		ResultTableCommon tableCommon = getTable(strName);
		if (tableCommon != null) {
			@SuppressWarnings("unchecked")
			ResultTable<T> table = (ResultTable<T>) tableCommon;
			return table;
		} else {
			return null;
		}
	}
}
