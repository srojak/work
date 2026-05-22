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
package srojak.utest.core;

import srojak.core.collections.IChangeEventingList;
/**
 * @author Stephen
 *
 */
public class ChEvArrayTest1 {
	public ChangeEventingStringStore _store;
	
	public ChEvArrayTest1() {
		_store = new ChangeEventingStringStore();
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChEvArrayTest1 app = new ChEvArrayTest1();
		IChangeEventingList<String> list = app._store.getList();
		list.add("First");
		list.add("Second");
		list.add("Third");
		app._store.getDirtyFlag().reset();
		
		list.addChangeListener(app, e -> {
			System.out.println("Change event " + e.getVerb());
		});
		list.add("Fourth");
		System.out.println("Is store dirty? " + app._store.getDirtyFlag().getState());
	}

}
