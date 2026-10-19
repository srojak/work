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
package srojak.core;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import srojak.core.containers.KeyMutableValueContainer;
import srojak.core.containers.KeyValueContainer;

/**
 * @author Stephen
 *
 * an interface for objects with key-value pairs.
 */
public interface KeyValue<K, V>
		extends Keyed<K> {
	
	boolean isValueNull();
	
	V value();
	
	static <K, V> Stream<KeyValue<K, V>> wrapAll(Collection<? extends V> values, Function<V, K> fnKeyExtract) {
		Objects.requireNonNull(values, "value");
		Objects.requireNonNull(fnKeyExtract, "fnKeyExtract");
		return values.stream().map(v -> new KeyValueContainer<K, V>(fnKeyExtract.apply(v), v));
	}
	
	static <K, V> Stream<KeyValue<K, V>> wrapAllMutable(Collection<? extends V> values, Function<V, K> fnKeyExtract) {
		Objects.requireNonNull(values, "value");
		Objects.requireNonNull(fnKeyExtract, "fnKeyExtract");
		return values.stream().map(v -> new KeyMutableValueContainer<K, V>(fnKeyExtract.apply(v), v));
	}
}
