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
package srojak.cdo.swing.styles;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;

import javax.swing.text.AttributeSet;

import srojak.core.keys.NameEquatableKey;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class KeyedStyleMap<K extends NameEquatableKey>
		extends HashMap<K, AttributeSet>
		implements KeyedStyleMapReadOnly<K> {
	private AttributeSet _styleDefault;

	/**
	 * 
	 */
	public KeyedStyleMap() {
		super();
		_styleDefault = null;
	}
	
	public AttributeSet getDefaultStyle() {
		return _styleDefault;
	}
	
	public void setDefaultStyle(AttributeSet style) {
		_styleDefault = style;
	}

	@Override
	public AttributeSet get(Object key) {
		Objects.requireNonNull(key, "key");
		AttributeSet style = super.get(key);
		if (style == null) {
			if (_styleDefault == null) {
				throw new NoSuchElementException(key.toString());
			} else {
				style = _styleDefault;
			}
		}
		return style;
	}

	@Override
	public AttributeSet put(K key, AttributeSet value) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(value, "value");
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends AttributeSet> m) {
		Objects.requireNonNull(m, "m");
		for (Map.Entry<? extends K, ? extends AttributeSet> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public AttributeSet putIfAbsent(K key, AttributeSet value) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(value, "value");
		return super.putIfAbsent(key, value);
	}

	@Override
	public boolean replace(K key, AttributeSet oldValue, AttributeSet newValue) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(newValue, "newValue");
		return super.replace(key, oldValue, newValue);
	}

	@Override
	public AttributeSet replace(K key, AttributeSet value) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(value, "value");
		return super.replace(key, value);
	}

	@Override
	public void replaceAll(BiFunction<? super K, ? super AttributeSet, ? extends AttributeSet> function) {
		// TODO prevent assignment of nulls
		super.replaceAll(function);
	}
}
