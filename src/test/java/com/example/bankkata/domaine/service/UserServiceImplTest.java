package com.example.bankkata.domaine.service;

import com.example.bankkata.domain.model.Account;
import com.example.bankkata.domain.model.Role;
import com.example.bankkata.domain.model.User;
import com.example.bankkata.domain.port.UserRepository;
import com.example.bankkata.domain.service.Account.AccountService;
import com.example.bankkata.domain.service.User.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    AccountService accountService;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        List<Role> roles = Arrays.asList(Role.USER);
        User user = new User("John", "Doe", "motDePasse", "john@example.com", roles);

        // Mocking the creation of an account
        Account account = new Account(0.0, false, 0.0);
        when(accountService.createAccount(0.0, false, 0.0)).thenReturn(account);

        // Mocking the saving of the user
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Calling the createUser method
        User createdUser = userService.createUser(user);

        // Verifying the result
        assertNotNull(createdUser);
        assertEquals(user, createdUser);
    }

    @Test
    void testUpdateUser() {
        List<Role> roles = Arrays.asList(Role.USER);
        User user = new User("John", "Doe", "motDePasse", "john@example.com", roles);
        user.setId(1L);

        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals(user, updatedUser);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(userId));
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        List<Role> roles = Arrays.asList(Role.USER);
        User user = new User("John", "Doe", "motDePasse", "john@example.com", roles);
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUserById(userId);

        assertNotNull(retrievedUser);
        assertEquals(user, retrievedUser);
    }

    @Test
    void testGetAllUsers() {
        List<Role> roles = Arrays.asList(Role.USER);
        List<User> userList = Arrays.asList(
                new User("John", "Doe", "motDePasse", "john@example.com", roles),
                new User("Jane", "Doe", "motDePasse", "jane@example.com", roles));

        when(userRepository.findAll()).thenReturn(userList);

        Iterable<User> allUsers = userService.getAllUsers();

        assertNotNull(allUsers);
        assertEquals(userList, allUsers);
    }

    @Test
    void testGetUserByEmail() {
        String email = "john@example.com";
        List<Role> roles = Arrays.asList(Role.USER);
        User expectedUser = new User("John", "Doe", "motDePasse", email, roles);

        when(userRepository.findByEmail(eq(email))).thenReturn(Optional.of(expectedUser));

        try {
            User actualUser = userService.getUserByEmail(email);
            assertNotNull(actualUser, "The user should not be null");
            assertEquals(expectedUser, actualUser, "The retrieved user should match the expected one");
        } catch (Exception e) {
            fail("No exception should be thrown for existing users");
        }
    }

    @Test
    void testGetUserByEmailNotFound() {
        String email = "missing@example.com";
        when(userRepository.findByEmail(eq(email))).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            userService.getUserByEmail(email);
        }, "Expected to throw, but did not");

        assertTrue(exception.getMessage().contains("No user found with email"), "The exception message should indicate the missing user");
    }
}
