package ma.enset.hospitalapp.security.service;

import ma.enset.hospitalapp.security.entities.AppRole;
import ma.enset.hospitalapp.security.entities.AppUser;

import javax.xml.transform.sax.SAXResult;

public interface Accountservice {
     AppUser addUser(String username, String password, String email, String confirmPassword);
     AppRole addNewRole(String role);
     void addRoleToUser(String username, String role);
     void removeRoleFromUser(String username, String role);
     AppUser loadUserByUsername(String username);
}
