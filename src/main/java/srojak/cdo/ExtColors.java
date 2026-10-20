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

import java.awt.Color;

import srojak.core.Labeled;
import srojak.core.text.LabeledEnvelope;

/**
 * @author Stephen
 *
 * @see https://www.color-meanings.com/category/graphic-design/
 */
public interface ExtColors {

	public static final Labeled<Color> VIVID_GREEN = new LabeledEnvelope<Color>(new Color(102, 255, 102), "VIVID_GREEN");
	public static final Labeled<Color> DARK_GREEN = new LabeledEnvelope<Color>(new Color(0, 204, 0), "DARK_GREEN");
	public static final Labeled<Color> EMERALD = new LabeledEnvelope<Color>(new Color(0, 138, 15), "EMERALD");
	public static final Labeled<Color> SAGE = new LabeledEnvelope<Color>(new Color(114, 140, 105), "SAGE");
	public static final Labeled<Color> FOREST_GREEN = new LabeledEnvelope<Color>(new Color(0, 68, 33), "FOREST_GREEN");
	public static final Labeled<Color> TURQUOISE = new LabeledEnvelope<Color>(new Color(49, 174, 162), "TURQUOISE");
	public static final Labeled<Color> DARK_CYAN = new LabeledEnvelope<Color>(new Color(0, 204, 204), "DARK_CYAN");
	public static final Labeled<Color> CARAMEL = new LabeledEnvelope<Color>(new Color(101, 53, 15), "CARAMEL");
	public static final Labeled<Color> BROWN = new LabeledEnvelope<Color>(new Color(35, 23, 9), "BROWN");
}
