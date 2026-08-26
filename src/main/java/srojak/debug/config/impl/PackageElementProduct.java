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
package srojak.debug.config.impl;

import java.util.Objects;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;

import srojak.xml.stream.StreamElementDeferredProductBase;

/**
 * @author Stephen
 *
 */
public class PackageElementProduct 
		extends StreamElementDeferredProductBase {
	private final String _namePackage;

	/**
	 * @param nameElement
	 * @param locStartElement
	 */
	public PackageElementProduct(QName nameElement, Location locStartElement, String strPackageName) {
		super(nameElement, locStartElement);
		Objects.requireNonNull(strPackageName, "strPackageName");
		_namePackage = strPackageName;
	}

	public String getPackageName() {
		return _namePackage;
	}
}
