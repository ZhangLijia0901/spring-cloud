package com.lj.classfile;

public class Test {

	public static void main(String[] args) {
		String classPath = "E:\\github\\spring-cloud\\cloud-parent\\cloud-yw\\target\\classes\\com\\lj\\webflux\\App.class";
		new ResolveClass().resolveClass(classPath).print();
	}
}
