package cloud.server.one.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;

@RestController
public class HelloController {

	@Autowired
	DiscoveryClient client;

	@GetMapping("hello")
	public String hello() {
		List<String> services = client.getServices();
		services.forEach(System.err::println);
		return "hello";
	}

}
