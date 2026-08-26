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
package srojak.debug.config;

import javax.xml.namespace.QName;

/**
 * @author Stephen
 *
 */
public interface DebugConfigNames {

	public static final String FILE_SWITCHES = "switches.xml";

	public static final QName ELEMENT_SWITCHES = new QName("DebugSwitches");
	public static final QName ELEMENT_CTRLSET = new QName("SwitchControlSet");
	public static final QName ELEMENT_PACKAGE = new QName("Package");
	public static final QName ELEMENT_CLASS = new QName("Class");
	public static final QName ELEMENT_SUBJECT = new QName("Subject");
	public static final QName ELEMENT_OPTION = new QName("Option");
	
	public static final QName ATTRIB_NAME = new QName("name");
	public static final QName ATTRIB_LEVEL = new QName("level");
	public static final QName ATTRIB_LOCS = new QName("locs");
	public static final QName ATTRIB_CASCADE = new QName("cascade");
	public static final QName ATTRIB_VALUE = new QName("value");
}
