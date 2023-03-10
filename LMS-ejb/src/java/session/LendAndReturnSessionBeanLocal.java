/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.LendAndReturn;
import exception.BookNotFoundException;
import exception.BookOnActiveLoanException;
import exception.FineNotPaidException;
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
    
    public void payLoan(Long id);

    LendAndReturn createNewLendAndReturn(LendAndReturn lend, String identityNo, String isbn) throws MemberNotFoundException, BookNotFoundException, InputDataValidationException;
    
    LendAndReturn createNewLend(LendAndReturn lendAndReturn, String identityNo, String isbn) throws MemberNotFoundException, BookNotFoundException, BookOnActiveLoanException, InputDataValidationException;
    
    public LendAndReturn createNewReturnOnLoan(Long loanId) throws MemberNotFoundException, BookNotFoundException, FineNotPaidException, InputDataValidationException;

    public List<LendAndReturn> getActiveLoans();
    
    public BigDecimal retrieveFineAmountById(Long lendId);
}
