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
package srojak.core.text;

import java.util.Objects;
import java.util.function.Function;

import srojak.core.TextRepresentation;

/**
 * @author Stephen
 *
 */
public class TextRepresentFunction<T>
		implements TextRepresentation {
	private final Function<T, String> _fnPresent;
	
	public TextRepresentFunction(Function<T, String> fnPresent) {
		Objects.requireNonNull(fnPresent, "fnPresent");
		_fnPresent = fnPresent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getTextFor(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return _fnPresent.apply((T) obj);
		}
	}

}
