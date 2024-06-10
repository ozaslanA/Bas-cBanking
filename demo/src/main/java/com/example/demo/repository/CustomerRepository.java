package com.example.demo.repository;

import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long > {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT DISTINCT a.customer FROM Account a WHERE a.balance > 1000")
    List<Customer> findCustomersWithBalanceGreaterThan1000();

    @Query("SELECT c FROM Customer c JOIN c.accounts a ORDER BY a.accountNumber ASC")
    List<Customer> findCustomersOrderByAccountNumberAsc();
    @Query("SELECT c FROM Customer c JOIN c.accounts a ORDER BY a.accountNumber DESC")
    List<Customer> findCustomersOrderByAccountNumberDesc();
    List<Customer> findByOrderByNameAsc();
    List<Customer> findByOrderByNameDesc();

    List<Customer> findByOrderByAccountsBalanceAsc();
    List<Customer> findByOrderByAccountsBalanceDesc();







}
