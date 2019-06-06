package com.lj.webflux;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import com.lj.webflux.config.Swagger2Configuration;
import com.lj.webflux.controller.UserController;

//@WxApplication
//@SpringBootApplication(scanBasePackageClasses = { Swagger2Configuration.class, UserController.class })
public class App_ implements InitializingBean, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (applicationContext != null) {
			if (applicationContext instanceof ConfigurableApplicationContext) {
				ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext)
						.getBeanFactory();

				Arrays.asList(BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, Object.class))
						.forEach(System.err::println);
			}
		}
	}

	public static void main(String[] args) {

	}

	static void testMethod(String s) {
		System.err.println("hello String : " + s);
	}

	static CallSite BootstrapMethod(MethodHandles.Lookup lookup, String name, MethodType mt) throws Throwable {
		return new ConstantCallSite(lookup.findStatic(App_.class, name, mt));
	}

	static MethodType MT_BootstrapMethod() {
		List<Class<?>> classs = new ArrayList<>();
		classs.add(MethodHandles.Lookup.class);
		classs.add(String.class);
		classs.add(MethodType.class);

		return MethodType.methodType(CallSite.class, classs);
//		return MethodType.fromMethodDescriptorString(
//				"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
//				null);
	}

	static MethodHandle MH_BootstrapMethod() throws Exception {
		return MethodHandles.lookup().findStatic(App_.class, "BootstrapMethod", MT_BootstrapMethod());
	}

	static MethodHandle INDY_BootstrapMethod() throws Throwable {
		CallSite callSite = (CallSite) MH_BootstrapMethod().invokeWithArguments(MethodHandles.lookup(), "testMethod",
				MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V", null));
		return callSite.dynamicInvoker();
	}

}
