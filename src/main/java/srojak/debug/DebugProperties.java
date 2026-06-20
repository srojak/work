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
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

import srojak.core.IPropertiesReadOnly;
import srojak.core.events.ActionCompletedEvent;
import srojak.core.events.ActionCompletedListener;
import srojak.core.events.ActionCompletedOriginator;
import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;
import srojak.core.io.PropertiesLoader;
import srojak.core.result.XResult;
/**
 * @author Stephen
 *
 */
public class DebugProperties
		implements IPropertiesReadOnly, DebugPropertyKeys, ActionCompletedOriginator {
	private final SingleEventListenerStore<ActionCompletedListener> _listeners;
	private final Properties _props;
	private boolean _bDiagNewSwitch;
	private boolean _bDiagNewClassOptions;
	
	public DebugProperties() {
		_listeners = new SingleEventListenerList<ActionCompletedListener>();
		_props = new Properties();
		_bDiagNewSwitch = false;
		_bDiagNewClassOptions = false;
	}
	
	private void setFlags() {
		_bDiagNewSwitch = isPropertyValueYesOrTrue(DIAG_NEW_SWITCH);
		_bDiagNewClassOptions = isPropertyValueYesOrTrue(DIAG_NEW_CLASS_OPTIONS);
	}
	
	public boolean isDiagNewSwitchEnabled() {
		return _bDiagNewSwitch;
	}
	
	public boolean isDiagNewClassOptionsEnabled() {
		return _bDiagNewClassOptions;
	}
	
	public XResult loadFromResource(Object objApp, String strName) {
		Objects.requireNonNull(objApp, "objApp");
		ClassLoader loader = objApp.getClass().getClassLoader();
		XResult result = PropertiesLoader.loadFromResource(_props, loader, strName);
		if (result.isValid()) {
			setFlags();
			ActionCompletedEvent event 
				= new ActionCompletedEvent(this, ActionCompletedEvent.ID_FILE_READ);
			_listeners.forEach(ls -> ls.completed(event));
		}
		return result;
	}
	
	public XResult loadFromCurrentDirectory(String strName) {
		Path pathCurrent = Path.of(System.getProperty("user.dir"));
		XResult result = PropertiesLoader.loadFromDirectory(_props, pathCurrent, strName);
		if (result.isValid()) {
			setFlags();
			ActionCompletedEvent event 
				= new ActionCompletedEvent(this, ActionCompletedEvent.ID_FILE_READ);
			_listeners.forEach(ls -> ls.completed(event));
		}
		return result;
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

	@Override
	public void addActionCompletedListener(ActionCompletedListener listener) {
		_listeners.add(listener);
	}

	@Override
	public void removeActionCompletedListener(ActionCompletedListener listener) {
		_listeners.remove(listener);
	}
}
