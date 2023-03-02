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

    private Long memberId;
    private Long bookId;
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
            lendAndReturnSessionLocal.createNewLendAndReturn(lend, memberId, bookId);
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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
