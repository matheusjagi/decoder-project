package com.pd.authuser.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.pd.authuser.dtos.UserDto;
import com.pd.authuser.models.UserModel;
import com.pd.authuser.services.UserService;
import com.pd.authuser.specifications.SpecificationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                       @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserModel> userModelPage = userService.findAll(spec, pageable);
        return ResponseEntity.ok(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserModel> getOneUser(@PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        userService.deleteById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully.");
    }

    @PutMapping
    public ResponseEntity<UserModel> updateUser(@RequestBody
                                                @Validated(UserDto.UserView.UserPut.class)
                                                @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {
        return ResponseEntity.ok(userService.update(userDto));
    }

    @PutMapping("/password")
    public ResponseEntity<Object> updatePassword(@RequestBody
                                                 @Validated(UserDto.UserView.PasswordPut.class)
                                                 @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {
        userService.updatePassword(userDto);
        return ResponseEntity.ok().body("Password updated successfully");
    }

    @PutMapping("/image")
    public ResponseEntity<Object> updateImage(@RequestBody
                                              @Validated(UserDto.UserView.ImagePut.class)
                                              @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
        userService.updateImage(userDto);
        return ResponseEntity.ok().body("Image updated successfully");
    }
}
