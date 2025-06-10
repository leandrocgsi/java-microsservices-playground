package br.com.erudio.controller;

import br.com.erudio.dto.Exchange;
import br.com.erudio.environment.InstanceInformationService;
import br.com.erudio.proxy.ExchangeProxy;
import br.com.erudio.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Book;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("book-service")
public class BookController {

	@Autowired
	private InstanceInformationService informationService;

	@Autowired
	private BookRepository repository;

	@Autowired
	private ExchangeProxy proxy;

	@GetMapping(value = "/{id}/{currency}",
               produces = { "application/json" })
	public Book findBook(
			@PathVariable("id") Long id,
			@PathVariable("currency") String currency) {

		String port = informationService.retrieveServerPort();

		Book book = repository.findById(id)
				.orElseThrow();

		var exchange = proxy.getExchange(book.getPrice(), "USD", currency);
		book.setEnvironment(port);
		book.setPrice(exchange.getConvertedValue());
		book.setCurrency(currency);
		return book;
	}

	/*
	@GetMapping(value = "/{id}/{currency}",
               produces = { "application/json" })
	public Book findBook(
			@PathVariable("id") Long id,
			@PathVariable("currency") String currency) {

		String port = informationService.retrieveServerPort();

		Book book = repository.findById(id)
				.orElseThrow();

		HashMap<String, String> params = new HashMap<>();

		params.put("amount", book.getPrice().toString());
		params.put("from", "USD");
		params.put("to", currency);

		var response = new RestTemplate()
				.getForEntity("http://localhost:8000/exchange-service/"
								+ " {amount}/{from}/{to}",
						Exchange.class, params);


		Exchange exchange = response.getBody();
		book.setEnvironment(port);
		book.setPrice(exchange.getConvertedValue());
		book.setCurrency(currency);
		return book;
	}*/
}