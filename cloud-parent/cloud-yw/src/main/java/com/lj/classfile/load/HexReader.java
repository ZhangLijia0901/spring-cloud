package com.lj.classfile.load;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/***
 * 16进制读取
 *
 */
public class HexReader {
	private char[] hexs;
	private int cursor = 0;

	public HexReader(char[] hexs) {
		this.hexs = hexs;
	}

	@SuppressWarnings("unchecked")
	public <T> T readContent(int len, Class<T> clazz) {
		String originalText = String.valueOf(Arrays.copyOfRange(hexs, cursor, cursor += len));
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
		return (T) originalText;
	}

	public void print() {
		for (int i = 0; i < hexs.length; i++) {
			if (i % 2 == 0)
				System.err.print(" ");
			if (i % 16 == 0)
				System.err.println();
			System.err.print(hexs[i]);

		}
	}
}
