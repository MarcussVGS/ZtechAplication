package ZtechAplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "ZtechAplication.model")
public class ZtechAplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZtechAplicationApplication.class, args);
	}

}
