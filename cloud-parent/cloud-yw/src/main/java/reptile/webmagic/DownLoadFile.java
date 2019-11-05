package reptile.webmagic;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

public class DownLoadFile {

	static String diskUri = "E:\\webmgic\\baidu";

	final static Set<String> uriFilter = new HashSet<String>();
	final static BlockingQueue<String> imgUrls = new LinkedTransferQueue<>();

	static {
		uriFilter.add(null);
		uriFilter.add("");
		uriFilter.add("http:");
		uriFilter.add("https:");
	}

	public static void downFile(boolean isDefultUri) {
		String imgUrl;
		try {
			imgUrl = imgUrls.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		String folder = diskUri;
		String fileName = null;
		if (!isDefultUri) {
			String[] uris = imgUrl.split("/");
			StringBuilder builder = new StringBuilder(diskUri);

			fileName = uris[uris.length - 1];
			for (int i = 0; i < uris.length - 1; i++) {
				if (uriFilter.contains(uris[i]))
					continue;
				builder.append("\\").append(uris[i]);
			}
			uris = null;

			folder = builder.toString();
		} else {
			int sufIndex = imgUrl.lastIndexOf(".");
			fileName = UUID.randomUUID().toString() + (sufIndex != -1 ? imgUrl.substring(sufIndex) : ".jpg");
		}
		File folderFile = new File(folder);
		if (!folderFile.exists())
			folderFile.mkdirs();

		folderFile = null;

		DataInputStream dataInputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			File file = new File(folder + "\\" + fileName);

			if (imgUrl.startsWith("//"))
				imgUrl = "http:" + imgUrl;

			URL url = new URL(imgUrl);
			dataInputStream = new DataInputStream(url.openStream());
			fileOutputStream = new FileOutputStream(file);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = dataInputStream.read(buffer)) > 0)
				fileOutputStream.write(buffer, 0, length);

		} catch (Exception e) {

		} finally {
			if (dataInputStream != null)
				try {
					dataInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (fileOutputStream != null)
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public static void put(String imgUrl) {
		try {
			imgUrls.put(imgUrl);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
