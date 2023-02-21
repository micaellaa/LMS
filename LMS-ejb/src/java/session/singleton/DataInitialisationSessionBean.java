/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.singleton;

import entity.Book;
import entity.Member;
import entity.Staff;
import exception.BookExistsException;
import exception.InputDataValidationException;
import exception.MemberExistsException;
import exception.StaffExistsException;
import exception.UnknownPersistenceException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.BookSessionBeanLocal;
import session.MemberSessionBeanLocal;
import session.StaffSessionBeanLocal;

/**
 *
 * @author micaella
 */
@Singleton
@LocalBean
@Startup

public class DataInitialisationSessionBean {

    @EJB
    private MemberSessionBeanLocal memberSessionBean;

    @EJB
    private BookSessionBeanLocal bookSessionBean;

    @EJB
    private StaffSessionBeanLocal staffSessionBean;
    
    @PersistenceContext
    private EntityManager em;
    
    public DataInitialisationSessionBean() {
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PostConstruct
    public void postConstruct() {
        if(em.find(Staff.class, 1l) == null) {
            initialiseData();
        }
    }
    
    private void initialiseData() {
        try {
           // create staff
           staffSessionBean.createNewStaff(new Staff("Eric", "Some", "eric", "password"));
           staffSessionBean.createNewStaff(new Staff("Sarah", "Brightman", "sarah", "password"));
           // create books
           bookSessionBean.createNewBook(new Book("Anna Karenina", "0451528611", "Leo Tolstoy"));
           bookSessionBean.createNewBook(new Book("Madame Bovary", "979-8649042031", "Gustave Flaubert"));
           bookSessionBean.createNewBook(new Book("Hamlet", "1980625026", "William Shakespeare"));
           bookSessionBean.createNewBook(new Book("The Hobbit", "9780007458424", "J R R Tolkien"));
           bookSessionBean.createNewBook(new Book("Great Expectations", "1521853592", "Charles Dickens"));
           bookSessionBean.createNewBook(new Book("Pride and Prejudice", "979-8653642272", "Jane Austen"));
           bookSessionBean.createNewBook(new Book("Wuthering Heights", "3961300224", "Emily Brontë"));
           // create members
           memberSessionBean.createNewMember(new Member("Tony", "Shade", 'M', 31, "S8900678A", "83722773", "13 Jurong East, Ave 3"));
           memberSessionBean.createNewMember(new Member("Dewi", "Tan", 'F', 35, "S8581028X", "94602711", "15 Computing Dr"));
        } catch (StaffExistsException | BookExistsException | MemberExistsException | UnknownPersistenceException | InputDataValidationException ex) {
           ex.printStackTrace();
        }
    }
}
