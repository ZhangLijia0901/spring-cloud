package com.lj.classfile.entity;

import static org.assertj.core.api.Assertions.contentOf;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import lombok.Data;

@Data
public class ClassInfo {
	private VersionInfo version; // 版本信息
	private Map<Integer, ConstantInfo> constantInfos;// 常量信息

	public String getConstantInfo(Integer index) {
		StringBuilder builder = new StringBuilder();
		getConstantInfo(index, builder);
		return builder.toString();
	}

	private void getConstantInfo(Integer index, final StringBuilder builder) {
		ConstantInfo constantInfo = constantInfos.get(index);
		if (constantInfo.getIsVal())
			builder.append(constantInfo.getVal());
		else
			constantInfo.getReferences().forEach(ref -> {
				builder.append(" ");
				getConstantInfo(ref, builder);
			});
	}

	public void print() {
		System.err.println(version.toString());
		System.err.println("Constant Pool: ");
		constantInfos.forEach((ref, constInfo) -> {
			System.err.println(String.format("#%d\t%s", ref, getConstantInfo(ref)));
		});
	}

}
