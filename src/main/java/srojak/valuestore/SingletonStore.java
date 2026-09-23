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
package srojak.valuestore;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 * The central store of all global stores.
 */
final class SingletonStore {
	private static final HashMap<PackageClassLocator, StoreValueKeyed> _map;
	private static ObservationCollector _collectObs;
	
	static {
		_map = new HashMap<PackageClassLocator, StoreValueKeyed>();
		_collectObs = ObservationCollector.makeInstance();
	}
	
	public static ObservationCollector getObservationWriter() {
		return _collectObs;
	}
	
	public static void setObservationWriter(ObservationCollector writer) {
		_collectObs = writer;
	}
	
	public static int size() {
		return _map.size();
	}
	
	public static boolean containsKey(PackageClassLocator locator) {
		return _map.containsKey(locator);
	}
	
	public static StoreValueKeyed getStore(PackageClassLocator locator) {
		StoreValueKeyed store = _map.get(locator);
		if (store != null) {
			_collectObs.buildAndWrite(ObsLevel.DEBUG2, sb -> {
				sb.append("retrieving store for ");
				sb.append(locator);
			});
		} else {
			_collectObs.buildAndWrite(ObsLevel.WARN, sb -> {
				sb.append("could not retrieve store for ");
				sb.append(locator);
			});
		}
		return store;
	}
	
	public static StoreValueKeyed getOrCreateStore(PackageClassLocator locator,
			Supplier<StoreValueKeyed> methodCreate) {
		Objects.requireNonNull(locator, "locator");
		Objects.requireNonNull(methodCreate, "methodCreate");
		StoreValueKeyed store = _map.get(locator);
		if (store == null) {
			store = methodCreate.get();
			Objects.requireNonNull(store, "created store");
			_map.put(locator, store);
			final int nSize = store.size();
			_collectObs.buildAndWrite(ObsLevel.DEBUG, sb -> {
				sb.append("created store for ");
				sb.append(locator);
				sb.append(" with ");
				sb.append(nSize);
				sb.append(" entries");
			});
		} else {
			_collectObs.buildAndWrite(ObsLevel.DEBUG2, sb -> {
				sb.append("retrieving store for ");
				sb.append(locator);
			});
		}
		return store;
	}
	
	public static void putStore(PackageClassLocator locator, StoreValueKeyed store) {
		Objects.requireNonNull(locator, "locator");
		Objects.requireNonNull(store, "store");
		_map.put(locator, store);
		final int nSize = store.size();
		_collectObs.buildAndWrite(ObsLevel.DEBUG, sb -> {
			sb.append("created store for ");
			sb.append(locator);
			sb.append(" with ");
			sb.append(nSize);
			sb.append(" entries");
		});
	}
	
	@SuppressWarnings("unchecked")
	public static <S extends StoreValueKeyed> S getStoreAs(PackageClassLocator locator) {
		return (S) _map.get(locator);
	}
	
	public static void forEach(BiConsumer<PackageClassLocator, ? super StoreValueKeyed> action) {
		_map.forEach(action);
	}
}
