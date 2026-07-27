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
package srojak.cdo.swing.models;

import java.util.List;

import javax.swing.ListSelectionModel;

import srojak.core.NameIdentifiedAndLabeled;
import srojak.core.containers.NamedAndLabeledInt;

/**
 * @author Stephen
 *
 */
public final class ListSelectChoiceModel 
		extends DefaultNamedChoiceModel {

	public static final String NAME_SINGLE = "single";
	public static final String NAME_SINGLE_INTV = "sintv";
	public static final String NAME_MULTI_INTV = "mintv";
	private static final List<NameIdentifiedAndLabeled> _listChoices;
	
	static {
		_listChoices = List.of(
				new NamedAndLabeledInt(NAME_SINGLE, "Single", ListSelectionModel.SINGLE_SELECTION),
				new NamedAndLabeledInt(NAME_SINGLE_INTV, "Single Interval",
						ListSelectionModel.SINGLE_INTERVAL_SELECTION),
				new NamedAndLabeledInt(NAME_MULTI_INTV, "Multiple Interval",
						ListSelectionModel.MULTIPLE_INTERVAL_SELECTION));
	}
	
	
	/**
	 * 
	 */
	public ListSelectChoiceModel() {
		super();
		super.setChoices(_listChoices);
		super.setSelectionByName(NAME_SINGLE);
	}

}
