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

import srojak.core.observe.SourceDetail;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public final class SourceLocationPseudo
		extends SourceLocationBase
		implements SourceLocation {
	private final String _label;
	
	public SourceLocationPseudo(String strLabel) {
		_label = strLabel;
	}

	@Override
	public boolean isReal() {
		return false;
	}

	@Override
	public String getPackageName() {
		return "";
	}

	@Override
	public String getClassName() {
		return "";
	}

	@Override
	public String getMethodName() {
		return "";
	}

	@Override
	public int getLineNumber() {
		return 0;
	}

	@Override
	public String toString(SourceDetail detail) {
		StringBuilder sb = new StringBuilder(CONTENT_LEADER);
		sb.append(_label);
		sb.append(']');
		return sb.toString();
	}

	@Override
	protected void getSimpleContent(StringBuilder sb) {
		sb.append(_label);
	}
}
