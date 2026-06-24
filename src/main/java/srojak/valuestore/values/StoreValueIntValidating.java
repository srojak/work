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
package srojak.valuestore.values;

import java.util.Objects;
import java.util.function.IntPredicate;

import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 */
public class StoreValueIntValidating
		extends StoreValueIntInstance {
	private final IntPredicate _predValid;

	/**
	 * @param key
	 * @param valueInitial
	 */
	public StoreValueIntValidating(NamedKey key, int valueInitial, IntPredicate validator) {
		super(key, valueInitial);
		Objects.requireNonNull(validator, "validator");
		_predValid = validator;
		if (!validator.test(valueInitial)) {
			faultInvalid(key, "valueInitial");
		}
	}

	@Override
	protected void validate(int value) {
		if (!_predValid.test(value)) {
			faultInvalid(getKey(), "value");
		}
	}
}
