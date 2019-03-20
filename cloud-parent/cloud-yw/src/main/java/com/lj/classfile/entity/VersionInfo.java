package com.lj.classfile.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 版本信息
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionInfo {
	private String magicNumber;// 魔数
	private int minorVersion; // 次版本
	private int mainVersion; // 主版本

	public String toString() {
		return String.format("魔数：%s, 版本号：%d.%d ", magicNumber, mainVersion, minorVersion);
	}
}
