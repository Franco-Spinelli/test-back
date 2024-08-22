package com.challenge.challengePlants.Service.User;

import com.challenge.challengePlants.Model.User.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserAuthenticated();
    Optional<User> findByUsername(String username);
}
