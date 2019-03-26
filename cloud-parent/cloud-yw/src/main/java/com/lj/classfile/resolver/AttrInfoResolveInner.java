package com.lj.classfile.resolver;

import com.lj.classfile.entity.AttrInfo;
import com.lj.classfile.load.HexReader;

import lombok.AllArgsConstructor;

/** 属性解析 */
interface AttrInfoResolverInner {
	AttrInfo doResolve(HexReader hexReader);
}
/** 标准属性解析 */
@AllArgsConstructor
class StandardResolver implements AttrInfoResolverInner {
	private Class<?> clazz;
	private Boolean isRef = false;

	public StandardResolver(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public AttrInfo doResolve(HexReader hexReader) {
		Integer length = hexReader.readContent(8, Integer.class);
		Object info = null;
		if (length > 0)
			info = hexReader.readContent(length * 2, clazz);

		AttrInfo attrInfo = new AttrInfo();
		attrInfo.setLength(length);
		attrInfo.setIsRef(isRef);
		if (isRef)
			attrInfo.setRef((Integer) info);
		else
			attrInfo.setInfo(info);
		return attrInfo;
	}
}
