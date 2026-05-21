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
package srojak.core.impl;

import srojak.core.NameTokenBase;

/**
 * @author Stephen
 *
 */
public final class NameTokenFixed 
		extends NameTokenBase {

	/**
	 * @param strName
	 */
	public NameTokenFixed(String strName) {
		super(strName);
	}

	@Override
	protected int getSeries() {
		return 1;
	}

	@Override
	protected String getLeaderTag() {
		return "token-fixed";
	}
}
