package Aplikacja.dao;
 
import java.util.List;

import Aplikacja.model.User;


public interface UserDAO extends GenericDAO<User, Long> {
 
    List<User> getAll();
    User getUserByUsername(String username);
     
}