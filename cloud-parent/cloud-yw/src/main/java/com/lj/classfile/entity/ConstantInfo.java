package com.lj.classfile.entity;

import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstantInfo {
	private String flag;// 标志
	private String descer;// 描述
	private String originalVal;// 原始值
	private Object val;// 解析的值
	private LinkedList<Integer> references;// 引用
	private Boolean isVal;// 是否有解析值

	public ConstantInfo(String descer) {
		this.descer = descer;
	}

	public ConstantInfo(String descer, Object val) {
		super();
		this.descer = descer;
		this.val = val;
		this.isVal = true;
	}

	public void addReferences(Integer reference) {
		this.isVal = false;
		if (references == null)
			this.references = new LinkedList<>();
		this.references.add(reference);
	}

	@Override
	public String toString() {
		if (this.isVal)
			return String.valueOf(this.val);
		else {
			StringBuilder builder = new StringBuilder();
			references.forEach((o) -> {
				builder.append(o);
				if (references.getLast() != o)
					builder.append(" -> ");
			});
			return builder.toString();
		}
	}

}
