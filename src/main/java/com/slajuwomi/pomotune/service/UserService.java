package com.slajuwomi.pomotune.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.slajuwomi.pomotune.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slajuwomi.pomotune.user.User;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/**
 * UserService - Business logic layer for User operations
 * 
 * This service handles all user-related business logic including: - User
 * validation before saving - Coordinating with the repository layer -
 * Converting between different data formats - Enforcing business rules
 */
@Service
public class UserService {

    @Autowired
    private Validator validator;

    // Final field ensures immutable dependency after construction
    private final UserRepository userRepository;

    /**
     * Constructor Injection - Spring's recommended dependency injection approach
     * Spring automatically finds and injects the UserRepository bean
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user in the system
     * 
     * Business logic flow: 1. Validate the user data using Bean Validation
     * annotations 2. If validation passes, save to database via repository 3. If
     * validation fails, throw exception with details
     * 
     * @param newUser The user object to create (from controller request)
     * @return The saved user with generated ID
     * @throws IllegalArgumentException if validation fails
     */
    public User createUser(User newUser) {
        // Step 1: Validate user data using Bean Validation
        // The validator checks all @NotBlank, @Email, @Size annotations on User entity
        Set<ConstraintViolation<User>> violations = validator.validate(newUser);

        // Step 2: Check if there are any validation errors
        if (!violations.isEmpty()) {
            // Build a user-friendly error message from all violations
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<User> violation : violations) {
                sb.append(violation.getMessage()).append(" ");
            }
            // Throw exception that controller will catch and convert to HTTP 400
            throw new IllegalArgumentException("Invalid user data: " + sb.toString());
        }

        // Step 3: Save user to database (this was missing in original code!)
        System.out.println("Saving user: " + newUser.getName());
        return userRepository.save(newUser);
    }

    /**
     * Retrieves all users from the database
     * 
     * @return List of all users (could be empty if no users exist)
     */
    public List<User> getAllUsers() {
        // JpaRepository provides findAll() method automatically
        return userRepository.findAll();
    }

    /**
     * Finds a user by their unique ID
     * 
     * @param id The user ID to search for
     * @return User object if found, null if not found
     */
    public User getUserById(Long id) {
        // findById returns Optional<User> - modern Java way to handle "might not exist"
        Optional<User> userOptional = userRepository.findById(id);

        // Convert Optional to null/User for simpler controller handling
        return userOptional.orElse(null);
    }

    /**
     * Finds a user by their email address
     * 
     * Note: Repository returns List<User> but email should be unique, so we expect
     * 0 or 1 result
     * 
     * @param email The email address to search for
     * @return User object if found, null if not found
     */
    public User getUserByEmail(String email) {
        // Use the custom repository method findByEmail
        List<User> users = userRepository.findByEmail(email);

        // Return first user if found, null if list is empty
        return users.isEmpty() ? null : users.get(0);
    }
}
