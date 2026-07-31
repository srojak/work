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

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.cdo.swing.components.ResponsiveRect;
import srojak.cdo.swing.models.ColorBoxSelectModelBase;
import srojak.core.InvalidOperationException;
import srojak.core.NameToken;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.tools.ListMethods;
import srojak.events.CollectionChangeEvent;
import srojak.events.CollectionChangeListener;
import srojak.events.ObjectValueChangeEvent;
import srojak.events.ObjectValueChangeListener;

/**
 * 
 * @author Stephen
 *
 * @param <C> A type that either is a {@code Color} or an object bearing color.
 * @param <R> A type of rectangular control that can properly display the chosen colors.
 */
/* ISSUE: the previous panel sent out ObjectChangeValueEvents. Does this panel still need that,
 * or can the model take care of it?
 */
@SuppressWarnings("serial")
public abstract class ColorBoxSelectPanelBase<C, R extends ResponsiveRect> 
		extends NameTokenTagPanel {
	private final CommonEventListenerStore _listeners;
	private final LinkedList<R> _listRects;
	private final Dimension _dmRect;
	private final Class<C> _classData;
	private R _rectSelected;
	private ColorBoxSelectModelBase<C> _model;
	private ModelListener _listenerModel;

	/**
	 * @param tokenName
	 */
	public ColorBoxSelectPanelBase(NameToken tokenName, Class<C> classData) {
		super(tokenName);
		Objects.requireNonNull(classData, "classData");
		_listeners = new CommonEventListenerList();
		_listRects = new LinkedList<R>();
		_dmRect = new Dimension(30, 30);
		_rectSelected = null;
		_classData = classData;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));	
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public ColorBoxSelectPanelBase(NameToken tokenName, boolean isDoubleBuffered, Class<C> classData) {
		super(tokenName, isDoubleBuffered);
		Objects.requireNonNull(classData, "classData");
		_listeners = new CommonEventListenerList();
		_listRects = new LinkedList<R>();
		_dmRect = new Dimension(30, 30);
		_rectSelected = null;
		_classData = classData;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));	
	}
	
	public Dimension getRectangleSize() {
		return _dmRect;
	}
	
	public void setRectangleSize(Dimension dmSize) {
		Objects.requireNonNull(dmSize, "dmSize");
		_dmRect.setSize(dmSize);
		for (R rect : _listRects) {
			rect.setMinimumSize(_dmRect);
			rect.setPreferredSize(_dmRect);
		}
	}
	
	private void changeModel(ColorBoxSelectModelBase<C> modelPrior,
			ColorBoxSelectModelBase<C> modelNew) {
		if (modelPrior != null) {
			modelPrior.removeChangeListener(_listenerModel);
			modelPrior.removeItemListener(_listenerModel);
			modelPrior.removeCollectionChangeListener(_listenerModel);
			modelPrior.removeObjectValueChangeListener(_listenerModel);
		}
		
		_model = modelNew;
		
		_listenerModel = new ModelListener();
		_model.addChangeListener(_listenerModel);
		_model.addItemListener(_listenerModel);
		_model.addCollectionChangeListener(_listenerModel);
		_model.addObjectValueChangeListener(_listenerModel);
	}
	
	public ColorBoxSelectModelBase<C> getModel() {
		return _model;
	}
	
	public <M extends ColorBoxSelectModelBase<C>> M getModelAs() {
		@SuppressWarnings("unchecked")
		M model2 = (M) _model;
		return model2;
	}
	
	public void setModel(ColorBoxSelectModelBase<C> model) {
		Objects.requireNonNull(model, "model");
		changeModel(getModel(), model);
		revalidate();
		repaint();
	}
	
	public C getSelection() {
		return _model.getSelection();
	}
	
	public void setSelection(C selection) {
		_model.setSelection(selection);
	}
	
	protected void forEachRect(Consumer<? super R> consumer) {
		_listRects.forEach(consumer);
	}
	
	protected abstract boolean isRectFor(R rect, C color);
	
	protected R findRectFor(C color) {
		return ListMethods.findInList(_listRects,
				r -> isRectFor(r, color));
	}
	
	private void clearPriorChoices() {
		while (!_listRects.isEmpty()) {
			R rect = _listRects.removeFirst();
			remove(rect);
		}
	}
	
	protected void sizeControl() {
		Dimension szControl = new Dimension(_listRects.size() * _dmRect.width, _dmRect.height);
		setMinimumSize(szControl);
		setPreferredSize(szControl);
		repaint();
	}
	
	protected abstract R createRectFor(boolean isDoubleBuffered, C color);
	
	private void loadChoices(Collection<? extends C> colors) {
		for (C color : colors) {
			R rect = createRectFor(isDoubleBuffered(), color);
			if (!rect.hasData()) {
				throw new InvalidOperationException("rectangle has no associated data");
			}
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
			@SuppressWarnings("unchecked")
			R rr = (R) e.getSource();
			if (SwingUtilities.isLeftMouseButton(e)) {
				if (rr != _rectSelected) {
					if (_rectSelected != null) {
						_rectSelected.setSelected(false);
					}
					rr.setSelected(true);
					_rectSelected = rr;
					@SuppressWarnings("unchecked")
					C selected = (C) rr.getData();
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
		implements ChangeListener, ItemListener, CollectionChangeListener, 
				ObjectValueChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			// the model communicates enabled state change
			if (isEnabled() != _model.isEnabled()) {
				setEnabled(_model.isEnabled());
				_listeners.sendToAll(ChangeListener.class, 
						() -> new ChangeEvent(ColorBoxSelectPanelBase.this),
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
		public void update(ObjectValueChangeEvent event) {
			@SuppressWarnings("unchecked")
			C color = (C) event.getValue();
			ObjectValueChangeEvent eventRelay = color != null
					? new ObjectValueChangeEvent(ColorBoxSelectPanelBase.this, color)
					: new ObjectValueChangeEvent(ColorBoxSelectPanelBase.this, _classData);
			_listeners.forEach(ObjectValueChangeListener.class, ls -> ls.update(eventRelay));
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			// the model communicates item selection/deselection
			@SuppressWarnings("unchecked")
			C color = (C) e.getItem();
			R rectColor = findRectFor(color);
			switch (e.getStateChange()) {
			case ItemEvent.DESELECTED:
				if (rectColor != null) {
					if (rectColor == _rectSelected) {
						_rectSelected.setSelected(false);
						_rectSelected = null;
						_listeners.sendToAll(ObjectValueChangeListener.class,
								() -> new ObjectValueChangeEvent(ColorBoxSelectPanelBase.this,
										_classData),
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
					_listeners.sendToAll(ObjectValueChangeListener.class,
							() -> new ObjectValueChangeEvent(ColorBoxSelectPanelBase.this,
									color),
							(ls, ev) -> ls.update(ev));
					revalidate();
					repaint();
				}
				break;
			}
		}
		
	}
}
