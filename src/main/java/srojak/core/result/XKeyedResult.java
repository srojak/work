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
package srojak.core.result;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class XKeyedResult {
	private final XResultKey _key;
	private final XResult _result;

	/**
	 * @param strName
	 */
	public XKeyedResult(XResultKey key, XResult result) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(result, "result");
		_key = key;
		_result = result;
	}
	
	public XResultKey key() {
		return _key;
	}

	public XResult result() {
		return _result;
	}
	
	public boolean isValid() {
		return _result.isValid();
	}
}
