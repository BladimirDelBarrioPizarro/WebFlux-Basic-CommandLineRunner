package com.reactor.flux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class FluxApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(FluxApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(FluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//IniciationFlux();
		//flatMapMethod();
		//mapperFlux();
		//listToMonoList();
		//UserCommentsFlatMap();
		//rangeMethod();
		//intervalMethod();
		delayMethod();
	}

	public List<String> userListMethod(){
		List<String> userList = new ArrayList<>();
		userList.add("Bladi test");
		userList.add("Eliamertix Fulano");
		userList.add("Eliamertix Mengano");
		userList.add("Claudia Sanchez");
		userList.add("Bruce Willis");
		userList.add("Bruce Pizarro");
		return userList;
	}

	public List<User> userMethodList(){
		List<User> userList = new ArrayList<>();
		User user1 = new User("Bladi test","TestAdrressB");
		User user2 = new User("Eliamertix Fulano","TestAdrressE1");
		User user3 = new User("Eliamertix Mengano","TestAdrressE2");
		User user4 = new User("Claudia Sanchez","TestAdrressC");
		User user5 = new User("Bruce Willis","TestAdrressB");
		User user6 = new User("Bruce Pizarro","TestAdrressB");
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
		userList.add(user4);
		userList.add(user5);
		userList.add(user6);
		return userList;
	}


		public void IniciationFlux(){
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
		//----------------------------------------------------------------------
		// Los flujos son inmutables
		Flux<String> names5 = Flux.just("Bladimir Pizarro","L2 Polanski","Claudia 2","L2 Eleazarix","Ivan 2","Frodo Bolson");
		Flux<User> users5 = names.map(item -> new User(item.split(" ")[0].toUpperCase(), item.split(" ")[1].toUpperCase()))
				.filter(user -> user.getName().toUpperCase().equals("L2"))
				.doOnNext(user -> {
					if(user == null){
						throw new RuntimeException("There can be no empty user");
					}
					System.out.println(user.getName());
				});
		//Al subscribir names5 lo observamos y al cambiar a users5
		names5.subscribe(item -> logger.info(item.toString()),
				error -> logger.error(error.getMessage()),
				new Runnable() {
					@Override
					public void run() {
						logger.info("End execution flux");
					}
				});

		//----------------------------------------------------------------------
		// Flux.fromIterable

		List<String> userList = userListMethod();

		Flux<String> names6 = Flux.fromIterable(userList);

		names6.subscribe(item -> logger.info(item.toString()),
				error -> logger.error(error.getMessage()),
				new Runnable() {
					@Override
					public void run() {
						logger.info("End execution flux");
					}
				});
		} // end IniciationFlux

        public void flatMapMethod(){
			List<String> userList = userListMethod();
			Flux<String> names = Flux.fromIterable(userList);
			Flux<User> usersFlux =  names.map(item -> new User(item.split(" ")[0].toUpperCase(), item.split(" ")[1].toUpperCase()))
					.flatMap(user -> {
						if(user.getName().equalsIgnoreCase("Bruce")){
							String name = user.getName().toLowerCase().concat(" Sopra");
							user.setName(name);
							user.setAddress("Avenida Manoteras");
							return Mono.just(user);
						}
						else{
							return Mono.empty();
						}
					});
					//.filter(user -> user.getName().toUpperCase().equalsIgnoreCase("Eliamertix"))
					/*.map( user -> {
						String name = user.getName().toLowerCase().concat(" Sopra");
						user.setName(name);
						user.setAddress("Avenida Manoteras");
						return user;
					});*/

					usersFlux.subscribe(item -> logger.info(item.toString()));

		} //end flatmap

		//---------------------------------------------------------------------------------------

	    public void mapperFlux(){
			List<User> userList = userMethodList();
			Flux.fromIterable(userList)
					.map(item -> item.getName().toUpperCase().concat(" example mapper"))
					.flatMap(nombre -> {
						if(nombre.contains("BRUCE")){
							return Mono.just(nombre);
						}
						else{
							return Mono.empty();
						}
					}).subscribe(item -> logger.info(item.toString()));
		}

	//---------------------------------------------------------------------------------------
	// Mappeo de lista de User a Mono User collectList() mostramos  usuario a usuario con forEach

		public void listToMonoList(){
			List<User> userList = userMethodList();
			Flux.fromIterable(userList)
					.collectList()
					.subscribe(list -> {
						list.forEach(item -> logger.info(item.toString()));
					});
		}

	//---------------------------------------------------------------------------------------
	// fromCallable zipWith

	public void UserCommentsFlatMap(){
		Mono<User> userMono = Mono.fromCallable(() -> new User("John","Principe Vergara"));
		Mono<Comments> commentsMono = Mono.fromCallable(() ->{
			List<String> listComments = new ArrayList<>();
			listComments.add("Comment 1");
			listComments.add("Comment 2");
			Comments comments = new Comments();
			comments.setComments(listComments);
			return comments;
		});

		//Mapeo con flatMap
		userMono.flatMap(item -> commentsMono.map(c -> new UserComments(item,c)));
        //Mapeo con zipWith
		Mono<UserComments> userCommentsMono = commentsMono.zipWith(userMono,(c,u) -> new UserComments(u,c));
		userCommentsMono.subscribe(userComments -> logger.info(userComments.toString()));
	}

	//---------------------------------------------------------------------------------------
    // range
	public void rangeMethod(){
		//Flux<Integer> ranges = Flux.range(0,4);
		Flux.just(1,2,3,4)
				.map(item -> item*2)
				.zipWith(Flux.range(0,4),(one,two) -> String.format("First flux: %d, Second Flux: %d",one,two))
				.subscribe(item -> logger.info(item.toString()));
	}

	//---------------------------------------------------------------------------------------
     // interval

	public void intervalMethod(){
		Flux<Integer> range = Flux.range(1,12);
		Flux<Long> delay = Flux.interval(Duration.ofSeconds(2));

		range.zipWith(delay,(r,d) -> r )
			 .doOnNext(item -> logger.info(item.toString()))
				.blockLast();

	}

	// delay
	public void delayMethod() throws InterruptedException {
		Flux<Integer> range = Flux.range(1,12)
				.delayElements(Duration.ofSeconds(2))
				.doOnNext(item -> logger.info(item.toString()));
		range.blockLast();
	}


























}
