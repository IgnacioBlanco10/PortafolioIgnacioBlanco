package Tienda_IgnacioB.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication // <--- CORREGIDO: Solo se usa la anotación base
@ComponentScan (basePackages = {"Tienda_IgnacioB", "com.tienda"}) // cambios
public class TiendaIgnacioBApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaIgnacioBApplication.class, args);
	}

}
