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

import srojak.core.keys.NamedKey;
import srojak.core.reflect.PackageClassLocator;
import srojak.valuestore.GlobalStoreDoubleFacadeBase;
import srojak.valuestore.StoreValueKeyed;
import srojak.valuestore.collections.StoreValueDoubleMap;
import srojak.valuestore.values.StoreValueDoubleValidating;

/**
 * @author Stephen
 *
 */
public class GuiDoubleStore 
		extends GlobalStoreDoubleFacadeBase {
	
	public static final NamedKey SCALE_HIGH_LIMIT = new NamedKey("ScaleHighLimit");
	public static final NamedKey SCALE_LOW_LIMIT = new NamedKey("ScaleLowLimit");
	public static final NamedKey ZOOM_IN_FACTOR = new NamedKey("ZoomInFactor");

	/**
	 * 
	 */
	public GuiDoubleStore() {
		super(GuiDoubleStore.class);
	}

	@Override
	protected StoreValueKeyed initializeStore(PackageClassLocator locator) {
		StoreValueDoubleMap map = new StoreValueDoubleMap(locator);
		map.define(new StoreValueDoubleValidating(SCALE_HIGH_LIMIT, 16d, v -> v > 1.0d));
		map.define(new StoreValueDoubleValidating(SCALE_LOW_LIMIT, 0.125d,
				v -> v < 1.0d && v > 0.0d));
		map.define(new StoreValueDoubleValidating(ZOOM_IN_FACTOR, 2d, v -> v > 1.0d));
		return map;
	}

}
