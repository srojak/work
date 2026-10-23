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
package srojak.kernel.services.lib;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import srojak.core.excepts.DuplicateKeyException;
import srojak.core.field.SetOnce;
import srojak.core.keys.ClassKey;
import srojak.core.sequence.DaySequencer;
import srojak.core.sequence.OrderedSequence;
import srojak.core.sequence.SimpleSequencer;
import srojak.kernel.KernelCentralStore;
import srojak.kernel.KernelSequenceKey;
import srojak.kernel.KernelServiceException;
import srojak.kernel.ServiceNames;
import srojak.kernel.endpoints.CalendarEndpoint;
import srojak.kernel.priv.KernelStore;
import srojak.kernel.services.AppKernelService;
import srojak.kernel.services.DependsOnService;
import srojak.kernel.services.KernelScheduleService;
import srojak.kernel.services.KernelSequenceService;
import srojak.kernel.services.KernelServiceProviderBase;

/**
 * @author Stephen
 *
 */
@AppKernelService(name = ServiceNames.SEQUENCER, mustShutDown = true)
@DependsOnService(ServiceNames.SCHEDULER)
public class SequenceServiceProvider 
		extends KernelServiceProviderBase 
		implements KernelSequenceService {
	private final SetOnce<KernelScheduleService> _svcSched;
	private final Map<ClassKey, OrderedSequence> _mapClassSequences;
	private final Map<KernelSequenceKey, OrderedSequence> _mapNameKeyedSequences;
	private CalendarEndpoint _endptCalendar;

	/**
	 * 
	 */
	public SequenceServiceProvider() {
		_svcSched = new SetOnce<KernelScheduleService>(SetOnce.DEFAULT);
		_mapClassSequences = new HashMap<ClassKey, OrderedSequence>();
		_mapNameKeyedSequences = new HashMap<KernelSequenceKey, OrderedSequence>();
		_endptCalendar = null;
	}

	@Override
	public void initialize(KernelCentralStore store) 
			throws KernelServiceException {
		KernelScheduleService scheduler = KernelStore.getServiceOfType(ServiceNames.SCHEDULER);
		_svcSched.set(scheduler);
		_endptCalendar = scheduler.getCalendarEndpoint();
	}

	@Override
	public void shutdown() {
		if (_endptCalendar != null) {
			_endptCalendar.orderlyClose();
			_endptCalendar = null;
		}
	}

	@Override
	public void assignSequence(ClassKey keyClass) {
		Objects.requireNonNull(keyClass, "keyClass");
		if (_mapClassSequences.containsKey(keyClass)) {
			throw new DuplicateKeyException("sequence exists for " + keyClass);
		}
		long lnSeries = System.identityHashCode(keyClass.getReferencedClass());
		SimpleSequencer sequencer = new SimpleSequencer(lnSeries);
		_mapClassSequences.put(keyClass, sequencer);
	}

	@Override
	public void assignDatedSequence(ClassKey keyClass) {
		Objects.requireNonNull(keyClass, "keyClass");
		if (_mapClassSequences.containsKey(keyClass)) {
			throw new DuplicateKeyException("sequence exists for " + keyClass);
		}
		long lnSeries = System.identityHashCode(keyClass.getReferencedClass());
		DaySequencer sequencer = new DaySequencer(lnSeries, _endptCalendar.mostRecentDate());
		_endptCalendar.addLifeCycleListener(sequencer);
		_endptCalendar.addCalendarListener(sequencer);
		_mapClassSequences.put(keyClass, sequencer);
	}

	@Override
	public void assignSequence(KernelSequenceKey keySequence) {
		Objects.requireNonNull(keySequence, "keySequence");
		if (_mapNameKeyedSequences.containsKey(keySequence)) {
			throw new DuplicateKeyException("sequence exists for " + keySequence);
		}
		long lnSeries = System.identityHashCode(keySequence.getName());
		SimpleSequencer sequencer = new SimpleSequencer(lnSeries);
		_mapNameKeyedSequences.put(keySequence, sequencer);
	}

	@Override
	public void assignDatedSequence(KernelSequenceKey keySequence) {
		Objects.requireNonNull(keySequence, "keySequence");
		if (_mapNameKeyedSequences.containsKey(keySequence)) {
			throw new DuplicateKeyException("sequence exists for " + keySequence);
		}
		long lnSeries = System.identityHashCode(keySequence.getName());
		DaySequencer sequencer = new DaySequencer(lnSeries, _endptCalendar.mostRecentDate());
		_endptCalendar.addLifeCycleListener(sequencer);
		_endptCalendar.addCalendarListener(sequencer);
		_mapNameKeyedSequences.put(keySequence, sequencer);
	}

	@Override
	public OrderedSequence getSequenceFor(ClassKey keyClass) {
		Objects.requireNonNull(keyClass, "keyClass");
		OrderedSequence sequence = _mapClassSequences.get(keyClass);
		if (sequence == null) {
			throw new NoSuchElementException();
		}
		return sequence;
	}

	@Override
	public OrderedSequence getSequenceFor(KernelSequenceKey keySequence) {
		Objects.requireNonNull(keySequence, "keySequence");
		OrderedSequence sequence = _mapNameKeyedSequences.get(keySequence);
		if (sequence == null) {
			throw new NoSuchElementException();
		}
		return sequence;
	}

}
