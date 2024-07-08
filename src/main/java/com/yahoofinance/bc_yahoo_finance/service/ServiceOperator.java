package com.yahoofinance.bc_yahoo_finance.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yahoofinance.bc_yahoo_finance.entity.QuoteData;
import com.yahoofinance.bc_yahoo_finance.infra.QuoteType;
import com.yahoofinance.bc_yahoo_finance.repository.QuoteDataRepository;

@Service
public class ServiceOperator {

    @Value(value = "${api.yahoofinance.domain}")
    private String domain;

    @Value(value = "${api.yahoofinance.endpoints.quote}")
    private String quoteEndPoint;

    @Value(value = "${api.yahoofinance.crumb}")
    private String crumb;

    @Value(value = "${api.yahoofinance.cookie}")
    private String cookie;
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private QuoteDataRepository quoteDataRepository;

    public String getStockData(String crumbKey, String symbol) {
        String apiUrl = String.format("https://query2.finance.yahoo.com/v7/finance/download/%s?crumb=%s", symbol,
                crumbKey);
        return restTemplate.getForObject(apiUrl, String.class);
    }

    public void fetchAndSaveStockQuotes(List<String> symbols) {
        for (String symbol: symbols) {
            getQuoteData(crumb, symbol);
        }
    }

    public void getQuoteData(String crumbKey, String symbol) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.domain + this.quoteEndPoint)
        .queryParam("symbols", symbol)
        .queryParam("crumb", crumb);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, cookie);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        Map<String, Object> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Map.class).getBody();

        // Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
        Map<String, Object> quoteResponse = (Map<String, Object>) response.get("quoteResponse");
        ArrayList<Object> result = (ArrayList<Object>) quoteResponse.get("result");

        if (quoteResponse != null && !quoteResponse.isEmpty()) {
            Map<String, Object> stockInfo = (Map<String, Object>) result.get(0);

            long regularMarketUnix = ((Number) stockInfo.get("regularMarketTime")).longValue();
            LocalDateTime marketTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(regularMarketUnix), ZoneId.systemDefault());

            QuoteData quoteData = QuoteData.builder()//
                                .symbol((String) stockInfo.get("symbol"))//
                                .regularMarketUnix(regularMarketUnix)//
                                .regularMarketPrice((double)stockInfo.get("regularMarketPrice"))//
                                .regularMarketChangePercent((double)stockInfo.get("regularMarketChangePercent"))//
                                .bid((double)stockInfo.get("bid"))//
                                .bidSize((int)stockInfo.get("bidSize"))//
                                .ask((double)stockInfo.get("ask"))//
                                .askSize((int)stockInfo.get("askSize"))//
                                .type(QuoteType.FIVEMIN.getType())//
                                .apiDatetime(LocalDateTime.now())// 
                                .marketTime(marketTime)//
                                .build();

            quoteDataRepository.save(quoteData);

        }
    }
}