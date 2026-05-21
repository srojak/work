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
package srojak.xml.dom;

import java.io.FileOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
/**
 * @author Stephen
 *
 */
public class XmlDomIO {
	
	public static void writeToFile(Document doc, FileOutputStream file)
			throws TransformerException {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer xf = factory.newTransformer();
		xf.setOutputProperty(OutputKeys.INDENT, "yes");
		xf.setOutputProperty(OutputKeys.METHOD, "xml");
		xf.transform(new DOMSource(doc), new StreamResult(file));
	}
}
