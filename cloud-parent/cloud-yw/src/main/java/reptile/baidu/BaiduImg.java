package reptile.baidu;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import reptile.webmagic.DownLoadFile;

public class BaiduImg {
//	public static void main(String[] args) {
//
//		new Thread(() -> {
//			String url = "https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=result&queryWord=%E8%A1%A8%E6%83%85%E5%8C%85&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=0&word=%E8%A1%A8%E6%83%85%E5%8C%85&s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&";
//			int i = 0;
//			while (true) {
//				HttpClient client = HttpClientBuilder.create().build();
//				StringBuilder builder = new StringBuilder(url);
//				builder.append("pn=").append(i * 50);
//				builder.append("&rn=50");
//				HttpGet get = new HttpGet(builder.toString());
//
//				HttpResponse response = null;
//				try {
//					response = client.execute(get);
//					String respData = EntityUtils.toString(response.getEntity());
//					JSONObject jsonObject = JSON.parseObject(respData);
//					JSONArray jsonArray = jsonObject.getJSONArray("data");
//					if (jsonArray == null)
//						return;
//					jsonArray.forEach((o) -> {
//						if (o instanceof JSONObject) {
//							String imgUrl = ((JSONObject) o).getObject("middleURL", String.class);
//							System.err.println(imgUrl);
//							if (imgUrl == null || "".equals(imgUrl))
//								return;
//							DownLoadFile.put(imgUrl);
//						}
//					});
//
//				} catch (ClientProtocolException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				i++;
//			}
//		}).start();
//
//		for (int i = 0; i < 3; i++) {
//			new Thread(() -> {
//				while (true) {
//					try {
//						DownLoadFile.downFile(true);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}).start();
//
//		}
//	}
}
