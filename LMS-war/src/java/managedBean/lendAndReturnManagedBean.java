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
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    
    private List<LendAndReturn> activeLoans;
    
    private Long lendId;
    
    private Long loanId;
    private LendAndReturn selectedLendAndReturn; // redundant
    private BigDecimal finalAmount;
    
    private String retrievedFineAmount;

    /**
     * Creates a new instance of lendAndReturnManagedBean
     */
    public lendAndReturnManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        setActiveLoans(lendAndReturnSessionLocal.getActiveLoans());
    }
    
    public void createNewLend(ActionEvent evt) {
        LendAndReturn lend = new LendAndReturn();
        
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        lend.setLendDate(today.getTime());
        lend.setReturnDate(null);
        lend.setFinalAmount(finalAmount);
        
        lend.setFinalAmount(BigDecimal.valueOf(0));
        lend.setIsActive(true);
        
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
        lend.setIsActive(true);
        
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
    
    public void createNewReturnForLoan() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String loanIdStr = params.get("loanId");
        Long loanId = Long.parseLong(loanIdStr);
        try {
            lendAndReturnSessionLocal.createNewReturnOnLoan(loanId);
        } catch (Exception e) {
            //show with an error icon context.addMessage(null, new
            //FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to delete customer"));
        }
        //context.addMessage(null, new FacesMessage("Success", "Successfully deleted customer"));
        init();
    } 
    
    public void setFineAmountForId() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String loanIdStr = params.get("loanId");
        Long loanId = Long.parseLong(loanIdStr);
        System.out.println("setFineAmountForId: " + loanId);
        try {
            this.retrievedFineAmount = lendAndReturnSessionLocal.retrieveFineAmountById(loanId).toString();
        } catch (Exception e) {
            //show with an error icon context.addMessage(null, new
            //FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to delete customer"));
        }
        //context.addMessage(null, new FacesMessage("Success", "Successfully deleted customer"));
        init();
    }
    
    public void loadSelectedLendAndReturn() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.selectedLendAndReturn
                    = lendAndReturnSessionLocal.retrieveLendAndReturnById(loanId);
            lendId = this.selectedLendAndReturn.getLendId();
            finalAmount = this.selectedLendAndReturn.getFinalAmount();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load customer"));
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

    public Long getLendId() {
        return lendId;
    }

    public void setLendId(Long lendId) {
        this.lendId = lendId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public LendAndReturn getSelectedLendAndReturn() {
        return selectedLendAndReturn;
    }

    public void setSelectedLendAndReturn(LendAndReturn selectedLendAndReturn) {
        this.selectedLendAndReturn = selectedLendAndReturn;
    }

    public LendAndReturnSessionBeanLocal getLendAndReturnSessionLocal() {
        return lendAndReturnSessionLocal;
    }

    public void setLendAndReturnSessionLocal(LendAndReturnSessionBeanLocal lendAndReturnSessionLocal) {
        this.lendAndReturnSessionLocal = lendAndReturnSessionLocal;
    }

    public List<LendAndReturn> getActiveLoans() {
        return activeLoans;
    }

    public void setActiveLoans(List<LendAndReturn> activeLoans) {
        this.activeLoans = activeLoans;
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

    public String getFinalAmount() {
        return finalAmount.toString(); //issues
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getRetrievedFineAmount() {
        return retrievedFineAmount;
    }

    public void setRetrievedFineAmount(String retrievedFineAmount) {
        this.retrievedFineAmount = retrievedFineAmount;
    }
    
    
    
}
