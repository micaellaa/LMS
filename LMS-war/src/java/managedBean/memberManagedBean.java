/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import entity.Member;
import exception.MemberExistsException;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
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
    
    /**
     * Creates a new instance of memberManagedBean
     */
    public memberManagedBean() {
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
        
        try {
            memberSessionLocal.createNewMember(member);
        } catch(MemberExistsException ex) {
            //
        } catch(Exception ex) {
            //
        } 
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
