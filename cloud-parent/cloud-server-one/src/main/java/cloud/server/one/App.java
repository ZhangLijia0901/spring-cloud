package cloud.server.one;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import cloud.common.config.CommonConfig;
import cloud.server.one.controller.TestController;

@SpringBootApplication(scanBasePackageClasses = { CommonConfig.class, TestController.class })
@EnableEurekaClient
@EnableHystrix
// @EnableDiscoveryClient
// @SpringCloudApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);

	}

}
