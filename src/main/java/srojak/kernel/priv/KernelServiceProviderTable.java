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
package srojak.kernel.priv;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.core.excepts.InvalidClassDefinitionException;
import srojak.core.excepts.InvalidOperationException;
import srojak.core.tools.ArrayMethods;
import srojak.core.tools.ListMethods;
import srojak.kernel.KernelServiceException;
import srojak.kernel.services.AppKernelService;
import srojak.kernel.services.DependsOnService;
import srojak.kernel.services.KernelService;
import srojak.kernel.services.KernelServiceInternal;

/**
 * @author Stephen
 *
 */
public class KernelServiceProviderTable {
	private final HashMap<String, Entry> _map;
	private final LinkedList<Entry> _queueStart;
	private final LinkedList<KernelServiceInternal> _stackShutdown;
	private final LinkedList<String> _stackResolve;
	private final KernelCentralStoreAccessor _storeCentral;
	
	public KernelServiceProviderTable() {
		_map = new HashMap<String, Entry>();
		_queueStart = new LinkedList<Entry>();
		_stackShutdown = new LinkedList<KernelServiceInternal>();
		_stackResolve = new LinkedList<String>();
		_storeCentral = new KernelCentralStoreAccessor();
	}
	
	public void define(Class<?> classProvider) {
		Objects.requireNonNull(classProvider, "classProvider");
		Entry entry = new Entry(classProvider);
		_map.put(entry.name(), entry);
		if (!entry.isLazyLoad()) {
			_queueStart.addLast(entry);
		}
	}
	
	public void resolvePending() 
			throws KernelServiceException {
		while (!_queueStart.isEmpty()) {
			Entry entry = _queueStart.pop();
			entry.getService(this);
		}
	}
	
	public void defineAndResolve(Class<?> classProvider) throws KernelServiceException {
		Objects.requireNonNull(classProvider, "classProvider");
		Entry entry = new Entry(classProvider);
		_map.put(entry.name(), entry);
		if (!entry.isLazyLoad()) {
			entry.getService(this);
		}
	}
	
	public KernelService get(String strName) 
			throws KernelServiceException {
		Entry entry = _map.get(strName);
		if (entry != null) {
			return entry.getService(this);
		} else {
			throw new KernelServiceException(strName, "not a known service");
		}
	}
	
	public void forEach(Consumer<Entry> consumer) {
		_map.values().forEach(consumer);
	}
	
	void pushLoad(String strName) 
			throws KernelServiceException {
		if (ListMethods.isTrueForAny(_stackResolve, i -> i.equals(strName))) {
			throw new KernelServiceException(strName, "cyclical dependencies");
		}
		_stackResolve.addFirst(strName);
	}
	
	void popLoad() {
		_stackResolve.pop();
	}
	
	void pushForShutdown(KernelServiceInternal service) {
		_stackShutdown.addFirst(service);
	}
	
	public void shutdownServices() {
		while (!_stackShutdown.isEmpty()) {
			KernelServiceInternal service = _stackShutdown.pop();
			service.shutdown();
		}
	}
	
	public static class Entry {
		private final AppKernelService _defn;
		private final Constructor<?> _constructor;
		private final String[] _depends;
		private KernelServiceInternal _service;
		
		Entry(Class<?> classProvider) {
			if (classProvider.isInterface()) {
				throw new IllegalArgumentException("not instantiable");
			}
			AppKernelService annotService = classProvider.getAnnotation(AppKernelService.class);
			if (annotService == null) {
				throw new IllegalArgumentException("not a service");
			}
			_defn = annotService;
			try {
				_constructor = classProvider.getConstructor();
			} catch (NoSuchMethodException exc) {
				throw new InvalidClassDefinitionException(classProvider, "has no default constructor", exc);
			}
			DependsOnService[] annotDepends
						= classProvider.getAnnotationsByType(DependsOnService.class);
				_depends = new String[annotDepends.length];
				ArrayMethods.mapInto(annotDepends, _depends, f -> f.value());		
			_service = null;
		}
		
		String name() {
			return _defn.name();
		}
		
		boolean isNameEqual(String strName) {
			return _defn.name().equals(strName);
		}

		boolean isLazyLoad() {
			return _defn.lazyLoad();
		}

		boolean mustShutDown() {
			return _defn.mustShutDown();
		}
		
		String[] dependencies() {
			return _depends;
		}
		
		boolean isLoaded() {
			return _service != null;
		}
		
		KernelServiceInternal getService(KernelServiceProviderTable table) 
				throws KernelServiceException {
			if (_service == null) {
				table.pushLoad(_defn.name());
				try {
					_service = (KernelServiceInternal) _constructor.newInstance();
					_service.initialize(table._storeCentral);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new InvalidOperationException("cannot create service " + _defn.name(), e);
				}
				if (_defn.mustShutDown()) {
					table.pushForShutdown(_service);
				}
				table.popLoad();
			}
			return _service;
		}
	}
}
