package com.lj.classfile;

import com.lj.classfile.resolve.ResolveConst;

import java.util.Map;

import com.lj.classfile.entity.ClassInfo;
import com.lj.classfile.entity.ConstantInfo;
import com.lj.classfile.entity.VersionInfo;
import com.lj.classfile.load.ClassFile;

/** 解析Class */
public class ResolveClass {

	public ResolveClass() {
	}

	public ClassInfo resolveClass(String classPath) {
		ClassFile classFile = new ClassFile(classPath);
		VersionInfo version = verify(classFile);/// 解析版本号
		Map<Integer, ConstantInfo> constantInfos = new ResolveConst().resolve(classFile);// 常量

		ClassInfo classInfo = new ClassInfo();
		classInfo.setVersion(version);
		classInfo.setConstantInfos(constantInfos);
		
		
		
		return classInfo;
	}

	private VersionInfo verify(ClassFile classFile) {
		String magicNumber = classFile.readContent(8, null);// 魔数
		int minorVersion = classFile.readContent(4, Integer.class); // 次版本
		int mainVersion = classFile.readContent(4, Integer.class); // 主版本
		VersionInfo version = new VersionInfo(magicNumber, minorVersion, mainVersion);
		return version;
	}
}
