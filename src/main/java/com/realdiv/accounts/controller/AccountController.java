package com.realdiv.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.realdiv.accounts.config.AccountConfig;
import com.realdiv.accounts.model.Account;
import com.realdiv.accounts.model.Property;
import com.realdiv.accounts.repository.AccountRepository;

@RestController
@RequestMapping("accounts")
public class AccountController {
    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    AccountConfig accountConfig;
    
    @GetMapping("/{customerId}")
    public Account getAccountDetails(
        @PathVariable int customerId
    ) {
        Account account = accountRepository.findByCustomerId(customerId);
        return account;
    }

    @GetMapping("/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Property properties = new Property(
                                accountConfig.getMsg(), 
                                accountConfig.getBuildVersion(),
                                accountConfig.getMailDetails(),
                                accountConfig.getActiveBranches()
                            );
        return ow.writeValueAsString(properties);
    }

}
