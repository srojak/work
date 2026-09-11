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
package srojak.cdo.swing.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import srojak.cdo.AWTCommon;
import srojak.cdo.swing.StyleName;
import srojak.cdo.swing.StyledTextMessageRelay;
import srojak.cdo.swing.collections.NamedStyleMap;
import srojak.cdo.swing.event.ActionListenerTextAreaCopy;
import srojak.cdo.swing.event.ActionListenerTextAreaHTMLCopy;
import srojak.cdo.swing.event.ActionListenerTextAreaSelectAll;
import srojak.cdo.swing.panels.ScrollingStyledMessagePanel;
import srojak.mantle.quants.TextBlockSize;

/**
 * @author Stephen
 *
 */
public class CommonStyledMessageAppFrame 
		extends CommonAppFrame 
		implements Runnable {
	protected final NamedStyleMap _mapStyles;
	private final ScrollingStyledMessagePanel _panelText;
	
	protected static final StyleName STYLE_MONO_BLACK_12 = StyleName.makeKey("MonoBlack12");
	protected static final StyleName STYLE_MONO_BLUE_12 = StyleName.makeKey("MonoBlue12");
	protected static final StyleName STYLE_MONO_RED_12 = StyleName.makeKey("MonoRed12");

	/**
	 * @param strAppName
	 */
	public CommonStyledMessageAppFrame(String strAppName, TextBlockSize sizeMessagePanel) {
		super(strAppName);
		Objects.requireNonNull(sizeMessagePanel, "sizeMessagePanel");
		
		_mapStyles = new NamedStyleMap();
		_panelText = new ScrollingStyledMessagePanel(ScrollingStyledMessagePanel.PANEL_NAME);
		_panelText.setFont(new Font(AWTCommon.FONT_FAMILY_MONOSPACED, Font.PLAIN, 12));
		_panelText.setViewpaneCharSize(sizeMessagePanel);
		_panelText.setEditable(false);
		_panelText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		_panelText.setBackground(Color.WHITE);
		
        addToLowerPanel(_panelText, BorderLayout.CENTER);
	}
	
	protected void createStyles() {
        MutableAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setBackground(style, Color.WHITE);
        StyleConstants.setForeground(style, Color.BLACK);
        StyleConstants.setFontFamily(style, AWTCommon.FONT_FAMILY_MONOSPACED);
        StyleConstants.setFontSize(style, 12);
        _mapStyles.put(STYLE_MONO_BLACK_12, style);
        
        style = new SimpleAttributeSet();
        StyleConstants.setBackground(style, Color.WHITE);
        StyleConstants.setForeground(style, Color.BLUE);
        StyleConstants.setFontFamily(style, AWTCommon.FONT_FAMILY_MONOSPACED);
        StyleConstants.setFontSize(style, 12);
        _mapStyles.put(STYLE_MONO_BLUE_12, style);
        
        style = new SimpleAttributeSet();
        StyleConstants.setBackground(style, Color.WHITE);
        StyleConstants.setForeground(style, Color.RED);
        StyleConstants.setFontFamily(style, AWTCommon.FONT_FAMILY_MONOSPACED);
        StyleConstants.setFontSize(style, 12);
        _mapStyles.put(STYLE_MONO_RED_12, style);
	}

	@Override
	protected void completeSetup() {
		createStyles();
		super.completeSetup();
	}

	@SuppressWarnings("serial")
	protected void addTextMenu() {
		JMenu menu = new JMenu("Text");
		addMenu(menu);

		// TODO: improve listeners
		JMenuItem itemMenu = new JMenuItem("Select All");
		menu.add(itemMenu);
		itemMenu.addActionListener(new ActionListenerTextAreaSelectAll(_panelText));

		itemMenu = new JMenuItem("Copy as HTML");
		menu.add(itemMenu);
		itemMenu.addActionListener(new ActionListenerTextAreaHTMLCopy(_panelText));

		itemMenu = new JMenuItem("Copy as Text");
		menu.add(itemMenu);
		itemMenu.addActionListener(new ActionListenerTextAreaCopy(_panelText));

		menu.addSeparator();
		itemMenu = new JMenuItem("Clear");
		menu.add(itemMenu);
		itemMenu.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_panelText.clearText();
			}

		});
	}

	protected ScrollingStyledMessagePanel getMessagePanel() {
		return _panelText;
	}
	
	protected StyledTextMessageRelay getMessageRelay() {
		return _panelText;
	}
	
	protected void relayText(AttributeSet style, String strText) {
    	Objects.requireNonNull(style, "style");
    	Objects.requireNonNull(strText, "strText");
		_panelText.writeln(style, strText);
	}
}
