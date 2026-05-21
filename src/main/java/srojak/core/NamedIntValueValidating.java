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
package srojak.core;

import java.util.Objects;
import java.util.function.IntPredicate;

/**
 * @author Stephen
 *
 */
public class NamedIntValueValidating
		extends NamedIntValue {
	private final IntPredicate _predValid;
	
	private static void faultInvalid(String strName, String strValueName) {
		throw new IllegalArgumentException("invalid " + strValueName + " for " + strName);
	}
	
	/**
	 * @param strName
	 * @param valueInitial
	 */
	public NamedIntValueValidating(String strName, int valueInitial, IntPredicate validator) {
		super(strName, valueInitial);
		Objects.requireNonNull(validator, "validator");
		_predValid = validator;
		if (!validator.test(valueInitial)) {
			faultInvalid(strName, "valueInitial");
		}
	}

	@Override
	protected void validate(int value) {
		if (!_predValid.test(value)) {
			faultInvalid(getName(), "value");
		}
	}
}
