package com.slajuwomi.pomotune.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slajuwomi.pomotune.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);

}
