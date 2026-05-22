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
package srojak.utest.core;

import srojak.core.StringBox;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.tools.EnvTool;

/**
 * @author Stephen
 *
 */
public class PackageClassLocatorTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Java version " + EnvTool.getJavaVersion());
		
		PackageClassLocator loc1 = new PackageClassLocator("srojak.common", "NameToken");
		StringBox boxMessage = new StringBox();
		if (loc1.tryValidate(boxMessage)) {
			System.out.println("validated " + loc1);
		} else {
			System.out.println("validation failed: " + boxMessage);
		}
	}

}
