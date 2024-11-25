package com.example.cafe.service;

import com.example.cafe.model.User;
import com.example.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User editUser(Long id, User new_user) {
        Optional<User> old = userRepository.findById(id);
        User old_user = old.get();
        old_user.setAdmin(new_user.isAdmin());
        old_user.setEmail(new_user.getEmail());
        old_user.setPassword(new_user.getPassword());
        old_user.setUsername(new_user.getUsername());
        old_user.setPhone(new_user.getPhone());
        return userRepository.save(old_user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
