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
package srojak.core.reflect;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class SynthClosedDelegate 
		extends AbstractSynthDelegate {
	private final WeakReference<Object> _instance;

	/**
	 * @param method
	 */
	public SynthClosedDelegate(Object objInstance, Method method) {
		super(method);
		Objects.requireNonNull(objInstance, "objInstance");
		_instance = new WeakReference<Object>(objInstance);
	}

	@Override
	public boolean isClosed() {
		return false;
	}

	@Override
	protected Object getInstance() {
		Object objInstance = _instance.get();
		if (objInstance == null) {
			invalidate();
		}
		return objInstance;
	}

}
