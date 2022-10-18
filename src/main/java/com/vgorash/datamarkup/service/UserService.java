package com.vgorash.datamarkup.service;

import com.vgorash.datamarkup.model.Role;
import com.vgorash.datamarkup.model.User;
import com.vgorash.datamarkup.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder encoder){
        this.userRepository = repository;
        this.passwordEncoder = encoder;
    }

    public User getUser(String username){
        Optional<User> userOptional = userRepository.findById(username);
        if(userOptional.isEmpty()){
            return null;
        }
        return userOptional.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = getUser(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public boolean addUser(String username, String password){
        if(userRepository.existsById(username)){
            return false;
        }
        User user = new User(username, passwordEncoder.encode(password));
        user.setAccessor(true);
        userRepository.saveAndFlush(user);
        return true;
    }

}
