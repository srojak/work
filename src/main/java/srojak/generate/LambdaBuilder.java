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
package srojak.generate;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaConversionException;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Objects;

import srojak.core.observe.CoreObservers;
import srojak.core.observe.HasReplaceableObservationCollector;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollectorCore;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.observe.code.CodeValueMethods;
import srojak.core.reflect.InterfaceMethods;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;

/**
 * @author Stephen
 *
 */
public class LambdaBuilder
		implements HasReplaceableObservationCollector {
	private final MethodHandles.Lookup _lookup;
	private ObservationCollectorCore _collectObs;
	private boolean _bInvokeExact;
	
	public static final ObservedActivity ACTIVITY_BUILD_LAMBDA = new SingleActivity("build lambda");
	public static final ObservedActivity ACTIVITY_GET_HANDLE = new SingleActivity("get handle");
	
	public LambdaBuilder(MethodHandles.Lookup proxy) {
		Objects.requireNonNull(proxy, "proxy");
		_lookup = proxy;
		_collectObs = CoreObservers.OBS_NULL;
		_bInvokeExact = false;
	}
	
	@Override
	public ObservationCollectorCore getObservationCollector() {
		return _collectObs;
	}

	@Override
	public void setObservationCollector(ObservationCollectorCore collector) {
		Objects.requireNonNull(collector, "collector");
		_collectObs = collector;
		_collectObs.record(ObsLevel.DETAIL, "collector changed");
	}
	
	public boolean isUsingInvokeExact() {
		return _bInvokeExact;
	}
	
	public void setInvokeExact(boolean bState) {
		_bInvokeExact = bState;
	}
	
	public XResultOf<MethodHandle> getHandleOf(Method method) {
		Objects.requireNonNull(method, "method");
		XResultCarrierOf<MethodHandle> result = new XResultCarrierOf<MethodHandle>(ACTIVITY_GET_HANDLE);
		try {
			MethodHandle handle = _lookup.unreflect(method);
			result.setResult(handle);
		} catch (IllegalAccessException exc) {
			_collectObs.recordThrowable(ObsLevel.ERROR, ACTIVITY_GET_HANDLE, exc, false);
			result.caughtThrowable(exc);
		}
		return result;
	}

	public XResultOf<MethodHandle> findStaticMethod(Class<?> classOwner, String strMethodName, MethodType typeMethod) {
		Objects.requireNonNull(classOwner, "classOwner");
		Objects.requireNonNull(strMethodName, "strMethodName");
		Objects.requireNonNull(typeMethod, "typeMethod");
		XResultCarrierOf<MethodHandle> result = new XResultCarrierOf<MethodHandle>(ACTIVITY_BUILD_LAMBDA);
		try {
			MethodHandle handle = _lookup.findStatic(classOwner, strMethodName, typeMethod);
			result.setResult(handle);
		} catch (NoSuchMethodException | IllegalAccessException exc) {
			_collectObs.recordThrowable(ObsLevel.ERROR, ACTIVITY_BUILD_LAMBDA, exc, false);
			result.caughtThrowable(exc);
		}
		return result;
	}
	
	public XResultOf<MethodHandle> findInstanceMethod(Class<?> classOwner, String strMethodName, MethodType typeMethod) {
		Objects.requireNonNull(classOwner, "classOwner");
		Objects.requireNonNull(strMethodName, "strMethodName");
		Objects.requireNonNull(typeMethod, "typeMethod");
		XResultCarrierOf<MethodHandle> result = new XResultCarrierOf<MethodHandle>(ACTIVITY_BUILD_LAMBDA);
		try {
			MethodHandle handle = _lookup.findVirtual(classOwner, strMethodName, typeMethod);
			result.setResult(handle);
		} catch (NoSuchMethodException | IllegalAccessException exc) {
			_collectObs.recordThrowable(ObsLevel.ERROR, ACTIVITY_BUILD_LAMBDA, exc, false);
			result.caughtThrowable(exc);
		}
		return result;	
	}
	
	private Object invokeLexprFromSite(CallSite site) throws Throwable {
		MethodHandle hClosure = site.getTarget();		
		if (_bInvokeExact) {
			return hClosure.invokeExact();
		} else {
			return hClosure.invoke();
		}
	}
	
	public <T> XResultOf<T> createLambdaForStatic(Class<T> classLambda,
			MethodHandle hMethod, MethodType typeImpl) {
		Objects.requireNonNull(classLambda, "classLambda");
		Objects.requireNonNull(hMethod, "hMethod");
		Objects.requireNonNull(typeImpl, "typeImpl");
		Method methodIntf = InterfaceMethods.getFunctionalInterfaceMethod(classLambda);
		if (methodIntf == null) {
			throw new IllegalArgumentException(classLambda.getSimpleName() + " is not a functional interface");
		}
		XResultCarrierOf<T> result = new XResultCarrierOf<T>(ACTIVITY_BUILD_LAMBDA);
		MethodType typeIntf = MethodType.methodType(methodIntf.getReturnType(), methodIntf.getParameterTypes());
		String strIntfMethodName = methodIntf.getName();
		_collectObs.observe(ObsLevel.DEBUG, cb -> {
			cb.appendValue("meta.", CodeValueMethods.capture("classLambda", classLambda.getSimpleName()));
			cb.appendValue("meta.", CodeValueMethods.capture("methodName", strIntfMethodName));
			cb.appendValue("meta.", CodeValueMethods.capture("typeIntf", typeIntf));
			cb.appendValue("meta.", CodeValueMethods.capture("typeImpl", typeImpl));
		});
		
		CallSite site = null;
		try {
			site = LambdaMetafactory.metafactory(
					_lookup,
					strIntfMethodName,
					MethodType.methodType(classLambda),
					typeIntf,
					hMethod,
					typeImpl);
		} catch (LambdaConversionException exc) {
			result.caughtThrowable(exc);
			return result;
		}
		
		MethodType typeSite = site.type();
		_collectObs.observe(ObsLevel.DEBUG, cb -> {
			cb.appendValue("site.", CodeValueMethods.capture("mtype", typeSite));
		});
		
		try {
			Object objLambda = invokeLexprFromSite(site);
			T lambda = classLambda.cast(objLambda);
			result.setResult(lambda);
		} catch (Throwable e) {
			result.caughtThrowable(e);
		}
		return result;
	}
	
	public <T> XResultOf<T> createLambdaForBoundInstance(Class<T> classLambda,
			MethodHandle hMethod, Object objImpl, MethodType typeImpl) {
		Objects.requireNonNull(classLambda, "classLambda");
		Objects.requireNonNull(hMethod, "hMethod");
		Objects.requireNonNull(objImpl, "objImpl");
		Objects.requireNonNull(typeImpl, "typeImpl");
		Method methodIntf = InterfaceMethods.getFunctionalInterfaceMethod(classLambda);
		if (methodIntf == null) {
			throw new IllegalArgumentException(classLambda.getSimpleName() + " is not a functional interface");
		}
		XResultCarrierOf<T> result = new XResultCarrierOf<T>(ACTIVITY_BUILD_LAMBDA);
		MethodType typeIntf = MethodType.methodType(methodIntf.getReturnType(), methodIntf.getParameterTypes());
		String strIntfMethodName = methodIntf.getName();
		
		CallSite site = null;
		try {
			site = LambdaMetafactory.metafactory(
					_lookup,
					strIntfMethodName,
					MethodType.methodType(classLambda, objImpl.getClass()),
					typeIntf,
					hMethod,
					typeImpl);
		} catch (LambdaConversionException exc) {
			result.caughtThrowable(exc);
			return result;
		}
		
		MethodType typeSite = site.type();
		_collectObs.observe(ObsLevel.DEBUG, cb -> {
			cb.appendValue("site.", CodeValueMethods.capture("mtype", typeSite));
		});
		
		MethodHandle hClosure = site.getTarget();		
		try {
			Object objLambda = hClosure.bindTo(objImpl).invokeWithArguments();
			T lambda = classLambda.cast(objLambda);
			result.setResult(lambda);
		} catch (Throwable e) {
			result.caughtThrowable(e);
		}
		return result;
	}
	
	public <T> XResultOf<T> createLambdaForUnboundInstance(Class<T> classLambda,
			MethodHandle hMethod, MethodType typeImpl) {
		Objects.requireNonNull(classLambda, "classLambda");
		Objects.requireNonNull(hMethod, "hMethod");
		Objects.requireNonNull(typeImpl, "typeImpl");
		Method methodIntf = InterfaceMethods.getFunctionalInterfaceMethod(classLambda);
		if (methodIntf == null) {
			throw new IllegalArgumentException(classLambda.getSimpleName() + " is not a functional interface");
		}
		XResultCarrierOf<T> result = new XResultCarrierOf<T>(ACTIVITY_BUILD_LAMBDA);
		MethodType typeIntf = MethodType.methodType(methodIntf.getReturnType(), methodIntf.getParameterTypes());
		String strIntfMethodName = methodIntf.getName();
		
		CallSite site = null;
		try {
			site = LambdaMetafactory.metafactory(
					_lookup,
					strIntfMethodName,
					MethodType.methodType(classLambda),
					typeIntf,
					hMethod,
					typeImpl);
		} catch (LambdaConversionException exc) {
			result.caughtThrowable(exc);
			return result;
		}
		
		MethodType typeSite = site.type();
		_collectObs.observe(ObsLevel.DEBUG, cb -> {
			cb.appendValue("site.", CodeValueMethods.capture("mtype", typeSite));
		});
		
		try {
			Object objLambda = invokeLexprFromSite(site);
			T lambda = classLambda.cast(objLambda);
			result.setResult(lambda);
		} catch (Throwable e) {
			result.caughtThrowable(e);
		}
		return result;
	}
}
