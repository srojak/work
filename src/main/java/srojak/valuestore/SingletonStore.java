/**
 * 
 */
package srojak.valuestore;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterNull;
import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 * The central store of all global stores.
 */
final class SingletonStore {
	private static final HashMap<PackageClassLocator, StoreValueKeyed> _map;
	private static ObservationWriter _writer;
	
	static {
		_map = new HashMap<PackageClassLocator, StoreValueKeyed>();
		_writer = new ObservationWriterNull();
	}
	
	public static ObservationWriter getObservationWriter() {
		return _writer;
	}
	
	public static void setObservationWriter(ObservationWriter writer) {
		_writer = writer;
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
			_writer.buildAndWrite(ObsLevel.DEBUG2, sb -> {
				sb.append("retrieving store for ");
				sb.append(locator);
			});
		} else {
			_writer.buildAndWrite(ObsLevel.WARN, sb -> {
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
			_writer.buildAndWrite(ObsLevel.DEBUG, sb -> {
				sb.append("created store for ");
				sb.append(locator);
				sb.append(" with ");
				sb.append(nSize);
				sb.append(" entries");
			});
		} else {
			_writer.buildAndWrite(ObsLevel.DEBUG2, sb -> {
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
		_writer.buildAndWrite(ObsLevel.DEBUG, sb -> {
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
