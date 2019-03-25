package com.lj.classfile.resolve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lj.classfile.entity.AttrInfo;
import com.lj.classfile.entity.ClassInfo;
import com.lj.classfile.load.HexReader;

/** 属性解析 */
public class AttrInfoResolve {
	static final Map<String, AttrInfoResolveInner> attrInfoResolveInners = new HashMap<>(); // 属性解析
	static {
		attrInfoResolveInners.put("ConstantValue", new StandardResolve(Integer.class, true));
		attrInfoResolveInners.put("Deprecated", new StandardResolve(Integer.class));
		attrInfoResolveInners.put("RuntimeVisibleAnnotations", new StandardResolve(String.class));
		attrInfoResolveInners.put("Code", new StandardResolve(null));
	}

	static final Map<String, AttrContentResolve> attrContentResolves = new HashMap<>(); // 属性表
	static {
		attrContentResolves.put("Code", new CodeResolve());
	}

	static List<AttrInfo> resolve(HexReader hexReader, int count, ClassInfo classInfo) {
		List<AttrInfo> attrInfos = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			Integer nameI = hexReader.readContent(4, Integer.class);// 属性名索引
			String name = classInfo.getConstantInfo(nameI);// 属性名

			AttrInfoResolveInner resolve = attrInfoResolveInners.get(name);
			AttrInfo attrInfo = resolve.doResolve(hexReader);// 解析
			if (attrInfo.getIsRef())
				attrInfo.setInfo(classInfo.getConstantInfo(attrInfo.getRef()));
			attrInfo.setIndex(nameI);
			attrInfo.setName(name);

			AttrContentResolve resolve2 = attrContentResolves.get(name);
			if (resolve2 != null)
				resolve2.doResolve(attrInfo);
			attrInfos.add(attrInfo);
		}
		return attrInfos;

	}
}
