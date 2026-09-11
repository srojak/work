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
package srojak.cdo.swing.interact;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

import javax.swing.JComponent;

import srojak.cdo.swing.VisualPropertyNames;

/**
 * @author Stephen
 *
 */
public class PropertyRepeatListener 
		implements PropertyChangeListener, VisualPropertyNames {
	private final JComponent _receiver;
	
	private static final Map<String, BiConsumer<JComponent, PropertyChangeEvent>> _mapResponse;
	
	static {
		_mapResponse = new HashMap<String, BiConsumer<JComponent, PropertyChangeEvent>>();
		_mapResponse.put(BACKGROUND, (c, e) -> {
			Color color = (Color) e.getNewValue();
			c.setBackground(color);
		});
		_mapResponse.put(FOREGROUND, (c, e) -> {
			Color color = (Color) e.getNewValue();
			c.setForeground(color);
		});
		_mapResponse.put(FONT, (c, e) -> {
			Font font = (Font) e.getNewValue();
			c.setFont(font);
		});
	}
	
	/**
	 * 
	 */
	public PropertyRepeatListener(JComponent receiver) {
		Objects.requireNonNull(receiver, "receiver");
		_receiver = receiver;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		BiConsumer<JComponent, PropertyChangeEvent> action = _mapResponse.get(evt.getPropertyName());
		if (action != null) {
			action.accept(_receiver, evt);
		}

	}

}
