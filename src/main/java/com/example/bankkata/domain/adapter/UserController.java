package com.example.bankkata.domain.adapter;

import com.example.bankkata.domain.model.User;
import com.example.bankkata.domain.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User retrievedUser = userService.getUserById(userId);
        return ResponseEntity.ok(retrievedUser);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    // Nouvelle méthode pour récupérer un utilisateur par email
    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
