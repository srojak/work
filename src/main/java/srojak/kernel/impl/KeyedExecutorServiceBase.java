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
package srojak.kernel.impl;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

import srojak.kernel.ExecutorServiceHandle;
import srojak.kernel.ExecutorServiceHandleObj;

/**
 * @author Stephen
 *
 */
public abstract class KeyedExecutorServiceBase 
		implements KeyedExecutor {
	private final ExecutorServiceHandle _handle;
	
	protected KeyedExecutorServiceBase(ExecutorService service) {
		Objects.requireNonNull(service, "service");
		_handle = new ExecutorServiceHandleObj(service);
	}

	@Override
	public ExecutorServiceHandle key() {
		return _handle;
	}

	@Override
	public boolean isKeyEqual(ExecutorServiceHandle key) {
		return _handle.isEqual(key);
	}

}
