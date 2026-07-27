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
package srojak.cdo.swing.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Objects;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.cdo.AWTGeometry;
import srojak.cdo.DoubleDimension;
import srojak.cdo.swing.functional.ControlModelManager;
import srojak.cdo.swing.models.CompassControlModel;
import srojak.cdo.swing.models.DefaultCompassControlModel;
import srojak.core.NameToken;
import srojak.core.events.ObjectOwnershipEvent;
import srojak.core.events.ObjectOwnershipListener;
import srojak.core.logic.BooleanLatch;
import srojak.events.ObjectValueChangeEvent;
import srojak.events.ObjectValueChangeListener;
import srojak.numerics.CircleOctant;
import srojak.numerics.RadiansMethods;
import srojak.numerics.compass.CompassPoint;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class CompassRoseControl
		extends NameTokenTagComponent {
	private final ControlModelManager<CompassControlModel> _model;
	private final BooleanLatch _latchInitSize;
	private final DoubleDimension _szOuterCircle;
	private final DoubleDimension _szInnerCircle;
	private final Dimension _szControl;
	private final Rectangle _rectExtent;
	private final Point _ptCenter;
	private final Dimension _szFontBox;
	private final Dimension _szFontBoxWide;
	private ModelListener _listenerModel;
	private Color _colorArrow;
	private boolean _bLabelPoints;
	private boolean _bShowExtents;

	public static final Dimension SIZE_MINIMUM;
	private static final double _radiusOuterLabeled;
	private static final double _radiusOuterUnlabeled;
	private static final double _radiusInner;
	private static final double _dPointerOffsetAngle;
	private static final int _cutoffLine;
	private static final int _offsetWLeft;
	private static final int _offsetEdge;
	private static final Stroke _strokeLines;
	
	static {
		SIZE_MINIMUM = new Dimension(60, 60);
		_radiusOuterLabeled = 0.33;
		_radiusOuterUnlabeled = 0.4;
		_radiusInner = 0.14;
		_dPointerOffsetAngle = 0.1875d * Math.PI;
		_cutoffLine = 3;
		_offsetWLeft = 3;
		_offsetEdge = 3;
		_strokeLines = new BasicStroke(3.0f);
	}
	
	/**
	 * @param tokenName
	 */
	public CompassRoseControl(NameToken tokenName) {
		super(tokenName);
		_listenerModel = null;
		_latchInitSize = new BooleanLatch();
		_bLabelPoints = true;
		_colorArrow = Color.RED;
		_bShowExtents = false;
		_model = new ControlModelManager<CompassControlModel>();
		_model.addObjectOwnershipListener(new ObjectOwnershipListener() {

			@Override
			public void acquire(ObjectOwnershipEvent event) {
				_listenerModel = new ModelListener();
				CompassControlModel m = event.getValueAs();
				m.addObjectValueChangeListener(_listenerModel);
				m.addChangeListener(_listenerModel);
			}

			@Override
			public void release(ObjectOwnershipEvent event) {
				if (_listenerModel != null) {
					CompassControlModel m = event.getValueAs();
					m.removeObjectValueChangeListener(_listenerModel);
					m.removeChangeListener(_listenerModel);
					_listenerModel = null;
				}
			}
			
		});
		_model.setModel(new DefaultCompassControlModel(getNameTag()));
		
		_szControl = new Dimension();
		_szOuterCircle = new DoubleDimension();
		_szInnerCircle = new DoubleDimension();
		_rectExtent = new Rectangle();
		_ptCenter = new Point();
		_szFontBox = new Dimension();
		_szFontBoxWide = new Dimension();
		
		setMinimumSize(SIZE_MINIMUM);
		addMouseListener(new MouseResponse());
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				Dimension szComponent = e.getComponent().getSize();
				computeSizes(szComponent.width, szComponent.height);
				computeLabelSizes();
			}
			
		});
	}
	
	public CompassControlModel getModel() {
		return _model.getModel();
	}
	
	/**
	 * @return the _bLabelPoints
	 */
	public boolean getLabelPoints() {
		return _bLabelPoints;
	}

	/**
	 * @param bState
	 */
	public void setLabelPoints(boolean bState) {
		_bLabelPoints = bState;
		if (_latchInitSize.getState()) {
			Dimension size = getSize();
			computeSizes(size.width, size.height);
		}
	}
	
	public Color getArrowColor() {
		return _colorArrow;
	}
	
	public void setArrowColor(Color color) {
		Objects.requireNonNull(color, "color");
		_colorArrow = color;
	}

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if (_latchInitSize.getState()) {
			computeLabelSizes();
		}
	}

	/**
	 * @return the _bShowExtents
	 */
	public boolean isShowingExtents() {
		return _bShowExtents;
	}

	/**
	 * @param bState 
	 */
	public void setShowExtents(boolean bState) {
		this._bShowExtents = bState;
	}

	private void computeSizes(int width, int height) {
		_szControl.setSize(width, height);
		int nSize = Math.min(width, height);
		_rectExtent.setSize(nSize, nSize);
		if (_szControl.width < _szControl.height) {
			_rectExtent.y = (_szControl.height - nSize) >> 1;
		} else {
			_rectExtent.x = (_szControl.width - nSize) >> 1;
		}
		double dRadius = nSize * (_bLabelPoints ? _radiusOuterLabeled : _radiusOuterUnlabeled);
		_szOuterCircle.setSize(dRadius, dRadius);
		_szInnerCircle.setSize(nSize * _radiusInner, nSize * _radiusInner);
		_ptCenter.setLocation(width >> 1, height >> 1);
	}
	
	private void computeLabelSizes() {
		Font font = getFont();
		FontMetrics fm = getFontMetrics(font);
		_szFontBox.setSize(fm.charWidth('S'), fm.getAscent() + fm.getLeading());
		_szFontBoxWide.setSize(fm.charWidth('W'), fm.getAscent() + fm.getLeading());
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		_latchInitSize.setState(true);
		computeSizes(width, height);
		computeLabelSizes();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Dimension szOuter = _szOuterCircle.reduce();
		g2d.setColor(getBackground());
		g2d.fillRect(0,  0, _szControl.width, _szControl.height);
		g2d.setColor(getForeground());
		Stroke strokeSave = g2d.getStroke();
		g2d.setStroke(_strokeLines);
		g2d.drawLine(_ptCenter.x, _ptCenter.y - szOuter.height + _cutoffLine, 
				_ptCenter.x, _ptCenter.y + szOuter.height - _cutoffLine);
		g2d.drawLine(_ptCenter.x - szOuter.width + _cutoffLine, _ptCenter.y, 
				_ptCenter.x + szOuter.width - _cutoffLine, _ptCenter.y);
		g2d.setStroke(strokeSave);
		
		if (_bLabelPoints) {
			int halfWidth = _szFontBox.width >> 1;
			g2d.drawString("N", _ptCenter.x - halfWidth, _rectExtent.y + _szFontBox.height);
			g2d.drawString("S", _ptCenter.x - halfWidth, 
					_rectExtent.y + _rectExtent.height - _offsetEdge);
			int halfHeight = _szFontBox.height >> 1;
			g2d.drawString("W", _rectExtent.x + _offsetWLeft, _ptCenter.y + halfHeight);
			g2d.drawString("E", _rectExtent.x + _rectExtent.width - _szFontBox.width - _offsetEdge,
					_ptCenter.y + halfHeight);
		}
		
		CompassPoint pc = _model.getModel().getCurrentValue();
		double dTheta = pc.getGraphicsRadians();
		double dRadius = Math.min(_szOuterCircle.getWidth(), _szOuterCircle.getHeight());
		Point2D ptPeak = new Point2D.Double(_ptCenter.getX() + dRadius * Math.cos(dTheta),
				_ptCenter.getY() + dRadius * Math.sin(dTheta));
		Path2D.Double triangle = new Path2D.Double();
		triangle.moveTo(ptPeak.getX(), ptPeak.getY());
		double dTheta1 = dTheta - _dPointerOffsetAngle;
		dRadius = Math.min(_szInnerCircle.getWidth(), _szInnerCircle.getHeight()); 
		triangle.lineTo(_ptCenter.getX() + dRadius * Math.cos(dTheta1),
				_ptCenter.getY() + dRadius * Math.sin(dTheta1));
		dTheta1 = dTheta + _dPointerOffsetAngle;
		triangle.lineTo(_ptCenter.getX() + dRadius * Math.cos(dTheta1),
				_ptCenter.getY() + dRadius * Math.sin(dTheta1));
		triangle.closePath();
		g2d.setPaint(_colorArrow);
		g2d.fill(triangle);
		
		if (_bShowExtents) {
			g2d.setColor(Color.CYAN);
			g2d.setStroke(_strokeLines);
			g2d.drawRect(_rectExtent.x, _rectExtent.y, _rectExtent.width, _rectExtent.height);
		}
	}
	
	private class ModelListener
		implements ChangeListener, ObjectValueChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			setEnabled(_model.getModel().isEnabled());
		}

		@Override
		public void update(ObjectValueChangeEvent event) {
			repaint();
			
		}
		
	}

	private class MouseResponse
		extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			if (SwingUtilities.isLeftMouseButton(e)) {
				CompassControlModel model = _model.getModel();
				CircleOctant octant = AWTGeometry.findGraphicsRelativeOctant(e.getPoint(), _ptCenter);
				CompassPoint cpoint = CompassPoint.find(c -> c.getOctant().equals(octant));
				model.setCurrentValue(cpoint);
				ActionEvent eventAction = new ActionEvent(CompassRoseControl.this, 
						ActionEvent.ACTION_PERFORMED, model.getActionCommand(), e.getModifiersEx());
				model.relayActionEvent(eventAction);
			}
		}
		
	}
}
