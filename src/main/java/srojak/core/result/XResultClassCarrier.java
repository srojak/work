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

import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public class XResultClassCarrier 
		extends XResultCarrierBase 
		implements XResultClass {
	private Class<?> _result;
	
	/**
	 * Constructor.
	 * @param activity
	 */
	public XResultClassCarrier(ObservedActivity activity) {
		super(SourceLocation.caller(), activity);
	}

	/**
	 * @param source
	 * @param activity
	 */
	public XResultClassCarrier(SourceLocation source, ObservedActivity activity) {
		super(source, activity);
		_result = null;
	}

	@Override
	public Class<?> getResult() {
		// TODO Auto-generated method stub
		return _result;
	}

	public void setResult(Class<?> result) {
		Objects.requireNonNull(result, "result");
		_result = result;
		markValid();
	}

	@Override
	protected void buildValidString(StringBuilder sb) {
		sb.append(", class=");
		sb.append(_result.getName());
	}
}
