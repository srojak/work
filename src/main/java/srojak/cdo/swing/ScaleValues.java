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
package srojak.cdo.swing;

import srojak.core.collections.KeyedArrayList;
import srojak.core.keys.NamedKey;
import srojak.numerics.vstore.StoreValueDouble;
import srojak.numerics.vstore.StoreValueKeyed;
import srojak.numerics.vstore.collections.NamedDoubleValueStoreAdapter;
import srojak.numerics.vstore.facades.NamedDoubleValueStoreFacade;
import srojak.numerics.vstore.primitives.StoreValueDoubleValidating;

/**
 * @author Stephen
 *
 */
public final class ScaleValues
		extends NamedDoubleValueStoreFacade {

	public static final NamedKey SCALE_HIGH_LIMIT = new NamedKey("ScaleHighLimit");
	public static final NamedKey SCALE_LOW_LIMIT = new NamedKey("ScaleLowLimit");
	public static final NamedKey ZOOM_IN_FACTOR = new NamedKey("ZoomInFactor");
	
	private static final NamedDoubleValueStoreAdapter _store
			= new NamedDoubleValueStoreAdapter(ScaleValues.class, new KeyedArrayList<NamedKey, StoreValueDouble>(3));

	public ScaleValues() {
		super();
	}

	@Override
	protected StoreValueKeyed store() {
		return _store;
	}

	@Override
	protected NamedDoubleValueStoreAdapter storeTyped() {
		return _store;
	}

	@Override
	protected void initializeSelf() {
		_store.define(new StoreValueDoubleValidating(SCALE_HIGH_LIMIT, 16d, v -> v > 1.0d));
		_store.define(new StoreValueDoubleValidating(SCALE_LOW_LIMIT, 0.125d,
				v -> v < 1.0d && v > 0.0d));
		_store.define(new StoreValueDoubleValidating(ZOOM_IN_FACTOR, 2d, v -> v > 1.0d));		
	}
}
