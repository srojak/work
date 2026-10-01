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
package srojak.debug;

import java.util.Objects;

import srojak.core.observe.HasObsLevel;
import srojak.core.observe.ObsLevel;
import srojak.debug.impl.ClassDebugStoreComparatorByKey;
import srojak.debug.impl.DebugNexusCore;

/**
 * @author Stephen
 *
 * The public class through which to read debug switches and options.
 */
public final class DebugContentReader 
		implements HasObsLevel {
	private ObsLevel _levelWrite;
	
	private ClassDebugStoreComparatorByKey COMPARE_STORE_BY_KEY = new ClassDebugStoreComparatorByKey();

	/**
	 * @param writer
	 */
	public DebugContentReader() {
		_levelWrite = ObsLevel.INFO;
	}

	@Override
	public ObsLevel getObsLevel() {
		return _levelWrite;
	}

	@Override
	public void setObsLevel(ObsLevel level) {
		ObsLevel.validateEventLevel(level);
		_levelWrite = level;
	}
	
	public void visitAllContent(DebugContentVisitor visitor) {
		Objects.requireNonNull(visitor, "visitor");
		DebugNexusCore.getAllClassEntries().sorted(COMPARE_STORE_BY_KEY).forEach(s -> {
			s.visit(visitor);
		});
	}
	
	public void visitAllContentForPackage(DebugContentVisitor visitor, String strPackage) {
		Objects.requireNonNull(visitor, "visitor");
		Objects.requireNonNull(strPackage, "strPackage");
		DebugNexusCore.getAllClassEntries().filter(s -> s.getLocator().getPackageName().equals(strPackage))
			.sorted(COMPARE_STORE_BY_KEY).forEach(s -> {
				s.visit(visitor);
		});
	}
}
