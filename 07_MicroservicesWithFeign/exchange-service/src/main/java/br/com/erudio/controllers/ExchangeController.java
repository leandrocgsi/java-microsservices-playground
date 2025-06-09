package br.com.erudio.controllers;

import java.math.BigDecimal;

import br.com.erudio.environment.InstanceInformationService;
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

    // http://localhost:8000/exchange-service/5/USD/BRL
    @GetMapping(value = "/{amount}/{from}/{to}",
            produces = { "application/json" })
    public Exchange getExchange (
            @PathVariable("amount") BigDecimal amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    ) {
        return new Exchange(1L, from, to,
            BigDecimal.ONE,BigDecimal.ONE, "PORT " + informationService.retrieveServerPort());
    }
}