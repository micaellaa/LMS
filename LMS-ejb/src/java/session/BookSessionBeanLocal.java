/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import exception.BookExistsException;
import exception.BookNotFoundException;
import exception.InputDataValidationException;
import exception.UnknownPersistenceException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author micaella
 */
@Local
public interface BookSessionBeanLocal {
    
    Book createNewBook(Book book) throws BookExistsException, UnknownPersistenceException, InputDataValidationException;
  
    List<Book> retrieveAllBooks();

    Book retrieveBookByBookId(Long bookId) throws BookNotFoundException;

    Book retrieveBookByIsbn(String isbn) throws BookNotFoundException;

    // void updateBook(Book book) throws BookNotFoundException, UpdateBookException, InputDataValidationException;
    
    // void deleteBook(Long bookId) throws BookNotFoundException, DeleteBookException;
}
