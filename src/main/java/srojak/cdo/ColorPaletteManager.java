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
package srojak.cdo;

import java.awt.Color;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 */
public class ColorPaletteManager {
	private final HashMap<NamedKey, Color> _map;

	public ColorPaletteManager() {
		_map = new HashMap<NamedKey, Color>();
	}
	
	public void putColor(NamedKey key, Color color) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(color, "color");
		_map.put(key, color);
	}
	
	public Color getColor(NamedKey key) {
		Objects.requireNonNull(key, "key");
		Color color = _map.get(key);
		if (color == null) {
			throw new NoSuchElementException("key " + key.getName() + " not found");
		}
		return color;
	}
	
	public void changeColor(NamedKey key, Color color) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(color, "color");
		if (_map.replace(key, color) == null) {
			throw new NoSuchElementException("key " + key.getName() + " not found");
		}
	}
}
