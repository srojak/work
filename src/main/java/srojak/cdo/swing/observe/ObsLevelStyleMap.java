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
package srojak.cdo.swing.observe;

import java.awt.Color;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import srojak.cdo.swing.StyleBuilder;
import srojak.core.CommonCollectionSize;
import srojak.core.observe.ObsLevel;

/**
 * @author Stephen
 *
 */
public class ObsLevelStyleMap
		implements CommonCollectionSize {
	private final HashMap<ObsLevel, StyleBuilder> _map;
	
	private static final HashMap<ObsLevel, StyleBuilder> _mapBase;
	
	static {
		_mapBase = new HashMap<ObsLevel, StyleBuilder>();
		_mapBase.put(ObsLevel.FATAL, a -> { makeColorBold(a, Color.MAGENTA); } );
		_mapBase.put(ObsLevel.SEVERE, a -> { makeColorBold(a, Color.MAGENTA); } );
		_mapBase.put(ObsLevel.ALERT, a -> { makeColorPlain(a, Color.MAGENTA); } );
		_mapBase.put(ObsLevel.NOTICE, a -> { makeColorPlain(a, Color.RED); } );
		_mapBase.put(ObsLevel.ERROR, a -> { makeColorPlain(a, Color.RED); } );
		_mapBase.put(ObsLevel.WARN, a -> { makeColorPlain(a, Color.ORANGE); } );
		_mapBase.put(ObsLevel.INFO, a -> { makeColorPlain(a, Color.GREEN); } );
		_mapBase.put(ObsLevel.DETAIL, a -> { makeColorPlain(a, Color.CYAN); } );
		_mapBase.put(ObsLevel.TRACE, a -> { makeColorBold(a, Color.BLUE); } );
		_mapBase.put(ObsLevel.DEBUG, a -> { makeColorPlain(a, Color.BLUE); } );
		_mapBase.put(ObsLevel.TRACE2, a -> { makeColorBold(a, Color.BLUE); } );
		_mapBase.put(ObsLevel.DEBUG2, a -> { makeColorPlain(a, Color.BLUE); } );
		_mapBase.put(ObsLevel.TRACE3, a -> { makeColorBold(a, Color.BLUE); } );
		_mapBase.put(ObsLevel.DEBUG3, a -> { makeColorPlain(a, Color.BLUE); } );
		_mapBase.put(ObsLevel.FINE, a -> { makeColorPlain(a, Color.DARK_GRAY); } );
		_mapBase.put(ObsLevel.FINER, a -> { makeColorPlain(a, Color.DARK_GRAY); } );
		_mapBase.put(ObsLevel.FINEST, a -> { makeColorPlain(a, Color.DARK_GRAY); } );
	}
		
	private static void makeColorBold(MutableAttributeSet attrs, Color color) {
		StyleConstants.setForeground(attrs, color);
		StyleConstants.setBold(attrs, true);
	}
	
	private static void makeColorPlain(MutableAttributeSet attrs, Color color) {
		StyleConstants.setForeground(attrs, color);
		StyleConstants.setBold(attrs, false);
	}
	
	public ObsLevelStyleMap() {
		_map = new HashMap<ObsLevel, StyleBuilder>(_mapBase);
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	public int size() {
		return _map.size();
	}
	
	public boolean containsKey(ObsLevel level) {
		return _map.containsKey(level);
	}
	
	public AttributeSet getStyle(ObsLevel level) {
		StyleBuilder builder = _map.get(level);
		if (builder == null) {
			throw new NoSuchElementException("level not found");
		}
		SimpleAttributeSet style = new SimpleAttributeSet();
		builder.accept(style);
		return style;
	}
	
	public void define(ObsLevel level, StyleBuilder builder) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(builder, "builder");
		_map.put(level, builder);
	}
}
