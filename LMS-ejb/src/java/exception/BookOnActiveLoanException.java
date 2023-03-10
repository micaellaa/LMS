/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author micaella
 */
public class BookOnActiveLoanException extends Exception {

    /**
     * Creates a new instance of <code>BookOnActiveLoanException</code> without
     * detail message.
     */
    public BookOnActiveLoanException() {
    }

    /**
     * Constructs an instance of <code>BookOnActiveLoanException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BookOnActiveLoanException(String msg) {
        super(msg);
    }
}
