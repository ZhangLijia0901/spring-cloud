package com.lj.classfile.resolve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lj.classfile.entity.AttrInfo;
import com.lj.classfile.entity.ClassInfo;
import com.lj.classfile.load.ClassFile;

/** 属性解析 */
public class AttrInfoResolve {
	static final Map<String, AttrInfoResolveInner> attrInfoResolveInners = new HashMap<>();
	static {
		attrInfoResolveInners.put("ConstantValue", new StandardResolve(Integer.class));
	}

	public List<AttrInfo> resolve(ClassFile classFile, int count, ClassInfo classInfo) {
		List<AttrInfo> attrInfos = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			Integer nameI = classFile.readContent(4, Integer.class);// 属性名索引
			String name = classInfo.getConstantInfo(nameI);// 属性名

			AttrInfoResolveInner resolve = attrInfoResolveInners.get(name);
			AttrInfo attrInfo = resolve.doResolve(classFile, classInfo);// 解析
			attrInfo.setIndex(nameI);
			attrInfo.setName(name);
			attrInfos.add(attrInfo);
		}
		return attrInfos;

	}
}
