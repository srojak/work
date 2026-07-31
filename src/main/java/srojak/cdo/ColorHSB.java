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
import java.util.Objects;

import srojak.numerics.IntervalType;
import srojak.numerics.intervals.IntervalFloat;

/**
 * @author Stephen
 *
 * @see java.awt.Color.getHSBColor for values domains
 */
public class ColorHSB {
	private final float _fHue;
	private final float _fSat;
	private final float _fBright;
	
	private static final IntervalFloat _intvDomain = new IntervalFloat(IntervalType.CLOSED, 0.0f, 1.0f);
	
	ColorHSB(float fHue, float fSaturation, float fBrightness, boolean bValidate) {
		if (bValidate) {
			if (!_intvDomain.isInInterval(fHue)) {
				throw new IllegalArgumentException("fHue is outside allowable domain");
			}
			if (!_intvDomain.isInInterval(fSaturation)) {
				throw new IllegalArgumentException("fSaturation is outside allowable domain");
			}
			if (!_intvDomain.isInInterval(fBrightness)) {
				throw new IllegalArgumentException("fBrightness is outside allowable domain");
			}
		}
		_fHue = fHue;
		_fSat = fSaturation;
		_fBright = fBrightness;
	}
	
	public ColorHSB(float fHue, float fSaturation, float fBrightness) {
		this (fHue, fSaturation, fBrightness, true);
	}
	
	public float getHue() {
		return _fHue;
	}
	
	public float getSaturation() {
		return _fSat;
	}
	
	public float getBrightness() {
		return _fBright;
	}
	
	public Color toColor() {
		return new Color(Color.HSBtoRGB(_fHue, _fSat, _fBright));	
	}
	
	public Color toColor(int alpha) {
		int rgb = Color.HSBtoRGB(_fHue, _fSat, _fBright);
		rgb |= (alpha & 0xff) << 24;
		return new Color(rgb, true);
	}
	
	public static ColorHSB fromColor(Color color) {
		Objects.requireNonNull(color, "color");
		float[] values = new float[3];
		Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), values);
		return new ColorHSB(values[0], values[1], values[2]);
	}
}
