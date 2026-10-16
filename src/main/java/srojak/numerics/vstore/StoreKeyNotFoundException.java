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
package srojak.numerics.vstore;

import srojak.core.keys.ClassKey;
import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 */
public class StoreKeyNotFoundException
		extends RuntimeException {
	private final ClassKey _key;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4600062079431588752L;

	/**
	 * @param message
	 */
	public StoreKeyNotFoundException(ClassKey key, String message) {
		super(message);
		_key = key;
	}
	
	public ClassKey getClassKey() {
		return _key;
	}
}
