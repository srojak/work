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

import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

import srojak.core.events.ActionCompletedEvent;
import srojak.core.events.ActionCompletedListener;
import srojak.core.events.ActionCompletedOriginator;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.result.XResult;

/**
 * @author Stephen
 *
 */
public abstract class PropertySetBase
		implements PropertiesReadOnly, ActionCompletedOriginator {
	protected final Properties _props;
	protected final CommonEventListenerStore _listeners;
	
	protected PropertySetBase() {
		_props = new Properties();
		_listeners = new CommonEventListenerList();
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
	public Set<String> getAllPropertyNames() {
		return _props.stringPropertyNames();
	}
	
	protected boolean evalProperty(String strKey, Predicate<String> predicate) {
		String strValue = _props.getProperty(strKey);
		if (strValue == null) {
			return false;
		} else {
			return predicate.test(strValue);
		}
	}
	
	protected boolean evalBooleanProperty(String strKey, boolean bDefault) {
		String strValue = _props.getProperty(strKey);
		if (strValue == null) {
			return bDefault;
		} else if (bDefault) {
			return strValue.equalsIgnoreCase("no") || strValue.equalsIgnoreCase("false");
		} else {
			return strValue.equalsIgnoreCase("yes") || strValue.equalsIgnoreCase("true");
		}
	}
	
	protected abstract void postLoad();
	
	public XResult loadFromResource(Object objApp, String strName) {
		Objects.requireNonNull(objApp, "objApp");
		ClassLoader loader = objApp.getClass().getClassLoader();
		XResult result = PropertiesLoader.loadFromResource(_props, loader, strName);
		if (result.isValid()) {
			postLoad();
			ActionCompletedEvent event 
				= new ActionCompletedEvent(this, ActionCompletedEvent.ID_FILE_READ);
			_listeners.forEach(ActionCompletedListener.class, ls -> ls.completed(event));
		}
		return result;
	}
	
	public XResult loadFromCurrentDirectory(String strName) {
		Path pathCurrent = Path.of(System.getProperty("user.dir"));
		XResult result = PropertiesLoader.loadFromDirectory(_props, pathCurrent, strName);
		if (result.isValid()) {
			postLoad();
			ActionCompletedEvent event 
				= new ActionCompletedEvent(this, ActionCompletedEvent.ID_FILE_READ);
			_listeners.forEach(ActionCompletedListener.class, ls -> ls.completed(event));
		}
		return result;
	}
	
	public XResult loadFrom(Path pathDir, String strName) {
		Objects.requireNonNull(pathDir, "pathDir");
		Objects.requireNonNull(strName, "strName");
		XResult result = PropertiesLoader.loadFromDirectory(_props, pathDir, strName);
		if (result.isValid()) {
			postLoad();
			ActionCompletedEvent event 
				= new ActionCompletedEvent(this, ActionCompletedEvent.ID_FILE_READ);
			_listeners.forEach(ActionCompletedListener.class, ls -> ls.completed(event));
		}
		return result;
	}

	@Override
	public void addActionCompletedListener(ActionCompletedListener listener) {
		_listeners.add(ActionCompletedListener.class, listener);
	}

	@Override
	public void removeActionCompletedListener(ActionCompletedListener listener) {
		_listeners.remove(ActionCompletedListener.class, listener);
	}
}
