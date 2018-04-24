package com.lj.webflux;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

//@WxApplication
@SpringBootApplication
public class App implements InitializingBean, ApplicationContextAware {

//	@Autowired
//	private HttpMessageConverters converters;

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
//		System.err.println("111111111111111111111111111111111" + converters);
//		converters.forEach(c -> {
//			System.err.println(c.getClass().toString() + c.getSupportedMediaTypes());
//		});

	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

//	@Bean
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {

		return new FastJsonHttpMessageConverter() {

			@Override
			public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
				// supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
				// supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
				super.setSupportedMediaTypes(
						Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON, MediaType.ALL));
			}

		};
	}
}
