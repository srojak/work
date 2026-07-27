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
package srojak.cdo.containers;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;

import srojak.core.field.SetOnce;
import srojak.core.field.SetOnceConditions;
import srojak.core.observe.ObsLevel;
import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class ResourceImage {
	private final Class<?> _classOwner;
	private final String _strName;
	private final SetOnce<Image> _image;
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(ResourceImage.class));
	}
	
	/**
	 * Constructor. 
	 *  
	 * @param classOwner A class in the same package as the image resource.
	 * @param strName The name of the image resource.
	 */
	public ResourceImage(Class<?> classOwner, String strName) {
		Objects.requireNonNull(classOwner, "classOwner");
		Objects.requireNonNull(strName, "strName");
		_classOwner = classOwner;
		_strName = strName;
		_image = new SetOnce<Image>(SetOnceConditions.DEFAULT);
	}
	
	public XResult load() {
		_image.faultIfAlreadySet();
		XResultStatusCarrier result = new XResultStatusCarrier();
		URL urlImage = _classOwner.getResource(_strName);
		if (urlImage == null) {
			String strMessage = "cannot locate resource \"" + _strName + "\" using "
					+ _classOwner.getName();
			_swDebugClass.write(ObsLevel.ERROR, strMessage);
			result.caughtException(new IOException(strMessage));
		} else {
			try {
				Image image = ImageIO.read(urlImage);
				_image.set(image);
				result.setValid();
			} catch (IOException exc) {
				result.caughtException(exc);
			}
		}
		return result;
	}
	
	public boolean isValid() {
		return _image.hasBeenSet();
	}
	
	public String getOriginName() {
		return _strName;
	}
	
	public Image getImage() {
		return _image.get();
	}

}
