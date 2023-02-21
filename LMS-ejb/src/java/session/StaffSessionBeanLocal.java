/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Staff;
import exception.InputDataValidationException;
import exception.InvalidLoginCredentialException;
import exception.StaffExistsException;
import exception.StaffNotFoundException;
import exception.UnknownPersistenceException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author micaella
 */
@Local
public interface StaffSessionBeanLocal {
    Long createNewStaff(Staff staff) throws StaffExistsException, UnknownPersistenceException, InputDataValidationException;
    
    List<Staff> retrieveAllStaffs();
    
    Staff retrieveStaffByStaffId(Long staffId) throws StaffNotFoundException;
    
    Staff retrieveStaffByUsername(String username) throws StaffNotFoundException;

    Staff staffLogin(String username, String password) throws InvalidLoginCredentialException;

    // void updateStaff(Staff staff) throws StaffNotFoundException, UpdateStaffException, InputDataValidationException;
    
    // void deleteStaff(Long staffId) throws StaffNotFoundException, DeleteStaffException;
}
