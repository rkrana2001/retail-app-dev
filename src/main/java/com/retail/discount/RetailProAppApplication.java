package com.retail.discount;


import com.retail.discount.controller.RetailProAppApplicationController;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RetailProAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(RetailProAppApplicationController.class, args);
    }
}