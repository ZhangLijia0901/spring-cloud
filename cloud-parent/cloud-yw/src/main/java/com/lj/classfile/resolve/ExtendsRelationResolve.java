package com.lj.classfile.resolve;

import java.util.ArrayList;
import java.util.List;

import com.lj.classfile.load.HexReader;

import lombok.Data;

/** 解析继承关系 */
public class ExtendsRelationResolve {

	/** 解析 */
	public static ExtendsRelation resolve(HexReader hexReader) {
		ExtendsRelation extendsRelation = new ExtendsRelation();
		extendsRelation.setThisClass(hexReader.readContent(4, Integer.class));// 获取当前类索引
		extendsRelation.setSuperClass(hexReader.readContent(4, Integer.class));// 获取父类索引

		int interfaceLen = hexReader.readContent(4, Integer.class);
		List<Integer> interfaces = new ArrayList<>();// 实现接口的索引
		for (int i = 0; i < interfaceLen; i++)
			interfaces.add(hexReader.readContent(4, Integer.class));
		extendsRelation.setInterfaces(interfaces);

		return extendsRelation;

	}

	/** 继承关系 */
	@Data
	public static class ExtendsRelation {
		private Integer thisClass; // 当前类的索引
		private Integer superClass;// 父类索引
		private List<Integer> interfaces;// 接口索引
	}
}
