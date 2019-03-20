package com.lj.classfile.resolve;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.lj.classfile.entity.ConstantInfo;
import com.lj.classfile.load.ClassFile;

/** 解析常量 */
public class ResolveConst {

	static Map<Integer, ResolveConstInner> resolveConstInners = new HashMap<>();
	static {
		// 值解析
		resolveConstInners.put(1, new ResolveConstFixedLength(0, "Utf8", String.class));
		resolveConstInners.put(3, new ResolveConstFixedLength(8, "Integer", Integer.class));
		resolveConstInners.put(4, new ResolveConstFixedLength(8, "Float", Float.class));
		resolveConstInners.put(5, new ResolveConstFixedLength(16, "Long", Long.class));
		resolveConstInners.put(6, new ResolveConstFixedLength(16, "Double", Double.class));
		// 索引解析
		resolveConstInners.put(7, new ResolveConstReference("Class", 4));
		resolveConstInners.put(8, new ResolveConstReference("String", 4));
		resolveConstInners.put(9, new ResolveConstReference("Fieldref"));
		resolveConstInners.put(10, new ResolveConstReference("Methodref"));
		resolveConstInners.put(11, new ResolveConstReference("Interfaceref"));
		resolveConstInners.put(12, new ResolveConstReference("NameAndType"));
		resolveConstInners.put(15, new ResolveConstReference("MethodHandle", 2, 4));
		resolveConstInners.put(16, new ResolveConstReference("MethodType", 4));
		resolveConstInners.put(18, new ResolveConstReference("InvokeDynamic"));
	}

	public Map<Integer, ConstantInfo> resolve(ClassFile classFile) {
		Map<Integer, ConstantInfo> infos = new LinkedHashMap<>();
		Integer constNumber = classFile.readContent(4, Integer.class);
		for (int i = 1; i < constNumber; i++) {
			int flag = classFile.readContent(2, Integer.class);
			ResolveConstInner resolve = resolveConstInners.get(flag);
			ConstantInfo content = resolve.doResolve(classFile);
			infos.put(i, content);
		}
		return infos;
//		contents.forEach((k, v) -> {
//			if (v.isVal())
//				System.err.println(String.format("#%d =%s \t %s", k, v.getDescer(), v.getVal()));
//			else {
//				System.err.print(String.format("#%d = %s \t ", k, v.getDescer()));
//				v.getReferences().forEach(o -> {
//					System.err.print(String.format("#%d", o));
//				});
//				System.err.println();
//			}
//
//		});
//		HashMap<String, Content>
	}

//	

}
