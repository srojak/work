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
package srojak.cdo;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import srojak.core.tools.ArrayMethods;

/**
 * @author Stephen
 *
 *
 * in Powershell, use this line to read clipboard:
 * 	Get-Clipboard -Format Text -TextFormatType Html
 */
public class HtmlSelection 
		implements Transferable, ClipboardOwner {
	private final String _html;
	private final String _text;
	
	private static final DataFlavor _flavorHtml;
	private static final DataFlavor[] ALL_FLAVORS;
	
	static {
		_flavorHtml = new DataFlavor("text/html; charset=utf-8", "HTML Text");
		DataFlavor[] flavors = { _flavorHtml, DataFlavor.stringFlavor };
		ALL_FLAVORS = flavors;
	}
	
	private static String reduceToText(String strHtml) {
		// crude but start here
		return strHtml.replaceAll("<[^>]*>", "");
	}
	
	public HtmlSelection(String strHyperText, String strText) {
		Objects.requireNonNull(strHyperText, "strHyperText");
		Objects.requireNonNull(strHyperText, "strText");
		_html = strHyperText;
		_text = strText;
	}
	
	public HtmlSelection(String strHyperText) {
		Objects.requireNonNull(strHyperText, "strHyperText");
		_html = strHyperText;
		_text = reduceToText(_html);
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// no action required
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return ALL_FLAVORS;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return ArrayMethods.equalsAny(ALL_FLAVORS, flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) 
			throws UnsupportedFlavorException, IOException {
		if (flavor.equals(_flavorHtml)) {
			return new ByteArrayInputStream(_html.getBytes(StandardCharsets.UTF_8));
		} else if (flavor.equals(DataFlavor.stringFlavor)) {
			return _text;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

}
