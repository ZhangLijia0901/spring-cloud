package com.lj.webflux.config;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

public class FastJsonHttpMessageConverterEx extends FastJsonHttpMessageConverter {
	@Override
	public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
		Assert.notEmpty(supportedMediaTypes, "MediaType List must not be empty");
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		System.err.println("设置 MediaType");
//		super.setSupportedMediaTypes(A);
	}


}
