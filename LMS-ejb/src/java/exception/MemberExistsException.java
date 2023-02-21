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
public class MemberExistsException extends Exception {

    /**
     * Creates a new instance of <code>MemberExistsException</code> without
     * detail message.
     */
    public MemberExistsException() {
    }

    /**
     * Constructs an instance of <code>MemberExistsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MemberExistsException(String msg) {
        super(msg);
    }
}
