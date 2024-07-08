package com.yahoofinance.bc_yahoo_finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BcYahooFinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BcYahooFinanceApplication.class, args);
	}

}
