package com.example.jobApplication.controller;

import com.example.jobApplication.dto.UserDto;
import com.example.jobApplication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto created = userService.createUser(userDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);

}


    //*POST** | `/api/auth/register` | Create a new user account | Public |
    //| **POST** | `/api/auth/login` | Log in and receive JWT token | Public |
    //| **GET** | `/api/auth/me` | Fetch authenticated user detail | Authorized |
}
