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
package srojak.cdo;

import java.awt.Component;
import java.awt.Container;
import java.util.List;
import java.util.Objects;

import srojak.core.functional.TreeDepthArgConsumer;
import srojak.core.functional.TreeDepthConsumer;
import srojak.core.functional.TreeDepthPredicate;

/**
 * @author Stephen
 *
 */
public class ContainerMethods {

	private static void walkNodes(Component component, int depth,
			TreeDepthConsumer<Component> consumer) {
		consumer.accept(depth, component);
		if (component instanceof Container ctnr) {
			depth += 1;
			for (Component child : ctnr.getComponents()) {
				walkNodes(child, depth, consumer);
			}
		}
	}
	
	public static void walkComponentTree(Container container, TreeDepthConsumer<Component> consumer) {
		Objects.requireNonNull(container, "container");
		Objects.requireNonNull(consumer, "consumer");
		walkNodes(container, 0, consumer);
	}
	
	private static <C extends Component> void walkNodes(Component component, int depth,
			Class<C> classNode, TreeDepthConsumer<C> consumer) {
		if (classNode.isAssignableFrom(component.getClass())) {
			@SuppressWarnings("unchecked")
			C c = (C) component;
			consumer.accept(depth, c);
		}
		if (component instanceof Container ctnr) {
			depth += 1;
			for (Component child : ctnr.getComponents()) {
				walkNodes(child, depth, classNode, consumer);
			}
		}
	}
	
	public static <C extends Component> void walkComponentTree(Container container,
			Class<C> classNode, TreeDepthConsumer<C> consumer) {
		Objects.requireNonNull(container, "container");
		Objects.requireNonNull(classNode, "classNode");
		Objects.requireNonNull(consumer, "consumer");
		walkNodes(container, 0, classNode, consumer);
	}
	
	private static <C extends Component> void walkNodesAndCollect(Component component,
			List<? super C> list, int depth, Class<C> classNode, TreeDepthPredicate<C> predicate) {
		if (classNode.isAssignableFrom(component.getClass())) {
			@SuppressWarnings("unchecked")
			C c = (C) component;
			if (predicate.test(depth, c)) {
				list.add(c);
			}
		}
		if (component instanceof Container ctnr) {
			depth += 1;
			for (Component child : ctnr.getComponents()) {
				walkNodesAndCollect(child, list, depth, classNode, predicate);
			}
		}
	}
	
	public static <C extends Component> void walkComponentTreeAndCollect(Container container,
			List<? super C> list, Class<C> classNode, TreeDepthPredicate<C> predicate) {
		Objects.requireNonNull(container, "container");
		Objects.requireNonNull(list, "list");
		Objects.requireNonNull(classNode, "classNode");
		Objects.requireNonNull(predicate, "predicate");
		walkNodesAndCollect(container, list, 0, classNode, predicate);
	}
	
	private static <U> void walkNodes(Component component, U arg, int depth,
			TreeDepthArgConsumer<Component, U> consumer) {
		consumer.accept(depth, component, arg);
		if (component instanceof Container ctnr) {
			depth += 1;
			for (Component child : ctnr.getComponents()) {
				walkNodes(child, arg, depth, consumer);
			}
		}
	}
	
	public static <U> void walkComponentTree(Container container, U arg,
			TreeDepthArgConsumer<Component, U> consumer) {
		Objects.requireNonNull(container, "container");
		Objects.requireNonNull(arg, "arg");
		Objects.requireNonNull(consumer, "consumer");
		walkNodes(container, arg, 0, consumer);
	}
	
	private static <C extends Component, U> void walkNodes(Component component, U arg, int depth,
			Class<C> classNode, TreeDepthArgConsumer<C, U> consumer) {
		if (classNode.isAssignableFrom(component.getClass())) {
			@SuppressWarnings("unchecked")
			C c = (C) component;
			consumer.accept(depth, c, arg);
		}
		if (component instanceof Container ctnr) {
			depth += 1;
			for (Component child : ctnr.getComponents()) {
					walkNodes(child, arg, depth, classNode, consumer);
			}
		}
	}
	
	public static <C extends Component, U> void walkComponentTree(Container container, U arg,
			Class<C> classNode, TreeDepthArgConsumer<C, U> consumer) {
		Objects.requireNonNull(container, "container");
		Objects.requireNonNull(arg, "arg");
		Objects.requireNonNull(classNode, "classNode");
		Objects.requireNonNull(consumer, "consumer");
		walkNodes(container, arg, 0, classNode, consumer);
	}
}
