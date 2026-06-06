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
package srojak.debug;

import java.nio.file.Path;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

import srojak.core.IPropertiesReadOnly;
import srojak.core.io.PropertiesLoader;
import srojak.core.specialized.StringBox;
/**
 * @author Stephen
 *
 */
public class DebugProperties
		implements IPropertiesReadOnly {
	private final Properties _props;
	private boolean _bLoaded;
	
	public DebugProperties() {
		_props = new Properties();
		_bLoaded = false;
	}
	
	public boolean isLoaded() {
		return _bLoaded;
	}
	
	public boolean tryLoadFromResource(Object objApp, String strName) {
		Objects.requireNonNull(objApp, "objApp");
		ClassLoader loader = objApp.getClass().getClassLoader();
		return PropertiesLoader.tryLoadFromResource(_props, loader, strName, null);
	}
	
	public void loadFromCurrentDirectory(String strName)
			throws IOException
	{
		Path pathCurrent = Path.of(System.getProperty("user.dir"));
		PropertiesLoader.loadFromDirectory(_props, pathCurrent, strName);
	}
	
	public boolean tryLoadFromCurrentDirectory(String strName, StringBox boxFailure) {
		Path pathCurrent = Path.of(System.getProperty("user.dir"));
		return PropertiesLoader.tryLoadFromDirectory(_props, pathCurrent, strName, boxFailure);
	}

	@Override
	public String getProperty(String key) {
		return _props.getProperty(key);
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		return _props.getProperty(key, defaultValue);
	}

	@Override
	public Set<String> stringPropertyNames() {
		return _props.stringPropertyNames();
	}
	
	public boolean evalProperty(String strKey, Predicate<String> predicate) {
		String strValue = _props.getProperty(strKey);
		if (strValue == null) {
			return false;
		} else {
			return predicate.test(strValue);
		}
	}
	
	public boolean isPropertyValueYesOrTrue(String strKey) {
		String strValue = _props.getProperty(strKey);
		if (strValue == null) {
			return false;
		} else {
			return strValue.equalsIgnoreCase("yes") || strValue.equalsIgnoreCase("true");
		}
	}
}
