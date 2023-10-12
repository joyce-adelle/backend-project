package com.crust.backendproject.repositories;

import com.crust.backendproject.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    @Modifying
    @Query("UPDATE user  SET user.password = ?1 WHERE user.email = ?2")
    void updatePasswordByEmail(String password, String email);

    List<User> findAll(Pageable pageable);

}
