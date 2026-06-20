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
package srojak.core.events;

import java.util.Objects;

import srojak.core.Named;
import srojak.core.tools.StringMethods;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class NameAndStateChangeEvent
		extends StateChangeEvent
		implements Named {
	private final String _strName;
	
	public NameAndStateChangeEvent(Object source, String strName, boolean bState) {
		super(source, bState);
		Objects.requireNonNull(strName, "strName");
		_strName = strName;
	}

	@Override
	public String getName() {
		return _strName;
	}

	@Override
	protected void formatData(StringBuilder sb) {
		sb.append(", name = ");
		sb.append(StringMethods.encloseInQuotes(_strName));
		super.formatData(sb);
	}
}

