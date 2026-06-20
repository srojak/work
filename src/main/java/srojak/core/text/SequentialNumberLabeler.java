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
package srojak.core.text;

import srojak.core.tools.StringMethods;

/**
 * @author Stephen
 *
 */
public class SequentialNumberLabeler<T> 
		implements SequentialLabeler<T> {
	private final int _nPositions;
	private final int _nOrigin;
	private int _nValue;
	
	public SequentialNumberLabeler(int nPositions, boolean bStartAtOne) {
		if (nPositions < 1) {
			throw new IllegalArgumentException("nPositions must be positive");
		}
		_nPositions = nPositions;
		_nOrigin = bStartAtOne ? 1 : 0;
		_nValue = _nOrigin;
	}

	@Override
	public void reset() {
		_nValue = _nOrigin;
	}

	@Override
	public LabeledEnvelope<T> generateNext(T value) {
		String strLabel = "#" + StringMethods.leftPadToSize(String.valueOf(_nValue), '0', _nPositions);
		_nValue++;
		return new LabeledEnvelope<T>(value, strLabel);
	}

}
