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
package srojak.core.faults;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import srojak.core.ClassAndContent;
import srojak.core.containers.ClassAndContentCarrier;

/**
 * @author Stephen
 *
 */
public class FaultMethods {
	
	private static final int SPACES_PER_INDENT = 4;
	
	public static String getThrowableClassName(boolean bUseFullClassName, Throwable t) {
		Class<?> classThrowable = t.getClass();
		if (bUseFullClassName) {
			return classThrowable.getName();
		} else {
			return classThrowable.getSimpleName();
		}
	}
	
	public static ClassAndContent defaultContentFormatter(Throwable t) {
		ClassAndContentCarrier cc = new ClassAndContentCarrier(t.getClass());
		String strMessage = t.getMessage();
		if (strMessage != null) {
			String[] message = t.getMessage().split("\\R");
			cc.addContent(message);
		}
		return cc;
	}
	
	public static StructuredFault process(Throwable t) {
		return process(t, FaultMethods::defaultContentFormatter);
	}

	public static StructuredFault process(Throwable t, ThrowableContentFormatter formatter) {
		if (t == null) {
			return new StructuredNullFault();
		} else {
			StructuredCommonFault fault = null;
			if (t instanceof Exception exc) {
				fault = new StructuredCommonFault(exc, formatter.format(t));
			} else if (t instanceof Error err) {
				fault = new StructuredCommonFault(err, formatter.format(t));
			} else {
				return new StructuredNullFault();
			}
			Throwable cause = t.getCause();
			if (cause != null) {
				fault.setCause(process(cause, formatter));
			}
			return fault;
		}
	}
	
	private static void formatFaultTo(StringBuilder sb, EnumSet<FaultFormat> formats, StructuredFault fault, int levelIndent) {
		CharSequence csIndent = " ".repeat(levelIndent * SPACES_PER_INDENT);
		ClassAndContent cc = fault.getContent();
		sb.append(cc.getReferencedClassName(formats.contains(FaultFormat.ShowFullClassName)));
		Iterator<String> iterator = cc.getContent().iterator();
		while (iterator.hasNext()) {
			sb.append("\n  ");
			sb.append(csIndent);
			sb.append(iterator.next());
		}
		if (formats.contains(FaultFormat.ShowStack)) {
			sb.append("\n");
			sb.append(csIndent);
			sb.append("stack trace:");
			for (StackTraceElement frame : fault.getStackTrace()) {
				sb.append("\n  ");
				sb.append(csIndent);
				sb.append(frame);
			}
		}
		if (formats.contains(FaultFormat.ShowCauses) && fault.hasCause()) {
			sb.append("\n cause: ");
			formatFaultTo(sb, formats, fault.getCause(), levelIndent + 1);
		}
	}
	
	public static void formatFaultTo(StringBuilder sb, EnumSet<FaultFormat> formats, StructuredFault fault) {
		if (fault.getFaultType() == FaultType.NULL) {
			sb.append("**null throwable");
		} else {
			sb.append(" caught ");
			formatFaultTo(sb, formats, fault, 0);
		}
	}
}
