package sv.edu.udb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SistemaPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaPedidosApplication.class, args);
	}

}

