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
package srojak.core.observe.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.InvalidOperationException;
import srojak.core.impl.MarkedObservationWriter;
import srojak.core.logic.FlagsShort;
import srojak.core.logic.FlagsShortTest;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollectWriterStore;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.code.CodeValuesObservation;

/**
 * @author Stephen
 *
 */
public abstract class ObservationCollectorBase
		implements ObservationCollectWriterStore {
	private final List<MarkedObservationWriter> _listWriters;
	private final FlagsShort _flags;
	
	public static final short FLAGS_HAS_PERM_WRITER = 0x1;
	public static final short FLAGS_WRITERS_LOCKED = 0x2;
	public static final short FLAGS_USE_FULL_CLASS_NAMES = 0x4;
	
	protected ObservationCollectorBase() {
		_listWriters = new LinkedList<MarkedObservationWriter>();
		_flags = new FlagsShort();
	}
	
	protected synchronized List<ObservationWriter> getAllWriters() {
		return _listWriters.stream().map(i -> i.getWrapped()).toList();
	}
	
	protected synchronized List<ObservationWriter> getWriters(ObsLevel level) {
		return _listWriters.stream().map(i -> i.getWrapped()).filter(w -> w.canWriteAt(level)).toList();
	}

	@Override
	public FlagsShortTest getFlags() {
		return _flags;
	}

	@Override
	public boolean hasPermanentWriters() {
		return _flags.test(FLAGS_HAS_PERM_WRITER);
	}

	@Override
	public boolean areWritesLocked() {
		return _flags.test(FLAGS_WRITERS_LOCKED);
	}
	
	protected void lockWritersInPlace() {
		_flags.set(FLAGS_WRITERS_LOCKED);
	}

	@Override
	public boolean useFullClassNames() {
		return _flags.test(FLAGS_USE_FULL_CLASS_NAMES);
	}

	@Override
	public void setUseFullClassNames(boolean bState) {
		_flags.apply(bState, FLAGS_USE_FULL_CLASS_NAMES);
	}

	@Override
	public synchronized void addWriter(ObservationWriter writer) {
		if (writer != null) {
			if (_flags.test(FLAGS_WRITERS_LOCKED)) {
				throw new InvalidOperationException("writers are locked");
			}
			if (!writer.canWrite()) {
				throw new IllegalArgumentException("writer is not valid");
			}
			_listWriters.add(new MarkedObservationWriter(writer, false));
		}
	}

	@Override
	public synchronized void addWriterPermanent(ObservationWriter writer) {
		if (writer != null) {
			if (_flags.test(FLAGS_WRITERS_LOCKED)) {
				throw new InvalidOperationException("writers are locked");
			}
			if (!writer.canWrite()) {
				throw new IllegalArgumentException("writer is not valid");
			}
			_listWriters.add(new MarkedObservationWriter(writer, true));
			_flags.set(FLAGS_HAS_PERM_WRITER);
		}
	}
	
	@Override
	public synchronized void removeWriter(ObservationWriter writer) {
		if (writer != null) {
			if (_flags.test(FLAGS_WRITERS_LOCKED)) {
				throw new InvalidOperationException("writers are locked");
			}
			Iterator<MarkedObservationWriter> iterator = _listWriters.iterator();
			while (iterator.hasNext()) {
				MarkedObservationWriter item = iterator.next();
				if (item.isForWriter(writer)) {
					if (item.isPermanent()) {
						throw new InvalidOperationException("remove writer", "writer is permanent");
					}
					iterator.remove();
					return;
				}
			}
		}
	}
	
	public synchronized void expiredWriter(ObservationWriter writer) {
		if (writer != null) {
			MarkedObservationWriter entryWriter = null;
			Iterator<MarkedObservationWriter> iterator = _listWriters.iterator();
			while (iterator.hasNext()) {
				MarkedObservationWriter item = iterator.next();
				if (item.isForWriter(writer)) {
					entryWriter = item;
					break;
				}
			}
			if (entryWriter != null) {
				_listWriters.remove(entryWriter);
				_flags.clear(FLAGS_WRITERS_LOCKED);
			}
		}
	}
	
	private synchronized void removeAllWriters() {
		_listWriters.clear();
	}

	@Override
	public void replaceAllWriters(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		if (_flags.test(FLAGS_WRITERS_LOCKED)) {
			throw new InvalidOperationException("writers are locked");
		}
		if (_flags.test(FLAGS_HAS_PERM_WRITER)) {
			throw new InvalidOperationException("remove all writers", "at least one writer is permanent");
		}
		removeAllWriters();
		addWriter(writer);
	}
	
	void writeCodeObservation(CodeValuesObservation observation) {
		List<ObservationWriter> listActive = getWriters(observation.level());
		if (!listActive.isEmpty()) {
			listActive.forEach(w -> w.writeObserved(observation));
		}
	}
}
