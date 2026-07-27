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
package srojak.debug.config;

import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.reflect.PackageClassLocator;
import srojak.debug.DebugNexus;

/**
 * @author Stephen
 *
 */
public class DebugConfigInterpreter {
	@SuppressWarnings("unused")
	private DebugNexus _nexus;
	private ObservationWriter _writer;
	
	public DebugConfigInterpreter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_nexus = new DebugNexus();
		_writer = writer;
	}
	
	protected void Interpret(Document docConfig) {
		Element elemRoot = docConfig.getDocumentElement();
		NodeList listPackages = elemRoot.getElementsByTagName("Package");
		for (int idx = 0; idx < listPackages.getLength(); idx++) {
			Node node = listPackages.item(idx);
			if (node instanceof Element elemPackage) {
				InterpretPackageElement(elemPackage);
			}
		}
	}
	
	protected void InterpretPackageElement(Element elemPackage) {
		String strName = elemPackage.getAttribute("name");
		if (strName == null) {
			_writer.write(ObsLevel.ERROR, "Package has no name");
			return;
		}
		NodeList listClasses = elemPackage.getElementsByTagName("Class");
		for (int idx = 0; idx < listClasses.getLength(); idx++) {
			Node node = listClasses.item(idx);
			if (node instanceof Element elemClass) {
				InterpretClassElement(elemClass, strName);
			}
		}
	}
	
	protected void InterpretClassElement(Element elemClass, String strPackageName) {
		String strName = elemClass.getAttribute("name");
		String strLevel = elemClass.getAttribute("level");
		if (strName == null) {
			_writer.write(ObsLevel.ERROR, "Class in package " + strPackageName + " has no name");
			return;
		}
		PackageClassLocator locClass = new PackageClassLocator(strPackageName, strName);
		@SuppressWarnings("unused")
		ObsLevel level = ObsLevel.WARN;
		if (strLevel != null) {
			try {
				level = ObsLevel.parse(strLevel);
			} catch (IllegalArgumentException exc) {
				_writer.write(ObsLevel.WARN, "Class " + locClass 
						+ " has unrecognized debug level [" + strLevel + "]");
			}
		}
		
	}
	
	protected void InterpretClassSubjectElement(Element elemSubject, PackageClassLocator locClass) {
		String strName = elemSubject.getAttribute("name");
		@SuppressWarnings("unused")
		String strLevel = elemSubject.getAttribute("level");
		if (strName == null) {
			_writer.write(ObsLevel.ERROR, "Subject in class " + locClass + " has no name");
			return;
		}
		
	}
}
