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

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;

import srojak.core.containers.SingletonContainer;
import srojak.core.data.DataErrorSeverity;
import srojak.core.observe.InvalidObservationLevelException;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultOf;
import srojak.debug.DebugSwitchKey;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.impl.ClassElementProduct;
import srojak.debug.config.impl.PackageElementProduct;
import srojak.debug.impl.ClassDebugOptionMap;
import srojak.debug.impl.DebugNexusCore;
import srojak.debug.impl.DebugSwitchContent;
import srojak.xml.stream.StreamElementAttribute;
import srojak.xml.stream.StreamElementAttributeSet;
import srojak.xml.stream.XmlStreamActionParserBase;

/**
 * @author Stephen
 *
 */
public class DebugConfigParser 
		extends XmlStreamActionParserBase 
		implements DebugConfigNames {
	private final SingletonContainer<PackageElementProduct> _ctnrPackage;
	private final SingletonContainer<ClassElementProduct> _ctnrClass;
	private ObsLevel _levelDefault;
	
	/**
	 * 
	 */
	public DebugConfigParser() {
		super();
		_ctnrPackage = new SingletonContainer<PackageElementProduct>();
		_ctnrClass = new SingletonContainer<ClassElementProduct>();
		_levelDefault = ObsLevel.INFO;
	}
	
	public ObsLevel getDefaultObsLevel() {
		return _levelDefault;
	}
	
	public void setDefaultObsLevel(ObsLevel level) {
		Objects.requireNonNull(level, "level");
		_levelDefault = level;
	}

	@Override
	protected void parseInit() {
		_ctnrPackage.clear();
		_ctnrClass.clear();
	}
	
	private ObsLevel readObsLevel(QName nameElement, StreamElementAttributeSet attribs,
			ObsLevel levelDefault) {
		StreamElementAttribute attrLevel = attribs.findAttributeByName(ATTRIB_LEVEL);
		ObsLevel level = levelDefault;
		if (attrLevel != null) {
			String strLevel = attrLevel.getValue();
			try {
				level = ObsLevel.parse(strLevel);
			} catch (InvalidObservationLevelException exc) {
				Location loc = super.getParserState().getCurentLocation();
				ObservationWriter writer = getObservationWriter();
				writer.write(ObsLevel.WARN, 
						String.format("line %d, element %s, attribute %s: unrecognized value \"%s\"",
								loc.getLineNumber(), nameElement, ATTRIB_LEVEL, strLevel));
			}
		}
		return level;
	}

	@Override
	protected void parseStartElement(QName nameElement, StreamElementAttributeSet attribs)
			throws XMLStreamException {
		if (nameElement.equals(ELEMENT_PACKAGE)) {
			StreamElementAttribute attrName = attribs.findAttributeByName(ATTRIB_NAME);
			Location location = getParserState().getCurentLocation();
			XResultOf<String> result = attribs.readRequiredStringAttribValue(ATTRIB_NAME);
			if (result.isValid()) {
				PackageElementProduct product = new PackageElementProduct(ELEMENT_PACKAGE, location, result.getResult());
				_ctnrPackage.set(product);
			} else {
				recordElementParseError(location, ELEMENT_PACKAGE, DataErrorSeverity.ERROR, "element has no name");
			}
		} else if (nameElement.equals(ELEMENT_CLASS)) {
			if (!_ctnrPackage.isEmpty()) {
				Location location = getParserState().getCurentLocation();
				String strPackageName = _ctnrPackage.get().getPackageName();
				PackageClassLocator locator = null;
				XResultOf<String> result = attribs.readRequiredStringAttribValue(ATTRIB_NAME);
				if (result.isValid()) {
					locator = new PackageClassLocator(strPackageName, result.getResult());
				} else {
					recordElementParseError(location, ELEMENT_CLASS, DataErrorSeverity.ERROR, "element has no name");
					return;
				}
				ObsLevel level = readObsLevel(nameElement, attribs, _levelDefault);
				boolean bShowSource = attribs.readBooleanAttribValue(ATTRIB_LOCS);
				boolean bCascade = attribs.readBooleanAttribValue(ATTRIB_CASCADE);
				ClassElementProduct product = new ClassElementProduct(ELEMENT_CLASS, location, locator);
				_ctnrClass.set(product);
				DebugSwitchKey key = DebugSwitchTool.makeClassKey(locator);
				DebugSwitchContent sw = DebugNexusCore.getContent(key);
				if (sw == null) {
					sw = DebugNexusCore.createSwitch(key);
					DebugNexusCore.putContent(sw);
				}
				sw.setLevel(level);
				sw.setShowSourceLocations(bShowSource);
				if (bCascade) {
					DebugNexusCore.enableBaseClassSwitches(key);
				}
			}
		} else if (nameElement.equals(ELEMENT_SUBJECT)) {
			if (!_ctnrClass.isEmpty()) {
				Location location = getParserState().getCurentLocation();
				PackageClassLocator locClass = _ctnrClass.get().getClassLocator();
				String strSubjectName = null;
				XResultOf<String> result = attribs.readRequiredStringAttribValue(ATTRIB_NAME);
				if (result.isValid()) {
					strSubjectName = result.getResult();
				} else {
					recordElementParseError(location, ELEMENT_SUBJECT, DataErrorSeverity.ERROR, "element has no name");
					return;
				}
				ObsLevel level = readObsLevel(nameElement, attribs, _levelDefault);
				boolean bShowSource = attribs.readBooleanAttribValue(ATTRIB_LOCS);
				DebugSwitchKey key = DebugSwitchTool.makeClassSubjectKey(locClass, strSubjectName);
				DebugSwitchContent sw = DebugNexusCore.getContent(key);
				if (sw == null) {
					sw = DebugNexusCore.createSwitch(key);
					DebugNexusCore.putContent(sw);
				}
				sw.setLevel(level);
				sw.setShowSourceLocations(bShowSource);
			}
		} else if (nameElement.equals(ELEMENT_OPTION)) {
			if (!_ctnrClass.isEmpty()) {
				Location location = getParserState().getCurentLocation();
				PackageClassLocator locClass = _ctnrClass.get().getClassLocator();
				String strOptionName = null;
				XResultOf<String> result = attribs.readRequiredStringAttribValue(ATTRIB_NAME);
				if (result.isValid()) {
					strOptionName = result.getResult();
				} else {
					recordElementParseError(location, ELEMENT_OPTION, DataErrorSeverity.ERROR, "element has no name");
					return;
				}
				int nValue = 0;
				XResultInt resultValue = attribs.readIntAttribValue(ATTRIB_VALUE);				
				if (resultValue.isValid()) {
					nValue = resultValue.getResult();
				} else {
					recordElementParseError(location, ELEMENT_OPTION, DataErrorSeverity.ERROR, strOptionName + " has no value");
					return;
				}
				ClassDebugOptionMap options = DebugNexusCore.getOptionsForClass(locClass);
				if (options == null) {
					options = DebugNexusCore.createOptionsForClass(locClass);
				}
				options.putOption(strOptionName, nValue);
			}
		} else if (nameElement.equals(ELEMENT_CTRLSET)) {
			StreamElementAttribute attrName = attribs.findAttributeByName(ATTRIB_NAME);
			DebugNexusCore.readingSwitchControlSet(attrName.getValue());
		}
	}

	@Override
	protected void parseEndElement(QName nameElement, String strElementText) {
		if (nameElement.equals(ELEMENT_CLASS)) {
			_ctnrClass.clear();
		} else if (nameElement.equals(ELEMENT_PACKAGE))
			_ctnrPackage.clear();
	}

}
