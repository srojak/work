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
import java.util.Objects;

import srojak.mantle.impl.ResultChoiceEntry;
import srojak.mantle.impl.ResultTableArray;

/**
 * @author Stephen
 *
 */
public class ResultTableBuilder<T> 
		implements ResultTableFactory<T> {
	private final LinkedList<ResultChoiceEntry<T>> _list;
	private final Class<T> _classValues;
	
	public ResultTableBuilder(Class<T> classValues) {
		Objects.requireNonNull(classValues, "classValues");
		_list = new LinkedList<ResultChoiceEntry<T>>();
		_classValues = classValues;
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	@Override
	public int size() {
		return _list.size();
	}

	@Override
	public void defineEntry(int nWidth, T value) {
		ResultChoiceEntry<T> entry = new ResultChoiceEntry<T>(nWidth, value);
		_list.add(entry);
	}

	@Override
	public ResultTable<T> create(String strName) {
		return new ResultTableArray<T>(strName, _classValues, _list);
	}

}
