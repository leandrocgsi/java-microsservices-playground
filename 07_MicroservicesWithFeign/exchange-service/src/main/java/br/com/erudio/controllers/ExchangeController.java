package br.com.erudio.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.erudio.environment.InstanceInformationService;
import br.com.erudio.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Exchange;

@RestController
@RequestMapping("exchange-service")
public class ExchangeController {

    @Autowired
    InstanceInformationService informationService;


    @Autowired
    private ExchangeRepository repository;


    // http://localhost:8000/exchange-service/5/USD/BRL
    @GetMapping(value = "/{amount}/{from}/{to}",
            produces = { "application/json" })
    public Exchange getExchange (
            @PathVariable("amount") BigDecimal amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    ) {
        Exchange exchange = repository.findByFromAndTo(from, to);

        if (exchange == null) throw new RuntimeException("Currency Unsupported");


        BigDecimal conversionFactor = exchange.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);
        exchange.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        exchange.setEnvironment("PORT " + informationService.retrieveServerPort());
        return exchange;

    }
}