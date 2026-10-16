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
package srojak.core.observe.text;

import java.util.Objects;
import java.util.function.Function;

import srojak.core.data.DataPassThrough;

/**
 * @author Stephen
 *
 */
public class TextObservationWithPassThrough 
		extends TextObservationBase {
	private final Function<DataPassThrough, String> _function;
	private final DataPassThrough _passThrough;

	/**
	 * 
	 */
	public TextObservationWithPassThrough(Function<DataPassThrough, String> fnMessage,
			DataPassThrough passThrough) {
		Objects.requireNonNull(fnMessage, "fnMessage");
		Objects.requireNonNull(passThrough, "passThrough");
		_function = fnMessage;
		_passThrough = passThrough;
		
	}

	@Override
	protected void resolveTo(StringBuilder sb) {
		sb.append(_function.apply(_passThrough));
	}

}
