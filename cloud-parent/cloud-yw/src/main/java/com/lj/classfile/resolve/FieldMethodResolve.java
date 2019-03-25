package com.lj.classfile.resolve;

import java.util.ArrayList;
import java.util.List;

import com.lj.classfile.entity.AccessFlags;
import com.lj.classfile.entity.ClassInfo;
import com.lj.classfile.entity.FieldMethodeInfo;
import com.lj.classfile.load.HexReader;

/** 字段方法解析 */
public class FieldMethodResolve {

	/** 解析方法和字段 */
	public static List<FieldMethodeInfo> resolve(HexReader hexReader, ClassInfo classInfo) {
		Integer fieldLen = hexReader.readContent(4, Integer.class);
		List<FieldMethodeInfo> fields = new ArrayList<>();

		for (int i = 0; i < fieldLen; i++) {
			FieldMethodeInfo field = new FieldMethodeInfo();
			List<AccessFlags> accFlags = AccessFlagResolve.resolveForField(hexReader);// 标志
			int nameI = hexReader.readContent(4, Integer.class);// 简单名称索引
			int descI = hexReader.readContent(4, Integer.class);// 描述索引

			int attrInfoLen = hexReader.readContent(4, Integer.class);// 属性信息
			if (attrInfoLen > 0)
				field.setAttrInfos(AttrInfoResolve.resolve(hexReader, attrInfoLen, classInfo));// 解析属性

			field.setAccessFlags(accFlags);
			field.setNameI(nameI);
			field.setDescI(descI);
			fields.add(field);
		}
		return fields;
	}

}
