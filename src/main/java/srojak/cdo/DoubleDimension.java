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

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.util.Objects;

import srojak.numerics.DoubleMethods;
import srojak.numerics.DoublePrecisionComparer;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 * A double precision implementation of a {@code Dimension2D} object.
 * 
 * Necessary to make owner scaling work consistently.
 */
public class DoubleDimension
		extends Dimension2D {
	private double _width;
	private double _height;
	
	/**
	 * Constructor.
	 * @param dWidth The width value.
	 * @param dHeight The height value.
	 */
	public DoubleDimension(double dWidth, double dHeight) {
		_width = dWidth;
		_height = dHeight;
	}
	
	/**
	 * Copy constructor.
	 * @param ddFrom the object from which to copy.
	 * @throws NullPointerException If argument is {@code null}.
	 */
	public DoubleDimension(DoubleDimension ddFrom) {
		Objects.requireNonNull(ddFrom, "ddFrom");
		_width = ddFrom._width;
		_height = ddFrom._height;
	}
		
	/**
	 * Interface-based copy constructor.
	 * @param dmFrom the object from which to copy.
	 * @throws NullPointerException If argument is {@code null}.
	 */
	public DoubleDimension(Dimension2D dmFrom) {
		Objects.requireNonNull(dmFrom, "dmFrom");
		_width = dmFrom.getWidth();
		_height = dmFrom.getHeight();
	}

	/**
	 * Default constructor.
	 */
	public DoubleDimension() {
		this(0.0d, 0.0d);
	}

	/**
	 * Get the value of the width.
	 */
	@Override
	public double getWidth() {
		return _width;
	}

	/**
	 * Get the value of the height.
	 */
	@Override
	public double getHeight() {
		return _height;
	}

	/**
     * Sets the size of this {@code DoubleDimension} object to the
     * specified width and height.
     * @param width  the new width for the {@code DoubleDimension}
     * object
     * @param height  the new height for the {@code DoubleDimension}
     * object
	 */
	@Override
	public void setSize(double width, double height) {
		_width = width;
		_height = height;
	}
	
	/**
     * Sets the size of this {@code DoubleDimension} object to
     * match the specified size.
	 * @param ddSize the new size for the {@code DoubleDimension} object.
	 * @throws NullPointerException If argument is {@code null}.
	 */
	public void setSize(DoubleDimension ddSize) {
		Objects.requireNonNull(ddSize, "ddSize");
		_width = ddSize._width;
		_height = ddSize._height;
	}
	
	/**
	 * Reduces the dimensions to the nearest integer.
	 * @return A {@code Dimension} object containing the reduced dimensions.
	 */
	public Dimension reduce() {
		return new Dimension((int)Math.round(_width), (int)Math.round(_height));
	}
	
	/**
	 * Produce a scaled dimension from the dimensions in this object.
	 * @param dScale The scale factor to use.
	 * @return A new {@code DoubleDimension} object with the scaled dimensions.
	 * @throws IllegalArgumentException if {@code dScale} is not a positive value.
	 */
	public DoubleDimension scale(double dScale) {
		if (DoubleMethods.compare(OrderedComparison.LE, dScale, 0.0d)) {
			throw new IllegalArgumentException("dNewScale must be positive");
		}
		DoubleDimension ddScale = new DoubleDimension();
		ddScale.setSize(_width * dScale, _height * dScale);
		return ddScale;
	}

	/**
	 * Returns a hash code for this object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(_height, _width);
	}

	/**
	 * Compare to another dimension object for equality.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Dimension2D other) {
			return DoublePrecisionComparer.DEFAULT_COMPARER.areEqual(_width, other.getWidth())
					&& DoublePrecisionComparer.DEFAULT_COMPARER.areEqual(_height, other.getHeight());
		} else {
			return false;
		}
	}

	/**
	 * Return a string representation of the values in this object.
	 */
	@Override
	public String toString() {
		return String.format("width=%.3f, height=%.3f", _width, _height);
	}

}
