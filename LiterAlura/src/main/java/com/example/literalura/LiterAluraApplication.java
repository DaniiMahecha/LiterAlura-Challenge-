package com.example.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.literalura.principal.Principal;
import com.example.literalura.repository.AutoresRepository;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    @Autowired
    private AutoresRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

       Principal principal = new Principal(repository);
       principal.menu();
    }
}
