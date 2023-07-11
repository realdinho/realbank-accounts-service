package com.realdiv.accounts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.realdiv.accounts.config.AccountConfig;
import com.realdiv.accounts.model.Account;
import com.realdiv.accounts.model.Card;
import com.realdiv.accounts.model.CustomerDetails;
import com.realdiv.accounts.model.Loan;
import com.realdiv.accounts.model.Property;
import com.realdiv.accounts.repository.AccountRepository;
import com.realdiv.accounts.service.client.CardFeignClient;
import com.realdiv.accounts.service.client.LoanFeignClient;

// import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
// import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
// import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class AccountController {
    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    AccountConfig accountConfig;

    @Autowired
    CardFeignClient cardFeignClient;

    @Autowired
    LoanFeignClient loanFeignClient;
    
    @GetMapping("/{customerId}")
    public Account getAccountDetails(
        @PathVariable int customerId
    ) {
        Account account = accountRepository.findByCustomerId(customerId);
        return account;
    }

    @GetMapping("/{customerId}/details")
    // @CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod = "getAccountDetailsFallback")
    // @Retry(name = "retryForCustomerDetails", fallbackMethod = "getAccountDetailsFallback")
    public CustomerDetails getCustomerDetails(
        @RequestHeader("realbank-correlation-id") String correlationId,
        @PathVariable int customerId
    ) {
        CustomerDetails customerDetails = this.getAccountDetailsByCustomerId(correlationId, customerId);
        return customerDetails;
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

    @GetMapping("/sayHello")
    // @RateLimiter(name = "sayHello", fallbackMethod = "sayHelloFallback")
    public String sayHello() {
        return "Hello, Welcome to RealBank";
    }

    // private String sayHelloFallback(Throwable t) {
    //     return "Hi, Welcome to RealBank";
    // }

    private CustomerDetails getAccountDetailsByCustomerId(String correlationId, int customerId) {
        Account account = accountRepository.findByCustomerId(customerId);
        List<Card> cards = cardFeignClient.getCardsList(correlationId, customerId);
        List<Loan> loans = loanFeignClient.getLoansList(correlationId, customerId);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccount(account);
        customerDetails.setCards(cards);
        customerDetails.setLoans(loans);

        return customerDetails;
    }

    // private CustomerDetails getAccountDetailsFallback(int customerId) {
    //     Account account = accountRepository.findByCustomerId(customerId);
    //     List<Card> cards = cardFeignClient.getCardsList(customerId);
    //     List<Loan> loans = loanFeignClient.getLoansList(customerId);

    //     CustomerDetails customerDetails = new CustomerDetails();
    //     customerDetails.setAccount(account);
    //     customerDetails.setCards(cards);
    //     customerDetails.setLoans(loans);

    //     return customerDetails;
    // }
}
