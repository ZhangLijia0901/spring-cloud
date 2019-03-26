package com.lj.classfile.resolver;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.lj.classfile.entity.ConstantInfo;
import com.lj.classfile.load.HexReader;

/** 解析常量 */
public class ConstantResolve {

	static Map<Integer, ConstResolveInner> resolveConstInners = new HashMap<>();
	static {
		// 值解析
		resolveConstInners.put(1, new ResolveConstFixedLength(0, "Utf8", String.class));
		resolveConstInners.put(3, new ResolveConstFixedLength(8, "Integer", Integer.class));
		resolveConstInners.put(4, new ResolveConstFixedLength(8, "Float", Float.class));
		resolveConstInners.put(5, new ResolveConstFixedLength(16, "Long", Long.class));
		resolveConstInners.put(6, new ResolveConstFixedLength(16, "Double", Double.class));
		// 索引解析
		resolveConstInners.put(7, new ResolveConstReference("Class", 4));// Utf8
		resolveConstInners.put(8, new ResolveConstReference("String", 4));// Utf8
		resolveConstInners.put(9, new ResolveConstReference("Fieldref"));// Class_info | NameAndType
		resolveConstInners.put(10, new ResolveConstReference("Methodref"));// Class_info | NameAndType
		resolveConstInners.put(11, new ResolveConstReference("Interfaceref"));// Class_info | NameAndType
		resolveConstInners.put(12, new ResolveConstReference("NameAndType"));// 字段或方法名 | 字段或方法描述
		resolveConstInners.put(15, new ResolveConstReference("MethodHandle", 2, 4));// 句柄类型 | 值
		resolveConstInners.put(16, new ResolveConstReference("MethodType", 4)); // 方法描述符
		resolveConstInners.put(18, new ResolveConstReference("InvokeDynamic"));// 引导方法表 | NameAndType
	}

	public Map<Integer, ConstantInfo> resolve(HexReader hexReader) {
		Map<Integer, ConstantInfo> infos = new LinkedHashMap<>();
		Integer constant_pool_count = hexReader.readContent(4, Integer.class);// 常量数量
		for (int i = 1; i < constant_pool_count; i++) {
			int flag = hexReader.readContent(2, Integer.class);
			ConstResolveInner resolve = resolveConstInners.get(flag);
			ConstantInfo content = resolve.doResolve(hexReader);
			infos.put(i, content);
		}
		return infos;

	}
}
