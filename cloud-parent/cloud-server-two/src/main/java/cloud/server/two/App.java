package cloud.server.two;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import cloud.common.config.CommonConfig;
import cloud.server.two.controller.TestController;

@SpringBootApplication(scanBasePackageClasses = { CommonConfig.class, TestController.class })
// @EnableDiscoveryClient
@EnableEurekaClient
// @SpringCloudApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
