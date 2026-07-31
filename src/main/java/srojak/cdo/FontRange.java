/**
 * 
 */
package srojak.cdo;

import java.awt.Font;

import srojak.numerics.intervals.IntervalInt;
/**
 * @author Stephen
 *
 */
public class FontRange {
	private final IntervalInt _rangeSizes;
	private Font _font;
	private int _style;
	
	public FontRange(String strName, IntervalInt rangeSizes,
			int style, int sizeStart) {
		_rangeSizes = rangeSizes;
		_style = style;
		_font = new Font(strName, style, sizeStart);
	}
	
	public Font getFont() {
		return _font;
	}
	
	public int getStyle() {
		return _style;
	}
	
	public int getSize() {
		return _font.getSize();
	}
	
	public IntervalInt getSizeRange() {
		return _rangeSizes;
	}
	
	public void resize(int sizeNew) {
		_font = _font.deriveFont((float) sizeNew);
	}
}
