package com.realdiv.accounts.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.realdiv.accounts.model.Loan;

@FeignClient("loans")
public interface LoanFeignClient {
    
    @RequestMapping(method = RequestMethod.GET, value = "/{loanId}", consumes = "application/json")
    List<Loan> getLoansList(
        @RequestHeader("realbank-correlation-id") String correlationId,
        @PathVariable int loanId
    );

}
