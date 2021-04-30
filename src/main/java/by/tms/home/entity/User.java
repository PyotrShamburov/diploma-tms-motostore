package by.tms.home.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.*;

@Getter
@Setter
@ToString(exclude = "announcements")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Pattern(regexp = "^[A-Za-z]{3,15}[0-9]{0,5}$",
            message = "Wrong format! Use characters (3 - 15) and numbers(0 - 5)!")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "Min 8 symbols, min 1 upper and lower case, without whitespace!")
    private String password;

    @Pattern(regexp = "^[A-Z]?[a-z]{3,15}$",
            message = "Wrong format! Only characters(3 - 15)!")
    private String firstName;

    @Pattern(regexp = "[A-Za-z0-9._%+-]{2,10}@[A-Za-z0-9.-]{3,6}\\.[A-Za-z]{2,4}",
            message = "Invalid email address!")
    private String email;
    @Pattern(regexp = "^(\\+\\d{12})|(\\d{11})$", message = "Invalid phone number format!")
    private String phoneNumber;
    @Pattern(regexp = "^[A-Z]?[a-z]{3,15}$", message = "Wrong format! Only characters(3 - 15)!")
    private String city;
    @Enumerated(EnumType.STRING)
    private Role role;
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Announcement> announcements;

    public User(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
