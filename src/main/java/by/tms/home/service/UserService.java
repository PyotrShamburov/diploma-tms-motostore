package by.tms.home.service;

import by.tms.home.entity.Announcement;
import by.tms.home.entity.Owner;
import by.tms.home.entity.User;
import by.tms.home.entity.exception.UserAlreadyExistException;
import by.tms.home.entity.exception.UserNotFoundException;

import java.security.Principal;

public interface UserService {
    User getByUsername(String username) throws UserNotFoundException;
    boolean saveUserInDatabase(User user);
    boolean containsUserWithSameUsername(User user) throws UserAlreadyExistException;
    Owner getOwnerFromUser(String username);
    void updateUserFirstName(String newUserFirstName, String oldFirstName);
    void updateUserPhoneNumber(String username, String newPhoneNumber);
    void updateUserEmail(String username, String newEmailAddress);
    boolean updateUserPassword(String username, String oldPassword, String newPassword);
}
