package com.yahoofinance.bc_yahoo_finance.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "weekly_historical_data")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyHistoricalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    @ElementCollection
    @CollectionTable(name = "weekly_historical_dates", joinColumns = @JoinColumn(name = "historical_data_id"))
    @Column(name = "date")
    private List<LocalDate> dates;

    @ElementCollection
    @CollectionTable(name = "weekly_historical_opens", joinColumns = @JoinColumn(name = "historical_data_id"))
    @Column(name = "open")
    private List<Double> opens;

    @ElementCollection
    @CollectionTable(name = "weekly_historical_highs", joinColumns = @JoinColumn(name = "historical_data_id"))
    @Column(name = "high")
    private List<Double> highs;

    @ElementCollection
    @CollectionTable(name = "weekly_historical_lows", joinColumns = @JoinColumn(name = "historical_data_id"))
    @Column(name = "low")
    private List<Double> lows;

    @ElementCollection
    @CollectionTable(name = "weekly_historical_closes", joinColumns = @JoinColumn(name = "historical_data_id"))
    @Column(name = "close")
    private List<Double> closes;

    @ElementCollection
    @CollectionTable(name = "weekly_historical_volumes", joinColumns = @JoinColumn(name = "historical_data_id"))
    @Column(name = "volume")
    private List<Integer> volumes;

}