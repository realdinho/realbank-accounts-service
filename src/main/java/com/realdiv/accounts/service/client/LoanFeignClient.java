package com.realdiv.accounts.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.realdiv.accounts.model.Loan;

@FeignClient("loans")
public interface LoanFeignClient {
    
    @RequestMapping(method = RequestMethod.GET, value = "/loans/{loanId}", consumes = "application/json")
    List<Loan> getLoansList(@PathVariable int loanId);

}
