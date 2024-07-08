package com.yahoofinance.bc_yahoo_finance.entity;

import java.io.Serializable;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "t_stocks")
@AllArgsConstructor
@NoArgsConstructor
public class Stock implements Serializable{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) 
  private Long id;

  private String symbol;

  public Stock(String symbol) {
    this.symbol = symbol;
  }
}
