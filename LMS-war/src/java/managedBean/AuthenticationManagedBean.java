/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import entity.Staff;
import exception.InvalidLoginCredentialException;
import exception.StaffNotFoundException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.StaffSessionBeanLocal;

/**
 *
 * @author micaella
 */
@Named(value = "authenticationManagedBean")
@SessionScoped
public class AuthenticationManagedBean implements Serializable {

    @EJB
    private StaffSessionBeanLocal staffSessionLocal;

    private String userName = null;
    private String password = null;
    private Long staffId = (long) -1;

    public AuthenticationManagedBean() {
    }

    public String login() {
        //by right supposed to use a session bean to //do validation here
        //...
        //simulate username/password
        Staff staff = null;
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            /*
            if (userName.equals("user1") && password.equals("password")) {
                //login successful
                //store the logged in user id 
                staffId = 10;
                //do redirect
                return "/secret/secret.xhtml?faces-redirect=true";
            } else {
                //login failed 
                userName = null;
                password = null;
                staffId = -1;
                return "login.xhtml";
            }
             */
            staff = staffSessionLocal.staffLogin(userName, password);
            
        } catch (StaffNotFoundException ex) {
            context.addMessage("loginAttempt", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Staff member can not be found"));
        } catch (InvalidLoginCredentialException ex) {
            context.addMessage("loginAttempt", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Password is invalid"));
        }
        if (staff != null) {
            staffId = staff.getStaffId();
            return "/secret/index.xhtml?faces-redirect=true";
        } else {
            //login failed
            userName = null;
            password = null;
            staffId = (long) -1;
            return "login.xhtml";
        }
    }

    public String logout() {
        userName = null;
        password = null;
        staffId = (long) -1;
        return "/login.xhtml?faces-redirect=true";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }
    
    
}
