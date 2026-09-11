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
package srojak.cdo.swing.functional;

import java.awt.Color;
import java.util.Objects;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import srojak.cdo.swing.StyledTextMessageRelay;
import srojak.core.TextMessageRelay;

/**
 * @author Stephen
 *
 */
public class PlainTextMessageAdapter
		implements TextMessageRelay {
	private final StyledTextMessageRelay _relayStyled;
	private final AttributeSet _style;
	
	public PlainTextMessageAdapter(AttributeSet style, StyledTextMessageRelay relayStyled) {
		Objects.requireNonNull(style, "style");
		Objects.requireNonNull(relayStyled, "relayStyled");
		_style = style;
		_relayStyled = relayStyled;		
	}

	public PlainTextMessageAdapter(StyledTextMessageRelay relayStyled) {
		Objects.requireNonNull(relayStyled, "relayStyled");
		_relayStyled = relayStyled;
		MutableAttributeSet styleOrdinary = new SimpleAttributeSet();
		StyleConstants.setForeground(styleOrdinary, Color.BLACK);
		_style = styleOrdinary;
	}

	@Override
	public void writeln(String strText) {
		_relayStyled.writeln(_style, strText);
	}

	@Override
	public void flush() {

	}

}
