package com.lj.classfile.resolve;

import java.util.ArrayList;
import java.util.List;

import com.lj.classfile.entity.AccessFlags;
import com.lj.classfile.entity.FieldInfo;
import com.lj.classfile.load.ClassFile;

/** 常量解析 */
public class FieldResolve {

	public static List<FieldInfo> resolve(ClassFile classFile) {
		Integer fieldLen = classFile.readContent(4, Integer.class);
		List<FieldInfo> fields = new ArrayList<>();

		for (int i = 0; i < fieldLen; i++) {
			FieldInfo field = new FieldInfo();
			List<AccessFlags> accFlags = AccessFlagResolve.resolveForField(classFile);// 标志
			int nameI = classFile.readContent(4, Integer.class);// 简单名称索引
			int descI = classFile.readContent(4, Integer.class);// 描述索引

			int attrInfoLen = classFile.readContent(4, Integer.class);// 属性信息
			if (attrInfoLen > 0)
				//

				field.setAccessFlags(accFlags);
			field.setNameI(nameI);
			field.setDescI(descI);
//			field.setAttrInfoI(attrInfoI);
			fields.add(field);
		}
		return fields;
	}

}
