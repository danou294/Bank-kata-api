package com.example.bankkata.adapter;

import com.example.bankkata.domain.adapter.UserController;
import com.example.bankkata.domain.model.User;
import com.example.bankkata.domain.model.Role;
import com.example.bankkata.domain.service.User.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        List<Role> roles = Arrays.asList(Role.USER);
        User user = new User("John", "Doe", "motDePasse", "john@example.com", roles);
        when(userService.createUser(any(User.class))).thenReturn(user);
        ResponseEntity<User> response = userController.createUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testUpdateUser() {
        List<Role> roles = Arrays.asList(Role.USER);
        User user = new User("John", "Doe", "motDePasse", "john@example.com", roles);
        when(userService.updateUser(any(User.class))).thenReturn(user);
        ResponseEntity<User> response = userController.updateUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);
        ResponseEntity<Void> response = userController.deleteUser(userId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        List<Role> roles = Arrays.asList(Role.USER);
        User user = new User("John", "Doe", "motDePasse", "john@example.com", roles);
        when(userService.getUserById(userId)).thenReturn(user);
        ResponseEntity<User> response = userController.getUserById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetAllUsers() {
        List<Role> roles = Arrays.asList(Role.USER);
        List<User> userList = Arrays.asList(
            new User("John", "Doe", "motDePasse", "john@example.com", roles),
            new User("Jane", "Doe", "motDePasse", "jane@example.com", roles)
        );
        when(userService.getAllUsers()).thenReturn(userList);
        ResponseEntity
        <Iterable<User>> response = userController.getAllUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void testGetUserByEmail() throws Exception {
        String email = "john@example.com";
        List<Role> roles = Arrays.asList(Role.USER);
        User user = new User("John", "Doe", "motDePasse", email, roles);
        when(userService.getUserByEmail(email)).thenReturn(user);
        ResponseEntity<User> response = userController.getUserByEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
}
