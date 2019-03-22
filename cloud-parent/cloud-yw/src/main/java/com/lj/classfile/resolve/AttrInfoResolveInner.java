package com.lj.classfile.resolve;

import com.lj.classfile.entity.AttrInfo;
import com.lj.classfile.entity.ClassInfo;
import com.lj.classfile.load.ClassFile;

import lombok.AllArgsConstructor;

/** 属性解析 */
interface AttrInfoResolveInner {
	AttrInfo doResolve(ClassFile classFile, ClassInfo classInfo);
}

@AllArgsConstructor
class StandardResolve implements AttrInfoResolveInner {
	private Class<?> clazz;

	@Override
	public AttrInfo doResolve(ClassFile classFile, ClassInfo classInfo) {
		Integer length = classFile.readContent(8, Integer.class);
		Object info = classFile.readContent(length * 2, clazz);

		return null;
	}

}
