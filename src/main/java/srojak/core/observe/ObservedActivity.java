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
package srojak.core.observe;

import srojak.core.observe.activity.SingleActivity;

/**
 * @author Stephen
 *
 */
public interface ObservedActivity {

	String describe();
	
	public static final ObservedActivity READ_FROM_FILE = new SingleActivity("read from file");
	public static final ObservedActivity READ_RESOURCE = new SingleActivity("read resource");
	public static final ObservedActivity READ_SCHEMA = new SingleActivity("read schema");
	public static final ObservedActivity SCALAR_PARSE = new SingleActivity("parse scalar");
	public static final ObservedActivity VALIDATE = new SingleActivity("validate");
	public static final ObservedActivity WRITE_TO_FILE = new SingleActivity("write to file");
}
