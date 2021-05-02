package by.tms.home.service.impl;

import by.tms.home.entity.Announcement;
import by.tms.home.entity.Owner;
import by.tms.home.entity.Role;
import by.tms.home.entity.User;
import by.tms.home.repository.UserRepository;
import by.tms.home.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private User user;

    @BeforeEach
    void setUp() {
        List<Announcement> ads = new ArrayList<>();
        ads.add(Mockito.mock(Announcement.class));
        user = new User(1, "username", "Burov6011", "Peter", "Burov6011@gmail.com", "+375295117730", "Minsk",
                Role.USER, ads);
    }

    @Test
    @Order(3)
    void getByUsername() {
        User actualUser = (User) userService.getByUsername(user.getUsername());
        actualUser.setPassword("Burov6011");
        assertEquals(user, actualUser);
    }

    @Test
    @Order(1)
    void saveUserInDatabase() {
        boolean actualResult = (boolean) userService.saveUserInDatabase(user);
        assertTrue(actualResult);
    }

    @Test
    @Order(2)
    void containsUserWithSameUsername() {
        boolean actualResult = (boolean) userService.containsUserWithSameUsername(user);
        assertTrue(actualResult);
    }

    @Test
    @Order(4)
    void getOwnerFromUser() {
        Owner expectedOwner = new Owner(0,"Peter", "+375295117730", "Minsk" );
        Owner actualOwner = (Owner) userService.getOwnerFromUser(user.getUsername());
        assertEquals(expectedOwner, actualOwner);
    }

    @Test
    @Order(5)
    void updateUserFirstName() {
        String expectedName = "Alex";
        userService.updateUserFirstName(expectedName, user.getUsername());
        Optional<User> byId = (Optional<User>) userRepository.findById(user.getId());
        User actualUser = new User();
        if (byId.isPresent()) {
            actualUser = byId.get();
        }
        assertEquals(expectedName, actualUser.getFirstName());
    }

    @Test
    @Order(6)
    void updateUserPhoneNumber() {
        String expectedPhone = "80295117730";
        userService.updateUserPhoneNumber(user.getUsername(), expectedPhone);
        Optional<User> byId = (Optional<User>) userRepository.findById(user.getId());
        User actualUser = new User();
        if (byId.isPresent()) {
            actualUser = byId.get();
        }
        assertEquals(expectedPhone, actualUser.getPhoneNumber());
    }

    @Test
    @Order(7)
    void updateUserEmail() {
        String expectedEmail = "petro-92@mail.ru";
        userService.updateUserEmail(user.getUsername(), expectedEmail);
        Optional<User> byId = (Optional<User>) userRepository.findById(user.getId());
        User actualUser = new User();
        if (byId.isPresent()) {
            actualUser = byId.get();
        }
        assertEquals(expectedEmail, actualUser.getEmail());
    }

    @Test
    @Order(8)
    void updateUserPassword() {
        String oldPassword = "Burov6011";
        String newPassword = "Burov611134";
        boolean actualResult = (boolean) userService.updateUserPassword(user.getUsername(), oldPassword, newPassword);
        assertTrue(actualResult);
    }
}