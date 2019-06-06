package com.lj.classfile;

import java.util.List;
import java.util.Map;

import com.lj.classfile.entity.AccessFlags;
import com.lj.classfile.entity.ClassInfo;
import com.lj.classfile.entity.ConstantInfo;
import com.lj.classfile.entity.FieldMethodeInfo;
import com.lj.classfile.entity.VersionInfo;
import com.lj.classfile.load.ClassFile;
import com.lj.classfile.load.HexReader;
import com.lj.classfile.resolver.AccessFlagResolver;
import com.lj.classfile.resolver.ConstantResolve;
import com.lj.classfile.resolver.ExtendsRelationResolve;
import com.lj.classfile.resolver.FieldMethodResolver;
import com.lj.classfile.resolver.ExtendsRelationResolve.ExtendsRelation;

/** 解析Class */
public class ResolveClass {

	public ResolveClass() {
	}

	/** 解析Class文件**/
	public ClassInfo resolveClass(String classPath) {
		HexReader hexReader = new ClassFile(classPath).toHexReader();
		VersionInfo version = verify(hexReader);/// 解析版本号
		Map<Integer, ConstantInfo> constantInfos = new ConstantResolve().resolve(hexReader);// 常量
		List<AccessFlags> accessFlags = AccessFlagResolver.resolveForClass(hexReader);// Class标志信息
		ExtendsRelation relation = ExtendsRelationResolve.resolve(hexReader);// 继承关系

		ClassInfo classInfo = new ClassInfo();
		classInfo.setVersion(version);
		classInfo.setConstantPool(constantInfos);
		classInfo.setAccessFlags(accessFlags);
		classInfo.setExtendsRelation(relation);

		List<FieldMethodeInfo> fields = FieldMethodResolver.resolve(hexReader, classInfo);// 字段
		classInfo.setFields(fields);
		List<FieldMethodeInfo> methods = FieldMethodResolver.resolve(hexReader, classInfo);// 方法
		methods.forEach((m)->{
			System.err.println(m.toString());
		});

		return classInfo;
	}

	private VersionInfo verify(HexReader hexReader) {
		String magicNumber = hexReader.readContent(8, null);// 魔数
		int minorVersion = hexReader.readContent(4, Integer.class); // 次版本
		int mainVersion = hexReader.readContent(4, Integer.class); // 主版本
		VersionInfo version = new VersionInfo(magicNumber, minorVersion, mainVersion);
		return version;
	}
}
