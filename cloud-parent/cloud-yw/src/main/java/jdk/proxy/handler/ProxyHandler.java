package jdk.proxy.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyHandler<T> implements InvocationHandler {

	/**  */
	private T target;

	public ProxyHandler(T target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 前置
		System.err.println("before ...   " + method);

		Object retVal = method.invoke(target, args);

		// 后置
		System.err.println(" after ...   " + retVal);

		return retVal;

	}

	@SuppressWarnings("unchecked")
	public T getProxy() {
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}
	
	public void generateProxyClass() {
		
	}

}
