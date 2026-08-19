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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.CommonCollectionSize;
import srojak.mantle.impl.ResultTableArray;
import srojak.mantle.reflect.GenericTypeInstance;

/**
 * @author Stephen
 *
 */
public class ResultTableAbstractBuilder
		implements CommonCollectionSize {
	private final LinkedList<ResultChoiceCommon> _listEntries;
	private final Class<?> _classValue;
	private final String _name;
	@SuppressWarnings("unused")
	private final ParameterizedType _typeTable;
	private final Constructor<ResultTableArray<?>> _constructor;
	
	private static final Class<?> _typeArray = ResultTableArray.class;

	@SuppressWarnings("unchecked")
	public ResultTableAbstractBuilder(Class<?> classValue, String strName) 
			throws NoSuchMethodException, SecurityException {
		Objects.requireNonNull(classValue, "classValue");
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		_listEntries = new LinkedList<ResultChoiceCommon>();
		_classValue = classValue;
		_name = strName;
		_typeTable = new GenericTypeInstance(_typeArray, _classValue);
		_constructor = (Constructor<ResultTableArray<?>>) _typeArray.getConstructor(String.class, Class.class, List.class);
	}
	
	public Class<?> getValueClass() {
		return _classValue;
	}

	@Override
	public boolean isEmpty() {
		return _listEntries.isEmpty();
	}

	@Override
	public int size() {
		return _listEntries.size();
	}
	
	public void addEntry(ResultChoiceCommon entry) {
		Objects.requireNonNull(entry, "entry");
		_listEntries.add(entry);
	}
	
	public ResultTableCommon create() throws InstantiationException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException {
		return _constructor.newInstance(_name, _classValue, _listEntries);
	}
}
