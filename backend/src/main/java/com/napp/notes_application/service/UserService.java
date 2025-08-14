package com.napp.notes_application.service;

import com.napp.notes_application.dto.LoginRequestDto;
import com.napp.notes_application.model.User;
import com.napp.notes_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService{

    final private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public User save(User user){
        return userRepository.save(user);
    }
}
