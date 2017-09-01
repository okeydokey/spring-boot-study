package com.okeydokey;

import com.okeydokey.domain.Customer;
import com.okeydokey.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        customerRepository.save(new Customer(null, "Eidetoshi", "Aekisugi"));
        customerRepository.save(new Customer(null, "Aidetoshi", "Bekisugi"));
        customerRepository.save(new Customer(null, "Cidetoshi", "Cekisugi"));
        customerRepository.save(new Customer(null, "Aidetoshi", "Dekisugi"));

        Pageable pageable = new PageRequest(0, 3);
        Page<Customer> page = customerRepository.findAll(pageable);

        page.getContent().forEach(System.out::println);
    }
}