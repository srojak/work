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
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class ImageTool {

	public static Image createSingleColorImage(Dimension szImage, Color color) {
		Objects.requireNonNull(szImage, "szImage");
		Objects.requireNonNull(color, "color");
		
		BufferedImage img = new BufferedImage(szImage.width, szImage.height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		g.setColor(color);
		g.fillRect(0, 0, szImage.width, szImage.height);
		g.dispose();
		return img;
	}
	
	public static Image resizeImage(Dimension szResize, Image imageOriginal) {
		Objects.requireNonNull(szResize, "szResize");
		Objects.requireNonNull(imageOriginal, "imageOriginal");
		
		BufferedImage imgNew = new BufferedImage(szResize.width, szResize.height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = imgNew.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(imageOriginal, 0, 0, szResize.width, szResize.height, null);
		g.dispose();
		return imgNew;
	}
}
