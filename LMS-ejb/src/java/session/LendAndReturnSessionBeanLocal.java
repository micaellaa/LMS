/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.LendAndReturn;
import exception.BookNotFoundException;
import exception.InputDataValidationException;
import exception.MemberNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author micaella
 */
@Local
public interface LendAndReturnSessionBeanLocal {
    public LendAndReturn retrieveLendAndReturnById(Long id);

    LendAndReturn createNewLendAndReturn(LendAndReturn lend, String identityNo, String isbn) throws MemberNotFoundException, BookNotFoundException, InputDataValidationException;

    public LendAndReturn createNewReturnOnLoan(Long loanId) throws MemberNotFoundException, BookNotFoundException, InputDataValidationException;

    public List<LendAndReturn> getActiveLoans();
    
    public BigDecimal retrieveFineAmountById(Long lendId);
}
