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
package srojak.core.text;

import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Predicate;

import srojak.core.NameToken;
import srojak.core.TextAnnotatedObject;
import srojak.core.logic.LockGate;
import srojak.core.tools.ListMethods;

/**
 * @author Stephen
 *
 */
public abstract class TextAnnotatedBase
		implements TextAnnotatedObject {

	private final LinkedList<String> _listAnnotations;
	private final LockGate _gateLock;
	
	protected TextAnnotatedBase() {
		_listAnnotations = new LinkedList<String>();
		_gateLock = new LockGate();
	}
	
	@Override
	public boolean isLocked() {
		return _gateLock.isLocked();
	}

	@Override
	public void lock() {
		_gateLock.lock();
	}

	@Override
	public boolean hasAnnotations() {
		return !_listAnnotations.isEmpty();
	}

	@Override
	public boolean hasAnnotation(Predicate<String> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		return ListMethods.isTrueForAny(_listAnnotations, predicate);
	}

	@Override
	public String[] getAnnotations() {
		return _listAnnotations.toArray(new String[_listAnnotations.size()]);
	}
	
	protected abstract NameToken getClassToken();
	
	public void clearAnnotations() {
		_gateLock.testLock(getClassToken());
		_listAnnotations.clear();
	}
	
	public void addAnnotation(String strText) {
		Objects.requireNonNull(strText, "strText");
		_gateLock.testLock(getClassToken());
		_listAnnotations.addLast(strText);
	}
}
