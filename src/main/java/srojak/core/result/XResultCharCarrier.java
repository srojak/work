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

import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public class XResultCharCarrier 
		extends XResultCarrierBase 
		implements XResultChar {
	private char _result;
	
	/**
	 * Constructor.
	 */
	public XResultCharCarrier(ObservedActivity activity) {
		super(SourceLocation.caller(), activity);
		_result = '\0';
	}

	/**
	 * @param source
	 */
	public XResultCharCarrier(SourceLocation source, ObservedActivity activity) {
		super(source, activity);
		_result = '\0';
	}

	@Override
	public char getResult() {
		return _result;
	}

	public void setResult(char result) {
		_result = result;
		markValid();
	}
}
