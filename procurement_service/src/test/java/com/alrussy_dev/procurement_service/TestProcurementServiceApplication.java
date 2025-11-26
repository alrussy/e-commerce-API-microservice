package com.alrussy_dev.procurement_service;

import org.springframework.boot.SpringApplication;

public class TestProcurementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProcurementServiceApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }
}
