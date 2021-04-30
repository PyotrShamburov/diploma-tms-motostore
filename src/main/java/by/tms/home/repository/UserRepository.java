package by.tms.home.repository;

import by.tms.home.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByUsername(String username);
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
