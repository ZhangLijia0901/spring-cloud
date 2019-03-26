package com.lj.classfile.resolver;

import com.lj.classfile.entity.ConstantInfo;
import com.lj.classfile.load.HexReader;

/** 解析常量 */
interface ConstResolveInner {
	ConstantInfo doResolve(HexReader hexReader);
}

/** 解析内容常量 */
class ResolveConstFixedLength implements ConstResolveInner {
	private int length;// 读取数据的长度, 为0则通过去取4位作为长度.
	private String descer;// 描述
	private Class<?> contentClass;// 解析后内容Class

	public ResolveConstFixedLength(int length, String descer, Class<?> contentClass) {
		this.length = length;
		this.descer = descer;
		this.contentClass = contentClass;
	}

	public ConstantInfo doResolve(HexReader hexReader) {
		int len = this.length;
		if (len == 0)
			len = hexReader.readContent(4, Integer.class) * 2;
		Object val = hexReader.readContent(len, contentClass);
		ConstantInfo content = new ConstantInfo(descer, val);
		return content;
	}
}

/** 解析引用常量 */
class ResolveConstReference implements ConstResolveInner {
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
	public ConstantInfo doResolve(HexReader hexReader) {
		ConstantInfo content = new ConstantInfo(descer);
		for (Integer refLen : refLens) {
			Integer ref = hexReader.readContent(refLen, Integer.class);
			content.addReferences(ref);
		}
		return content;
	}

}
