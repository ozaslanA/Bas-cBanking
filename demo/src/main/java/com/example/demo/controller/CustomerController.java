package com.example.demo.controller;
import com.example.demo.dtos.CustomerWithAccountsDTO;
import com.example.demo.dtos.request.CreateCustomerRequest;
import com.example.demo.dtos.request.TransferRequest;
import com.example.demo.dtos.request.UpdateCustomerRequest;
import com.example.demo.dtos.responses.CustomerAccountResponse;
import com.example.demo.dtos.responses.CustomerResponse;
import com.example.demo.model.Customer;
import com.example.demo.service.AccountService;
import com.example.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private CustomerService customerService;
    private AccountService accountService;

    @Autowired
    public CustomerController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }


    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }


    @GetMapping("/withAccounts")
    public ResponseEntity<List<CustomerWithAccountsDTO>> getAllCustomersWithAccounts() {
        List<CustomerWithAccountsDTO> customers = customerService.getAllCustomersWithAccounts();
        return ResponseEntity.ok(customers);
    }


    @GetMapping("/sorted/customerBalanceAsc")
    public List<CustomerWithAccountsDTO> getCustomersSortedByBalanceAsc() {
        return customerService.getCustomersOrderByBalanceAsc();
    }


    @GetMapping("/sorted/customerBalanceDesc")
    public List<CustomerWithAccountsDTO> getCustomersSortedByBalanceDesc() {
        return customerService.getCustomersOrderByBalanceDesc();
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById( @PathVariable Long id) {
        CustomerResponse customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }


    @GetMapping("/sorted/nameAsc")
    public ResponseEntity<List<Customer>> getAllCustomersSortedByNameAsc() {
        List<Customer> customers = customerService.getAllCustomersSortedByNameAsc();
        return ResponseEntity.ok(customers);
    }


    @GetMapping("/sorted/nameDesc")
    public ResponseEntity<List<Customer>> getAllCustomersSortedByNameDesc() {
        List<Customer> customers = customerService.getAllCustomersSortedByNameDesc();
        return ResponseEntity.ok(customers);
    }


    @GetMapping("/sorted/balance1000")
    public List<Customer> getCustomersWithBalanceGreaterThan1000() {
        return customerService.getCustomersWithBalanceGreaterThan1000();
    }


    @GetMapping("/sorted/accountNumberAsc")
    public List<Customer> getByOrderByAccountNumberAsc() {
        return customerService.getByOrderByAccountNumberAsc();
    }


    @GetMapping("/sorted/accountNumberDesc")
    public List<Customer> getByOrderByAccountNumberDesc() {
        return customerService.getByOrderByAccountNumberDesc();
    }


    @PostMapping("/create")
    public ResponseEntity<CustomerResponse> createCustomer( @Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse createdCustomer = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer( @Valid @PathVariable Long id, @RequestBody UpdateCustomerRequest request) {
        CustomerResponse updatedCustomer = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(updatedCustomer );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);

        return ResponseEntity.ok("Customer Başarıyla silindi");
    }


    @GetMapping("/sorted/max-balance-accounts")
    public ResponseEntity<List<CustomerAccountResponse>> getAccountsMaxBalancePerCustomer() {
        List<CustomerAccountResponse> responses = customerService.getAccountsMaxBalancePerCustomer();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }


    @PostMapping("/transfer")
    public ResponseEntity<Void> transferBalance(@RequestBody TransferRequest transferRequest) {
        accountService.transferBalance(transferRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
