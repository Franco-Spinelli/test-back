package com.challenge.challengePlants.Service.User;

import com.challenge.challengePlants.Model.User.User;
import com.challenge.challengePlants.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //Find user authenticated in  the moment
        String username = authentication.getName();
        return findByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Map<String, String> getUserInformation() {
        User user = getUserAuthenticated().orElseThrow(()->  new IllegalStateException("Unauthenticated user"));
        Map<String,String> userMap = new HashMap<>();
        userMap.put("name", user.getFirstname()+ " " + user.getLastname());
        return userMap;
    }
}