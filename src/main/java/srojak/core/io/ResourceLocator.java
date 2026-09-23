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
package srojak.core.io;

import java.io.InputStream;
import java.util.Objects;

import srojak.core.Named;

/**
 * @author Stephen
 *
 */
public class ResourceLocator
		implements Named {
	private final Class<?> _classOwner;
	private final String _path;

	/**
	 * 
	 */
	public ResourceLocator(Class<?> classOwner, String strPath) {
		Objects.requireNonNull(classOwner, "classOwner");
		Objects.requireNonNull(strPath, "strPath");
		if (strPath.isBlank()) {
			throw new IllegalArgumentException("strPath is blank");
		}
		_classOwner = classOwner;
		_path = strPath;
	}
	
	public InputStream getResourceStream() {
		return _classOwner.getClassLoader().getResourceAsStream(_path);
	}

	@Override
	public String getName() {
		String strPackage = _classOwner.getPackageName();
		return strPackage + "/" + _path;
	}

}
