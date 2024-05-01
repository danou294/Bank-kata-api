package com.example.bankkata.domain.service.User;

import com.example.bankkata.domain.model.Account;
import com.example.bankkata.domain.model.User;
import com.example.bankkata.domain.port.UserRepository;
import com.example.bankkata.domain.service.Account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    @Override
    public User createUser(User user) {
        String hashedMotDePasse = passwordEncoder.encode(user.getMotDePasse());
        user.setMotDePasse(hashedMotDePasse);

        // Créer un compte pour l'utilisateur
        Account account = accountService.createAccount();
        user.setAccount(account);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (userRepository.existsById(user.getId())) {
            String hashedMotDePasse = passwordEncoder.encode(user.getMotDePasse());
            user.setMotDePasse(hashedMotDePasse);
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + user.getId());
        }
    }

    @Override
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("No user found with email " + email));
    }


}
