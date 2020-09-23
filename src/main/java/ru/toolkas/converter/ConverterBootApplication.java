package ru.toolkas.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ConverterBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConverterBootApplication.class, args);
	}
}
