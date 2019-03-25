package com.lj.classfile.entity;

import lombok.Data;

/** 属性信息 */
@Data
public class AttrInfo {

	private Integer index;// 属性名索引
	private String name;// 属性名称
	private Integer length;// 长度
	private Boolean isRef = false;// info索引标志
	private Integer ref;// 索引
	private Object info;// 属性信息

}
