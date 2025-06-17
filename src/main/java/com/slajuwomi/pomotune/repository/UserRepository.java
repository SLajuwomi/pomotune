package com.slajuwomi.pomotune.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slajuwomi.pomotune.user.domain.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);

}
