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

import java.io.InputStream;
import java.util.Objects;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservedActivity;

/**
 * @author Stephen
 *
 */
public class XmlStreamInputFactory
		implements XmlStreamFactory {
	private final XMLInputFactory _factory;
	private ObservationCollector _collectObs;
	
	public XmlStreamInputFactory(boolean bNamespaceAware) {
		_factory = XMLInputFactory.newFactory();
		_collectObs = ObservationCollector.makeInstance();
		_factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.valueOf(bNamespaceAware));
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
	
	public boolean isNamespaceAware() {
		Boolean bValue = (Boolean) _factory.getProperty(XMLInputFactory.IS_NAMESPACE_AWARE);
		return bValue.booleanValue();
	}
	
	public XMLStreamReader createStreamReader(ObservedActivity activity, InputStream stream)
			throws XMLStreamException {
		Objects.requireNonNull(activity, "activity");
		_collectObs.write(ObsLevel.TRACE, "creating stream reader for " + activity.describe());
		return _factory.createXMLStreamReader(stream);
	}
	
	public XMLEventReader createEventReader(ObservedActivity activity, InputStream stream) 
			throws XMLStreamException {
		Objects.requireNonNull(activity, "activity");
		_collectObs.write(ObsLevel.TRACE, "creating event reader for " + activity.describe());
		return _factory.createXMLEventReader(stream);
	}
}
