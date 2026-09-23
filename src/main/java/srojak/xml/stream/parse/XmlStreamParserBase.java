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
package srojak.xml.stream.parse;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.ObservationCollector;
import srojak.core.text.TextBufferSegment;
import srojak.core.tools.StringMethods;
import srojak.xml.stream.XmlPendingTextCollector;
import srojak.xml.stream.XmlStreamMethods;
import srojak.xml.stream.impl.StreamParserStateBasicCtnr;
import srojak.xml.stream.work.XmlStreamWorkItemMap;

/**
 * @author Stephen
 *
 */
public abstract class XmlStreamParserBase 
		extends XmlStreamParserCommonBase
		implements XmlStreamParser {
	private final XmlStreamWorkItemMap _mapWork;	
	private final XmlPendingTextCollector _collectText;
	private ObservationCollector _collectObs;

	protected static final boolean DEFAULT_TO_NULL_WRITER = false;
	
	/**
	 * @param bDefaultToNullWriter
	 */
	public XmlStreamParserBase() {
		super(DEFAULT_TO_NULL_WRITER);
		_mapWork = new XmlStreamWorkItemMap();
		_collectText = new XmlPendingTextCollector();
		_collectObs = ObservationCollector.makeInstance();
	}

	@Override
	public ObservationCollector getObservationCollector() {
		return _collectObs;
	}

	protected abstract void parseInit();

	protected void parseEndDocument() {

	}

	protected abstract void parseStartElement(QName nameElement, XmlStreamWorkItemMap mapWork, StreamElementAttributeSet attribs) 
			throws XMLStreamException ;

	protected abstract void parseEndElement(QName nameElement, XmlStreamWorkItemMap mapWork, String strElementText);

	protected void parseComment(TextBufferSegment segment) {

	}

	protected void parseOther(int nEventType) {

	}

	@Override
	protected void prepareToRun() {
		_mapWork.clear();
		_collectText.setIgnoreInitialWhiteSpace(removeLeadingWhiteSpace());
		parseInit();
	}

	@Override
	protected void interpret(int nEvent, Location loc, StreamParserStateBasicCtnr ctnrState, String strEventType)
			throws XMLStreamException {
		ObservationCollector writerObs = getObservationCollector();
		
		QName nameCurrent = null;
		StreamElementAttributeSet attribs = null;
		String strElementText = null;
		TextBufferSegment segment = null;
		{
			SingleObservationCollector collectNode = writerObs.createCollector(ObsLevel.DEBUG2);
			collectNode.append("read event ");
			collectNode.append(strEventType);
			collectNode.append(" at location ");
			collectNode.append( XmlStreamMethods.format(loc));
			if (nEvent == START_ELEMENT) {
				nameCurrent = getElementName();
				attribs = getAttributes();
				collectNode.append(" start element ");
				collectNode.append(nameCurrent);
				collectNode.append(", ");
				collectNode.append(attribs.size());
				collectNode.append(" attributes");		
			} else if (nEvent == END_ELEMENT) {
				nameCurrent = getElementName();
				collectNode.append(" end element ");
				collectNode.append(nameCurrent);
			}
			collectNode.commit();
		}
		
		switch (nEvent) {
		case START_DOCUMENT:
			break;
			
		case END_DOCUMENT:
			ctnrState.clearAtElementStart();
			_collectText.reset();
			break;
			
		case PROCESSING_INSTRUCTION:
			break;
		
		case START_ELEMENT:
			_collectText.reset();
			ctnrState.startElement(nameCurrent);
			parseStartElement(nameCurrent, _mapWork, attribs);
			break;
			
		case END_ELEMENT:
			if (!_collectText.isEmpty()) {
				strElementText = _collectText.getContent();
				writerObs.write(ObsLevel.DEBUG, "pending text " + strElementText.length() + " chars");
			}
			ctnrState.endElement(nameCurrent);
			parseEndElement(nameCurrent, _mapWork, strElementText);			
			break;
			
		case COMMENT:
			segment = getCharacterSegment();
			if (recordComments()) {
				writerObs.write(ObsLevel.INFO, "at " + XmlStreamMethods.format(loc) 
						+ " comment: " + StringMethods.encloseInQuotes(segment.toString()));
			}
			parseComment(segment);
			break;
			
		case CHARACTERS:
			if (ctnrState.isAtElementStart()) {
				segment = getCharacterSegment();
				_collectText.acceptChars(segment);
			}
			break;
			
		case CDATA:
			if (ctnrState.isAtElementStart()) {
				segment = getCharacterSegment();
				_collectText.acceptCData(segment);
			}
			break;
			
		case SPACE:
			break;
			
		default:
			ctnrState.clearAtElementStart();
			parseOther(nEvent);
			break;
		}
	}
}
