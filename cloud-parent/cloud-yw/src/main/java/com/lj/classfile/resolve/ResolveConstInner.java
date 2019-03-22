package com.lj.classfile.resolve;

import com.lj.classfile.entity.ConstantInfo;
import com.lj.classfile.load.ClassFile;

/** 解析常量 */
abstract class ConstResolveInner {
	abstract ConstantInfo doResolve(ClassFile classFile);
}

/** 解析内容常量 */
class ResolveConstFixedLength extends ConstResolveInner {
	private int length;// 读取数据的长度, 为0则通过去取4位作为长度.
	private String descer;// 描述
	private Class<?> contentClass;// 解析后内容Class

	public ResolveConstFixedLength(int length, String descer, Class<?> contentClass) {
		this.length = length;
		this.descer = descer;
		this.contentClass = contentClass;
	}

	@Override
	ConstantInfo doResolve(ClassFile classFile) {
		int len = this.length;
		if (len == 0)
			len = classFile.readContent(4, Integer.class) * 2;
		Object val = classFile.readContent(len, contentClass);
		ConstantInfo content = new ConstantInfo(descer, val);
		return content;
	}
}

/** 解析引用常量 */
class ResolveConstReference extends ConstResolveInner {
	private String descer;// 描述
	private Integer[] refLens;// 引用长度

	public ResolveConstReference(String descer, Integer... refs) {
		this.descer = descer;
		this.refLens = refs;
	}

	public ResolveConstReference(String descer) {
		this.descer = descer;
		this.refLens = new Integer[] { 4, 4 };

	}

	@Override
	ConstantInfo doResolve(ClassFile classFile) {
		ConstantInfo content = new ConstantInfo(descer);
		for (Integer refLen : refLens) {
			Integer ref = classFile.readContent(refLen, Integer.class);
			content.addReferences(ref);
		}
		return content;
	}

}
