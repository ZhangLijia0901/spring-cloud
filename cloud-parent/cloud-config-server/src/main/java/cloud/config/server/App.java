package cloud.config.server;

import java.io.File;
import java.util.jar.JarFile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class App {
	public static void main(String[] args) {
//		SpringApplication.run(App.class, args);

		File f = new File("D:\\work\\.m2\\repository");
	}

}
