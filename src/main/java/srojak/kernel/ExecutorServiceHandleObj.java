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
package srojak.kernel;

import java.util.concurrent.ExecutorService;

import srojak.core.Equatable;
import srojak.core.keys.EquatableHandleBase;

/**
 * @author Stephen
 *
 */
public class ExecutorServiceHandleObj 
		extends EquatableHandleBase
		implements ExecutorServiceHandle {

	/**
	 * 
	 */
	private static final long serialClassUID = -3568026429449094988L;
	private static final String nameRoot = ExecutorServiceHandleObj.class.getSimpleName();

	/**
	 * @param obj
	 */
	public ExecutorServiceHandleObj(ExecutorService service) {
		super(service);
	}

	@Override
	protected long getRootId() {
		return serialClassUID;
	}

	@Override
	protected String getRootName() {
		return nameRoot;
	}

	@Override
	public boolean isEqual(ExecutorServiceHandle obj) {
		return commonEquals(obj);
	}

}
