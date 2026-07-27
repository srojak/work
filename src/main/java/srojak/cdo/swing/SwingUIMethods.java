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

import java.awt.Dimension;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;

/**
 * @author Stephen
 *
 */
public class SwingUIMethods {
	
	public static XResult setLookAndFeel(String strClassName) {
		XResultStatusCarrier result = new XResultStatusCarrier();
		try {
			UIManager.setLookAndFeel(strClassName);
			result.setValid();
		} catch (ClassNotFoundException exc) {
			result.caughtException(exc);
		} catch (InstantiationException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		} catch (UnsupportedLookAndFeelException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public static XResult setLookAndFeel(LookAndFeel newLookAndFeel) {
		Objects.requireNonNull(newLookAndFeel, "newLookAndFeel");
		XResultStatusCarrier result = new XResultStatusCarrier();
		try {
			UIManager.setLookAndFeel(newLookAndFeel);
			result.setValid();
		} catch (UnsupportedLookAndFeelException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public static int estimateTextSizeAllowance(String strLargeText, int nPadding) {
		JLabel label = new JLabel(strLargeText);
		Dimension szPreferred = label.getPreferredSize();
		return szPreferred.width + nPadding;
	}
}
