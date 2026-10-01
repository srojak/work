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
package srojak.core.props;

import java.nio.file.Path;

import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.result.XResult;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.core.result.XResultModifierFlags;

/**
 * @author Stephen
 *
 */
public class DevEnvProperties 
		extends PropertySetBase 
		implements DevEnvPropertyKeys {
	
	public static final String PROPERTIES_FILE_NAME = "devenv.txt";
	public static final int FLAGS_IS_DEV_ENV = 0x2;
	public static final int FLAGS_IS_ECLIPSE = 0x4;
	
	private static final ObservedActivity ACTIVITY_LOAD = new SingleActivity("load dev env properties");

	/**
	 * 
	 */
	public DevEnvProperties() {
		super();
	}

	@Override
	protected void postLoad() {
		_flags.set(FLAGS_IS_DEV_ENV);
		_flags.apply(evalBooleanProperty(IS_ECLIPSE, false), FLAGS_IS_ECLIPSE);
	}
	
	public boolean isDevEnv() {
		return _flags.test(FLAGS_IS_DEV_ENV);
	}
	
	public boolean isEclipse() {
		return _flags.test(FLAGS_IS_ECLIPSE);
	}
	
	public XResultInt loadFromParent() {
		XResultIntCarrier result = new XResultIntCarrier(ACTIVITY_LOAD);
		if (_flags.test(FLAGS_WAS_LOADED)) {
			result.setResult(XResultModifierFlags.MOD_DONE_ONCE);
			return result;
		}
		Path pathCurrent = Path.of(System.getProperty("user.dir"));
		
		setProperty(DIR_MODULE, pathCurrent.toString());
		Path pathParent = pathCurrent.resolve("..").normalize();
		XResult resultLoad = loadFrom(pathParent, PROPERTIES_FILE_NAME);
		result.copyFrom(resultLoad);
		if (resultLoad.isValid()) {
			setProperty(DIR_WORKSPACE, pathParent.toString());
		}
		return result;
	}
}
