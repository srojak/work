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
package srojak.xml.stream;

import java.util.*;
import javax.xml.stream.*;

/**
 * @author Stephen
 *
 */
public class XmlStreamEventsDictionary {
	private static final HashMap<String, Integer> _map;
	
	static {
		_map = new HashMap<String, Integer>();
		_map.put("Attribute", XMLStreamConstants.ATTRIBUTE);
		_map.put("Cdata",  XMLStreamConstants.CDATA);
		_map.put("Chars",  XMLStreamConstants.CHARACTERS);
		_map.put("Comment",  XMLStreamConstants.COMMENT);
		_map.put("DTD",  XMLStreamConstants.DTD);
		_map.put("EndDocument",  XMLStreamConstants.END_DOCUMENT);
		_map.put("EndElement",  XMLStreamConstants.END_ELEMENT);
		_map.put("EntityDecl",  XMLStreamConstants.ENTITY_DECLARATION);
		_map.put("EntityRef",  XMLStreamConstants.ENTITY_REFERENCE);
		_map.put("Namespace",  XMLStreamConstants.NAMESPACE);
		_map.put("NotDecl",  XMLStreamConstants.NOTATION_DECLARATION);
		_map.put("ProcInst",  XMLStreamConstants.PROCESSING_INSTRUCTION);
		_map.put("WSpace",  XMLStreamConstants.SPACE);
		_map.put("StartDocument",  XMLStreamConstants.START_DOCUMENT);
		_map.put("StartElement",  XMLStreamConstants.START_ELEMENT);
	}
	
	public int getCodeForName(String strName) {
		Integer intVal = _map.get(strName);
		return intVal == null ? 0 : intVal;
	}
	
	public String getNameForCode(int code) {
		for (Map.Entry<String, Integer> entry : _map.entrySet()) {
			if (entry.getValue() == code) {
				return entry.getKey();
			}
		}
		return "?? (" + code + ")";
	}
}
