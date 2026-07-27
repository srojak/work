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
package srojak.psq;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.CommonCollectionSize;
import srojak.core.EmptyCollectionException;
import srojak.core.NameToken;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.events.CollectionChangeEvent;
import srojak.events.CollectionChangeEventOriginator;
import srojak.events.CollectionChangeListener;
import srojak.events.CollectionSizeChangeEvent;
import srojak.events.CollectionSizeChangeListener;
import srojak.spatial.S2Coords;
import srojak.spatial.S2Geometry;
import srojak.spatial.S2Segment;
import srojak.spatial.S2Surface;

/**
 * @author Stephen
 *
 */
public class PlaneSquarePath
		implements CommonCollectionSize, CollectionChangeEventOriginator {
	private final CommonEventListenerStore _listeners;
	private final LinkedList<S2Coords> _listCoords;
	private final S2Surface _surface;
	
	public static final NameToken ClassToken;
	@SuppressWarnings("unused")
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = PlaneSquarePath.class;
		ClassToken = NameToken.classNameFactory(classThis);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	public PlaneSquarePath(S2Surface surface) {
		Objects.requireNonNull(surface, "surface");
		_listeners = new CommonEventListenerList();
		_listCoords = new LinkedList<S2Coords>();
		_surface = surface;
	}

	@Override
	public boolean isEmpty() {
		return _listCoords.isEmpty();
	}

	@Override
	public int size() {
		return _listCoords.size();
	}
	
	private void raiseCollectionChanged(CollectionChangeEvent event) {
		_listeners.forEach(CollectionChangeListener.class, ls -> {
			ls.collectionChanged(event);
		});
		CollectionSizeChangeEvent eventSize = new CollectionSizeChangeEvent(this, _listCoords.size());
		_listeners.forEach(CollectionSizeChangeListener.class, ls -> {
			ls.sizeChanged(eventSize);
		});
	}
	
	public void clear() {
		boolean bWasEmpty = _listCoords.isEmpty();
		_listCoords.clear();
		if (!bWasEmpty) {
			CollectionChangeEvent event
				= new CollectionChangeEvent(this, CollectionChangeEvent.VERB_CLEAR);
			raiseCollectionChanged(event);
		}
	}
	
	public S2Coords get(int index) {
		return _listCoords.get(index);
	}
	
	public S2Coords getLast() {
		return _listCoords.getLast();
	}
	
	public List<S2Segment> getAsSegments() {
		return S2Geometry.pointsToSegments(_listCoords, true);
	}
	
	public void add(S2Coords coords) {
		Objects.requireNonNull(coords, "coords");
		_listCoords.addLast(coords);
		CollectionChangeEvent event
			= new CollectionChangeEvent(this, CollectionChangeEvent.VERB_ADD, coords);
		raiseCollectionChanged(event);
	}
	
	public List<S2Coords> getAll() {
		return _listCoords.stream().toList();
	}
	
	public void removeLast() {
		if (_listCoords.isEmpty()) {
			throw new EmptyCollectionException();
		}
		S2Coords coords =_listCoords.removeLast();
		CollectionChangeEvent event
			= new CollectionChangeEvent(this, CollectionChangeEvent.VERB_REMOVE, coords);
		raiseCollectionChanged(event);
	}
	
	public boolean removeAllStartingWith(S2Coords coordsRemove) {
		Objects.requireNonNull(coordsRemove, "coordsRemove");
		int indexStart = _listCoords.lastIndexOf(coordsRemove);
		if (indexStart < 0) {
			return false;
		}
		while (_listCoords.size() > indexStart) {
			_listCoords.removeLast();
		}
		CollectionChangeEvent event
			= new CollectionChangeEvent(this, CollectionChangeEvent.VERB_REMOVE_MULT);
		raiseCollectionChanged(event);
		return true;
	}

	@Override
	public void addCollectionChangeListener(CollectionChangeListener listener) {
		_listeners.add(CollectionChangeListener.class, listener);
	}

	@Override
	public void removeCollectionChangeListener(CollectionChangeListener listener) {
		_listeners.remove(CollectionChangeListener.class, listener);
	}

	@Override
	public void addCollectionSizeChangeListener(CollectionSizeChangeListener listener) {
		_listeners.add(CollectionSizeChangeListener.class, listener);
	}

	@Override
	public void removeCollectionSizeChangeListener(CollectionSizeChangeListener listener) {
		_listeners.remove(CollectionSizeChangeListener.class, listener);
	}
}
