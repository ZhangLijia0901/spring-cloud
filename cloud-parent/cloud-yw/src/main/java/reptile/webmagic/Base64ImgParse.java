package reptile.webmagic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

public class Base64ImgParse {
	static String diskUri = "F:\\webmgic\\baidu";

	public static void parseImg(String base64Img) {
		byte[] bs = Base64.getDecoder().decode(base64Img);

		String folder = diskUri;
		File folderFile = new File(folder);
		if (!folderFile.exists())
			folderFile.mkdirs();

		folderFile = null;
		OutputStream out = null;
		try {
			File file = new File(folder + "\\" + UUID.randomUUID().toString() + ".png");
			out = new FileOutputStream(file);
			out.write(bs);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public static void main(String[] args) {

	}
}
