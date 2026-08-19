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

import srojak.core.NameToken;
import srojak.core.field.SetOnce;
import srojak.core.field.SetOnceConditions;

/**
 * @author Stephen
 *
 */
public abstract class UnitTestCondition {
	private final SetOnce<String> _strCondition;
	
	private static final NameToken _tokenCondition = NameToken.factory("UnitTestCondition");
	
	public UnitTestCondition() {
		_strCondition = new SetOnce<String>(_tokenCondition, SetOnceConditions.DEFAULT);
	}

	public UnitTestCondition(String strCondition) {
		Objects.requireNonNull(strCondition, "strCondition");
		_strCondition = new SetOnce<String>(_tokenCondition, SetOnceConditions.DEFAULT);
		_strCondition.set(strCondition);
	}
	
	protected void setConditionDesc(String strCondition) {
		_strCondition.set(strCondition);
	}
	
	public String getConditionDesc() {
		return _strCondition.get();
	}
}
