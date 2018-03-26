package cloud.server.one.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cloud.common.controller.BaseController;

@RestController
public class TestController extends BaseController {
	Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = { MAPPING_URL.TEST })
	public Map<String, Object> test() {
		logger.info("执行 test, 机器: [{}:{}]", appName, port);
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("thread", Thread.currentThread().getName());
		resMap.put("currentDate", LocalDateTime.now());
		resMap.put("appName", appName);
		resMap.put("port", port);
		return resMap;
	}

	@RequestMapping(value = { MAPPING_URL.PROVIDER })
	public Map<String, Object> provider() {
		logger.info("执行 provider, 机器: [{}:{}]", appName, port);
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("thread", Thread.currentThread().getName());
		resMap.put("currentDate", LocalDateTime.now());
		resMap.put("appName", appName);
		resMap.put("port", port);
		return resMap;
	}

	@RequestMapping(value = { MAPPING_URL.CONSUMER })
	public Map<String, Object> consumer() {
		logger.info("执行 consumer, 机器: [{}:{}]", appName, port);
		String respJson = restTemplate.getForObject("http://SERVER-TWO/" + MAPPING_URL.PROVIDER, String.class);
		logger.info("调用远程服务： [{}]", respJson);
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("thread", Thread.currentThread().getName());
		resMap.put("currentDate", LocalDateTime.now());
		resMap.put("appName", appName);
		resMap.put("port", port);
		return resMap;
	}
}
