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
package srojak.cdo.swing.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.cdo.ColorSelectionProvider;
import srojak.cdo.events.ColorValueChangeListener;
import srojak.cdo.swing.components.ResponsiveColorRect;
import srojak.cdo.swing.components.ResponsiveRect;
import srojak.cdo.swing.models.ColorBoxSelectModel;
import srojak.cdo.swing.models.DefaultColorBoxSelectModel;
import srojak.core.NameToken;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.tools.ListMethods;
import srojak.events.CollectionChangeEvent;
import srojak.events.CollectionChangeListener;
import srojak.events.ObjValueChangeEvent;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ColorBoxSelectPanel
		extends NameTokenTagPanel {
	private final CommonEventListenerStore _listeners;
	private final LinkedList<ResponsiveColorRect> _listRects;
	private final Dimension _dmRect;
	private ResponsiveColorRect _rectSelected;
	private ColorBoxSelectModel _model;
	private ModelListener _listenerModel;
	
	/**
	 * 
	 */
	public ColorBoxSelectPanel(NameToken tokenName) {
		super(tokenName);
		_listeners = new CommonEventListenerList();
		_listRects = new LinkedList<ResponsiveColorRect>();
		_dmRect = new Dimension(30, 30);
		_rectSelected = null;
		postConstruct();
	}

	/**
	 * @param isDoubleBuffered
	 */
	public ColorBoxSelectPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, isDoubleBuffered);
		_listeners = new CommonEventListenerList();
		_listRects = new LinkedList<ResponsiveColorRect>();
		_dmRect = new Dimension(30, 30);
		_rectSelected = null;
		postConstruct();
	}
	
	private void postConstruct() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));	
		changeModel(null, new DefaultColorBoxSelectModel());
	}
	
	private void changeModel(ColorBoxSelectModel modelPrior, ColorBoxSelectModel modelNew) {
		if (modelPrior != null) {
			modelPrior.removeChangeListener(_listenerModel);
			modelPrior.removeItemListener(_listenerModel);
			modelPrior.removeCollectionChangeListener(_listenerModel);
			modelPrior.removeColorValueChangeListener(_listenerModel);
		}
		
		_model = modelNew;
		
		_listenerModel = new ModelListener();
		_model.addChangeListener(_listenerModel);
		_model.addItemListener(_listenerModel);
		_model.addCollectionChangeListener(_listenerModel);
		_model.addColorValueChangeListener(_listenerModel);
	}
	
	public Dimension getRectangleSize() {
		return _dmRect;
	}
	
	public void setRectangleSize(Dimension dmSize) {
		Objects.requireNonNull(dmSize, "dmSize");
		_dmRect.setSize(dmSize);
		for (ResponsiveColorRect rect : _listRects) {
			rect.setMinimumSize(_dmRect);
			rect.setPreferredSize(_dmRect);
		}
	}
	
	public ColorBoxSelectModel getModel() {
		return _model;
	}
	
	public void setModel(ColorBoxSelectModel model) {
		Objects.requireNonNull(model, "model");
		changeModel(getModel(), model);
		revalidate();
		repaint();
	}
	
	public ColorSelectionProvider getSelection() {
		return _model.getSelection();
	}
	
	private ResponsiveColorRect findRectFor(ColorSelectionProvider color) {
		return ListMethods.findInList(_listRects,
				r -> color.isSelectionColorEqual(r.getAssociatedObject()));
	}
	
	public void setSelection(ColorSelectionProvider color) {
		_model.setSelection(color);
	}
	
	public void setSelection1(ColorSelectionProvider color) {
		if (color != null) {
			Color colorSelection = color.getSelectionColor();
			for (ResponsiveColorRect rect : _listRects) {
				if (colorSelection.equals(rect.getAssociatedObject().getSelectionColor())) {
					_rectSelected = rect;
					_rectSelected.setSelected(true);
					_model.setSelection(_rectSelected.getAssociatedObject());
					/*
					ObjValueChangeEvent<Color> event 
							= new ObjValueChangeEvent<Color>(this, _colorSelected.getSelectionColor());
					raiseColorChangeEvent(event);
					*/
				}
			}
		}
	}
	
	public void addColorValueChangeListener(ColorValueChangeListener listener) {
		_listeners.add(ColorValueChangeListener.class, listener);
	}
	
	public void removeColorValueChangeListener(ColorValueChangeListener listener) {
		_listeners.remove(ColorValueChangeListener.class, listener);
	}
	
	protected void raiseColorChangeEvent(ObjValueChangeEvent<Color> event) {
		_listeners.forEachReversed(ColorValueChangeListener.class, ls -> ls.update(event));
	}
	
	private void clearPriorChoices() {
		while (!_listRects.isEmpty()) {
			ResponsiveRect rect = _listRects.removeFirst();
			remove(rect);
		}
	}
	
	protected void sizeControl() {
		Dimension szControl = new Dimension(_listRects.size() * _dmRect.width, _dmRect.height);
		setMinimumSize(szControl);
		setPreferredSize(szControl);
		repaint();
	}
	
	private void loadChoices(Collection<? extends ColorSelectionProvider> providers) {
		for (ColorSelectionProvider csp : providers) {
			ResponsiveColorRect rect = new ResponsiveColorRect(isDoubleBuffered(), csp);
			rect.setMinimumSize(_dmRect);
			rect.setPreferredSize(_dmRect);
			rect.addMouseListener(new SquareMouseAdapter());
			_listRects.addLast(rect);
			add(rect);
		}
		sizeControl();
	}

	private class SquareMouseAdapter
			extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			ResponsiveColorRect rr = (ResponsiveColorRect) e.getSource();
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (rr != _rectSelected) {
					if (_rectSelected != null) {
						_rectSelected.setSelected(false);
					}
					rr.setSelected(true);
					_rectSelected = rr;
					ColorSelectionProvider selected = rr.getAssociatedObject();
					_model.setSelection(selected);
					/* should come back around
					ObjValueChangeEvent<Color> event 
						= new ObjValueChangeEvent<Color>(this, _colorSelected.getSelectionColor());
					raiseColorChangeEvent(event);
					*/
				}
			}
		}
		
	}
	
	private class ModelListener
		implements ChangeListener, ItemListener, CollectionChangeListener, ColorValueChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			// the model communicates enabled state change
			if (isEnabled() != _model.isEnabled()) {
				setEnabled(_model.isEnabled());
				_listeners.sendToAll(ChangeListener.class, () -> new ChangeEvent(ColorBoxSelectPanel.this),
						(ls, ev) -> ls.stateChanged(ev));
				repaint();
			}
		}

		@Override
		public void collectionChanged(CollectionChangeEvent event) {
			// the model communicates changes to the set of choices
			switch (event.getVerb()) {
			case CollectionChangeEvent.VERB_CLEAR:
				clearPriorChoices();
				break;
				
			case CollectionChangeEvent.VERB_ADD_MULT:
				clearPriorChoices();
				loadChoices(_model.getChoices());
				repaint();
				break;
			}
			
		}

		@Override
		public void update(ObjValueChangeEvent<Color> event) {
			Color color = event.getValue();
			ObjValueChangeEvent<Color> eventRelay = color != null
					? new ObjValueChangeEvent<Color>(ColorBoxSelectPanel.this, color)
					: new ObjValueChangeEvent<Color>(ColorBoxSelectPanel.this, Color.class);
			_listeners.forEach(ColorValueChangeListener.class, ls -> ls.update(eventRelay));
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			// the model communicates item selection/deselection
			ColorSelectionProvider color = (ColorSelectionProvider) e.getItem();
			ResponsiveColorRect rectColor = findRectFor(color);
			switch (e.getStateChange()) {
			case ItemEvent.DESELECTED:
				if (rectColor != null) {
					if (rectColor == _rectSelected) {
						_rectSelected.setSelected(false);
						_rectSelected = null;
						_listeners.sendToAll(ColorValueChangeListener.class,
								() -> new ObjValueChangeEvent<Color>(ColorBoxSelectPanel.this,
										Color.class),
								(ls, ev) -> ls.update(ev));
						revalidate();
						repaint();
					}
				}
				break;
				
			case ItemEvent.SELECTED:
				if (rectColor != _rectSelected) {
					if (_rectSelected != null) {
						_rectSelected.setSelected(false);
						_rectSelected = null;
					}
					if (rectColor != null) {
						_rectSelected = rectColor;
						_rectSelected.setSelected(true);
					}
					_listeners.sendToAll(ColorValueChangeListener.class,
							() -> new ObjValueChangeEvent<Color>(ColorBoxSelectPanel.this,
									color.getSelectionColor()),
							(ls, ev) -> ls.update(ev));
					revalidate();
					repaint();
				}
				break;
			}
		}
		
	}
}
