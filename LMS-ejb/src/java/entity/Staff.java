/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author micaella
 */
@Entity
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;
    
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (staffId != null ? staffId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the staffId fields are not set
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.staffId == null && other.staffId != null) || (this.staffId != null && !this.staffId.equals(other.staffId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Staff[ id=" + staffId + " ]";
    }
    
}
