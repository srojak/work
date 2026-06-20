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

import java.util.Objects;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.core.NameToken;
import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;
import srojak.events.IntValueChangeEvent;
import srojak.events.IntValueChangeListener;
import srojak.events.IntValueChangeEventOriginator;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class IntegerSpinnerPanel 
		extends NameTokenTagPanel
		implements IntValueChangeEventOriginator {
    private SpinnerNumberModel _model;
    private JSpinner _spinner;
    private SingleEventListenerStore<IntValueChangeListener> _listeners;
    
	/**
	 * 
	 */
	public IntegerSpinnerPanel(NameToken tokenName, SpinnerNumberModel model) {
		super(tokenName);
		Objects.requireNonNull(model, "model");
		_model = model;
	   	_spinner = new JSpinner(_model);
	   	_listeners = new SingleEventListenerList<IntValueChangeListener>();
	   	postConstruct();
	}

	/**
	 * @param isDoubleBuffered
	 */
	public IntegerSpinnerPanel(NameToken tokenName, boolean isDoubleBuffered, SpinnerNumberModel model) {
		super(tokenName, isDoubleBuffered);
		Objects.requireNonNull(model, "model");
		_model = model;
	   	_spinner = new JSpinner(_model);
	   	_listeners = new SingleEventListenerList<IntValueChangeListener>();
	   	postConstruct();
	}
	
	private void postConstruct() {
		add(_spinner);
		_model.addChangeListener(new SpinnerListener());
	}
	
	public SpinnerNumberModel getModel() {
		return _model;
	}
	
	public int getValue() {
		return (int) _model.getNumber();
	}

	@Override
	public void addIntValueChangeListener(IntValueChangeListener listener) {
		_listeners.add(listener);
	}

	@Override
	public void removeIntValueChangeListener(IntValueChangeListener listener) {
		_listeners.remove(listener);
	}

	class SpinnerListener
			implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			IntValueChangeEvent event 
				= new IntValueChangeEvent(IntegerSpinnerPanel.class, getValue());
			_listeners.forEach(ls -> ls.update(event));
		}
		
	}
}
