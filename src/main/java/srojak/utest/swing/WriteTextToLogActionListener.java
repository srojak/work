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
package srojak.utest.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JOptionPane;

import srojak.cdo.swing.frames.AppFrameContainer;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.debug.DebugNexus;

/**
 * @author Stephen
 *
 */
public class WriteTextToLogActionListener
		implements ActionListener {
	private final AppFrameContainer _frame;
	private final ObsLevel _level;
	private final DebugNexus _nexus;

	/**
	 * 
	 */
	public WriteTextToLogActionListener(AppFrameContainer ctnrFrame, ObsLevel level) {
		Objects.requireNonNull(ctnrFrame, "ctnrFrame");
		Objects.requireNonNull(level, "level");
		_frame = ctnrFrame;
		_level = level;
		_nexus = new DebugNexus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String strText = _frame.showInputDialog("Enter text to write", "Debug Text",
				JOptionPane.PLAIN_MESSAGE);
		if (strText != null) {
			ObservationWriter writer = _nexus.getWriter();
			writer.write(_level, "Text message: " + strText);
		}
	}

}
