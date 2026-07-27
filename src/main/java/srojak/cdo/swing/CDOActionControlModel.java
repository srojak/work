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
package srojak.cdo.swing;

import java.awt.event.ActionEvent;

import srojak.cdo.ActionCommandBearing;
import srojak.cdo.ActionEventOriginator;
import srojak.cdo.swing.event.ChangeEventOriginator;
import srojak.core.NameTokenTagged;
import srojak.events.ObjectValueChangeEventOriginator;

/**
 * @author Stephen
 *
 */
public interface CDOActionControlModel 
		extends CDOControlModel, NameTokenTagged, ActionCommandBearing,
			ActionEventOriginator, ChangeEventOriginator,
			ObjectValueChangeEventOriginator {

    /**
     * Indicates if the button can be selected or triggered by
     * an input device, such as a mouse pointer.
     *
     * @return <code>true</code> if the button is enabled
     */
    boolean isEnabled();

    /**
     * Enables or disables the button.
     *
     * @param bState whether or not the button should be enabled
     * @see #isEnabled
     */
    public void setEnabled(boolean bState);
    
    void relayActionEvent(ActionEvent event);

    
}
