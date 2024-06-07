package com.example.demo.dtos.request;

import lombok.Data;

@Data
public class CreateCustomerRequest {
    private String name;
    private String email;
}
