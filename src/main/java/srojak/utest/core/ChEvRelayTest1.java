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

import srojak.core.collections.ChangeEventingListRelay;
import srojak.core.collections.IChangeEventingList;
/**
 * @author Stephen
 *
 */
public class ChEvRelayTest1 {
	public ChangeEventingStringStore _store;
	public ChangeEventingListRelay<String> _relay;
	
	public ChEvRelayTest1() {
		_store = new ChangeEventingStringStore();
		_relay = new ChangeEventingListRelay<String>();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChEvRelayTest1 app = new ChEvRelayTest1();
		IChangeEventingList<String> list = app._store.getList();
		list.add("First");
		list.add("Second");
		list.add("Third");
		app._store.getDirtyFlag().reset();

		list.addChangeListener(app, e -> {
			System.out.println("Change event " + e.getVerb());
		});
		app._relay.addChangeListener(app, e -> {
			System.out.println("Relay change event " + e.getVerb());
		});
		app._relay.bind(list);
		app._relay.add("Fourth");
		app._relay.unbind();
		app._relay.add("zero");
		System.out.println("Inner list has " + list.size() + " items");
		System.out.println("Is store dirty? " + app._store.getDirtyFlag().getState());
	}

}
