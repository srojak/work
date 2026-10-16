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

import srojak.core.NameToken;
import srojak.core.field.SetOnce;
import srojak.core.field.SetOnceConditions;
import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 */
public abstract class StoreValueCalculationBindable<C extends StoreValueKeyed>
		extends StoreValueCalculationBase {
	private final SetOnce<C> _boundCollection;	
	
	/**
	 * @param valuesDependentOn
	 */
	public StoreValueCalculationBindable(NamedKey dependentCar, NamedKey[] dependentCdr) {
		super(dependentCar, dependentCdr);
		_boundCollection = new SetOnce<C>(NameToken.factory("BoundCollection"), SetOnceConditions.DEFAULT);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void bindTo(StoreValueKeyed collection) {
		_boundCollection.set((C) collection);
		
	}

	public C getBoundCollection() {
		return _boundCollection.get();
	}
}
