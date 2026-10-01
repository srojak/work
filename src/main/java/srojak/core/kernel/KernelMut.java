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
package srojak.core.kernel;

import java.util.Objects;

import srojak.core.kernel.priv.KernelStore;
import srojak.core.observe.ObservationWriter;

/**
 * @author Stephen
 *
 */
public class KernelMut {

	public static void setLogWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		KernelStore.OUTPUT.setLogWriter(writer);
	}
	
	public static void addOnShutdownAction(Runnable action) {
		Objects.requireNonNull(action, "action");
		KernelStore.addShutdownAction(action);
	}
	
}
