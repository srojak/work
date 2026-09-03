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
package srojak.cdo.swing.models;

import srojak.cdo.CanBeEnabled;
import srojak.cdo.ModelAttribCodes;
import srojak.cdo.events.ModelChangeEvent;
import srojak.cdo.events.ModelChangeListener;
import srojak.cdo.events.ModelChangeOriginator;
import srojak.cdo.swing.CDOControlModel;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.logic.FlagsInt;

/**
 * @author Stephen
 *
 */
public abstract class ControlModelBase 
		implements CDOControlModel, CanBeEnabled, ModelChangeOriginator, ModelAttribCodes {
	protected final CommonEventListenerStore _listeners;
	protected final FlagsInt _flags;
	
	public ControlModelBase() {
		_listeners = new CommonEventListenerList();
		_flags = new FlagsInt();
	}

	@Override
	public boolean isEnabled() {
		return _flags.test(F_ENABLED);
	}

	@Override
	public void setEnabled(boolean bState) {
		_flags.apply(bState, F_ENABLED);
		fireModelChanged(MA_ENABLED);
	}
	
	protected void fireModelChanged(int idAttrib) {
		ModelChangeEvent event = new ModelChangeEvent(this, idAttrib);
		_listeners.forEach(ModelChangeListener.class, ls -> ls.attribChanged(event));
	}

	@Override
	public void addModelChangeListener(ModelChangeListener listener) {
		_listeners.add(ModelChangeListener.class, listener);	
	}

	@Override
	public void removeModelChangeListener(ModelChangeListener listener) {
		_listeners.remove(ModelChangeListener.class, listener);	
	}
}
