package com.realdiv.accounts.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CustomerDetails {
    
    private Account account;
    private List<Loan> loans;
    private List<Card> cards;

}
