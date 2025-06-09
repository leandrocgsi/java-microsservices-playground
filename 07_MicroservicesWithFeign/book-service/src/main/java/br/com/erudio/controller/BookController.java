package br.com.erudio.controller;

import br.com.erudio.environment.InstanceInformationService;
import br.com.erudio.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Book;

@RestController
@RequestMapping("book-service")
public class BookController {

	@Autowired
	private InstanceInformationService informationService;

	@Autowired
	private BookRepository repository;

	@GetMapping(value = "/{id}/{currency}",
               produces = { "application/json" })
	public Book findBook(
			@PathVariable("id") Long id,
			@PathVariable("currency") String currency) {

		String port = informationService.retrieveServerPort();

		Book book = repository.findById(id)
				.orElseThrow();
		book.setEnvironment(port);
		book.setCurrency(currency);
		return book;
	}
}