/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import entity.Member;
import exception.MemberExistsException;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import session.MemberSessionBeanLocal;

/**
 *
 * @author micaella
 */
@Named(value = "memberManagedBean")
@RequestScoped
public class memberManagedBean {

    @EJB
    private MemberSessionBeanLocal memberSessionLocal;

    private String firstName;
    private String lastName;
    private char gender;
    private int age;
    private String identityNo;
    private String phone;
    private String address;
    
    private List<Member> members;
    private String searchType = "NAME";
    private String searchString;
    
    /**
     * Creates a new instance of memberManagedBean
     */
    public memberManagedBean() {
    }
    
    public void init() {
        /*
        if (searchString == null || searchString.equals("")) {
            members = memberSessionLocal.searchMembers(null);
        } else {
            switch (searchType) {
                case "NAME":
                    members = memberSessionLocal.searchMembers(searchString);
                    break;
                case "IDENTITYNO": {
                    members = memberSessionLocal.retrieveMembersByIdentityNo(searchString);
                    break;
                }
                case "CONTACTNO": {
                    members = memberSessionLocal.retrieveMembersByContact(searchString);
                    break;
                }
            }
        }
        */
    }
    
    public void createNewMember(ActionEvent evt) {
        
        Member member = new Member();
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setGender(gender);
        member.setAge(age);
        member.setIdentityNo(identityNo);
        member.setPhone(phone);
        member.setAddress(address);
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            memberSessionLocal.createNewMember(member);
        } catch(MemberExistsException ex) {
            context.addMessage("memberForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Member identity number already exists"));
            return;
        } catch(Exception ex) {
            context.addMessage("memberForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to register member"));
            return;
        } 
        init();
        context.addMessage("memberForm", new FacesMessage("Success", "Successfully registered member " + member.getFirstName()));
    } //end createNewMember

    public MemberSessionBeanLocal getMemberSessionLocal() {
        return memberSessionLocal;
    }

    public void setMemberSessionLocal(MemberSessionBeanLocal memberSessionLocal) {
        this.memberSessionLocal = memberSessionLocal;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
}
