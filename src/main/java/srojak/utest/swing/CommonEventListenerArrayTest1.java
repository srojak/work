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

import srojak.core.events.CommonEventListenerArray;
/**
 * @author Stephen
 *
 */
public class CommonEventListenerArrayTest1 {
	private CommonEventListenerArray _listeners;
	
	public CommonEventListenerArrayTest1() {
		_listeners = new CommonEventListenerArray();
	}
	
	public void addActionListener(ActionListener listener) {
		_listeners.add(ActionListener.class, listener);
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
		CommonEventListenerArrayTest1 app = new CommonEventListenerArrayTest1();
		CommonEventListenerArray array = app._listeners;
		System.out.println("Array size " + array.getListenerCount());
		app.addActionListener(e -> {
			System.out.println(" Hello");
		});
		AbstractAction listener2
				= new TestActionListener(e -> {
					System.out.println(" Listener2");
		});
		app.addActionListener(listener2);
		System.out.println("Add 2; array size " + array.getListenerCount());
		AbstractAction listener3
				= new TestActionListener(e -> {
					System.out.println(" Listener3");
		});
		app.addActionListener(listener3);
		System.out.println("Array " + array);
		app.executeAllActions();
		array.remove(ActionListener.class, listener2);
		System.out.println("Remove 2; array size " + array.getListenerCount());
		System.out.println("Array " + array);
		List<ActionListener> alist = array.getListeners(ActionListener.class);
		System.out.println("List of ActionListeners: list size " + alist.size());
	}
}
