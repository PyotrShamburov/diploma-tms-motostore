package by.tms.home.service.impl;

import by.tms.home.entity.Announcement;
import by.tms.home.entity.Owner;
import by.tms.home.entity.Role;
import by.tms.home.entity.User;
import by.tms.home.entity.exception.UserAlreadyExistException;
import by.tms.home.entity.exception.UserNotFoundException;
import by.tms.home.repository.UserRepository;
import by.tms.home.service.AnnouncementService;
import by.tms.home.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getByUsername(String username) {
        Optional<User> byUsername = (Optional<User>) userRepository.getByUsername(username);
        if (byUsername.isPresent()) {
            return byUsername.get();
        }
        throw new UserNotFoundException("User with this username not found!");
    }

    @Override
    public boolean saveUserInDatabase(User user) {
        if (!containsUserWithSameUsername(user)) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setRole(Role.USER);
            userRepository.save(user);
            return true;
        }
        throw new UserAlreadyExistException("User with this username already exist!");
    }

    @Override
    public boolean containsUserWithSameUsername(User user) {
        String checkedUsername = user.getUsername();
        return userRepository.existsByUsername(checkedUsername);
    }

    @Override
    public Owner getOwnerFromUser(String username) {
        User byUsername = (User) getByUsername(username);
        Owner owner = new Owner();
        owner.setName(byUsername.getFirstName());
        owner.setCity(byUsername.getCity());
        owner.setPhoneNumber(byUsername.getPhoneNumber());
        return owner;
    }

    @Override
    public void updateUserFirstName(String newUserFirstName, String username) {
        User byUsername = (User) getByUsername(username);
        byUsername.setFirstName(newUserFirstName);
        userRepository.save(byUsername);
    }

    @Override
    public void updateUserPhoneNumber(String username, String newPhoneNumber) {
        User byUsername = (User) getByUsername(username);
        byUsername.setPhoneNumber(newPhoneNumber);
        userRepository.save(byUsername);
    }

    @Override
    public void updateUserEmail(String username, String newEmailAddress) {
        User byUsername = (User) getByUsername(username);
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
        return userRepository.findByUsername(s);
    }
}
