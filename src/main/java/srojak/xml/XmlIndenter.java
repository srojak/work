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
package srojak.xml;

import java.util.function.Consumer;


import srojak.core.collections.TStackDepth;
import srojak.core.tools.StringBuilderTool;
/**
 * @author Stephen
 *
 */
public class XmlIndenter {
	private boolean _bNewLines;
	private int _nIndent;
	private TStackDepth _stack;

	public XmlIndenter(TStackDepth stack) {
		_bNewLines = false;
		_nIndent = 2;
		_stack = stack;		
	}
	
	public boolean isWritingNewLines() {
		return _bNewLines;
	}
	
	public void writeNewLines(boolean bState) {
		_bNewLines = bState;
	}
	
	public int getIndent() {
		return _nIndent;
	}
	
	public void setIndent(int nSpaces) {
		if (nSpaces < 0) {
			throw new IllegalArgumentException("nSpaces < 0");
		}
		_nIndent = nSpaces;
	}
	
	private StringBuilder formIndent() {
		StringBuilder sb = new StringBuilder();
		StringBuilderTool.appendLine(sb);
		int nChars = _stack.size() * _nIndent;
		if (nChars > 0) {
			StringBuilderTool.appendChars(sb, ' ', nChars);
		}
		return sb;
	}
	
	public String writeNewlineIndent() {
		if (_bNewLines) {
			StringBuilder sb = formIndent();
			return sb.toString();
		} else {
			return null;
		}
	}
	
	public void writeNewlineIndent(Consumer<String> consumer) {
		if (_bNewLines) {
			StringBuilder sb = formIndent();
			consumer.accept(sb.toString());
		}		
	}
}
