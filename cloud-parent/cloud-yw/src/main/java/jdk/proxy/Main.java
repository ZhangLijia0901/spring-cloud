package jdk.proxy;

import java.io.File;

public class Main {
//	public static void main(String[] args) throws Exception {
		// Field field = System.class.getDeclaredField("props");
		// field.setAccessible(true);
		// Properties props = (Properties) field.get(null);
		// props.put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		//
		// ProxyHandler<UserService> handler = new ProxyHandler<UserService>(new
		// UserServiceImpl());
		// UserService proxy = handler.getProxy();
		// proxy.getUserInfo();

		// ObjectOutputStream stream = new ObjectOutputStream(new
		// FileOutputStream("D:/UserService.txt"));
		// stream.writeObject(proxy);

		// E:\xp1.0xxx
//		removeJar(new File("E:\\xp1.0xxx"));
//	}

	static void removeJar(File folderFile) {
		for (File file : folderFile.listFiles()) {
			if (file.isDirectory())
				removeJar(file);
			if (file.getPath().indexOf("\\target") != -1) {
				System.err.println("target ---- " + file.getPath());
				file.delete();
			}
			if (file.getName().endsWith(".jar")) {
				file.delete();
				System.err.println(file.getPath());
			}
		}

		// if (folderFile.isDirectory())
		// removeJar();
	}
}
