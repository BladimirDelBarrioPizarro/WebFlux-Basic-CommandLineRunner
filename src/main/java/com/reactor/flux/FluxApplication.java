package com.reactor.flux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class FluxApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(FluxApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(FluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Flux<String> names = Flux.just("Bladi","Claudia","L","Ivan")
				.doOnNext(System.out::println); //doNext notifica que han llegado elementos al flujo (referencia de método)

		names.subscribe(logger::info); //Nos subscribimos al flujo, info y println se ejecutan a la vez porque ambas estan subscritas

		//----------------------------------------------------------------

		// ERRORES

		Flux<String> names2 = Flux.just("Bladi2","","Claudia2","L2","Ivan2")
				.doOnNext(item -> {
					if(item.isEmpty()){
						throw new RuntimeException("There can be no empty names");
					}
					System.out.println(item);
				});
		names2.subscribe(logger::info,
				error -> logger.error(error.getMessage()));


	}
}
