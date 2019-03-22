package com.lj.classfile.entity;

import java.util.List;
import java.util.Map;

import com.lj.classfile.resolve.ExtendsRelationResolve.ExtendsRelation;

import lombok.Data;

@Data
public class ClassInfo {
	private VersionInfo version; // 版本信息
	private Map<Integer, ConstantInfo> constantPool;// 常量信息
	private List<AccessFlags> accessFlags; // 标志信息
	private ExtendsRelation extendsRelation;// 继承关系
	private List<FieldInfo> fields;// 字段集

	/** 根据索引获取信息 */
	public String getConstantInfo(Integer index) {
		StringBuilder builder = new StringBuilder();
		getConstantInfo(index, builder);
		return builder.toString();
	}

	private void getConstantInfo(Integer index, final StringBuilder builder) {
		ConstantInfo constantInfo = constantPool.get(index);
		if (constantInfo.getIsVal())
			builder.append(constantInfo.getVal());
		else
			constantInfo.getReferences().forEach(ref -> {
				builder.append(" ");
				getConstantInfo(ref, builder);
			});
	}

	public String getFields() {
		StringBuilder builder = new StringBuilder();
		fields.forEach(field -> {
			builder.append(getField(field)).append("\r\n");
		});
		return builder.toString();
	}

	private String getField(FieldInfo field) {
		String format = field.toString();
		String desc = getConstantInfo(field.getDescI());
		String name = getConstantInfo(field.getNameI());
		return String.format(format, desc, name);
	}

	public void print() {
		System.err.println(version.toString());
		System.err.print("flags：");
		accessFlags.forEach(o -> {
			System.err.print(String.format("%s ", o.getAccName()));
		});
		System.err.println(String.format("\nthis class: %s", getConstantInfo(extendsRelation.getThisClass())));
		System.err.println(String.format("super class: %s", getConstantInfo(extendsRelation.getSuperClass())));
//		System.err.println(getFields());

		System.err.println("Constant Pool: ");
		constantPool.forEach((ref, constInfo) -> {
			System.err.println(String.format("#%d\t%s", ref, getConstantInfo(ref)));
		});

	}

}
