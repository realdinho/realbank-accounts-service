package com.realdiv.accounts.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.realdiv.accounts.model.Card;

@FeignClient("cards")
public interface CardFeignClient {
    
    @GetMapping("/cards/{customerId}")
    List<Card> getCardsList(@PathVariable int customerId);

}
