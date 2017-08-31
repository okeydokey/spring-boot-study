package com.okeydokey;

import com.okeydokey.domain.Customer;
import com.okeydokey.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class App implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Customer created = customerRepository.save(new Customer(null, "Hidetoshi", "Dekisugi"));

        System.out.println(created + " is created!");

        customerRepository.findAll().forEach(System.out::println);
    }
}