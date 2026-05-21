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
package srojak.core.collections;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.List;
import java.util.Objects;

import srojak.core.observe.ExceptionAnalyzer;
import srojak.core.observe.ExceptionAnalyzerNull;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public class ListModCountTracker {
	private final List<?> _list;
	private final Field _fieldModCount;
	private final int _modCountOrig;
	private final ExceptionAnalyzer _analyzer;
	
	public ListModCountTracker(List<?> list, ExceptionAnalyzer analyzer) {
		Objects.requireNonNull(list, "list");
		Objects.requireNonNull(analyzer, "writer");
		_list = list;
		_analyzer = analyzer;
		Class<?> classList = _list.getClass();
		Field field = null;
		int nCount = -1;
		fieldSearch:
		while (field == null) {
			try {
				field = classList.getDeclaredField("modCount");
				field.setAccessible(true);
				nCount = field.getInt(_list);
				_analyzer.getWriter().write(ObsLevel.DEBUG, "list mod count = " + nCount);
			} catch (NoSuchFieldException exc) {
				classList = classList.getSuperclass();
				if (classList == Object.class) {
					break fieldSearch;
				}
			} catch (InaccessibleObjectException exc) {
				_analyzer.getWriter().write(ObsLevel.DEBUG, "found field in class " + classList.getSimpleName());
				_analyzer.analyze(ObsLevel.ERROR, SourceLocation.here(), exc);
				field = null;
				break fieldSearch;
			} catch (Exception exc) {
				_analyzer.analyze(ObsLevel.WARN, SourceLocation.here(), exc);
			}
		}
		_fieldModCount = field;
		_modCountOrig = nCount;
	}
	
	public ListModCountTracker(List<?> list) {
		this(list, new ExceptionAnalyzerNull());
	}
	
	public boolean hasModCount() {
		return _fieldModCount != null;
	}
	
	public boolean hasModCountChanged() {
		if (_fieldModCount != null) {
			try {
				int nCount = _fieldModCount.getInt(_list);
				return nCount != _modCountOrig;
			} catch (Exception exc) {
				_analyzer.analyze(ObsLevel.WARN, SourceLocation.here(), exc);
			}
		}
		return false;
	}
}
