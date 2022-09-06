package com.pd.authuser.services.impl;

import com.pd.authuser.controllers.UserController;
import com.pd.authuser.dtos.UserDto;
import com.pd.authuser.enums.UserStatus;
import com.pd.authuser.enums.UserType;
import com.pd.authuser.models.UserModel;
import com.pd.authuser.repositories.UserRepository;
import com.pd.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Transactional
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        Page<UserModel> userModelPage = userRepository.findAll(spec, pageable);
        createdLinkHateoas(userModelPage);
        return userModelPage;
    }

    private static void createdLinkHateoas(Page<UserModel> userModelPage) {
        if (!userModelPage.isEmpty()) {
            userModelPage.toList().forEach(user ->
                user.add(linkTo(methodOn(UserController.class).getOneUser(user.getId())).withSelfRel())
            );
        }
    }

    @Override
    public UserModel findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
    }

    @Override
    public void deleteById(UUID userId) {
        findById(userId);
        userRepository.deleteById(userId);

        log.info("User delete successfully - User ID: {}", userId);
    }

    @Override
    public UserModel save(UserDto userDto) {
        existsByUsername(userDto.getUsername());
        existsByEmail(userDto.getEmail());

        UserModel user = new UserModel();
        BeanUtils.copyProperties(userDto, user);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserType(UserType.STUDENT);
        user.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        user = userRepository.save(user);

        log.info("User saved successfully - User ID: {}", user.getId());
        return user;
    }

    @Override
    public UserModel update(UserDto userDto) {
        UserModel user = findById(userDto.getId());
        user.setFullName(userDto.getFullName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setCpf(userDto.getCpf());
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        user = userRepository.save(user);

        log.info("User updated successfully - User ID: {}", user.getId());
        return user;
    }

    @Override
    public void updatePassword(UserDto userDto) {
        UserModel user = findById(userDto.getId());
        checkEqualsPassword(userDto, user);
        user.setPassword(userDto.getPassword());
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userRepository.save(user);

        log.info("User updated password successfully - User ID: {}", user.getId());
    }

    @Override
    public void updateImage(UserDto userDto) {
        UserModel user = findById(userDto.getId());
        user.setImageUrl(userDto.getImageUrl());
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userRepository.save(user);

        log.info("User updated image successfully - User ID: {}", user.getId());
    }

    private static void checkEqualsPassword(UserDto userDto, UserModel user) {
        if (!user.getPassword().equals(userDto.getOldPassword())) {
            log.warn("Error: Mismatched old password! User ID: {}", user.getId());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: Mismatched old password!");
        }
    }

    private void existsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            log.warn("Error: Email [{}] is already taken!", email);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: Email is already taken!");
        }
    }

    private void existsByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            log.warn("Error: Username [{}] is already taken!", username);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error: Username is already taken!");
        }
    }
}
