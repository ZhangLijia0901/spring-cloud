package com.lj.classfile.load;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Hex;

/**
 * 
 * Class文件加载
 */
public class ClassFile {
	String filePath;

	public ClassFile(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 加载class文件并转换成16进制数据
	 */
	public HexReader toHexReader() {
		File file = new File(filePath);
		char[] hexs = null;
		if (!file.exists())
			hexs = new char[0];
		hexs = readClasstoHex(file);
		return new HexReader(hexs);
	}

	/** 读取class文件 */
	char[] readClasstoHex(File file) {
		try (InputStream is = new FileInputStream(file)) {
			byte[] bs = new byte[(int) file.length()];
			is.read(bs);
			return Hex.encodeHex(bs, false);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return new char[0];
	}
}
