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
package srojak.core.reflect;

import java.util.Objects;

import srojak.core.specialized.StringBox;

/**
 * @author Stephen
 *
 */
public class PackageClassLocator
		implements Comparable<PackageClassLocator> {
	private final String _strPackage;
	private final String _strClass;
	private boolean _bValidated;
	
	public PackageClassLocator(String strPackage, String strClass) {
		Objects.requireNonNull(strPackage, "strPackage");
		Objects.requireNonNull(strClass, "strClass");
		if (strPackage.isEmpty())
			throw new IllegalArgumentException("strPackage is empty");
		if (strClass.isEmpty())
			throw new IllegalArgumentException("strClass is empty");
		_strPackage = strPackage;
		_strClass = strClass;
		_bValidated = false;
	}
	
	public PackageClassLocator(Class<?> classLoc) {
		Objects.requireNonNull(classLoc);
		_strPackage = classLoc.getPackageName();
		_strClass = classLoc.getSimpleName();
		_bValidated = true;
	}
	
	public String getPackageName() {
		return _strPackage;
	}
	
	public String getClassName() {
		return _strClass;
	}
	
	public String getFullName() {
		return _strPackage + "." + _strClass;
	}
	
	public boolean isJavaClass() {
		return _strPackage.startsWith("java.") || _strPackage.startsWith("javax.")
				|| _strPackage.startsWith("jdk.");
	}
	
	public boolean isValidated() {
		return _bValidated;
	}
	
	private boolean validateInner() 
			throws ClassNotFoundException {
		if (_bValidated) {
			return true;
		} else {
			Class.forName(getFullName());
			_bValidated = true;
			return true;
		}
	}
	
	public boolean validate()
			throws ClassNotFoundException {
		return validateInner();
	}
	
	public boolean tryValidate(StringBox boxFailure) {
		Objects.requireNonNull(boxFailure, "boxFailure");
		boxFailure.reset();
		boolean bResult = false;
		try {
			bResult = validateInner();
		} catch (ClassNotFoundException ex) {
			boxFailure.setContent(ex.getMessage());
		}
		return bResult;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_strPackage, _strClass);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null)
			return false;
		else if (obj instanceof PackageClassLocator other) {
			return _strPackage.equals(other._strPackage)
					&& _strClass.equals(other._strClass);
		} else
			return false;
	}

	@Override
	public int compareTo(PackageClassLocator o) {
		if (this == o)
			return 0;
		else if (o == null) {
			return 1;
		} else {
			int nResult = _strPackage.compareTo(o._strPackage);
			if (nResult == 0) {
				nResult = _strClass.compareTo(o._strClass);
			}
			return nResult;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("package=");
		sb.append(_strPackage);
		sb.append(", class=");
		sb.append(_strClass);
		return sb.toString();
	}
}
