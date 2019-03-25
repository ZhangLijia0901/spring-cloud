package com.lj.classfile.resolve;

import java.util.ArrayList;
import java.util.List;

import com.lj.classfile.entity.AccessFlags;
import com.lj.classfile.load.HexReader;

/** 访问标志解析 */
public class AccessFlagResolve {

	/** 解析Class的访问标志 */
	public static List<AccessFlags> resolveForClass(HexReader hexReader) {
		int accValue = hexReader.readContent(4, Integer.class);

		List<AccessFlags> accessFlags = new ArrayList<AccessFlags>();
		for (AccessFlags accessFlag : AccessFlags.values())
			if (accessFlag.isScopeClass() && (accValue & accessFlag.getValue()) != 0)
				accessFlags.add(accessFlag);
		return accessFlags;
	}

	/** 解析Method的访问标志 */
	static List<AccessFlags> resolveForMethod(String accessFlagValue) {
		int accValue = Integer.valueOf(accessFlagValue, 16);

		List<AccessFlags> accessFlags = new ArrayList<AccessFlags>();
		for (AccessFlags accessFlag : AccessFlags.values())
			if (accessFlag.isScopeMethod() && (accValue & accessFlag.getValue()) != 0)
				accessFlags.add(accessFlag);
		return accessFlags;
	}

	/** 解析Field的访问标志 */
	static List<AccessFlags> resolveForField(HexReader hexReader) {
		int accValue = hexReader.readContent(4, Integer.class);

		List<AccessFlags> accessFlags = new ArrayList<AccessFlags>();
		for (AccessFlags accessFlag : AccessFlags.values())
			if (accessFlag.isScopeField() && (accValue & accessFlag.getValue()) != 0)
				accessFlags.add(accessFlag);
		return accessFlags;
	}
}
