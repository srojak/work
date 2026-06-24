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
package srojak.valuestore.values;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.valuestore.StoreValueKeyed;

/**
 * @author Stephen
 *
 */
public abstract class StoreValueCalculationBase {
	private final NamedKey[] _depends;

	protected StoreValueCalculationBase(NamedKey dependentCar, NamedKey[] dependentCdr) {
		Objects.requireNonNull(dependentCar, "valueFirstDepenedent");
		Objects.requireNonNull(dependentCdr, "valuesDependentOn");
		_depends = new NamedKey[dependentCdr.length + 1];
		_depends[0] = dependentCar;
		System.arraycopy(dependentCdr, 0, _depends, 1, dependentCdr.length);
	}
	
	/**
	 * An array of all other keys for values on which this calculation is dependent.
	 * @return The array of other keys for values on which this calculation is dependent.
	 */
	public NamedKey[] getDependencies() {
		return _depends;
	}
	
	public abstract void bindTo(StoreValueKeyed collection);
}
