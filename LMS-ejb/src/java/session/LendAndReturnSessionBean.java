/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import exception.BookNotFoundException;
import exception.InputDataValidationException;
import exception.MemberNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author micaella
 */
@Stateless
public class LendAndReturnSessionBean implements LendAndReturnSessionBeanLocal {
    
    private final long msInADay = 86400000;
    private final long msIn2Weeks = 1209600000;
    private final long finePerDay = (long) 0.5;
    
    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public LendAndReturnSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @EJB
    private BookSessionBeanLocal bookSessionBean;

    @EJB
    private MemberSessionBeanLocal memberSessionBean;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void persist(Object object) {
        em.persist(object);
    }
    
    @Override
    public LendAndReturn retrieveLendAndReturnById(Long id) {
        System.out.println("retrieveLendAndReturnById: " + id);
        LendAndReturn lendAndReturn = em.find(LendAndReturn.class, id);
        return lendAndReturn;
    }

    @Override
    public BigDecimal retrieveFineAmountById(Long lendId) {
        LendAndReturn lendAndReturn = this.retrieveLendAndReturnById(lendId);
        Date lendDate = lendAndReturn.getLendDate();
        
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
            
        long finableMs = Math.abs(today.getTime().getTime() - lendDate.getTime()) - msIn2Weeks;
        if (finableMs > 0) {
            long fineAmount = finableMs / msInADay * finePerDay;
            return new BigDecimal(fineAmount);
        }
      
        return BigDecimal.ZERO;
    }

    @Override
    public LendAndReturn createNewLendAndReturn(LendAndReturn lendAndReturn, String identityNo, String isbn) throws MemberNotFoundException, BookNotFoundException, InputDataValidationException {
        
        
        try {
            Member member = memberSessionBean.retrieveMemberByIdentityNo(identityNo);

            lendAndReturn.setMember(member);
            member.addLendAndReturn(lendAndReturn);
            
            Book book = bookSessionBean.retrieveBookByIsbn(isbn);
            lendAndReturn.setBook(book);
            book.addLendAndReturn(lendAndReturn);
            
            Set<ConstraintViolation<LendAndReturn>> constraintViolations = validator.validate(lendAndReturn);
            
            if (constraintViolations.isEmpty()) {
                em.persist(lendAndReturn);
                em.flush();
                return lendAndReturn;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
            
        } catch (MemberNotFoundException ex) {
            throw new MemberNotFoundException("Unable to record new lend or return as the member record cannot be found.");
        } catch (BookNotFoundException ex) {
            throw new BookNotFoundException("Unable to record new lend or return as the book record cannot be found.");
        } 
    }
    
    @Override
    public LendAndReturn createNewReturnOnLoan(Long loanId) throws MemberNotFoundException, BookNotFoundException, InputDataValidationException {
        try {
            LendAndReturn loan = retrieveLendAndReturnById(loanId);
            LendAndReturn returnOnLoan = new LendAndReturn();
            
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            returnOnLoan.setLendDate(null);
            returnOnLoan.setReturnDate(today.getTime()); 
            returnOnLoan.setFinalAmount(BigDecimal.valueOf(0));
            returnOnLoan.setIsActive(false);
            
            returnOnLoan = createNewLendAndReturn(returnOnLoan, loan.getMember().getIdentityNo(), loan.getBook().getIsbn());
            
            if (returnOnLoan != null) {
                // 'deactivate' the corresponding loan to this return
                loan.setIsActive(false);
            }
            
            return returnOnLoan;
        } catch (MemberNotFoundException ex) {
            throw new MemberNotFoundException("Unable to record new lend or return as the member record cannot be found.");
        } catch (BookNotFoundException ex) {
            throw new BookNotFoundException("Unable to record new lend or return as the book record cannot be found.");
        } 
    }
    
    public List<LendAndReturn> getActiveLoans() {
        Query query = em.createQuery("SELECT l FROM LendAndReturn l");
        List<LendAndReturn> allLendAndReturns = query.getResultList();
        List<LendAndReturn> activeLoans = new ArrayList();
        
        Iterator<LendAndReturn> iter = allLendAndReturns.iterator();
        
        while (iter.hasNext()) {
            LendAndReturn curr = iter.next();
            if (curr.getIsActive()) {
                activeLoans.add(curr);
            }
        }
        
        return activeLoans;
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<LendAndReturn>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
