package com.lj.webflux;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@SpringBootApplication
public class App implements InitializingBean {

	@Autowired
	private HttpMessageConverters converters;

	@Override
	public void afterPropertiesSet() throws Exception {
//		System.err.println("111111111111111111111111111111111" + converters);
		converters.forEach(c -> {
			System.err.println(c.getClass().toString() + c.getSupportedMediaTypes());
		});

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
