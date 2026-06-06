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

import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.event.ListSelectionEvent;

/**
 * @author Stephen
 *
 */
public class AWTEventFormatters {
	
	private static void formatSource(StringBuilder sb, EventObject event) {
		sb.append("source=");
		if (event.getSource() == null) {
			sb.append("null");
		} else {
			Class<?> clsSource = event.getSource().getClass();
			sb.append(clsSource.getName());
		}
	}

	public static String formatEvent(ActionEvent event) {
		StringBuilder sb = new StringBuilder("ActionEvent(");
		formatSource(sb, event);
		sb.append(", ");
		sb.append(event.paramString());
		sb.append(')');
		return sb.toString();
	}
	
	public static String formatEvent(ListSelectionEvent event) {
		StringBuilder sb = new StringBuilder("ListSelectionEvent(");
		formatSource(sb, event);
		sb.append(", firstIndex=");
		sb.append(event.getFirstIndex());
		sb.append(", lastIndex=");
		sb.append(event.getLastIndex());
		sb.append(", isAdjusting=");
		sb.append(event.getValueIsAdjusting());
		sb.append(')');
		return sb.toString();
	}
}
