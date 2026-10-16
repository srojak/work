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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.ClassAndContent;

/**
 * @author Stephen
 *
 */
public class ClassAndContentCarrier
		implements ClassAndContent {
	private final Class<?> _classRef;
	private final List<String> _listContent;
	
	public ClassAndContentCarrier(Class<?> classFor) {
		Objects.requireNonNull(classFor, "classFor");
		_classRef = classFor;
		_listContent = new LinkedList<String>();
	}

	@Override
	public Class<?> getReferencedClass() {
		return _classRef;
	}

	@Override
	public String getReferencedClassName(boolean bGetFullName) {
		if (bGetFullName) {
			return _classRef.getName();
		} else {
			return _classRef.getSimpleName();
		}
	}

	@Override
	public List<String> getContent() {
		return List.copyOf(_listContent);
	}

	public void addContent(String str) {
		Objects.requireNonNull(str, "str");
		_listContent.add(str);
	}
	
	public void addContent(Collection<String> collection) {
		Objects.requireNonNull(collection, "collection");
		_listContent.addAll(collection);
	}
	
	public void addContent(String[] array) {
		Objects.requireNonNull(array, "array");
		Collections.addAll(_listContent, array);
	}
}
