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

import srojak.core.Lockable;
import srojak.core.NameToken;
import srojak.core.logic.LockGate;

/**
 * @author Stephen
 *
 */
public class NameIdentifiedInt 
		extends NameIdentifiedBase
		implements Lockable {
	private final LockGate _gateLock;
	private int _value;

	public static final NameToken ClassToken;

	static {
		Class<?> classThis = NameIdentifiedInt.class;
		ClassToken = NameToken.classNameFactory(classThis);
	}
	
	/**
	 * @param strName
	 */
	public NameIdentifiedInt(String strName, int nValue) {
		super(strName);
		_gateLock = new LockGate();
		_value = nValue;
	}

	@Override
	protected boolean canBeComparedTo(NameIdentifiedBase other) {
		return other instanceof NameIdentifiedInt;
	}

	@Override
	public boolean isLocked() {
		return _gateLock.isLocked();
	}

	@Override
	public void lock() {
		_gateLock.lock();
	}

	public int getValue() {
		return _value;
	}
	
	public void setValue(int nValue) {
		_gateLock.testLock(ClassToken);
		_value = nValue;
	}
}
