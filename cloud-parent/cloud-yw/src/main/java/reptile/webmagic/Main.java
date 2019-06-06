package reptile.webmagic;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Main {
//	public static void main(String[] args) {
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
//
//		String url = "https://www.taobao.com/";
//		url = "https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1516263414303_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&hs=2&word=%E8%A1%A8%E6%83%85%E5%8C%85";
//		Spider.create(new BaiduImgPageProcessor()).addUrl(url).thread(4).run();
//
//		// updateFileName();
//
//	}

	static void updateFileName() {
		File file = new File("C:\\Users\\suneee\\Documents\\表情包 - Google 搜索_files\\");
		File[] listFiles = file.listFiles();

		for (File file2 : listFiles) {
			// System.err.println(file2);
			if (file2.getName().indexOf(".") == -1) {
				System.err.println(file2.getName());
				file2.renameTo(new File(file2.toString() + ".jpg")); // 改名
			}
		}
	}
}

class BaiduImgPageProcessor implements PageProcessor {
	Set<String> exUrl = new HashSet<>();

	@Override
	public void process(Page page) {

		page.getHtml().links().all().forEach((url) -> {
			if (url == null || "".equals(url))
				return;
			if (exUrl.contains(url))
				return;

			exUrl.add(url);
			page.addTargetRequest(url);
		});

		List<String> imgUrls = page.getHtml().xpath("//img/@data-imgurl").all();

		imgUrls.forEach((imgUrl) -> {
			System.err.println(imgUrl);
			DownLoadFile.put(imgUrl);
		});
	}

	@Override
	public Site getSite() {
		return Site.me();
	}

}

class GoogleImgProcessor implements PageProcessor {

	@Override
	public void process(Page page) {
		List<String> imgUrls = page.getHtml().xpath("//img/@src").all();
		imgUrls.forEach(System.err::println);
	}

	@Override
	public Site getSite() {
		return Site.me();
	}

}

class CommonImgPageProcessor implements PageProcessor {
	Set<String> exUrl = new HashSet<>();

	@Override
	public void process(Page page) {

		page.getHtml().links().all().forEach((url) -> {
			if (url == null || "".equals(url))
				return;
			if (exUrl.contains(url))
				return;

			exUrl.add(url);
			page.addTargetRequest(url);
		});

		List<String> imgUrls = page.getHtml().xpath("//img/@src").all();

		imgUrls.forEach((imgUrl) -> {
			DownLoadFile.put(imgUrl);
		});
	}

	@Override
	public Site getSite() {
		return Site.me();
	}

}

class GithubRepoPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

	@Override
	public void process(Page page) {
		page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
		page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
		page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
		if (page.getResultItems().get("name") == null) {
			// skip this page
			page.setSkip(false);
		}
		page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
	}

	@Override
	public Site getSite() {
		return site;
	}

}