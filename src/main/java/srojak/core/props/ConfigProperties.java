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
package srojak.core.props;

/**
 * @author Stephen
 *
 */
public class ConfigProperties 
		extends PropertySetBase 
		implements ConfigPropertyKeys {

	public static final String PROPERTIES_FILE_NAME = "config.properties";
	public static final int FLAGS_VERBOSE_MODULE_INSTALL = 0x2;
	public static final int FLAGS_VERBOSE_CLASS_FIND = 0x4;
	
	/**
	 * 
	 */
	public ConfigProperties() {
		super();
	}

	@Override
	protected void postLoad() {
		_flags.apply(evalBooleanProperty(VERBOSE_MODULE_INSTALL, false), FLAGS_VERBOSE_MODULE_INSTALL);
		_flags.apply(evalBooleanProperty(VERBOSE_CLASS_FIND, false), FLAGS_VERBOSE_CLASS_FIND);
	}
}
