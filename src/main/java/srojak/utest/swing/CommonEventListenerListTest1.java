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
package srojak.utest.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.event.ChangeListener;

import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.numerics.OrderedComparison;
import srojak.utest.TestIdentifier;
import srojak.utest.UnitTestConditionInt;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 */
public class CommonEventListenerListTest1 {
	private CommonEventListenerList _listeners;
	
	public CommonEventListenerListTest1() {
		_listeners = new CommonEventListenerList();
	}
	
	public void addActionListener(ActionListener listener) {
		_listeners.add(ActionListener.class, listener);
	}
	
	public void addChangeListener(ChangeListener listener) {
		_listeners.add(ChangeListener.class, listener);
	}
	
	public void executeAllActions() {
		System.out.println("Execute all");
		ActionEvent event = new ActionEvent(this, 0, "exec");
		_listeners.forEachReversed(ActionListener.class, ls -> ls.actionPerformed(event));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommonEventListenerListTest1 app = new CommonEventListenerListTest1();
		UnitTestSeries series = new UnitTestSeries("CommonEventListenerList");
		ObservationWriter writer = new ObservationWriterPrintStream(System.err);
		series.getOptions().setObservationWriter(writer);
		
		CommonEventListenerStore listeners = app._listeners;
		series.expectValueWhere(TestIdentifier.name("at start"), "listener count",
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, 0),
					listeners.getListenerCount());
		app.addActionListener(e -> {
			System.out.println(" Hello");
		});
		AbstractAction listener2
				= new TestActionListener(e -> {
					System.out.println(" Listener2");
		});
		app.addActionListener(listener2);
		series.expectValueWhere(TestIdentifier.name("at start +2"), "listener count",
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, 2),
					listeners.getListenerCount());
		app.addChangeListener(e -> {
			System.out.print(" Changed");
		});
		AbstractAction listener3
				= new TestActionListener(e -> {
					System.out.println(" Listener3");
		});
		app.addActionListener(listener3);
		System.out.println("List " + listeners);
		app.executeAllActions();
		listeners.remove(ActionListener.class, listener2);
		series.expectValueWhere(TestIdentifier.name("after remove 2"), "listener count",
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, 3),
					listeners.getListenerCount());
		System.out.println("Array " + listeners);
		List<ActionListener> alist = listeners.getListeners(ActionListener.class);
		series.expectValueWhere(TestIdentifier.name("action listeners"), "size",
				UnitTestConditionInt.makeValueCondition(OrderedComparison.EQ, 2),
					alist.size());
		
		series.complete();
	}

}
