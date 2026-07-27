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
package srojak.cdo.swing.functional;

import java.util.Objects;

import srojak.cdo.swing.DataComponent;
import srojak.cdo.swing.DxButtonModelFacadeBearing;
import srojak.cdo.swing.DxButtonModelPublisher;
import srojak.cdo.swing.collections.ButtonModelFacadeMap;
import srojak.mantle.Receptor;

/**
 * @author Stephen
 *
 */
public class OrchReceptor<T extends DataComponent>
		extends Receptor<T> {
	private final ButtonModelFacadeMap _mapButtonModels;
	
	public OrchReceptor(Class<T> classValue, ButtonModelFacadeMap mapButtonModels) {
		super(classValue);
		Objects.requireNonNull(mapButtonModels, "mapButtonModels");
		_mapButtonModels = mapButtonModels;
	}

	@Override
	protected void afterReceiving(T value) {
		super.afterReceiving(value);
		if (value instanceof DxButtonModelFacadeBearing facadeBearing) {
			_mapButtonModels.addAll(facadeBearing.getButtonModelList());
		} else if (value instanceof DxButtonModelPublisher pub) {
			_mapButtonModels.addAll(pub.getButtonModelList());
		}
	}
}
