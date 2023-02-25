/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Member;
import exception.BookNotFoundException;
import exception.InputDataValidationException;
import exception.MemberExistsException;
import exception.MemberNotFoundException;
import exception.UnknownPersistenceException;
import javax.ejb.Local;

/**
 *
 * @author micaella
 */
@Local
public interface MemberSessionBeanLocal {
    public Member retrieveMemberByMemberId(Long memberId) throws MemberNotFoundException, BookNotFoundException ;
    public Member createNewMember(Member member) throws MemberExistsException, UnknownPersistenceException, InputDataValidationException;
}
