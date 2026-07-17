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
package srojak.core.observe;

/**
 * @author Stephen
 *
 * The minimum interface every write-to destination must offer
 */
public interface ObservationCommonWriter {
	
	boolean isLevelAccepted(ObsLevel level);
	
	/**
	 * Write the contents of an observation collector.
	 * @param collector The observation collector bearing the content.
	 * @param locOrigin The location at which the collector was created.
	 * @param strText The text content from the collector.
	 */
	void write(ObservationCollector collector, SourceLocation locOrigin, String strText);
	
	/**
	 * Write a diagnostic message.
	 * @param strText The text of the message.
	 */
	void writeDiagnostic(String strText);
}
