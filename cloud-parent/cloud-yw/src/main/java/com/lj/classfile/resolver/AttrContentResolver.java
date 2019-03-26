package com.lj.classfile.resolver;

import com.lj.classfile.entity.AttrInfo;
import com.lj.classfile.entity.ClassInfo;
import com.lj.classfile.load.HexReader;

/** 属性内容解析 */
interface AttrContentResolver {
	void doResolve(AttrInfo info, ClassInfo classInfo);
}

class CodeResolver implements AttrContentResolver {
//	static Map<String, String > commands = 
	@Override
	public void doResolve(AttrInfo info, ClassInfo classInfo) {
		HexReader hexReader = new HexReader(info.getInfo().toString().toCharArray());
		hexReader.print();
		int max_stack = hexReader.readContent(4, Integer.class);
		int max_locals = hexReader.readContent(4, Integer.class);
		int code_length = hexReader.readContent(8, Integer.class);
		String code_hexs = hexReader.readContent(8, null);// 指令集
		int tryCatch_length = hexReader.readContent(4, Integer.class);// 异常表数量
		String tryCath_hexs = hexReader.readContent(4 * 2 * tryCatch_length, null);// 异常表集(try{开始-结束(不含)}catch{处理行}异常类型)

		int tableCount = hexReader.readContent(4, Integer.class);
		for (int i = 0; i < tableCount; i++) {
			int nameI = hexReader.readContent(4, Integer.class);
			String name = classInfo.getConstantInfo(nameI);
//			LineNumberTable
//			LocalVariableTable
		}
	}
}

/** Code的属性解析 */
interface CodeAttrResolver {
	void doResolve(String hexs);
}
