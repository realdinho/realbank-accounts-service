package com.realdiv.accounts.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.realdiv.accounts.model.Card;

@FeignClient("cards")
public interface CardFeignClient {
    
    @GetMapping("/{customerId}")
    List<Card> getCardsList(
        @RequestHeader("realbank-correlation-id") String correlationId,
        @PathVariable int customerId
    );

}
