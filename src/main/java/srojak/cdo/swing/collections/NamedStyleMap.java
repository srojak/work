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
package srojak.cdo.swing.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

import javax.swing.text.AttributeSet;

import srojak.cdo.swing.StyleName;
import srojak.core.DuplicateKeyException;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class NamedStyleMap
		extends HashMap<StyleName, AttributeSet> {

	/**
	 * 
	 */
	public NamedStyleMap() {
		super();
	}

	@Override
	public AttributeSet put(StyleName key, AttributeSet value) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(value, "value");
		if (containsKey(key)) {
			throw new DuplicateKeyException();
		}
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends StyleName, ? extends AttributeSet> m) {
		Objects.requireNonNull(m, "m");
		for (Map.Entry<? extends StyleName, ? extends AttributeSet> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public AttributeSet putIfAbsent(StyleName key, AttributeSet value) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(value, "value");
		return super.putIfAbsent(key, value);
	}

	@Override
	public boolean replace(StyleName key, AttributeSet oldValue, AttributeSet newValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AttributeSet replace(StyleName key, AttributeSet value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AttributeSet merge(StyleName key, AttributeSet value,
			BiFunction<? super AttributeSet, ? super AttributeSet, ? extends AttributeSet> remappingFunction) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void replaceAll(BiFunction<? super StyleName, ? super AttributeSet, ? extends AttributeSet> function) {
		throw new UnsupportedOperationException();
	}

}
