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
package srojak.mantle.impl;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;
import java.util.function.ObjIntConsumer;

import srojak.mantle.restbl.ResultChoice;
import srojak.mantle.restbl.ResultTable;
import srojak.numerics.IRandomSource;

/**
 * @author Stephen
 *
 */
public class ResultTableArray<T>
		implements ResultTable<T> {
	private final ResultChoiceEntry<T>[] _table;
	private final Class<T> _classValues;
	private final String _name;
	private int _totalWidth;
	
	@SuppressWarnings("unchecked")
	public ResultTableArray(String strName, Class<T> classValues, List<ResultChoiceEntry<T>> list) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		Objects.requireNonNull(classValues, "classValues");
		Objects.requireNonNull(list, "list");
		_table = list.toArray((ResultChoiceEntry<T>[]) Array.newInstance(ResultChoiceEntry.class, 0));
		_classValues = classValues;
		_name = strName;
		calculateTotal();
	}
	
	private void calculateTotal() {
		_totalWidth = 0;
		for (ResultChoiceEntry<T> entry : _table) {
			_totalWidth += entry.getWidth();
		}
	}

	boolean contains(Object obj) {
		for (ResultChoiceEntry<T> entry : _table) {
			if (entry.isValueEqual(obj))
				return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public boolean isEmpty() {
		return _table.length == 0;
	}

	@Override
	public int size() {
		return _table.length;
	}
 
	@Override
	public int getTotalWidth() {
		return _totalWidth;
	}

	@Override
	public Class<?> getElementClass() {
		return _classValues;
	}

	@Override
	public ResultChoice<T> getChoice(T value) {
		for (ResultChoiceEntry<T> entry : _table) {
			if (entry.isValueEqual(value)) {
				return entry;
			}
		}
		return null;
	}

	@Override
	public boolean setChoiceEnabled(T value, boolean bState) {
		for (ResultChoiceEntry<T> entry : _table) {
			if (entry.isValueEqual(value)) {
				entry.setEnabled(bState);
				calculateTotal();
				return true;
			}
		}
		return false;
	}

	@Override
	public T select(IRandomSource rsrc) {
		int nRoll = rsrc.genIntInRange(_totalWidth);
		for (ResultChoiceEntry<T> entry : _table) {
			if (nRoll < entry.getWidth()) {
				return entry.getValue();
			} else {
				nRoll -= entry.getWidth();
			}
		}
		throw new RuntimeException("computation error in select");
	}

	@Override
	public void overAllCommon(ObjIntConsumer<Object> consumer) {
		for (ResultChoiceEntry<T> entry : _table) {
			consumer.accept(entry.getValue(), entry.getWidth());
		}
	}

	@Override
	public void overAll(ObjIntConsumer<T> consumer) {
		for (ResultChoiceEntry<T> entry : _table) {
			consumer.accept(entry.getValue(), entry.getWidth());
		}
	}

	
}
