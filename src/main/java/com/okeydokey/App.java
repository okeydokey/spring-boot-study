package com.okeydokey;

import com.okeydokey.domain.Customer;
import com.okeydokey.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class App implements CommandLineRunner {

    @Autowired
    CustomerService customerService;


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Customer created = customerService.save(new Customer(null, "Hidetoshi", "Dekisugi"));

        System.out.println(created + " is created!");

        customerService.findAll().forEach(System.out::println);
    }
}