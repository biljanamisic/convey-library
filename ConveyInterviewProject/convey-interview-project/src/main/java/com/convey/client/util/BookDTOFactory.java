package com.convey.client.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;

import com.convey.api.AuthorDTO;
import com.convey.api.BookDTO;
import com.convey.client.util.exceptions.InconsistentBookDataException;
import com.convey.core.Book.Genre;

public class BookDTOFactory {
		
	private static AuthorDTOFactory authorDTOFactory = new AuthorDTOFactory();
		
	private List<String> populateTitles() {
		List<String> titles = new ArrayList<>();
		titles.add("Na Drini cuprija");
		titles.add("Konstantinovo raskrsce");
		titles.add("Zlocin i kazna");
		titles.add("Gorski vijenac");
		titles.add("Veliki rat");
		titles.add("Misliti na javi");
		titles.add("Seobe");
		titles.add("Hazarski recnik");
		
		return titles;
	}
	
	private String generateIsbn() {
		int count = 10;
		boolean letters = false;
		boolean numbers = true;
		return "978" + RandomStringUtils.random(count, letters, numbers);		
	}
		
	private List<Integer> populateTotalNumbersOfPages() {
		List<Integer> totalNumbersOfPages = new ArrayList<>();
		totalNumbersOfPages.add(502);
		totalNumbersOfPages.add(856);
		totalNumbersOfPages.add(205);
		totalNumbersOfPages.add(125);
		totalNumbersOfPages.add(1005);
		totalNumbersOfPages.add(825);
		totalNumbersOfPages.add(741);
		totalNumbersOfPages.add(444);
		
		return totalNumbersOfPages;
	}
	
	private void validateBookDataConsistency() throws InconsistentBookDataException {
		boolean booksHaveSameNumberOfData = populateTitles().size() == populateTotalNumbersOfPages().size();
		if (!booksHaveSameNumberOfData) {
			throw new InconsistentBookDataException("Number of pages and titles respectively"
					+ " do not correspond to total number of books in preparation");
		}		
	}
	
	private static Genre assignGenre() {
		Genre[] genres = Genre.values();
		Random random = new Random();
		return genres[random.nextInt(genres.length - 1)];
	}
	
	public List<BookDTO> createBooksWithOneAuthor() throws InconsistentBookDataException {
		List<String> titles = populateTitles();
		List<Integer> totalNumbersOfPages = populateTotalNumbersOfPages();
		validateBookDataConsistency();	
		List<BookDTO> booksForSaving = new ArrayList<>();
		
		for (int i = 0; i < titles.size(); i++) {
			BookDTO bookDTO = new BookDTO();
			bookDTO.setTitle(titles.get(i));
			bookDTO.setIsbn(generateIsbn());
			bookDTO.setNumberOfPages(totalNumbersOfPages.get(i));
			bookDTO.setGenre(assignGenre());
			Set<AuthorDTO> authors = authorDTOFactory.createAuthorsPerBook(titles.get(i));  
			bookDTO.setAuthors(authors);   
			booksForSaving.add(bookDTO); 
		}
		
		return booksForSaving;
	}
	
	public List<BookDTO> packBooksForSaving() throws InconsistentBookDataException {		
		List<BookDTO> booksForSaving = createBooksWithOneAuthor();
		booksForSaving.addAll(createTwoBooksWithMultipleAuthors());		
		return booksForSaving;
	}
	
	public Set<BookDTO> createTwoBooksWithMultipleAuthors() {
		Set<BookDTO> books = new HashSet<>();		
		Set<AuthorDTO> authors1 = new HashSet<>();
		Set<AuthorDTO> authors2 = new HashSet<>();
		
		BookDTO bookDTO = new BookDTO();
		bookDTO.setTitle("Neka  knjiga sa 2 autora");
		bookDTO.setIsbn(generateIsbn());
		bookDTO.setGenre(Genre.CLASSIC);
		bookDTO.setNumberOfPages(555);
		
		BookDTO bookDTO2 = new BookDTO();
		bookDTO2.setTitle("Neka druga knjiga sa 3 autora");
		bookDTO2.setIsbn(generateIsbn());
		bookDTO2.setGenre(Genre.CLASSIC); 
		bookDTO2.setNumberOfPages(333); 
		
		AuthorDTO author1 = new AuthorDTO();
		author1.setFirstname("Pera");
		author1.setLastname("Peric");
		
		AuthorDTO author2 = new AuthorDTO();
		author2.setFirstname("Zika");
		author2.setLastname("Zikic");
		
		AuthorDTO author3 = new AuthorDTO();
		author3.setFirstname("Sima");
		author3.setLastname("Simic");
		
		AuthorDTO author4 = new AuthorDTO();
		author4.setFirstname("Mile");
		author4.setLastname("Milic");
		
		AuthorDTO author5 = new AuthorDTO();
		author5.setFirstname("Ana");
		author5.setLastname("Anic");
		
		authors1.add(author1);
		authors1.add(author2);
		
		authors2.add(author3);
		authors2.add(author4);
		authors2.add(author5);		
		
		bookDTO.setAuthors(authors1);
		bookDTO2.setAuthors(authors2);
		
		books.add(bookDTO);
		books.add(bookDTO2);
		
		return books;
	}
}
