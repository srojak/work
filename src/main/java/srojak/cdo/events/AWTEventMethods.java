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
package srojak.cdo.events;

import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class AWTEventMethods {

	public static ItemEvent createItemSelectionEvent(ItemSelectable originator,
			Object objItem, boolean bIsSelected) {
		// the constructor checks originator
		Objects.requireNonNull(objItem, "objItem");
		return new ItemEvent(originator, ItemEvent.ITEM_STATE_CHANGED, objItem,
				bIsSelected ? ItemEvent.SELECTED : ItemEvent.DESELECTED);
	}
	
	private static void buildItemEventFormat(StringBuilder sb, ItemEvent event) {
		if (event.getID() == ItemEvent.ITEM_STATE_CHANGED) {
			sb.append("state change: state=");
			switch (event.getStateChange()) {
			case ItemEvent.SELECTED:
				sb.append("SELECTED");
				return;
				
			case ItemEvent.DESELECTED:
				sb.append("DESELECTED");
				return;
				
			default:
				sb.append(event.getStateChange());
				return;
			}
		} else {
			sb.append("ID=");
			sb.append(event.getID());
			sb.append(", state=");
			sb.append(event.getStateChange());
			return;
		}
	}
	
	public static void formatItemEvent(StringBuilder sb, ItemEvent event) {
		Objects.requireNonNull(sb, "sb");
		Objects.requireNonNull(event, "event");
		buildItemEventFormat(sb, event);
	}
	
	public static String formatItemEvent(ItemEvent event) {
		Objects.requireNonNull(event, "event");
		StringBuilder sb = new StringBuilder();
		buildItemEventFormat(sb, event);
		return sb.toString();
	}
	
	public static ActionEvent copyActionEvent(Object objRelay, ActionEvent event) {
		Objects.requireNonNull(objRelay, "objRelay");
		Objects.requireNonNull(event, "event");
		return new ActionEvent(objRelay, event.getID(), event.getActionCommand(), 
				event.getWhen(), event.getModifiers());			
	}
}
