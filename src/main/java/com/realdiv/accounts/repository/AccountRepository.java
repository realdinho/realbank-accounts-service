package com.realdiv.accounts.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.realdiv.accounts.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    
    Account findByCustomerId(int customerId); 

}
