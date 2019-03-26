package com.lj.classfile.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lj.classfile.entity.AttrInfo;
import com.lj.classfile.entity.ClassInfo;
import com.lj.classfile.load.HexReader;
 
/** 属性解析 */
public class AttrInfoResolve {
	static final Map<String, AttrInfoResolverInner> attrInfoResolverInners = new HashMap<>(); // 属性解析
	static {
		attrInfoResolverInners.put("ConstantValue", new StandardResolver(Integer.class, true));
		attrInfoResolverInners.put("Deprecated", new StandardResolver(Integer.class));
		attrInfoResolverInners.put("RuntimeVisibleAnnotations", new StandardResolver(String.class));
		attrInfoResolverInners.put("Code", new StandardResolver(null));
		attrInfoResolverInners.put("Exceptions", new StandardResolver(null));

	}

	static final Map<String, AttrContentResolver> attrContentResolvers = new HashMap<>(); // 属性表
	static {
		attrContentResolvers.put("Code", new CodeResolver());
	}

	static List<AttrInfo> resolve(HexReader hexReader, int count, ClassInfo classInfo) {
		List<AttrInfo> attrInfos = new ArrayList<>();

		for (int i = 0; i < count; i++) {
			Integer nameI = hexReader.readContent(4, Integer.class);// 属性名索引
			String name = classInfo.getConstantInfo(nameI);// 属性名

			AttrInfoResolverInner resolve = attrInfoResolverInners.get(name);
			AttrInfo attrInfo = resolve.doResolve(hexReader);// 解析
			if (attrInfo.getIsRef())
				attrInfo.setInfo(classInfo.getConstantInfo(attrInfo.getRef())); // 00010012
			attrInfo.setIndex(nameI);
			attrInfo.setName(name);

			AttrContentResolver resolve2 = attrContentResolvers.get(name);
			if (resolve2 != null)
				resolve2.doResolve(attrInfo, classInfo);
			attrInfos.add(attrInfo);
		}
		return attrInfos;

	}
}
