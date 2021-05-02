package by.tms.home.service.impl;

import by.tms.home.entity.Owner;
import by.tms.home.entity.Role;
import by.tms.home.entity.User;
import by.tms.home.entity.exception.UserAlreadyExistException;
import by.tms.home.entity.exception.UserNotFoundException;
import by.tms.home.repository.UserRepository;
import by.tms.home.service.AnnouncementService;
import by.tms.home.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getByUsername(String username) {
        Optional<User> byUsername = (Optional<User>) userRepository.getByUsername(username);
        if (byUsername.isPresent()) {
            log.warn("User with username ["+username+"] found!");
            return byUsername.get();
        }
        log.warn("User with username ["+username+"] not found!");
        throw new UserNotFoundException("User with this username not found!");
    }

    @Override
    public boolean saveUserInDatabase(User user) {
        if (!containsUserWithSameUsername(user)) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setRole(Role.USER);
            userRepository.save(user);
            log.info("New saved user in database: "+user);
            return true;
        }
        log.warn("User with username ["+user.getUsername()+"] already exist!");
        throw new UserAlreadyExistException("User with this username already exist!");
    }

    @Override
    public boolean containsUserWithSameUsername(User user) {
        String checkedUsername = user.getUsername();
        log.info("Contains user by username: "+checkedUsername);
        return userRepository.existsByUsername(checkedUsername);
    }

    @Override
    public Owner getOwnerFromUser(String username) {
        User byUsername = (User) getByUsername(username);
        Owner owner = new Owner();
        owner.setName(byUsername.getFirstName());
        owner.setCity(byUsername.getCity());
        owner.setPhoneNumber(byUsername.getPhoneNumber());
        log.info("New owner from user: "+owner);
        return owner;
    }

    @Override
    public void updateUserFirstName(String newUserFirstName, String username) {
        User byUsername = (User) getByUsername(username);
        log.info("User for change first name: "+byUsername);
        log.info("New first name: "+newUserFirstName);
        byUsername.setFirstName(newUserFirstName);
        userRepository.save(byUsername);
    }

    @Override
    public void updateUserPhoneNumber(String username, String newPhoneNumber) {
        User byUsername = (User) getByUsername(username);
        log.info("User for change phone number: "+byUsername);
        log.info("New phone number: "+newPhoneNumber);
        byUsername.setPhoneNumber(newPhoneNumber);
        userRepository.save(byUsername);
    }

    @Override
    public void updateUserEmail(String username, String newEmailAddress) {
        User byUsername = (User) getByUsername(username);
        log.info("User for change email: "+byUsername);
        log.info("New email address: "+newEmailAddress);
        byUsername.setEmail(newEmailAddress);
        userRepository.save(byUsername);
    }

    @Override
    public boolean updateUserPassword(String username, String oldPassword, String newPassword) {
        User byUsername = (User) getByUsername(username);
        String encodedPasswordFromDatabase = (String) byUsername.getPassword();
        log.info("encoded password from database = "+encodedPasswordFromDatabase);
        boolean oldPassEqualsEnteredPass = (boolean) passwordEncoder.matches(oldPassword, encodedPasswordFromDatabase);
        if (oldPassEqualsEnteredPass) {
            log.info("Encoded password from DB equals to old entered password!");
            String encodedNewPassword = (String) passwordEncoder.encode(newPassword);
            log.info("New encoded password ="+encodedNewPassword);
            byUsername.setPassword(encodedNewPassword);
            userRepository.save(byUsername);
            log.info("User "+byUsername+" saved");
            return true;
        }
        log.warn("Old password and entered old password NOT equals!");
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("Loaded user by username for detail service: "+s);
        return userRepository.findByUsername(s);
    }
}
