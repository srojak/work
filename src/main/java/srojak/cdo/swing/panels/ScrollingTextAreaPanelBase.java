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
package srojak.cdo.swing.panels;

import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.Document;

import srojak.cdo.TextMessageComponent;
import srojak.core.NameToken;
import srojak.mantle.quants.TextBlockSize;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public abstract class ScrollingTextAreaPanelBase
		extends ScrollingViewPanel
		implements TextMessageComponent {
    protected final JTextArea _areaText;
    protected final Document _textDocument;

	/**
	 * @param tokenName
	 */
	public ScrollingTextAreaPanelBase(NameToken tokenName, TextBlockSize size) {
		super(tokenName);
		Objects.requireNonNull(size, "size");
        _areaText = new JTextArea(size.lines(), size.columns());
        _textDocument = _areaText.getDocument();
    	postConstruct();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public ScrollingTextAreaPanelBase(NameToken tokenName, boolean isDoubleBuffered, TextBlockSize size) {
		super(tokenName, isDoubleBuffered);
		Objects.requireNonNull(size, "size");
        _areaText = new JTextArea(size.lines(), size.columns());
        _textDocument = _areaText.getDocument();
    	postConstruct();
	}
	
	private void postConstruct() {
    	setView(_areaText);
		
    	setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    	setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	@Override
	public void clearText() {
        _areaText.setText("");
	}

	@Override
	public void selectAllText() {
    	_areaText.selectAll();
	}

	@Override
	public String getSelectedString() {
        return _areaText.getSelectedText();
	}

}
