package cloud.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class App {
	public static void main(String[] args) {
//		test();
		SpringApplication.run(App.class, args);
	}

	static void test() {
		new A().a();
	}

	static class A {
		void a() {
			StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
			stackTrace =new Throwable().getStackTrace();
			for (StackTraceElement s : stackTrace) {

				System.err.println(s.getClassName() + ", " + s.getMethodName() + ", " + s.getLineNumber());
			}
		}
	}
}
