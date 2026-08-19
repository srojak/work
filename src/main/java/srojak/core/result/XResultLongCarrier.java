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

import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public class XResultLongCarrier 
		extends XResultCarrierBase 
		implements XResultLong {
	private long _result;
	
	/**
	 * Constructor.
	 */
	public XResultLongCarrier() {
		super(SourceLocation.caller());
		_result = -1L;
	}

	/**
	 * @param source
	 */
	public XResultLongCarrier(SourceLocation source) {
		super(source);
		_result = -1L;
	}

	@Override
	public long getResult() {
		return _result;
	}

	public void setResult(long result) {
		_result = result;
		markValid();
	}
}
