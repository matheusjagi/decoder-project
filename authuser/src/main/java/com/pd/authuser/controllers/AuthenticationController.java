package com.pd.authuser.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.pd.authuser.dtos.UserDto;
import com.pd.authuser.models.UserModel;
import com.pd.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserModel> registerUser(@RequestBody
                                                  @Validated(UserDto.UserView.RegistrationPost.class)
                                                  @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
        return new ResponseEntity<>(userService.save(userDto), HttpStatus.CREATED);
    }
}
