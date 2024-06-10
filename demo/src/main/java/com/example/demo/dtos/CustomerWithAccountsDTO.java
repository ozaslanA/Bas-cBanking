package com.example.demo.dtos;

import com.example.demo.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerWithAccountsDTO {
    private Long id;
    private String name;
    private String email;
    private List<AccountDTO> accounts;

    public static CustomerWithAccountsDTO fromEntity(Customer customer) {
        List<AccountDTO> accountDTOs = customer.getAccounts().stream()
                .map(AccountDTO::fromEntity)
                .collect(Collectors.toList());

        return new CustomerWithAccountsDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                accountDTOs
        );
    }

    public static List<CustomerWithAccountsDTO> fromEntityList(List<Customer> customers) {
        return customers.stream()
                .map(CustomerWithAccountsDTO::fromEntity)
                .collect(Collectors.toList());
    }
}