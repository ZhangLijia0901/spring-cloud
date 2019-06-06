package com.lj.classfile.resolver;

import java.util.HashMap;
import java.util.Map;

import com.lj.classfile.entity.AttrInfo;
import com.lj.classfile.entity.AttrInfo.AttrTable;
import com.lj.classfile.entity.ClassInfo;
import com.lj.classfile.load.HexReader;

/** 属性内容解析 */
interface AttrContentResolver {
	void doResolve(AttrInfo info, ClassInfo classInfo);
}

class CodeResolver implements AttrContentResolver {
//	static Map<String, String > commands = 
	static Map<String, CodeAttrResolver> CODEATTR_RESOLVERS = new HashMap<>();
	static {
		CODEATTR_RESOLVERS.put(LineNumberTableResolver.name, new LineNumberTableResolver());
		CODEATTR_RESOLVERS.put(LocalVariableTableResolver.name, new LocalVariableTableResolver());
	}

	@Override
	public void doResolve(AttrInfo info, ClassInfo classInfo) {
		HexReader hexReader = new HexReader(info.getInfo().toString().toCharArray());
		hexReader.print();
		int max_stack = hexReader.readContent(4, Integer.class);// 操作数栈最大深度
		int max_locals = hexReader.readContent(4, Integer.class);// 本地变量数量
		int code_length = hexReader.readContent(8, Integer.class);// 指令数量
		String code_hexs = hexReader.readContent(code_length * 2, null);// 指令集
		int tryCatch_length = hexReader.readContent(4, Integer.class);// 异常表数量
		String tryCath_hexs = hexReader.readContent(4 * 2 * tryCatch_length, null);// 异常表集(try{开始-结束(不含)}catch{处理行}异常类型)

		int tableCount = hexReader.readContent(4, Integer.class);
		for (int i = 0; i < tableCount; i++) {
			int nameI = hexReader.readContent(4, Integer.class);// 属性表名索引
			String name = classInfo.getConstantInfo(nameI); // 属性表名
			int attr_length = hexReader.readContent(8, Integer.class);// 属性内容长度
			String attr_hexs = hexReader.readContent(attr_length * 2, null);// 属性内容
			CodeAttrResolver codeAttrResolver = CODEATTR_RESOLVERS.get(name);
			codeAttrResolver.doResolve(info, classInfo, attr_hexs);
//			LineNumberTable
//			LocalVariableTable
		}
	}
}

/** Code的属性解析 */
interface CodeAttrResolver {
	void doResolve(AttrInfo info, ClassInfo classInfo, String hexs);
}

/** 行号解析 */
class LineNumberTableResolver implements CodeAttrResolver {
	static String name = "LineNumberTable";

	@Override
	public void doResolve(AttrInfo info, ClassInfo classInfo, String hexs) {
		HexReader hexReader = new HexReader(hexs.toCharArray());
		Integer line_number_length = hexReader.readContent(4, Integer.class);
		Map<Integer, Integer> line_number_table = new HashMap<>();
		for (int i = 0; i < line_number_length; i++) {
			Integer start_pc = hexReader.readContent(4, Integer.class); // 字节码行号
			Integer line_number = hexReader.readContent(4, Integer.class);// Java 代码行号
			line_number_table.put(start_pc, line_number);
		}
		info.addAttrTable(name, new AttrTable(name, hexs, line_number_table));
	}
}

class LocalVariableTableResolver implements CodeAttrResolver {
	static String name = "LocalVariableTable";

	@Override
	public void doResolve(AttrInfo info, ClassInfo classInfo, String hexs) {
		HexReader hexReader = new HexReader(hexs.toCharArray());
		Integer local_variable_length = hexReader.readContent(4, Integer.class);
		for (int i = 0; i < local_variable_length; i++) {
			Integer start_pc = hexReader.readContent(4, Integer.class); // 局部变量作用域开始
			Integer length = hexReader.readContent(4, Integer.class);// 局部变量作用域长度
			Integer name_index = hexReader.readContent(4, Integer.class);// 变量名索引
			Integer descriptor_index = hexReader.readContent(4, Integer.class);// 局部变量描述符索引
			Integer index = hexReader.readContent(4, Integer.class);// 在栈帧局部变量表中Slot的位置
		}
	}
}
