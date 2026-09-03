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
package srojak.cdo.swing.components;

import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Optional;

import srojak.cdo.swing.functional.EnumValueRenderer;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class EnumerationComboBox<E extends Enum<E>> 
		extends CommonItemComboBox<E> {

	/**
	 * @param tokenName
	 * @param data
	 */
	public EnumerationComboBox(NameToken tokenName, Collection<E> data) {
		super(tokenName, data);
		postConstruct();
	}

	public EnumerationComboBox(NameToken tokenName, Class<E> classEnum) {
		super(tokenName, EnumSet.allOf(classEnum));
		postConstruct();
	}
	
	private void postConstruct() {
		Optional<E> widest = getDataAsStream().max(Comparator.comparingInt(e -> e.name().length()));
		if (widest.isPresent()) {
			setPrototypeDisplayValue(widest.get());
		}
		super.setRenderer(new EnumValueRenderer<E>());
	}
}
