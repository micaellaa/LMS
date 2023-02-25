/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import entity.Book;
import exception.BookExistsException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import session.BookSessionBeanLocal;

/**
 *
 * @author micaella
 */
@Named(value = "bookManagedBean")
@RequestScoped
public class bookManagedBean {

    @EJB
    private BookSessionBeanLocal bookSessionLocal;
    
    private String title;
    private String isbn;
    private String author;

    /**
     * Creates a new instance of bookManagedBean
     */
    public bookManagedBean() {
    }
    
    public void addNewBook(ActionEvent evt) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthor(author);
        try {
            bookSessionLocal.createNewBook(book);
        } catch(BookExistsException ex) {
            //
        } catch(Exception ex) {
            //
        } 
    }

    public BookSessionBeanLocal getBookSessionLocal() {
        return bookSessionLocal;
    }

    public void setBookSessionLocal(BookSessionBeanLocal bookSessionLocal) {
        this.bookSessionLocal = bookSessionLocal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
}
