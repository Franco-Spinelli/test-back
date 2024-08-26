package com.challenge.challengePlants.Service.User;

import com.challenge.challengePlants.Model.User.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserAuthenticated();
    Optional<User> findByUsername(String username);
    Map<String,String> getUserInformation();
}
