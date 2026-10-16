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
package srojak.kernel.services.lib;

import java.util.LinkedList;
import java.util.List;

import srojak.kernel.KernelServiceEndpointControl;
import srojak.kernel.ServiceNames;
import srojak.kernel.impl.KernelEndpointHome;
import srojak.kernel.services.AppKernelService;
import srojak.kernel.services.KernelEndpointService;
import srojak.kernel.services.KernelServiceProviderBase;

/**
 * @author Stephen
 *
 */
@AppKernelService(name = ServiceNames.ENDPOINT, lazyLoad = false)
public class EndpointServiceProvider 
		extends KernelServiceProviderBase 
		implements KernelEndpointService, KernelEndpointHome {
	private final List<KernelServiceEndpointControl> _listEndpoints;

	/**
	 * 
	 */
	public EndpointServiceProvider() {
		_listEndpoints = new LinkedList<KernelServiceEndpointControl>();
	}

	@Override
	public void attach(KernelServiceEndpointControl endpoint) {
		_listEndpoints.add(endpoint);
	}

	@Override
	public void detach(KernelServiceEndpointControl endpoint) {
		_listEndpoints.remove(endpoint);
	}

	@Override
	public int countOpenEndpoints() {
		return _listEndpoints.size();
	}
}
