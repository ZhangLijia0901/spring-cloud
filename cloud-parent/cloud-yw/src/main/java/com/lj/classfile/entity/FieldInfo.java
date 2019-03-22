package com.lj.classfile.entity;

import java.util.List;

import lombok.Data;

/** 字段信息 */
@Data
public class FieldInfo {
	private List<AccessFlags> accessFlags; // 标志信息
	private Integer nameI;// 简单名称索引
	/**
	 * B:byte; C:char; D:double; F:float; I:int; J:long; S:short; Z:boolean; V:void;
	 * L:Ljava/lang/Object;
	 */
	private Integer descI; // 描述索引

	private List<AttrInfo> attrInfos;// 属性信息索引

	public String toString() {
		StringBuilder builder = new StringBuilder();
		accessFlags.forEach(o -> {
			builder.append(o.getAccDesc()).append(" ");
		});
		builder.append("%s %s;");
		return builder.toString();
	}

}
