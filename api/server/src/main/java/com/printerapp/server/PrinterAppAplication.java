package com.printerapp.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.printerapp")
@EntityScan(basePackages = "com.printerapp.infrastructure.persistence.models")
public class PrinterAppAplication {
    public static void main(String[] args) {
        SpringApplication.run(PrinterAppAplication.class, args);
    }
}
