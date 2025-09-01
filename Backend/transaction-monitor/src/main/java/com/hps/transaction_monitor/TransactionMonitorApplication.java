package com.hps.transaction_monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class TransactionMonitorApplication {

	public static void main(String[] args) {
		Test1 test = new Test1();
		test.testMethod();

		SpringApplication.run(TransactionMonitorApplication.class, args);
	}

}
