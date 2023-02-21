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
public class BookExistsException extends Exception {

    /**
     * Creates a new instance of <code>BookExistsException</code> without detail
     * message.
     */
    public BookExistsException() {
    }

    /**
     * Constructs an instance of <code>BookExistsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BookExistsException(String msg) {
        super(msg);
    }
}
