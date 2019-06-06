package com.lj.webflux.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户信息")
public class User {

	@ApiModelProperty(notes = "唯一")
	private String id;
	@ApiModelProperty(notes = "名称")
	private String name;
	@ApiModelProperty(notes = "年龄")
	private String age;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}
