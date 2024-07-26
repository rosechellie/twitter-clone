package com.twitterclone.repository;

import java.util.List;
import java.util.Optional;

import com.twitterclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAll();

}
