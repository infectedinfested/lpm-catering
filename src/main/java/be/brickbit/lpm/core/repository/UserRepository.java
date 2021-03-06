package be.brickbit.lpm.core.repository;

import be.brickbit.lpm.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findBySeatNumber(Integer seatNumber);
}
