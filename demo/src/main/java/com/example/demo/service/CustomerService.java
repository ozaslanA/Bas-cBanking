package com.example.demo.service;

import com.example.demo.dtos.CustomerWithAccountsDTO;
import com.example.demo.dtos.request.CreateCustomerRequest;
import com.example.demo.dtos.request.UpdateCustomerRequest;
import com.example.demo.dtos.responses.CustomerAccountResponse;
import com.example.demo.dtos.responses.CustomerResponse;
import com.example.demo.exceptions.BankException;
import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
// Tüm customer bilgisini verir.
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail()))
                .collect(Collectors.toList());
    }

    // Ayrıntılı müşteri bilgisi verir.
    public List<CustomerWithAccountsDTO> getAllCustomersWithAccounts() {
        List<Customer> customers = customerRepository.findAll();
        return CustomerWithAccountsDTO.fromEntityList(customers);
    }


    // Customer bilgisini İsme göre betik sıralar.
    public List<Customer> getAllCustomersSortedByNameAsc() {
        List<Customer> customers = customerRepository.findByOrderByNameAsc();
        if(customers == null || customers.isEmpty()){
            throw new BankException("Sıralanacak bir Müşteri Bulunamadı", HttpStatus.NOT_FOUND);

    }
        return customers;

    }


    // Customer bilgisini İsme göre betik  ters sıralar.
    public List<Customer> getAllCustomersSortedByNameDesc() {
        List<Customer> customers = customerRepository.findByOrderByNameDesc();
        if(customers == null || customers.isEmpty()){
            throw new BankException("Sıralanacak bir Müşteri Bulunamadı", HttpStatus.NOT_FOUND);
        }
        return customers;

    }


// Müşteri bakiyesine göre büyükten küçüğe sıralar
    public List<CustomerWithAccountsDTO> getCustomersOrderByBalanceAsc() {
        List<Customer> customers = customerRepository.findAll();
        return CustomerWithAccountsDTO.fromEntityList(customers);
    }


// Müşteri bakiyesine göre küçükten büyüğe sıralar
    public List<CustomerWithAccountsDTO> getCustomersOrderByBalanceDesc() {
        List<Customer> customers = customerRepository.findAll();
        return CustomerWithAccountsDTO.fromEntityList(customers);
    }



// Bakiyesi 1000 den büyük olan Müşteri bilgilerini verir.
    public List<Customer> getCustomersWithBalanceGreaterThan1000() {
        List<Customer> customers = customerRepository.findCustomersWithBalanceGreaterThan1000();
        if(customers == null || customers.isEmpty()){
            throw new BankException("Bakiyesi 1000'den büyük müşteri bulunamadı", HttpStatus.NOT_FOUND);
        }
        return customers;

    }


// AccountNumber a göre büyükten küçüğüe sıralama
public List<Customer> getByOrderByAccountNumberAsc(){
         List <Customer> customers = customerRepository.findCustomersOrderByAccountNumberAsc();
        if(customers == null || customers.isEmpty()){
            throw new BankException("Sıralancak Bir Müşteri Numarası Bulunamadı",HttpStatus.NOT_FOUND);
        }
        return customers;
}


// AccountNumber a göre küçükten büyüğe sıralama
    public List<Customer> getByOrderByAccountNumberDesc(){
        List <Customer> customers = customerRepository.findCustomersOrderByAccountNumberDesc();
        if(customers == null || customers.isEmpty()){
            throw new BankException("Sıralancak Bir Müşteri Numarası Bulunamadı",HttpStatus.NOT_FOUND);
        }
        return customers;
    }



    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bu id ye ait customer bulunamadı: " + id));
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail());
    }




    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Bu email zaten kullanımda : " + request.getEmail());
        }
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        Customer savedCustomer = customerRepository.save(customer);
        return new CustomerResponse(savedCustomer.getId(), savedCustomer.getName(), savedCustomer.getEmail());
    }


    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bu id ye ait customer bulunamadı: " + id));
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        Customer updatedCustomer = customerRepository.save(customer);
        return new CustomerResponse(updatedCustomer.getId(), updatedCustomer.getName(), updatedCustomer.getEmail());
    }


    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bu id ye ait customer bulunamadı: " + id));
        customerRepository.delete(customer);
    }


    public List<CustomerAccountResponse> getAccountsMaxBalancePerCustomer() {
        List<Account> accounts = customerRepository.findAccountsMaxBalancePerCustomer();

        if (accounts.isEmpty() || accounts == null) {
            throw new BankException("İlgili Hesaplar Bulunamadı.",HttpStatus.NOT_FOUND);
        }

        return accounts.stream()
                .map(account -> new CustomerAccountResponse(
                        account.getId(),
                        account.getAccountNumber(),
                        account.getBalance(),
                        account.getCustomer().getId(),
                        account.getCustomer().getName(),
                        account.getCustomer().getEmail()))
                .collect(Collectors.toList());
    }

}
