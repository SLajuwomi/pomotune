package com.slajuwomi.pomotune.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.slajuwomi.pomotune.user.domain.User;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("save a new user")
    void testSave() {
        User u = new User();
        u.setName("Jack");
        u.setEmail("blank@example.com");
        u.setPassword("password");

        User savedUser = userRepository.save(u);

        assertEquals(u.getName(), savedUser.getName());
        assertEquals(u.getEmail(), savedUser.getEmail());
        assertEquals(u.getPassword(), savedUser.getPassword());

    }

    @Test
    @DisplayName("find user by email")
    void testFindByEmail() {
        User u = new User();
        u.setName("Test User");
        u.setEmail("test@example.com");
        u.setPassword("password");
        userRepository.save(u);

        List<User> foundUsers = userRepository.findByEmail("test@example.com");

        assertEquals(1, foundUsers.size());
        assertEquals("test@example.com", foundUsers.get(0).getEmail());
        assertEquals("Test User", foundUsers.get(0).getName());
    }

    @Test
    @DisplayName("find user by email returns empty list when no match")
    void testFindByEmailReturnsEmptyList() {
        List<User> foundUsers = userRepository.findByEmail("nonexistent@example.com");

        assertTrue(foundUsers.isEmpty());
    }
}
