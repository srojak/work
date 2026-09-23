/**
 * 
 */
package srojak.utest.debug;

import java.util.Objects;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.reflect.PackageClassLocator;
import srojak.debug.DebugContentVisitor;
import srojak.debug.DebugOptionNameValue;
import srojak.debug.DebugSwitch;

/**
 * @author Stephen
 *
 */
public class DebugContentTestVisitor1 
		implements DebugContentVisitor {
	private final ObservationCollector _collObs;
	private StringBuilder _sb;
	
	public DebugContentTestVisitor1(ObservationCollector collObs) {
		Objects.requireNonNull(collObs, "collObs");
		_collObs = collObs;
		_sb = null;
	}

	@Override
	public void visitClassSwitch(DebugSwitch sw) {
		_sb = new StringBuilder();
		_sb.append("for class ");
		_sb.append(sw.getKey().getClassLocator());
		_sb.append("\n  ");
		_sb.append(sw);
	}

	@Override
	public void visitSubjectSwitch(DebugSwitch sw) {
		_sb.append("\n  ");
		_sb.append(sw);
	}

	@Override
	public void visitClassOption(DebugOptionNameValue option) {
		_sb.append("\n  ");
		_sb.append(option);	
	}

	@Override
	public void endClass(PackageClassLocator locator) {
		_collObs.write(ObsLevel.INFO, _sb.toString());		
	}
}
