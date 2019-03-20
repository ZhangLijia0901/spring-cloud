package com.lj.classfile.load;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class ClassFile {

	private char[] fileHexs;
	private int cursor = 0;

	public ClassFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists())
			this.fileHexs = new char[0];
		this.fileHexs = readClasstoHex(file);
	}

	@SuppressWarnings("unchecked")
	public <T> T readContent(int len, Class<T> clazz) {
		String originalText = String.valueOf(Arrays.copyOfRange(fileHexs, cursor, cursor += len));
		if (clazz == null)
			return (T) originalText;
		if (Number.class.isAssignableFrom(clazz)) {
			try {
				Method valueOfMethod = clazz.getDeclaredMethod("valueOf", String.class, int.class);
				return (T) valueOfMethod.invoke(null, originalText, 16);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (clazz == String.class) {
			byte[] decodeHex;
			try {
				decodeHex = Hex.decodeHex(originalText.toCharArray());
				return (T) new String(decodeHex);
			} catch (DecoderException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	/** 读取class文件 */
	static char[] readClasstoHex(File file) {

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
