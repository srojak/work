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

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.cdo.swing.DxButtonModelFacadeBearing;
import srojak.cdo.swing.models.DxButtonModelFacade;
import srojak.core.containers.Receptor;

/**
 * @author Stephen
 *
 */
public class OrchReceptor<T>
		extends Receptor<T> {
	private final Consumer<Collection<DxButtonModelFacade>> _consumerButtonModels;
	
	public OrchReceptor(Consumer<Collection<DxButtonModelFacade>> consumerButtonModels) {
		super();
		Objects.requireNonNull(consumerButtonModels, "consumerButtonModels");
		_consumerButtonModels = consumerButtonModels;
	}

	@Override
	protected void afterReceiving(T value) {
		super.afterReceiving(value);
		if (value instanceof DxButtonModelFacadeBearing facadeBearing) {
			_consumerButtonModels.accept(facadeBearing.getButtonModelList());
		}
	}
}
