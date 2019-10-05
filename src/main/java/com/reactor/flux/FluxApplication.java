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

		// ERRORES and  onComplete Runnable // map

		Flux<String> names2 = Flux.just("Bladi2","Damian","Claudia2","L2","Ivan2")
				.map(String::toUpperCase)
				.doOnNext(item -> {
					if(item.isEmpty()){
						throw new RuntimeException("There can be no empty names");
					}
					System.out.println(item);
				});

		names2.subscribe(logger::info,
				error -> logger.error(error.getMessage()),
				new Runnable() {
					@Override
					public void run() {
						logger.info("End execution flux");
					}
				});

		//--------------------------------------------------
		//map

		Flux<User> names3 = Flux.just("Bladi2","Damian","Claudia2","L2","Ivan2")
				.map(item -> new User(item.toLowerCase(),null))
				.doOnNext(item -> {
					if(item == null){
						throw new RuntimeException("There can be no empty user");
					}
					System.out.println(item.getName());
				});

		names3.subscribe(item -> logger.info(item.toString()),
				error -> logger.error(error.getMessage()),
				new Runnable() {
					@Override
					public void run() {
						logger.info("End execution flux");
					}
				});

		//-----------------------------------------------------------------
		//filter
		Flux<User> names4 = Flux.just("Bladimir Pizarro","L2 Polanski","Claudia 2","L2 Eleazarix","Ivan 2","Frodo Bolson")
				.map(item -> new User(item.split(" ")[0].toUpperCase(), item.split(" ")[1].toUpperCase()))
				.filter(user -> user.getName().toUpperCase().equals("L2"))
				.doOnNext(user -> {
					if(user == null){
						throw new RuntimeException("There can be no empty user");
					}
					System.out.println(user.getName());
				});

		names4.subscribe(item -> logger.info(item.toString()),
				error -> logger.error(error.getMessage()),
				new Runnable() {
					@Override
					public void run() {
						logger.info("End execution flux");
					}
				});

	}
}
