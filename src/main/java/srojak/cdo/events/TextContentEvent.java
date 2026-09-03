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
package srojak.cdo.events;

import java.awt.AWTEvent;

/**
 * @author Stephen
 *
 */
public class TextContentEvent 
		extends AWTEvent 
		implements CDOEventID {
	private final int _idRef;
	private final String _text;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7997696874224657159L;

	/**
	 * @param source
	 * @param id
	 */
	public TextContentEvent(Object source, int idRef, String strText) {
		super(source, TEXT_CONTENT);
		_idRef = idRef;
		_text = strText;
	}
	
	public int getReferent() {
		return _idRef;
	}

	public String getText() {
		return _text;
	}
	
	public boolean isTextNullOrEmpty() {
		return _text == null || _text.isEmpty();
	}
}
