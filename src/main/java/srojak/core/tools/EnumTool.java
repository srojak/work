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
package srojak.core.tools;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Stephen
 *
 */
public class EnumTool<E extends Enum<?>> {
	private final E[] _values;
	
	public EnumTool(Class<E> classEnum) {
		Objects.requireNonNull(classEnum, "classEnum");
		_values = classEnum.getEnumConstants();
	}

	public E findFirst(Predicate<? super E> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		return Arrays.stream(_values).filter(predicate).findFirst().orElse(null);
	}
	
	public E findFirstByName(Predicate<String> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		return Arrays.stream(_values).filter(v -> predicate.test(v.name())).findFirst().orElse(null);
	}
}
