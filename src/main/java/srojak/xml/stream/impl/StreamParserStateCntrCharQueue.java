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
package srojak.xml.stream.impl;

import java.util.Objects;

import javax.xml.namespace.QName;

import srojak.core.collections.TQueue;
import srojak.xml.XmlParseTextFilter;
import srojak.xml.filters.XmlParseTextNullFilter;
import srojak.xml.stream.XmlStreamParserStateWithChars;

/**
 * @author Stephen
 *
 */
public class StreamParserStateCntrCharQueue 
		extends StreamParserStateCtnrBase 
		implements XmlStreamParserStateWithChars {
	private final TQueue<String> _queuePendingText;
	private XmlParseTextFilter _filterText;

	/**
	 * 
	 */
	public StreamParserStateCntrCharQueue() {
		super();
		_queuePendingText = new TQueue<String>();
		_filterText = new XmlParseTextNullFilter();
	}
	
	@Override
	public XmlParseTextFilter getTextFilter() {
		return _filterText;
	}
	
	@Override
	public void setTextFilter(XmlParseTextFilter filter) {
		Objects.requireNonNull(filter, "filter");
		_filterText = filter;
	}

	@Override
	public void reset() {
		super.reset();
		_queuePendingText.clear();
	}

	@Override
	public void start() {
		super.start();
		_queuePendingText.clear();
	}

	@Override
	public void startElement(QName nameElement) {
		super.startElement(nameElement);
		_queuePendingText.clear();
	}

	@Override
	public void endElement(QName nameElementRead) {
		// check for text before calling
		super.endElement(nameElementRead);
		_queuePendingText.clear();
	}

	@Override
	public void clearCharacters() {
		_queuePendingText.clear();
	}

	@Override
	public void saveCharacters(String strChars) {
		_queuePendingText.enqueue(strChars);
	}

	@Override
	public void gatherCollectedText(QName nameCurrent, StringBuilder sbText) {
		int nSeq = 0;
		while (!_queuePendingText.isEmpty()) {
			String strText = _queuePendingText.dequeue();
			strText = _filterText.interpretText(nameCurrent, strText, nSeq++, this);
			sbText.append(strText);
		}
	}

}
