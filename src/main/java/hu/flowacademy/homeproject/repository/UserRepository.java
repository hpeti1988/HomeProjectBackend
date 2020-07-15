package hu.flowacademy.homeproject.repository;

import hu.flowacademy.homeproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    User findByEmail(String email);
    
    Optional <User> findFirstByUsername(String name);

    User findByUsername(String username);



}
