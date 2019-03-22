package com.lj.classfile.entity;

import lombok.Getter;

@Getter
public enum AccessFlags {
	PUBLIC(0x1, "ACC_PUBLIC", "public", true, true, true), //
	PRIVATE(0x2, "ACC_PRIVATE", "private", true, true, true), //
	PROTECTED(0x4, "ACC_PROTECTED", "protected", true, true, true), //
	STATIC(0x8, "ACC_STATIC", "static", true, true, true), //
	//
	FINAL(0x10, "ACC_FINAL", "final", true, true, true), //
	SUPER(0x20, "ACC_SUPER", "super", true, false, false), //
	SYNCHRONIZED(0x20, "ACC_SYNCHRONIZED", "synchronized", false, true, false), //
	BRIDGE(0x40, "ACC_BRIDGE", "bridge", false, true, false), //
	VOLATILE(0x40, "ACC_VOLATILE", "volatile", false, false, true), //
	VARARGS(0x80, "ACC_VARARGS", "varargs", false, true, false), //
	TRANSIENT(0x80, "ACC_TRANSIENT", "transient", false, false, true), //
	//
	NATIVE(0x100, "ACC_NATIVE", "native", false, true, false), //
	INTERFACE(0x200, "ACC_INTERFACE", "interface", true, false, false), //
	ABSTRACT(0x400, "ACC_ABSTRACT", "abstract", true, true, false), //
	STRICTFP(0x800, "ACC_STRICTFP", "strictfp", false, true, false), //
	//
	SYNTHETIC(0x1000, "ACC_SYNTHETIC", "synthetic", true, true, true), //
	ANNOTATION(0x2000, "ACC_ANNOTATION", "annotation", true, false, false), //
	ENUM(0x4000, "ACC_ENUM", "enum", true, false, true), //
	//
	CONSTRUCTOR(0x10000, "ACC_CONSTRUCTOR", "constructor", false, true, false), //
	DECLARED_SYNCHRONIZED(0x20000, "ACC_DECLARED-SYNCHRONIZED", "declared-synchronized", false, true, false);

	private int value;// 标识值
	private String accName;// 标识名
	private String accDesc;// 标识描述
	private boolean scopeClass;// 作用于Class
	private boolean scopeMethod;// 作用于Method
	private boolean scopeField;// 作用于Field

	private AccessFlags(int value, String accName, String accDesc, boolean scopeClass, boolean scopeMethod,
			boolean scopeField) {
		this.value = value;
		this.accName = accName;
		this.accDesc = accDesc;
		this.scopeClass = scopeClass;
		this.scopeMethod = scopeMethod;
		this.scopeField = scopeField;
	}
}
