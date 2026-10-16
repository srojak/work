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
package srojak.kernel.priv;

import java.util.Objects;

import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.SourceLocation;
import srojak.kernel.KernelPrivilegedOperations;

/**
 * @author Stephen
 *
 */
public class KernelPrivOpsProvider 
		implements KernelPrivilegedOperations, CoreObservers {
	private final boolean _bValid;
	
	public KernelPrivOpsProvider() {
		SourceLocation locCaller = SourceLocation.caller();
		Thread thread = Thread.currentThread();
		if (thread.getThreadGroup() == KernelStore.getKernelThreadGroup()) {
			_bValid = true;
		} else {
			String strFullName = locCaller.getFullClassName();
			boolean bWork = false;
			try {
				Class<?> classCaller = Class.forName(strFullName);
				Module module = classCaller.getModule();
				if (module.isNamed() && module.getName().startsWith("srojak.kernel")) {
					bWork = true;
				}
			} catch (ClassNotFoundException e) {
				OBS_LOG.record(ObsLevel.ERROR, "failed to resolve class \"" + strFullName + "\"");
			}
			_bValid = bWork;
		}
		checkAccess();
	}
	
	private void checkAccess() {
		if (!_bValid) {
			throw new SecurityException("access to privileged operations denied");
		}
	}

	@Override
	public boolean isValid() {
		return _bValid;
	}

	@Override
	public ThreadGroup createThreadGroupUnderKernel(String strName) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		checkAccess();
		return new ThreadGroup(KernelStore.getKernelThreadGroup(), strName);
	}



}
