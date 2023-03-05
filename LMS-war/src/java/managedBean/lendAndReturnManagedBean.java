/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import entity.Book;
import entity.LendAndReturn;
import exception.BookNotFoundException;
import exception.InputDataValidationException;
import exception.MemberNotFoundException;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import session.BookSessionBeanLocal;
import session.LendAndReturnSessionBeanLocal;

/**
 *
 * @author micaella
 */
@Named(value = "lendAndReturnManagedBean")
@RequestScoped
public class lendAndReturnManagedBean {

    @EJB
    private BookSessionBeanLocal bookSessionLocal;
    
    @EJB
    private LendAndReturnSessionBeanLocal lendAndReturnSessionLocal;

    private String identityNo;
    private Book book;
    private String isbn;
    //private Date lendDate;
    //private Date returnDate;
    private BigDecimal finalAmount;

    /**
     * Creates a new instance of lendAndReturnManagedBean
     */
    public lendAndReturnManagedBean() {
    }
    
    public void createNewLend(ActionEvent evt) {
        LendAndReturn lend = new LendAndReturn();
        
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        lend.setLendDate(today.getTime());
        lend.setReturnDate(null);
        lend.setFinalAmount(finalAmount);
        
        lend.setFinalAmount(BigDecimal.valueOf(0));
        
        try {
            lendAndReturnSessionLocal.createNewLendAndReturn(lend, identityNo, isbn);
        } catch (MemberNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (BookNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (InputDataValidationException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void createNewReturn(ActionEvent evt) {
        LendAndReturn lend = new LendAndReturn();
        
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        lend.setLendDate(null);
        lend.setReturnDate(today.getTime());
        lend.setFinalAmount(finalAmount);
        
        lend.setFinalAmount(BigDecimal.valueOf(0));
        
        try {
            lendAndReturnSessionLocal.createNewLendAndReturn(lend, identityNo, isbn);
        } catch (MemberNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (BookNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (InputDataValidationException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /*
    public void createNewLendAndReturn(ActionEvent evt) {
        LendAndReturn lendAndReturn = new LendAndReturn();
        lendAndReturn.setLendDate(lendDate);
        lendAndReturn.setReturnDate(returnDate);
        lendAndReturn.setFinalAmount(finalAmount);
        
        try {
            lendAndReturnSessionLocal.createNewLendAndReturn(lendAndReturn, identityNo, isbn);
        } catch (MemberNotFoundException ex) {
            //
        } catch (BookNotFoundException ex) {
            //
        }
    }
    */
    
    public void retrieveBook(ActionEvent evt) {
        
        try {
            book = bookSessionLocal.retrieveBookByIsbn(isbn);
        } catch (BookNotFoundException ex) {
            //
        }
    }
    
    /*
    public void setToLend() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        this.lendDate = today.getTime();
        this.returnDate = null;
    }
    
    public void setToReturn() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        this.returnDate = today.getTime();
        this.lendDate = null;
    }
    */

    public LendAndReturnSessionBeanLocal getLendAndReturnSessionLocal() {
        return lendAndReturnSessionLocal;
    }

    public void setLendAndReturnSessionLocal(LendAndReturnSessionBeanLocal lendAndReturnSessionLocal) {
        this.lendAndReturnSessionLocal = lendAndReturnSessionLocal;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
    public String getBookTitle() {
        return this.book.getTitle();
    }
    
    public String getBookAuthor() {
        return this.book.getAuthor();
    }
    
    public String getBookIsbn() {
        return this.book.getIsbn();
    }

    /*
    public Date getLendDate() {
        return lendDate;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    */

    public BigDecimal getFinalAmount() {
        return finalAmount; //issues
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }
    
    
}
