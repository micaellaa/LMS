/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.LendAndReturn;
import exception.BookExistsException;
import exception.BookNotFoundException;
import exception.InputDataValidationException;
import exception.UnknownPersistenceException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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
public class BookSessionBean implements BookSessionBeanLocal {
    @PersistenceContext
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public BookSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Book createNewBook(Book book) throws BookExistsException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<Book>> constraintViolations = validator.validate(book);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(book);
                em.flush();
                return book;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new BookExistsException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public List<Book> retrieveAllBooks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Book retrieveBookByBookId(Long bookId) throws BookNotFoundException {
        Book book = em.find(Book.class, bookId);

        if (book != null) {
            return book;
        } else {
            throw new BookNotFoundException("Book ID " + bookId + " does not exist!");
        }
    }
    
    @Override
    public Boolean isBookOnActiveLoan(Book book) {
        ArrayList<LendAndReturn> lendAndReturns = book.getLendAndReturns();
        Iterator<LendAndReturn> iter = lendAndReturns.iterator();
        
        while (iter.hasNext()) {
            LendAndReturn curr = iter.next();
            if (curr.getLendDate() != null && curr.getIsActive()) {// if a lendAndReturn is an active loan
                return true;
            }
        }
        return false;
    }

    @Override
    public Book retrieveBookByIsbn(String isbn) throws BookNotFoundException {
        Query query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :inIsbn");
        query.setParameter("inIsbn", isbn);
        try {
            return (Book) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new BookNotFoundException("Book isbn " + isbn + " does not exist!");
        }
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Book>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
