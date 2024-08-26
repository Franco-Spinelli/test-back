package com.challenge.challengePlants.Controller;

import com.challenge.challengePlants.Service.User.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("get-user")
    public ResponseEntity<?>getUser(){
        return ResponseEntity.ok(userService.getUserInformation());
    }
}
