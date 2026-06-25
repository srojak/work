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

import srojak.core.observe.InvalidObservationLevelException;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterNull;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.tools.StringMethods;
import srojak.debug.DebugSwitchKey;
import srojak.debug.DebugSwitchTool;
import srojak.debug.impl.ClassDebugOptionMap;
import srojak.debug.impl.DebugNexusCore;
import srojak.debug.impl.DebugSwitchContent;
import srojak.xml.stream.XmlElementAttribute;
import srojak.xml.stream.XmlStreamEventsDictionary;
import srojak.xml.stream.XmlStreamInputBuilder;
import srojak.xml.stream.XmlStreamParserBase;

/**
 * @author Stephen
 *
 */
public class DebugConfigParser
		extends XmlStreamParserBase {
	private ObservationWriter _writer;
	private ObsLevel _levelDefault;
	private String _strPackageName;
	private PackageClassLocator _locClass;

	private static final XmlStreamEventsDictionary EVENTS;
	private static final QName ELEMENT_CTRLSET;
	private static final QName ELEMENT_PACKAGE;
	private static final QName ELEMENT_CLASS;
	private static final QName ELEMENT_SUBJECT;
	private static final QName ELEMENT_OPTION;
	private static final QName ATTRIB_NAME;
	private static final QName ATTRIB_LEVEL;
	private static final QName ATTRIB_LOCS;
	private static final QName ATTRIB_CASCADE;
	private static final QName ATTRIB_VALUE;
	private static final String[] BOOL_TRUE;
	
	static {
		EVENTS = new XmlStreamEventsDictionary();
		ELEMENT_CTRLSET = new QName("SwitchControlSet");
		ELEMENT_PACKAGE = new QName("Package");
		ELEMENT_CLASS = new QName("Class");
		ELEMENT_SUBJECT = new QName("Subject");
		ELEMENT_OPTION = new QName("Option");
		ATTRIB_NAME = new QName("name");
		ATTRIB_LEVEL = new QName("level");
		ATTRIB_LOCS = new QName("locs");
		ATTRIB_CASCADE = new QName("cascade");
		ATTRIB_VALUE = new QName("value");
		BOOL_TRUE = new String[] { "t", "true" };
	}
	
	/**
	 * @param builder
	 */
	public DebugConfigParser(XmlStreamInputBuilder builder) {
		super(builder);
		_writer = new ObservationWriterNull();
		_levelDefault = ObsLevel.WARN;
	}
	
	public ObservationWriter getObservationWriter() {
		return _writer;
	}
	
	public void setObservationWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writer = writer;
	}

	@Override
	protected void parseInit() {
		_strPackageName = null;
		_locClass = null;
	}

	@Override
	protected void parseEndDocument() {
		super.parseEndDocument();
	}

	@Override
	protected void parseStartElement(QName nameElement, XmlElementAttribute[] attributes) {
		if (nameElement.equals(ELEMENT_PACKAGE)) {
			XmlElementAttribute attrName = findAttributeByName(attributes, ATTRIB_NAME);
			_strPackageName = attrName.getValue();
		} else if (nameElement.equals(ELEMENT_CLASS)) {
			XmlElementAttribute attrName = findAttributeByName(attributes, ATTRIB_NAME);
			_locClass = new PackageClassLocator(_strPackageName, attrName.getValue());
			ObsLevel level = readObsLevel(nameElement, attributes, _levelDefault);
			boolean bShowSource = readBooleanAttribValue(ATTRIB_LOCS, attributes);
			boolean bCascade = readBooleanAttribValue(ATTRIB_CASCADE, attributes);
			DebugSwitchKey key = DebugSwitchTool.makeClassKey(_locClass);
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
		} else if (nameElement.equals(ELEMENT_SUBJECT)) {
			XmlElementAttribute attrName = findAttributeByName(attributes, ATTRIB_NAME);
			ObsLevel level = readObsLevel(nameElement, attributes, _levelDefault);
			boolean bShowSource = readBooleanAttribValue(ATTRIB_LOCS, attributes);
			DebugSwitchKey key = DebugSwitchTool.makeClassSubjectKey(_locClass, attrName.getValue());
			DebugSwitchContent sw = DebugNexusCore.getContent(key);
			if (sw == null) {
				sw = DebugNexusCore.createSwitch(key);
				DebugNexusCore.putContent(sw);
			}
			sw.setLevel(level);
			sw.setShowSourceLocations(bShowSource);
		} else if (nameElement.equals(ELEMENT_OPTION)) {
			XmlElementAttribute attrName = findAttributeByName(attributes, ATTRIB_NAME);
			XmlElementAttribute attrValue = findAttributeByName(attributes, ATTRIB_VALUE);
			int nValue = Integer.parseInt(attrValue.getValue());
			ClassDebugOptionMap options = DebugNexusCore.getOptionsForClass(_locClass);
			if (options == null) {
				options = DebugNexusCore.createOptionsForClass(_locClass);
			}
			options.putOption(attrName.getValue(), nValue);
		} else if (nameElement.equals(ELEMENT_CTRLSET)) {
			XmlElementAttribute attrName = findAttributeByName(attributes, ATTRIB_NAME);
			DebugNexusCore.readingSwitchControlSet(attrName.getValue());
		}
	}
	
	@Override
	protected void parseEndElement(QName nameElement, String strText) {
		if (nameElement.equals(ELEMENT_CLASS)) {
			_locClass = null;
		} else if (nameElement.equals(ELEMENT_PACKAGE))
			_strPackageName = null;
	}

	@Override
	protected void parseComment(String strText) {
		super.parseComment(strText);
	}

	@Override
	protected void parseOther(int nEventType) {
		String strEvent = EVENTS.getNameForCode(nEventType);
		_writer.write(ObsLevel.WARN, "unexpected event type " + strEvent);
	}
	
	private ObsLevel readObsLevel(QName nameElement, XmlElementAttribute[] attributes,
			ObsLevel levelDefault) {
		XmlElementAttribute attrLevel = findAttributeByName(attributes, ATTRIB_LEVEL);
		ObsLevel level = levelDefault;
		if (attrLevel != null) {
			String strLevel = attrLevel.getValue();
			try {
				level = ObsLevel.parse(strLevel);
			} catch (InvalidObservationLevelException exc) {
				Location loc = super.getParserState().getCurentLocation();
				_writer.write(ObsLevel.WARN, 
						String.format("line %d, element %s, attribute %s: unrecognized value \"%s\"",
								loc.getLineNumber(), nameElement, ATTRIB_LEVEL, strLevel));
			}
		}
		return level;
	}
	
	private boolean readBooleanAttribValue(QName nameAttribute,
			XmlElementAttribute[] attributes) {
		XmlElementAttribute attrib = findAttributeByName(attributes, nameAttribute);
		if (attrib != null && attrib.hasValue()) {
			String strValue = attrib.getValue();
			if (StringMethods.forAnyOfArray(strValue, (s, t) -> s.equals(t), BOOL_TRUE)) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	private boolean readShowSourceLocations(QName nameElement, XmlElementAttribute[] attributes) {
		XmlElementAttribute attrLocs = findAttributeByName(attributes, ATTRIB_LOCS);
		if (attrLocs != null && attrLocs.hasValue()) {
			String strLocs = attrLocs.getValue();
			if (StringMethods.forAnyOfArray(strLocs, (s, t) -> s.equals(t), 
					BOOL_TRUE)) {
				return true;
			}
		}
		return false;
	}
}
