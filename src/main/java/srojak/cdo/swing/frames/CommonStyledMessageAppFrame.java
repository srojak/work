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
import srojak.cdo.ExtColors;
import srojak.cdo.swing.StyleName;
import srojak.cdo.swing.StyledTextMessageRelay;
import srojak.cdo.swing.event.ActionListenerTextAreaCopy;
import srojak.cdo.swing.event.ActionListenerTextAreaHTMLCopy;
import srojak.cdo.swing.event.ActionListenerTextAreaSelectAll;
import srojak.cdo.swing.panels.ScrollingStyledMessagePanel;
import srojak.cdo.swing.styles.MonospaceTextStyleNames;
import srojak.cdo.swing.styles.NamedStyleMap;
import srojak.cdo.swing.styles.NamedStyleMapReadOnly;
import srojak.mantle.quants.TextBlockSize;

/**
 * @author Stephen
 *
 */
public class CommonStyledMessageAppFrame 
		extends CommonAppFrame 
		implements Runnable, MonospaceTextStyleNames {
	private final NamedStyleMap _mapStyles;
	private final ScrollingStyledMessagePanel _panelText;
	
	/**
	 * @param strAppName
	 */
	public CommonStyledMessageAppFrame(String strAppName, TextBlockSize sizeMessagePanel) {
		super(strAppName);
		Objects.requireNonNull(sizeMessagePanel, "sizeMessagePanel");
		
		// TODO: fix encapsulation problem caused here
		_mapStyles = new NamedStyleMap();
		_panelText = new ScrollingStyledMessagePanel(ScrollingStyledMessagePanel.PANEL_NAME);
		_panelText.setFont(new Font(AWTCommon.FONT_FAMILY_MONOSPACED, Font.PLAIN, 12));
		_panelText.setViewpaneCharSize(sizeMessagePanel);
		_panelText.setEditable(false);
		_panelText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		_panelText.setBackground(Color.WHITE);
		
        addToLowerPanel(_panelText, BorderLayout.CENTER);
	}
	
	protected void createStyles(NamedStyleMap mapStyles) {
        MutableAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setBackground(style, Color.WHITE);
        StyleConstants.setForeground(style, Color.BLACK);
        StyleConstants.setFontFamily(style, AWTCommon.FONT_FAMILY_MONOSPACED);
        StyleConstants.setFontSize(style, 12);
        mapStyles.put(STYLE_MONO_BLACK_12, style);
        
        style = new SimpleAttributeSet();
        StyleConstants.setBackground(style, Color.WHITE);
        StyleConstants.setForeground(style, Color.BLUE);
        StyleConstants.setFontFamily(style, AWTCommon.FONT_FAMILY_MONOSPACED);
        StyleConstants.setFontSize(style, 12);
        mapStyles.put(STYLE_MONO_BLUE_12, style);
        
        style = new SimpleAttributeSet();
        StyleConstants.setBackground(style, Color.WHITE);
        StyleConstants.setForeground(style, Color.RED);
        StyleConstants.setFontFamily(style, AWTCommon.FONT_FAMILY_MONOSPACED);
        StyleConstants.setFontSize(style, 12);
        mapStyles.put(STYLE_MONO_RED_12, style);
        
        style = new SimpleAttributeSet();
        StyleConstants.setBackground(style, Color.WHITE);
        StyleConstants.setForeground(style, Color.MAGENTA);
        StyleConstants.setFontFamily(style, AWTCommon.FONT_FAMILY_MONOSPACED);
        StyleConstants.setFontSize(style, 12);
        mapStyles.put(STYLE_MONO_MAGENTA_12, style);
       
        style = new SimpleAttributeSet();
        StyleConstants.setBackground(style, Color.WHITE);
        StyleConstants.setForeground(style, ExtColors.DARK_GREEN.getWrapped());
        StyleConstants.setFontFamily(style, AWTCommon.FONT_FAMILY_MONOSPACED);
        StyleConstants.setFontSize(style, 12);
        mapStyles.put(STYLE_MONO_DARK_GREEN_12, style);
	}

	@Override
	protected void completeSetup() {
		createStyles(_mapStyles);
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
	
	public NamedStyleMapReadOnly getStyleMap() {
		return _mapStyles;
	}
	
	protected AttributeSet getStyle(StyleName name) {
		return _mapStyles.get(name);
	}

	protected ScrollingStyledMessagePanel getMessagePanel() {
		return _panelText;
	}
	
	protected StyledTextMessageRelay getMessageRelay() {
		return _panelText;
	}
	
	public void relayText(AttributeSet style, String strText) {
    	Objects.requireNonNull(style, "style");
    	Objects.requireNonNull(strText, "strText");
		_panelText.writeln(style, strText);
	}
}
