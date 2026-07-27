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
package srojak.cdo.swing.trees;

import java.awt.Component;
import java.util.HashMap;
import java.util.Objects;

import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import srojak.core.NameToken;
import srojak.core.NameTokenTaggedObject;

/**
 * @author Stephen
 *
 */
public class MultiTreeCellRenderer
		implements TreeCellRenderer {
	private final HashMap<NameToken, TreeCellRenderer> _mapNames;
	private final HashMap<Class<?>, TreeCellRenderer> _mapClasses;
	private final TreeCellRenderer _default;
	
	public MultiTreeCellRenderer(TreeCellRenderer rendererDefault) {
		Objects.requireNonNull(rendererDefault, "rendererDefault");
		_mapNames = new HashMap<NameToken, TreeCellRenderer>();
		_mapClasses = new HashMap<Class<?>, TreeCellRenderer>();
		_default = rendererDefault;
	}
	
	public void addRenderer(NameToken token, TreeCellRenderer renderer) {
		Objects.requireNonNull(token, "token");
		Objects.requireNonNull(renderer, "renderer");
		_mapNames.put(token, renderer);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		TreeCellRenderer renderer = null;
		if (value instanceof NameTokenTaggedObject objNTT) {
			renderer = _mapNames.get(objNTT.getNameTag());
			if (renderer != null) {
				// peel off the named wrapper
				return renderer.getTreeCellRendererComponent(tree,  objNTT.getObject(),
						selected, expanded, leaf, row, hasFocus);
			}
		}
		Class<?> classObj = value.getClass();
		renderer = _mapClasses.get(classObj);
		if (renderer == null) {
			renderer = _default;
		}
		return renderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
	}
}
