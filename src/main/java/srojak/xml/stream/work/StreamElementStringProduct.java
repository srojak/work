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
package srojak.xml.stream.work;

import java.util.Objects;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;

/**
 * @author Stephen
 *
 */
public class StreamElementStringProduct 
		extends StreamElementDeferredProductBase {
	private final String _content;

	public StreamElementStringProduct(QName nameElement, Location locStartElement, String strContent) {
		super(nameElement, locStartElement);
		Objects.requireNonNull(strContent, "strContent");
		_content = strContent;
	}

	public String getContent() {
		return _content;
	}
}
