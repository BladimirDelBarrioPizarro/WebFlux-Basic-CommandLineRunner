package com.webflux.app;

import com.webflux.app.dao.BillingDao;
import com.webflux.app.model.entity.Billing;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@SpringBootApplication
public class AppApplication implements CommandLineRunner {

	private static final Logger logg = LoggerFactory.getLogger(AppApplication.class);

	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;

	@Autowired
	private BillingDao billingDao;
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		reactiveMongoTemplate.dropCollection("billing").subscribe();

		Flux.just(new Billing( 3, 45, 30),
				new Billing( 3, 67, 30),
				new Billing(3, 78, 30),
				new Billing( 3, 112, 30)
		)
				.flatMap(item -> {
					SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
					String dateStr = DATE_FORMAT.format(new Date());
					item.setDate(dateStr);
					return billingDao.save(item);
				})
				.subscribe(item -> logg.info("Insert: "+item.toString()));
	}

}