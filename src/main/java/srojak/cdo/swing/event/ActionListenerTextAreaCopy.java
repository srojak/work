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
package srojak.cdo.swing.event;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

import javax.swing.Icon;

import srojak.cdo.TextMessageComponent;
import srojak.cdo.swing.base.TextAreaAbstractActionBase;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ActionListenerTextAreaCopy
		extends TextAreaAbstractActionBase {

	/**
	 * @param text
	 */
	public ActionListenerTextAreaCopy(TextMessageComponent text) {
		super(text);
	}

	/**
	 * @param text
	 * @param name
	 */
	public ActionListenerTextAreaCopy(TextMessageComponent text, String name) {
		super(text, name);
	}

	/**
	 * @param text
	 * @param name
	 * @param icon
	 */
	public ActionListenerTextAreaCopy(TextMessageComponent text, String name, Icon icon) {
		super(text, name, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        String strText = getTextArea().getSelectedString();
        if (strText != null) {
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            cb.setContents(new StringSelection(strText), null);
        }
	}

}
