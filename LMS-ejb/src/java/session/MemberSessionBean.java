/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import exception.UnknownPersistenceException;
import entity.Member;
import exception.InputDataValidationException;
import exception.MemberExistsException;
import exception.MemberNotFoundException;
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
public class MemberSessionBean implements MemberSessionBeanLocal {
    @PersistenceContext
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    // for data validation
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public MemberSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Member retrieveMemberByMemberId(Long memberId) throws MemberNotFoundException {
        Member member = em.find(Member.class, memberId);

        if (member != null) {
            return member;
        } else {
            throw new MemberNotFoundException("Member ID " + memberId + " cannot be found.");
        }
    }
    
    @Override
    public Member retrieveMemberByIdentityNo(String identityNo) throws MemberNotFoundException {
        Query query = em.createQuery("SELECT m FROM Member m WHERE m.identityNo = :inIdentityNo");
        query.setParameter("inIdentityNo", identityNo);
        try {
            return (Member) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new MemberNotFoundException("Member with identity number " + identityNo + " does not exist!");
        }
    }
    
    @Override
    public Member createNewMember(Member member) throws MemberExistsException, UnknownPersistenceException, InputDataValidationException {
        Set<ConstraintViolation<Member>> constraintViolations = validator.validate(member);

        if (constraintViolations.isEmpty()) {
            try {
                em.persist(member);
                em.flush();

                return member;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new MemberExistsException();
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
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Member>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
