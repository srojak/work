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
package srojak.numerics.vstore.facades;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;

import srojak.core.keys.ClassKey;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.numerics.vstore.StoreValueKeyed;

/**
 * @author Stephen
 *
 */
final class FacadeCatalog {
	private static final HashMap<ClassKey, StoreValueKeyed> _map;
	private static ObservationCollector _collectObs;
	
	static {
		_map = new HashMap<ClassKey, StoreValueKeyed>();
		_collectObs = CoreObservers.OBS_LOG;
	}
	
	public static ObservationCollector getObservationWriter() {
		return _collectObs;
	}
	
	public static void setObservationWriter(ObservationCollector collector) {
		Objects.requireNonNull(collector, "collector");
		_collectObs = collector;
	}
	
	public static int size() {
		return _map.size();
	}
	
	public static boolean containsKey(ClassKey key) {
		return _map.containsKey(key);
	}
	
	public static boolean registerStore(ClassKey key, StoreValueKeyed store) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(store, "store");
		return _map.putIfAbsent(key, store) == null;
	}
	
	public static StoreValueKeyed getStore(ClassKey key) {
		StoreValueKeyed store = _map.get(key);
		if (store != null) {
			_collectObs.observe(ObsLevel.DEBUG2, cb -> {
				cb.appendContent("retrieving store for ");
				cb.appendContent(key.getReferencedClass());
			});
		} else {
			_collectObs.observe(ObsLevel.WARN, cb -> {
				cb.appendContent("could not retrieve store for ");
				cb.appendContent(key.getReferencedClass());
			});
		}
		return store;
	}
	
	@SuppressWarnings("unchecked")
	public static <S extends StoreValueKeyed> S getStoreAs(ClassKey key) {
		return (S) _map.get(key);
	}
	
	public static void forEach(BiConsumer<ClassKey, ? super StoreValueKeyed> action) {
		_map.forEach(action);
	}
}
