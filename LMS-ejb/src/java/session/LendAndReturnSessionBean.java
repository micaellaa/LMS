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
import exception.MemberNotFoundException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author micaella
 */
@Stateless
public class LendAndReturnSessionBean implements LendAndReturnSessionBeanLocal {
    
    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;
    
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
    public LendAndReturn createNewLendAndReturn(LendAndReturn lendAndReturn, Long memberId, Long bookId) throws MemberNotFoundException, BookNotFoundException {
        try {
            em.persist(lendAndReturn);
            
            Member member = memberSessionBean.retrieveMemberByMemberId(memberId);

            lendAndReturn.setMember(member);
            member.addLendAndReturn(lendAndReturn);
            
            Book book = bookSessionBean.retrieveBookByBookId(bookId);
            lendAndReturn.setBook(book);
            book.addLendAndReturn(lendAndReturn);

            em.flush();

            return lendAndReturn;
        } catch (MemberNotFoundException ex) {
            throw new MemberNotFoundException("Unable to record new lend or return as the member record cannot be found.");
        } catch (BookNotFoundException ex) {
            throw new BookNotFoundException("Unable to record new lend or return as the book record cannot be found.");
        } 
    }
    
    
}
