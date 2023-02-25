/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.LendAndReturn;
import exception.BookNotFoundException;
import exception.MemberNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author micaella
 */
@Local
public interface LendAndReturnSessionBeanLocal {
    LendAndReturn createNewLendAndReturn(LendAndReturn lend, Long memberId, Long bookId) throws MemberNotFoundException, BookNotFoundException;
}
