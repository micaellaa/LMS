/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Staff;
import exception.InputDataValidationException;
import exception.InvalidLoginCredentialException;
import exception.StaffExistsException;
import exception.StaffNotFoundException;
import exception.UnknownPersistenceException;
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
public class StaffSessionBean implements StaffSessionBeanLocal {
    @PersistenceContext
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public StaffSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Staff retrieveStaffByStaffId(Long staffId) throws StaffNotFoundException {
        Staff staff = em.find(Staff.class, staffId);

        if (staff != null) {
            return staff;
        } else {
            throw new StaffNotFoundException("Staff ID " + staffId + " does not exist!");
        }
    }

    @Override
    public Long createNewStaff(Staff staff) throws StaffExistsException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<Staff>> constraintViolations = validator.validate(staff);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(staff);
                em.flush();

                return staff.getStaffId();
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new StaffExistsException();
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
    public List<Staff> retrieveAllStaffs() {
        Query query = em.createQuery("SELECT s FROM Staff s");
        return query.getResultList();
    }

    @Override
    public Staff retrieveStaffByUsername(String username) throws StaffNotFoundException {
        Query query = em.createQuery("SELECT s FROM Staff s WHERE s.userName = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (Staff) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }
    }

    @Override
    public Staff staffLogin(String username, String password) throws StaffNotFoundException, InvalidLoginCredentialException {
        try {
            System.out.println("staffLogin(): " + username);
            Staff staff = retrieveStaffByUsername(username);

            if (staff.getPassword().equals(password)) {
                // staff.getSaleTransactionEntities().size(); //?
                return staff;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (StaffNotFoundException ex) {
            throw new StaffNotFoundException("Username does not exist or invalid password!");
        } 
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Staff>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
    
}
