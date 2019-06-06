package com.lj.classfile.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
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

	// ------------- Code属性解析的内容
	public boolean isCode() {
		return "Code".equals(name);
	}

	private Map<String, AttrTable> attrTables;

	public void addAttrTable(String name, AttrTable attrTable) {
		if (attrTables == null)
			attrTables = new HashMap<>();
		attrTables.put(name, attrTable);
	}

	@Data
	@AllArgsConstructor
	public static class AttrTable {
		private String name; // 属性名
		private String originalContent;// 原始内容
		private Object content;// 解析后内容

		@SuppressWarnings("unchecked")
		public <T> T getContent() {
			if (content == null)
				return null;
			return (T) content;
		}
	}
}
