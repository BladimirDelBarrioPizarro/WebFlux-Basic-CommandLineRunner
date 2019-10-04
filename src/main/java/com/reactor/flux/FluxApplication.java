package com.reactor.flux;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class FluxApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Flux<String> names = Flux.just("Bladi","Claudia","L","Ivan")
				.doOnNext(System.out::println); //doNext notifica que han llegado elementos al flujo (referencia de método)
		names.subscribe(); //Nos subscribimos al flujo
	}
}
