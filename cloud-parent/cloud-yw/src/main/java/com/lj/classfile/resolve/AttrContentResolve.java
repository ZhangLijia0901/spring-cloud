package com.lj.classfile.resolve;

import com.lj.classfile.entity.AttrInfo;
import com.lj.classfile.load.HexReader;

/** 属性内容解析 */
interface AttrContentResolve {
	void doResolve(AttrInfo info);
}

class CodeResolve implements AttrContentResolve {
	@Override
	public void doResolve(AttrInfo info) {
		HexReader hexReader = new HexReader(info.getInfo().toString().toCharArray());
		hexReader.print();
	}
}
