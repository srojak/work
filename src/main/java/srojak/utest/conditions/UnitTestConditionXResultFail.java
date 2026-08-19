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
package srojak.utest.conditions;

import java.util.Objects;

import srojak.core.result.XResult;
import srojak.utest.UnitTestConditionXResult;

/**
 * @author Stephen
 *
 */
public class UnitTestConditionXResultFail 
		extends UnitTestCondition
		implements UnitTestConditionXResult {
	private final Class<?> _classExcept;

	/**
	 * @param strCondition
	 */
	public UnitTestConditionXResultFail(Class<?> classException) {
		super();
		Objects.requireNonNull(classException, "classException");
		if (!Exception.class.isAssignableFrom(classException)) {
			throw new IllegalArgumentException("classException is not an exception");
		}
		_classExcept = classException;
		setConditionDesc("Result_Caught_" + classException.getSimpleName());
	}

	@Override
	public boolean test(XResult actual) {
		if (actual.isValid())
			return false;
		Exception exc = actual.getException();
		if (exc != null) {
			if (_classExcept.isAssignableFrom(exc.getClass())) {
				return true;
			}
		}
		return false;
	}

}
