package com.minden.ai.scm.repository;

import com.minden.ai.scm.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author mahendrasridayarathna
 * @created 29/04/2024 - 1:12 pm
 * @project IntelliJ IDEA
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
