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
package srojak.cdo.swing.frames;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;

import srojak.cdo.swing.TreeWindowFlags;
import srojak.cdo.swing.panels.DialogBaseButtonPanel;
import srojak.core.tools.BitMethods;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class TreeDisplayWindow 
		extends JFrame 
		implements TreeWindowFlags {
	protected final JTree _tree;
	private ButtonModel _modelClose;

	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public TreeDisplayWindow(String title, TreeNode tnRoot, int flags)
			throws HeadlessException {
		super(title);
		
		_tree = new JTree(tnRoot);
		
		postConstruct(flags);
	}

	private void postConstruct(int flags) {
		JRootPane root = getRootPane();
		root.setLayout(new BorderLayout());
		
		if (BitMethods.test(flags, HIDE_ROOT_NODE)) {
			_tree.setRootVisible(false);
			_tree.setShowsRootHandles(true);
		}
		
		DialogBaseButtonPanel panelButtons = new DialogBaseButtonPanel(DialogBaseButtonPanel.PANEL_NAME);
		JButton bnClose = new JButton("Close");
		_modelClose = bnClose.getModel();
		panelButtons.addButton(bnClose, 0);
		_modelClose.addActionListener(e -> {
			this.dispose();
		});
		root.add(panelButtons, BorderLayout.SOUTH);
		
		root.add(new JScrollPane(_tree), BorderLayout.CENTER);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	protected JMenuBar createMenuBar() {
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		return bar;
	}
	
	public void display() {
		pack();
		setVisible(true);
	}
}
