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

import javax.swing.text.AttributeSet;

import srojak.cdo.swing.StyledTextMessageRelay;
import srojak.core.keys.NameEquatableKey;

/**
 * @author Stephen
 *
 */
public class StyledMessageRelayConnectorMappingBase<K extends NameEquatableKey> 
		extends StyledMessageRelayConnectorBase {
	protected final KeyedStyleMap<K> _mapStyles;

	/**
	 * @param receiver
	 */
	public StyledMessageRelayConnectorMappingBase(StyledTextMessageRelay receiver) {
		super(receiver);
		_mapStyles = new KeyedStyleMap<K>();
	}
	
	public void println(K key, String strText) {
		AttributeSet style = _mapStyles.get(key);
		forward(style, strText);
	}
}
