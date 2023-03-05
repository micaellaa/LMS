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
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<LendAndReturn>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
