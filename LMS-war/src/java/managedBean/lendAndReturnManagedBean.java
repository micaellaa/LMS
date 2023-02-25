/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import exception.BookNotFoundException;
import exception.MemberNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import session.LendAndReturnSessionBeanLocal;

/**
 *
 * @author micaella
 */
@Named(value = "lendAndReturnManagedBean")
@RequestScoped
public class lendAndReturnManagedBean {

    @EJB
    private LendAndReturnSessionBeanLocal lendAndReturnSessionLocal;

    private Member member;
    private Book book;
    private Date lendDate;
    private Date returnDate;
    private BigDecimal finalAmount;

    /**
     * Creates a new instance of lendAndReturnManagedBean
     */
    public lendAndReturnManagedBean() {
    }
    
    public void createNewLendAndReturn(ActionEvent evt) {
        LendAndReturn lend = new LendAndReturn();
        lend.setLendDate(lendDate);
        lend.setReturnDate(returnDate);
        lend.setFinalAmount(finalAmount);
        
        try {
            lendAndReturnSessionLocal.createNewLendAndReturn(lend, member.getMemberId(), book.getBookId());
        } catch (MemberNotFoundException ex) {
            //
        } catch (BookNotFoundException ex) {
            //
        }
    }

    public LendAndReturnSessionBeanLocal getLendAndReturnSessionLocal() {
        return lendAndReturnSessionLocal;
    }

    public void setLendAndReturnSessionLocal(LendAndReturnSessionBeanLocal lendAndReturnSessionLocal) {
        this.lendAndReturnSessionLocal = lendAndReturnSessionLocal;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

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

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }
    
    
}
