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
package srojak.xml.stream.factories;

import java.io.OutputStream;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;

/**
 * @author Stephen
 *
 */
public class XmlStreamOutputFactory 
		implements XmlStreamFactory {
	private final XMLOutputFactory _factory;
	private ObservationCollector _collectObs;
	
	public XmlStreamOutputFactory() {
		_factory = XMLOutputFactory.newInstance();
		_collectObs = ObservationCollector.makeInstance();
	}

	@Override
	public ObservationCollector getObservationCollector() {
		return _collectObs;
	}

	@Override
	public Object getFactoryProperty(String name) {
		return _factory.getProperty(name);
	}

	@Override
	public void setFactoryProperty(String name, Object value) {
		_factory.setProperty(name, value);
	}

	public XMLStreamWriter createWriter(OutputStream stream) 
			throws XMLStreamException {
		_collectObs.write(ObsLevel.TRACE, "creating stream writer");
		return _factory.createXMLStreamWriter(stream);
	}
	
	public XMLEventWriter createEventWriter(OutputStream stream)
			throws XMLStreamException {
		_collectObs.write(ObsLevel.TRACE, "creating event writer");
		return _factory.createXMLEventWriter(stream);
	}
}
