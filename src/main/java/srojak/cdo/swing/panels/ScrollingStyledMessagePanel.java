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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.IOException;
import java.io.StringWriter;

import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;

import srojak.cdo.TextMessageComponent;
import srojak.core.NameToken;
import srojak.core.field.Lazy;
import srojak.core.observe.ObsLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ScrollingStyledMessagePanel 
		extends ScrollingViewPanel
		implements TextMessageComponent {
	private final JTextPane _paneText;
    private final StyledDocument _docText;
    private final SimpleAttributeSet _styleBase;

    public static final NameToken PANEL_NAME;
	private static final DebugSwitch _swDebugClass;
    private static final Lazy<HTMLEditorKit> _kitHText;

	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = ScrollingMessagePanel.class;
		PANEL_NAME = NameToken.classNameFactory(classThis);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
		_kitHText = new Lazy<HTMLEditorKit>(() -> new HTMLEditorKit());
	}
    
	/**
	 * @param tokenName
	 */
	public ScrollingStyledMessagePanel(NameToken tokenName) {
		super(tokenName);
		_paneText = new JTextPane();
		_docText = _paneText.getStyledDocument();
		_styleBase = new SimpleAttributeSet();
    	postConstruct();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public ScrollingStyledMessagePanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, isDoubleBuffered);
		_paneText = new JTextPane();
		_docText = _paneText.getStyledDocument();
		_styleBase = new SimpleAttributeSet();
    	postConstruct();
	}

	private void postConstruct() {
		setView(_paneText);
		_paneText.setEditable(false);
		StyleConstants.setForeground(_styleBase, getForeground());
		
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	public void setViewpaneCharSize(int nLines, int nColumns) {
		FontMetrics fm = _paneText.getFontMetrics(_paneText.getFont());
		int width = fm.charWidth('m') * nColumns;
		int height = fm.getHeight() * nLines;
		_paneText.setPreferredSize(new Dimension(width, height));
	}
	
	@Override
	public Font getFont() {
		return _paneText.getFont();
	}

	@Override
	public void setFont(Font font) {
		_paneText.setFont(font);
	}
	
	public MutableAttributeSet getStyleContainer() {
		return new SimpleAttributeSet();
	}

	public void writeLine(AttributeSet style, String strText) {
		int nLength = _docText.getLength();
		try {
			_docText.insertString(nLength, strText, style);
			nLength = _docText.getLength();
			_docText.insertString(nLength, "\n", _styleBase);
			
		} catch (BadLocationException e) {
			_swDebugClass.writeException(ObsLevel.WARN, e, false);
		}
	}

	@Override
	public void clearText() {
		int nLength = _docText.getLength();
		if (nLength > 0) {
			try {
				_docText.remove(0, nLength);
			} catch (BadLocationException e) {
				
			}
		}		
	}

	@Override
	public void selectAllText() {
		_paneText.selectAll();
	}

	@Override
	public String getSelectedString() {
		return _paneText.getSelectedText();
	}
	
	public String getSelectedStringAsHTML() {
		int nStart = _paneText.getSelectionStart();
		int nEnd = _paneText.getSelectionEnd();
		if (nStart == nEnd) {
			return "";
		}
		HTMLEditorKit kitEdit = _kitHText.get();
		StringWriter writer = new StringWriter();
		try {
			kitEdit.write(writer, _docText, nStart, nEnd - nStart);
		} catch (IOException e) {
			_swDebugClass.writeException(ObsLevel.WARN, e, false);
		} catch (BadLocationException e) {
			_swDebugClass.writeException(ObsLevel.WARN, e, false);
		}
		return writer.toString();		
	}
}
