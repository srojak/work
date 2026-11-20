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
package srojak.mantle.text;

import java.util.List;

/**
 * @author Stephen
 *
 */
public class TextLineTokenizer {
	
	public boolean processLine(String strLine, List<String> listTokens) {
		StringBuilder sbCurrent = null;
		boolean bEscaped = false;
		boolean bQuoted = false;
		int nBracketLevels = 0;
		for (int pos = 0; pos < strLine.length(); pos++) {
			char c = strLine.charAt(pos);
			if (bEscaped) {
				if (sbCurrent == null) {
					sbCurrent = new StringBuilder();
				}
				sbCurrent.append(c);
				bEscaped = false;
				continue;
			}
			if (c == '\\') {
				bEscaped = true;
				continue;
			}
			if (Character.isWhitespace(c)) {
				if (sbCurrent != null) {
					if (bQuoted || nBracketLevels > 0) {
						sbCurrent.append(c);
					} else {
						listTokens.add(sbCurrent.toString());
						sbCurrent = null;
					}
				}
				continue;
			}
			
			switch (c) {
			case '\"':
				if (sbCurrent == null) {
					sbCurrent = new StringBuilder();
					bQuoted = true;
				} else if (bQuoted) {
					listTokens.add(sbCurrent.toString());
					sbCurrent = null;
					bQuoted = false;
				} else {
					sbCurrent.append(c);
				}
				break;
				
			case '[':
				if (sbCurrent == null) {
					sbCurrent = new StringBuilder();
				} else {
					sbCurrent.append(c);
				}
				nBracketLevels++;
				break;
				
			case ']':
				if (sbCurrent != null) {
					if (nBracketLevels <= 1) {
						listTokens.add(sbCurrent.toString());
						sbCurrent = null;
						nBracketLevels = 0;
					} else {
						sbCurrent.append(c);
						nBracketLevels--;
					}
				} else {
					sbCurrent = new StringBuilder();
					sbCurrent.append(c);
				}
				break;
				
			default:
				if (sbCurrent == null) {
					sbCurrent = new StringBuilder();
				}
				sbCurrent.append(c);
				break;
			}
		}
		
		if (sbCurrent != null) {
			listTokens.add(sbCurrent.toString());
			sbCurrent = null;
		}
		
		return !listTokens.isEmpty();
	}
}
