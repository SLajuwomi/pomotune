package com.slajuwomi.pomotune.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.DisplayName;
import com.slajuwomi.pomotune.user.domain.User;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("save a new user")
    void testSave() {
        User u = new User();
        u.setName("Jack");
        u.setEmail("blank@example.com");
        u.setId(2L);
        u.setPassword("password");

        userRepository.save(u);

        User savedUser = userRepository.findById(2L).orElseThrow();
        assertEquals(u.getName(), savedUser.getName());
        assertEquals(u.getEmail(), savedUser.getEmail());
        assertEquals(u.getPassword(), savedUser.getPassword());

    }
}
